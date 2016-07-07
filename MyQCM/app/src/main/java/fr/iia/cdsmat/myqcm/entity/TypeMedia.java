package fr.iia.cdsmat.myqcm.entity;

import java.util.Date;
import java.util.ArrayList;

/**
 * Class managing TypeMedia object
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class TypeMedia {

    //region ATTRIBUTE
    private int Id;
    private int IdServer;
    private String Name;
    private Date CreatedAt;
    private Date UpdatedAt;
    private ArrayList<Media> Medias;
    //endregion

    //region GETTER and SETTER

    /**
     * Get id's typeMedia attribute
     * @return int
     */
    public int getId() {
        return Id;
    }

    /**
     * Set id's typeMedia attribute
     * @return int
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Get WebServer typeMedia's id attribute
     * @return int
     */
    public int getIdServer() {
        return IdServer;
    }

    /**
     * Set WebServer typeMedia's id attribute
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
     * Set createdAt attribute
     * @return Date
     */
    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    /**
     * Get Media arrayList attribute
     * @return media list
     */
    public ArrayList<Media> getMedias() {
        return Medias;
    }

    /**
     * Set media arrayList attribute
     * @return media list
     */
    public void setMedias(ArrayList<Media> medias) {
        Medias = medias;
    }
    //endregion

    //region CONSTRUCTOR

    /**
     * TypeMedia's constructor
     * @param id
     * @param idServer
     * @param name
     * @param updatedAt
     */
    public TypeMedia(int id, int idServer, String name, Date updatedAt) {
        Id = id;
        IdServer = idServer;
        Name = name;
        UpdatedAt = updatedAt;
    }
    //endregion
}
