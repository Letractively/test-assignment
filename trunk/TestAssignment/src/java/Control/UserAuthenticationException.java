package Control;

/**
 *
 * Класс - ошибка авторизации.
 * @author Роман
 */
public class UserAuthenticationException extends Exception{

    public UserAuthenticationException() {
    }

    public UserAuthenticationException(String message) {
        super(message);
    }
    
}
