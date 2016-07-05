package fr.iia.cdsmat.myqcm.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Class managing Media object
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class Media {

    //region ATTRIBUTES
    private int Id;

    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int IdServer;

    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    private String Name;

    @SerializedName("url")
    @Expose(serialize = true, deserialize = true)
    private String Url;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date UpdatedAt;

    @SerializedName("typeMedia")
    @Expose(serialize = true, deserialize = true)
    private TypeMedia TypeMedia;
    //endregion

    //region GETTER and SETTER

    /**
     * Get Media's id attribute
     * @return int
     */
    public int getId() {
        return Id;
    }

    /**
     * Set mcq's id attribute
     * @param id
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Get WebServer mcq's id attribute
     * @return int
     */
    public int getIdServer() {
        return IdServer;
    }

    /**
     * Set WebServer mcq's id attribute
     * @param idServer
     */
    public void setIdServer(int idServer) {
        IdServer = idServer;
    }

    /**
     * Get name's id attribute
     * @return name
     */
    public String getName() {
        return Name;
    }

    /**
     * Get name's id attribute
     * @return name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Get url attribute
     * @return url
     */
    public String getUrl() {
        return Url;
    }

    /**
     * Set url attribute
     * @return url
     */
    public void setUrl(String url) {
        Url = url;
    }

    /**
     * Get Media's UpdatedAt attribute
     * @return Date
     */
    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    /**
     * Set Media's Updated attribute
     * @return Date
     */
    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    /**
     * Get TypeMedia
     * @return Date
     */
    public TypeMedia getTypeMedia() {
        return TypeMedia;
    }

    /**
     * Set TypeMedia attribute
     * @return Date
     */
    public void setTypeMedia(TypeMedia typeMedia) {
        TypeMedia = typeMedia;
    }

    //endregion

    //region CONSTRUCTOR

    /**
     * Media's constructor
     * @param id
     * @param idServer
     * @param name
     * @param url
     * @param updatedAt
     * @param typeMedia
     */
    public Media(int id, int idServer, String name, String url, Date updatedAt, TypeMedia typeMedia) {
        Id = id;
        IdServer = idServer;
        Name = name;
        Url = url;
        UpdatedAt = updatedAt;
        TypeMedia = typeMedia;
    }
    //endregion

    //region METHOD
    @Override
    public String toString() {
        return "Media{" +
                "Name='" + Name + '\'' +
                ", Url='" + Url + '\'' +
                ", TypeMedia=" + TypeMedia +
                '}';
    }
    //endregion
}
