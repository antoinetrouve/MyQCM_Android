package fr.iia.cdsmat.myqcm.entity;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by Antoine Trouvé on 04/04/2016.
 * antoinetrouve.france@gmail.com
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

    public ArrayList<Media> getMedias() {
        return Medias;
    }

    public void setMedias(ArrayList<Media> medias) {
        Medias = medias;
    }
    //endregion

    //region CONSTRUCTOR
    public TypeMedia(int id, int idServer, String name, Date updatedAt) {
        Id = id;
        IdServer = idServer;
        Name = name;
        UpdatedAt = updatedAt;
    }
    //endregion
}
