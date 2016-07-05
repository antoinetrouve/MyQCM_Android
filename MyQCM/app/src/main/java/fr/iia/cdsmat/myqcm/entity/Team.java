package fr.iia.cdsmat.myqcm.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int IdServer;

    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    private String Name;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date UpdatedAt;

    @SerializedName("mcqs")
    @Expose(serialize = true, deserialize = true)
    private ArrayList<Mcq> Mcqs;

    @SerializedName("users")
    @Expose(serialize = true, deserialize = true)
    private ArrayList<User> Users;
    //endregion

    //region GETTER and SETTER
    /**
     * Get id's team attribute
     * @return int
     */
    public int getId() {
        return Id;
    }

    /**
     * Set id's team attribute
     * @return int
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Get WebServer team's id attribute
     * @return int
     */
    public int getIdServer() {
        return IdServer;
    }

    /**
     * Set WebServer team's id attribute
     * @return int
     */
    public void setIdServer(int idServer) {
        IdServer = idServer;
    }

    /**
     * Get name attribute
     * @return String
     */
    public String getName() {
        return Name;
    }

    /**
     * Set name attribute
     * @return String
     */
    public void setName(String name) {
        Name = name;
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
     * Get list of mcq attribute
     * @return mcq list
     */
    public ArrayList<Mcq> getMcqs() {
        return Mcqs;
    }

    /**
     * Set list of mcq attribute
     * @return mcq list
     */
    public void setMcqs(ArrayList<Mcq> mcqs) {
        Mcqs = mcqs;
    }

    /**
     * Get user list attribute
     * @return user list
     */
    public ArrayList<User> getUsers() {
        return Users;
    }

    /**
     * Set users list attribute
     * @return users list
     */
    public void setUsers(ArrayList<User> users) {
        Users = users;
    }

    //endregion

    //region CONSTRUCTOR

    /**
     * Team's constructor
     * @param id
     * @param idServer
     * @param name
     * @param updatedAt
     */
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
