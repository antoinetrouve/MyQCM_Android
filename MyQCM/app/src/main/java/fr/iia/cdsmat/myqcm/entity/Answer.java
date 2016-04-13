package fr.iia.cdsmat.myqcm.entity;

import java.util.Date;

/**
 * Created by Antoine Trouvé on 04/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class Answer {
    //region ATTRIBUTES
    private int Id;
    private int IdServer;
    private String Value;
    private boolean IsValid;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Question Question;
    //endregion

    //region GETTER and SETTER

    /**
     * Get answer's id attribute
     * @return int
     */
    public int getId() {
        return Id;
    }

    /**
     * Set Answer's id attribute
     * @param id
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Get WebServer answer's id attribute
     * @return int
     */
    public int getIdServer() {
        return IdServer;
    }

    /**
     * Set WebServer answer's id attribute
     * @param idServer
     */
    public void setIdServer(int idServer) {
        IdServer = idServer;
    }

    /**
     * Get answer's value attribute
     * @return String
     */
    public String getValue() {
        return Value;
    }

    /**
     * Set answer's value attribute
     * @param value
     */
    public void setValue(String value) {
        Value = value;
    }

    /**
     * Get answer's IsValid attribute
     * @return boolean
     */
    public boolean getIsValid() {
        return IsValid;
    }

    /**
     * Set answer's IsValid attribute
     * @param isValid
     */
    public void setIsValid(boolean isValid) {
        IsValid = isValid;
    }

    /**
     * Get answer's createdAt attribute
     * @return Date
     */
    public Date getCreatedAt() {
        return CreatedAt;
    }

    /**
     * Set answer's createdAt attribute
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    /**
     * Get answer's UpdatedAt attribute
     * @return Date
     */
    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    /**
     * Set answer's UpdatedAt attribute
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    /**
     * Get answer's question attribute
     * @return Question
     */
    public Question getQuestion() {
        return Question;
    }

    /**
     * Set answer's question attribute
     * @param question
     */
    public void setQuestion(Question question) {
        Question = question;
    }
    //endregion

    //region CONSTRUCTOR
    public Answer(int id, int idServer, String value, boolean isValid, Date updatedAt, Question question) {
        Id = id;
        IdServer = idServer;
        Value = value;
        IsValid = isValid;
        UpdatedAt = updatedAt;
        Question = question;
    }
    //endregion

    //region METHOD
    @Override
    public String toString() {
        return "Answer{" +
                "Value='" + Value + '\'' +
                '}';
    }
    //endregion
}
