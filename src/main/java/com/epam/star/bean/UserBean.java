package com.epam.star.bean;

import com.epam.star.dao.ClientDao;
import com.epam.star.entity.Client;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

    private String login;
    private String password;
    private Client user;

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Inject
    private ClientDao clientDao;

    public String doLogin(){

        try{
            user = clientDao.findByCredentials(login, password);
            return "LogOk";
        }catch (Exception e){
            return "home";
        }

    }

}
