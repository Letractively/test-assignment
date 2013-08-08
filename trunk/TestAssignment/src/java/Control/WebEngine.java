package Control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Роман
 */
public class WebEngine {
    /**
     * Список авторизованых на данный момент пользователей
     */
    private static Map<String,WebUser> usersMap = new HashMap<String, WebUser>();
    
    
    /**
     * идентификация пользователя в системе.
     * проверка атрибута сессии.Проверка куки пользователя. Проверка сушествования такого пользователя в памяти
     */
    public static void Identification() throws UserAuthenticationException{
        if()
    }
    
    /**
     * Аутентификация пользователя в системе.
     *  проверка логина и пароля
     * @throws UserAuthenticationException 
     * @return обьект пользователя
     */
    public static void Authenticate(String login, String password, HttpServletResponse response, HttpSession session) throws UserAuthenticationException{
        WebUser result;
        String hashPassword;
        if (!usersMap.containsKey(login)){
            throw new UserAuthenticationException("Пользователь с таким логином не зарегистрирован. Зарегестрируйтесь пожалусйта!");
        }
        hashPassword = WebEngine.getHash(password);
        if (!usersMap.get(login).getPasswordHash().equals(hashPassword)){
            throw new UserAuthenticationException("Пароль не верный");
        }
        
        Avtorizare(login, hashPassword, response, session);
    }
    
    
    /**
     * Авторизация пользователя в системе. 
     * установление куки, запись пользователя в сесиию
     */
    public static void Avtorizare(String login, String passwordHash, HttpServletResponse response, HttpSession session) throws UserAuthenticationException{
        //проверка на существование пользователя
        if (!usersMap.containsKey(login)){
            throw new UserAuthenticationException("Пользователь с таким логином не зарегистрирован. Зарегестрируйтесь пожалусйта!");
        }
        //установление куки
        Cookie loginCookie = new Cookie("login", login);
        loginCookie.setMaxAge(-1); // -1 кука будет храниться до закрытия браузера
        response.addCookie(loginCookie);
        Cookie passwordCookie =  new Cookie("passwordHash", passwordHash);
        passwordCookie.setMaxAge(-1); // -1 кука будет храниться до закрытия браузера
        response.addCookie(passwordCookie);
        // установление отметку в сессии о том, что авторизация прошла успешно
        session.setAttribute("login", true);
    }
    
    
    /**
     * выход из системы, удаление куки, удаление отметки из сессии
     */
    public static void Deavtorizare(HttpServletResponse response, HttpSession session){
        //установление куки
        Cookie loginCookie = new Cookie("login", "");
        loginCookie.setMaxAge(0); // 0 кука будет удалена
        response.addCookie(loginCookie);
        Cookie passwordCookie =  new Cookie("passwordHash", "");
        passwordCookie.setMaxAge(0); // 0 кука будет удалена
        response.addCookie(passwordCookie);
        // установление отметку в сессии о том, что пользовательская сессия закончена (не веб сессия)
        session.setAttribute("login", false);
    }
    
    /**
     * регистрация пользователя в системе
     * @param user пользователь который будет зарегистрирован
     */
    public static void RegisterWebUser(WebUser user){
        usersMap.put(user.getLogin(), user);
    }
    
    /**
     * выполнение односторонней хеш функции
     * в данном случае взята MD5
     */
    public static String getHash(String inputString){
        try {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] array = md.digest(inputString.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
       }
        return sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
    }
    return null;
    }
}
