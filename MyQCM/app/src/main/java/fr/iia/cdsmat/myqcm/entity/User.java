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
    /**
     * Get id's user attribute
     * @return int
     */
    public int getId() {
        return Id;
    }

    /**
     * Set id's user attribute
     * @return int
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Get WebServer user's id attribute
     * @return int
     */
    public int getIdServer() {
        return IdServer;
    }

    /**
     * Set WebServer user's id attribute
     * @return int
     */
    public void setIdServer(int idServer) {
        IdServer = idServer;
    }

    /**
     * Get username attribute
     * @return username
     */
    public String getUsername() {
        return Username;
    }

    /**
     * Set username attribute
     * @return username
     */
    public void setUsername(String username) {
        Username = username;
    }

    /**
     * Get email attribute
     * @return string
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Set email attribute
     * @return string
     */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * Get password attribute
     * @return password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * Set password attribute
     * @return password
     */
    public void setPassword(String password) {
        Password = password;
    }

    /**
     * Get lastLogin attribute
     * @return Date
     */
    public Date getLastLogin() {
        return LastLogin;
    }

    /**
     * Set lastLogin attribute
     * @return Date
     */
    public void setLastLogin(Date lastLogin) {
        LastLogin = lastLogin;
    }

    /**
     * Get createdAt attribute
     * @return Date
     */
    public Date getCreatedAt() {
        return CreatedAt;
    }

    /**
     * Set createdAt attribute
     * @return Date
     */
    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    /**
     * Get updatedAt attribute
     * @return Date
     */
    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    /**
     * Set updatedAt attribute
     * @return Date
     */
    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    /**
     * Get team attribute
     * @return team
     */
    public Team getTeam() {
        return Team;
    }

    /**
     * Set team attribute
     * @return Team
     */
    public void setTeam(Team team) {
        Team = team;
    }

    /**
     * Get mcq list attribute
     * @return mcq list
     */
    public ArrayList<Mcq> getMcqs() {
        return Mcqs;
    }

    /**
     * Set mcq list attribute
     * @return mcq list
     */
    public void setMcqs(ArrayList<Mcq> mcqs) {
        Mcqs = mcqs;
    }

    //endregion

    //region CONSTRUCTOR

    /**
     * User's constructor
     * @param id
     * @param idServer
     * @param username
     * @param password
     * @param email
     * @param lastLogin
     * @param createdAt
     * @param updatedAt
     */
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
