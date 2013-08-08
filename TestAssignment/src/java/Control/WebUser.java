package Control;

/**
 * класс - Веб пользователь. 
 * @author Роман
 */
public class WebUser {
   
    private String login;
    
    /**
     * хеш функция от паролья пользователя.
     * безопаснее хранить хеш от паролья, нежели сам пароль
     */
    private String passwordHash;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public WebUser(String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object obj) {
        WebUser opponent = (WebUser)obj;
        return this.login.equals(opponent.getLogin());
    }
    
    
    
   
}
