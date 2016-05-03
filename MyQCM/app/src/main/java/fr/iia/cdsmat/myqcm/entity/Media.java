package fr.iia.cdsmat.myqcm.entity;

import java.util.Date;

/**
 * Class managing Media object
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class Media {

    //region ATTRIBUTES
    private int Id;
    private int IdServer;
    private String Name;
    private String Url;
    private Date CreatedAt;
    private Date UpdatedAt;
    private TypeMedia TypeMedia;
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
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

    public TypeMedia getTypeMedia() {
        return TypeMedia;
    }

    public void setTypeMedia(TypeMedia typeMedia) {
        TypeMedia = typeMedia;
    }

    //endregion

    //region CONSTRUCTOR
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
