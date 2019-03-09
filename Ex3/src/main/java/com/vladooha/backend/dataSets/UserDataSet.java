package com.vladooha.backend.dataSets;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserDataSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


//    public void setId(long id) {
//        this.id = id;
//    }

    public void setLogin(String login) {
        /*
        if (login == null || login.length() < 6) {
            return false;
        } else {
            this.login = login;

            return true;
        }
        */

        this.login = login;
    }

    public void setEmail(String email) {
        /*
        if (email == null || email.length() < 8 || !email.contains("@")) {
            return false;
        } else {
            this.email = email;

            return true;
        }
        */

        this.email = email;
    }

    public void setPassword(String password) {
        /*
        if (password == null || password.length() < 6) {
            return false;
        } else {
            this.password = password;

            return true;
        }
        */

        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
