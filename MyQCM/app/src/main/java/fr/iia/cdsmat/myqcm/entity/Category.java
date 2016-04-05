package fr.iia.cdsmat.myqcm.entity;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Antoine Trouvé on 04/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class Category {

    //region ATTRIBUTES
    private int Id;
    private int IdServer;
    private String Name;
    private Date CreatedAt;
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
    public Category(int id, int idServer, String name, Date updatedAt, ArrayList<Mcq> mcqs) {
        Id = id;
        IdServer = idServer;
        Name = name;
        UpdatedAt = updatedAt;
        Mcqs = mcqs;
    }
    //endregion

    //region METHOD
    @Override
    public String toString() {
        return "Category{" +
                "Name='" + Name + '\'' +
                '}';
    }
    //endregion
}
