package fr.iia.cdsmat.myqcm.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Class managing Answer object
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class Answer {
    //region ATTRIBUTES
    private int Id;

    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int IdServer;

    @SerializedName("value")
    @Expose(serialize = true, deserialize = true)
    private String Value;

    @SerializedName("is_valid")
    @Expose(serialize = true, deserialize = true)
    private boolean IsValid;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date UpdatedAt;

    @SerializedName("selected")
    @Expose(serialize = true, deserialize = true)
    boolean selected = false;

    @SerializedName("question")
    @Expose(serialize = true, deserialize = true)
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

    /**
     * Get boolean isSelected
     * @return boolean
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Set boolean selected
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    //endregion

    //region CONSTRUCTOR

    /**
     * Answer's constructor
     * @param id
     * @param idServer
     * @param value
     * @param isValid
     * @param updatedAt
     * @param question
     */
    public Answer(int id, int idServer, String value, boolean isValid, Date updatedAt, Question question) {
        Id = id;
        IdServer = idServer;
        Value = value;
        IsValid = isValid;
        UpdatedAt = updatedAt;
        Question = question;
    }

    /**
     * Answer's constructor
     * @param id
     * @param idServer
     * @param value
     * @param isValid
     * @param updatedAt
     */
    public Answer(int id, int idServer, String value, boolean isValid, Date updatedAt){
        Id = id;
        IdServer = idServer;
        Value = value;
        IsValid = isValid;
        UpdatedAt = updatedAt;
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
