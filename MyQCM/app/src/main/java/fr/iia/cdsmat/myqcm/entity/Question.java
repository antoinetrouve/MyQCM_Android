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

    /**
     * Get id's mcq attribute
     * @return int
     */
    public int getId() {
        return Id;
    }

    /**
     * Set id's mcq attribute
     * @return int
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Get WebServer question's id attribute
     * @return int
     */
    public int getIdServer() {
        return IdServer;
    }

    /**
     * Set WebServer question's id attribute
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
     * @param name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Get question's createdAt attribute
     * @return Date
     */
    public Date getCreatedAt() {
        return CreatedAt;
    }

    /**
     * Set question's createdAt attribute
     * @return Date
     */
    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    /**
     * Get question's updatedAt attribute
     * @return Date
     */
    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    /**
     * Set question's updatedAt attribute
     * @return Date
     */
    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    /**
     * Get Media attribute
     * @return media
     */
    public Media getMedia() {
        return Media;
    }

    /**
     * Set media attribute
     * @return media
     */
    public void setMedia(Media media) {
        Media = media;
    }

    /**
     * Get mcq attribute
     * @return mcq
     */
    public Mcq getMcq() {
        return Mcq;
    }

    /**
     * Set mcq attribute
     * @return mcq
     */
    public void setMcq(Mcq mcq) {
        Mcq = mcq;
    }

    /**
     * get list answers
     * @return list of answers
     */
    public ArrayList<Answer> getAnswers() {
        return Answers;
    }

    /**
     * Set list answers
     * @return list of answers
     */
    public void setAnswers(ArrayList<Answer> answers) {
        Answers = answers;
    }
    //endregion

    //region CONSTRUCTOR

    /**
     * Question's constructor
     * @param id
     * @param idServer
     * @param name
     * @param updatedAt
     * @param mcq
     */
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
