package fr.iia.cdsmat.myqcm.entity;

import java.util.Date;
import java.util.ArrayList;
import com.google.gson.annotations.*;

/**
 * Class managing Category object
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class Category {

    //region ATTRIBUTES
    private int Id;

    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int IdServer;

    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    private String Name;

    private Date CreatedAt;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date UpdatedAt;

    private ArrayList<Mcq> Mcqs;
    //endregion

    //region GETTER and SETTER
    /**
     * Get category's id attribute
     * @return int
     */
    public int getId() {
        return Id;
    }

    /**
     * Set category's id attribute
     * @param id
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Get WebServer category's id attribute
     * @return int
     */
    public int getIdServer() {
        return IdServer;
    }

    /**
     * Set WebServer category's id attribute
     * @param idServer
     */
    public void setIdServer(int idServer) {
        IdServer = idServer;
    }

    /**
     * Get category's name attribute
     * @return String
     */
    public String getName() {
        return Name;
    }

    /**
     * Set category's name attribute
     * @param name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Get Category's CreatedAt attribute
     * @return Date
     */
    public Date getCreatedAt() {
        return CreatedAt;
    }

    /**
     * Set Category's CreatedAt attribute
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    /**
     * Get Category's UpdatedAt attribute
     * @return Date
     */
    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    /**
     * Set Category's UpdatedAt attribute
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    /**
     * Get Category's Mcq List collection
     * @return ArrayList
     */
    public ArrayList<Mcq> getMcqs() {
        return Mcqs;
    }

    /**
     * Set Category's Mcq List collection
     * @param mcqs
     */
    public void setMcqs(ArrayList<Mcq> mcqs) {
        Mcqs = mcqs;
    }
    //endregion

    //region CONSTRUCTOR

    /**
     * Category's constructor
     * @param idServer
     * @param name
     * @param updatedAt
     */
    public Category( int idServer, String name, Date updatedAt) {
        this.IdServer = idServer;
        this.Name = name;
        this.UpdatedAt = updatedAt;
    }

    /**
     * Category's constructor
     * @param name
     */
    public Category(String name) {
        this.Name = name;
    }

    //endregion

    //region METHOD
    @Override
    public String toString() {
        return  Name;
    }
    //endregion
}
