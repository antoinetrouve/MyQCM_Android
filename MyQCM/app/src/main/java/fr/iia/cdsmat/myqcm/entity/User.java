package fr.iia.cdsmat.myqcm.entity;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Antoine Trouvé on 04/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class User {

    //region ATTRIBUTE
    private int Id;
    private int IdServer;
    private String Username;
    private String Email;
    private Date LastLogin;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Team Team;
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

    public fr.iia.cdsmat.myqcm.entity.Team getTeam() {
        return Team;
    }

    public void setTeam(fr.iia.cdsmat.myqcm.entity.Team team) {
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
    public User(int id, int idServer, String username, String email, Date lastLogin, Date createdAt, Date updatedAt, fr.iia.cdsmat.myqcm.entity.Team team, ArrayList<Mcq> mcqs) {
        Id = id;
        IdServer = idServer;
        Username = username;
        Email = email;
        LastLogin = lastLogin;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        Team = team;
        Mcqs = mcqs;
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
