/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author Роман
 */
public class WebEngine {
    /**
     * Аутентификация пользователя в системе.
     *  проверка логина и пароля
     * @throws UserAuthenticationException 
     * @return обьект пользователя
     */
    public static WebUser Authenticate(String lodin, String password) throws UserAuthenticationException{
        //
        return new WebUser();
    }
    
    
    /**
     * Авторизация пользователя в системе. 
     * установление куки, запись пользователя в сесиию
     */
    public static void Avtorizare(){
        //
    }
    
    
    /**
     * выход из системы, удаление куки.
     */
    public static void Deavtorizare(){
        //
    }
    
}
