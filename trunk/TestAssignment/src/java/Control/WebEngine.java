package Control;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Роман
 */
public class WebEngine {
    /**
     * Имя атрибута, под которым в session будет храниться true или false, которое
     * определяет начата ли пользовательсякая сессия
     */
    static final String SESSION_ATTRIBUTE_NAME="login";
    /**
     * имя куки, в которую будет сохраняться логин
     */
    static final String COOKIE_LOGIN_NAME="login";
    /**
     * омя куки, в которую будет сохраняться хеш от пароль(в кукки будем хранить хеш от пароля, не сам пароль)
     */
    static final String COOKIE_PASSWORDHASH_NAME="passwordHash";
    
    /**
     * Список авторизованых на данный момент пользователей
     * ключ - логин
     * обект - обьект WebUser
     */
    private static Map<String,WebUser> usersMap = new HashMap<String, WebUser>();
    
    
    /**
     * идентификация пользователя в системе.
     * проверка атрибута сессии.Проверка куки пользователя. Проверка сушествования такого пользователя в памяти
     */
    public static void Identification(HttpSession session, Cookie[] cookies, HttpServletResponse response) throws UserAuthenticationException{
        Cookie cookieLogin = null;
        Cookie cookiePasswordHash = null;
        //сначала определяем начата ли наша сессия поверх Http
        if(session.getAttribute(SESSION_ATTRIBUTE_NAME)!=null){
            if (((Boolean)session.getAttribute(SESSION_ATTRIBUTE_NAME))==Boolean.TRUE){
                //все нормально, сессия жива, пользователь определен.
                return;
            }
        }
        //ссесия не начата, возможно пользователь закрыл браузер
        //проверяем, возможно есть сохранненные куки(была нажата кнопка "запомнить меня").
        for (Cookie cookie : cookies) {
            if(cookie.getName().equalsIgnoreCase(COOKIE_LOGIN_NAME)){
                cookieLogin = cookie;
            }
            if(cookie.getName().equalsIgnoreCase(COOKIE_PASSWORDHASH_NAME)){
                cookiePasswordHash = cookie;
            }            
        }
        //проверяем куки
        if(cookieLogin!=null & cookiePasswordHash!=null){
            //куки есть, теперь проверяем, зарегистрирован ли такой пользователь c таким паролем в системе
            if (!usersMap.containsKey(cookieLogin.getValue())){
                if (usersMap.get(cookieLogin.getValue()).getPasswordHash().equals(cookiePasswordHash.getValue())){
                    //все нормлаьно, куки те, авторизуеся по кукам
                    Avtorizare(cookieLogin.getValue(), cookiePasswordHash.getValue(), response, session);
                }else{
                    throw new UserAuthenticationException("неверный пароль в Cookie");
                }
            }
            throw new UserAuthenticationException("неверные данные в Cookie");
        }
        throw new UserAuthenticationException("Авторизуйтесь пожалуйста");
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
        Cookie loginCookie = new Cookie(COOKIE_LOGIN_NAME, login);
        loginCookie.setMaxAge(Integer.MAX_VALUE); // кука будет храниться постоянно
        response.addCookie(loginCookie);
        Cookie passwordCookie =  new Cookie(COOKIE_PASSWORDHASH_NAME, passwordHash);
        passwordCookie.setMaxAge(Integer.MAX_VALUE); // -1 кука будет храниться постоянно
        response.addCookie(passwordCookie);
        // установление отметку в сессии о том, что авторизация прошла успешно
        session.setAttribute(SESSION_ATTRIBUTE_NAME, true);
    }
    
    
    /**
     * выход из системы, удаление куки, удаление отметки из сессии
     */
    public static void Deavtorizare(HttpServletResponse response, HttpSession session){
        //установление куки
        Cookie loginCookie = new Cookie(COOKIE_LOGIN_NAME, "");
        loginCookie.setMaxAge(0); // 0 кука будет удалена
        response.addCookie(loginCookie);
        Cookie passwordCookie =  new Cookie(COOKIE_PASSWORDHASH_NAME, "");
        passwordCookie.setMaxAge(0); // 0 кука будет удалена
        response.addCookie(passwordCookie);
        // установление отметку в сессии о том, что пользовательская сессия закончена (не веб сессия)
        session.setAttribute(SESSION_ATTRIBUTE_NAME, false);
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
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");//выбераем алгоритм шифрования
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
