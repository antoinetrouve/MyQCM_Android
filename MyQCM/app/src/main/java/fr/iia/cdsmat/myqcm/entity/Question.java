package fr.iia.cdsmat.myqcm.entity;

import java.util.Date;
import java.util.ArrayList;

/**
 * Class managing Question object
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class Question {

    //region ATTRIBUTE
    private int Id;
    private int IdServer;
    private String Name;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Media Media;
    private Mcq Mcq;
    private ArrayList<Answer> Answers;
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

    public Media getMedia() {
        return Media;
    }

    public void setMedia(Media media) {
        Media = media;
    }

    public Mcq getMcq() {
        return Mcq;
    }

    public void setMcq(Mcq mcq) {
        Mcq = mcq;
    }

    public ArrayList<Answer> getAnswers() {
        return Answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        Answers = answers;
    }
    //endregion

    //region CONSTRUCTOR
    public Question(int id, int idServer, String name, Date updatedAt, Mcq mcq) {
        Id = id;
        IdServer = idServer;
        Name = name;
        UpdatedAt = updatedAt;
        Mcq = mcq;
    }
    //endregion

    //region METHOD
    @Override
    public String toString() {
        return Name ;
    }

    //endregion
}
