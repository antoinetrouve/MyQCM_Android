package fr.iia.cdsmat.myqcm.entity;

import java.util.Date;
import java.util.ArrayList;

/**
 * Class managing User object
 * @author Antoine Trouve antoinetrouve.france@gmail.com
 * @version 1.0 - 04/04/2016
 */
public class User {

    //region ATTRIBUTE
    private int     Id;
    private int     IdServer;
    private String  Username;
    private String  Email;
    private String  Password;
    private Date    LastLogin;
    private Date    CreatedAt;
    private Date    UpdatedAt;

    private Team    Team;
    private ArrayList<Mcq> Mcqs;
    //endregion

    //region GETTER and SETTER
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdServer() {
        return IdServer;
    }

    public void setIdServer(int idServer) {
        IdServer = idServer;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Date getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        LastLogin = lastLogin;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    public Team getTeam() {
        return Team;
    }

    public void setTeam(Team team) {
        Team = team;
    }

    public ArrayList<Mcq> getMcqs() {
        return Mcqs;
    }

    public void setMcqs(ArrayList<Mcq> mcqs) {
        Mcqs = mcqs;
    }

    //endregion

    //region CONSTRUCTOR
    public User(int id, int idServer, String username, String password, String email, Date lastLogin, Date createdAt, Date updatedAt) {
        Id = id;
        IdServer = idServer;
        Username = username;
        Password = password;
        Email    = email;
        LastLogin = lastLogin;
        UpdatedAt = updatedAt;
        CreatedAt = createdAt;
    }
    //endregion

    //region METHOD
    @Override
    public String toString() {
        return "User{" +
                "Username='" + Username + '\'' +
                ", Email='" + Email + '\'' +
                ", LastLogin=" + LastLogin +
                ", Team=" + Team +
                '}';
    }


    //endregion
}
