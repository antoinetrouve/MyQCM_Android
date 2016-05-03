package fr.iia.cdsmat.myqcm.entity;

import java.util.Date;
import java.util.ArrayList;

/**
 * Class managing Team object
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class Team {

    //region ATTRIBUTE
    private int Id;
    private int IdServer;
    private String Name;
    private Date CreatedAt;
    private Date UpdatedAt;
    private ArrayList<Mcq> Mcqs;
    private ArrayList<User> Users;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public ArrayList<Mcq> getMcqs() {
        return Mcqs;
    }

    public void setMcqs(ArrayList<Mcq> mcqs) {
        Mcqs = mcqs;
    }

    public ArrayList<User> getUsers() {
        return Users;
    }

    public void setUsers(ArrayList<User> users) {
        Users = users;
    }

    //endregion

    //region CONSTRUCTOR
    public Team(int id, int idServer, String name, Date updatedAt) {
        Id = id;
        IdServer = idServer;
        Name = name;
        UpdatedAt = updatedAt;
    }
    //endregion

    //region METHOD
    @Override
    public String toString() {
        return "Team{" +
                "Name='" + Name + '\'' +
                ", Mcqs=" + Mcqs +
                '}';
    }

    //endregion
}
