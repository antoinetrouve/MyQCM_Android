package fr.iia.cdsmat.myqcm.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.ArrayList;

/**
 * Class managing Mcq object
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class Mcq {

    //region ATTRIBUTES
    private int Id;

    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int IdServer;

    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    private String Name;

    @SerializedName("is_Actif")
    @Expose(serialize = true, deserialize = true)
    private boolean IsActif;

    @SerializedName("countdown")
    @Expose(serialize = true, deserialize = true)
    private int Countdown;

    @SerializedName("diff_deb")
    @Expose(serialize = true, deserialize = true)
    private Date DiffDeb;

    @SerializedName("diff_end")
    @Expose(serialize = true, deserialize = true)
    private Date DiffEnd;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date UpdatedAt;

    @SerializedName("category")
    @Expose(serialize = true, deserialize = true)
    private Category Category;

    @SerializedName("questions")
    @Expose(serialize = true, deserialize = true)
    private ArrayList<Question> Questions;

    private ArrayList<Result> Results;
    //endregion

    //region GETTER and SETTER
    /**
     * Get mcq's id attribute
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
     * Get mcq's name attribute
     * @return String
     */
    public String getName() {
        return Name;
    }

    /**
     * Set mcq's name attribute
     * @param name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Get mcq's isActif attribute
     * @return boolean
     */
    public boolean getIsActif() {
        return IsActif;
    }

    /**
     * Set mcq's isActif attribute
     * @param isActif
     */
    public void setIsActif(boolean isActif) {
        IsActif = isActif;
    }

    /**
     * Get mcq's countdown attribute
     * @return int
     */
    public int getCountdown() {
        return Countdown;
    }

    /**
     * Set mcq's countdown attribute
     * @param countdown
     */
    public void setCountdown(int countdown) {
        Countdown = countdown;
    }

    /**
     * Get mcq's DiffDeb atttribute
     * @return Date
     */
    public Date getDiffDeb() {
        return DiffDeb;
    }

    /**
     * Set mcq's DiffDeb attribute
     * @param diffDeb
     */
    public void setDiffDeb(Date diffDeb) {
        DiffDeb = diffDeb;
    }

    /**
     * Get mcq's DiffEnd attribute
     * @return Date
     */
    public Date getDiffEnd() {
        return DiffEnd;
    }

    /**
     * Set mcq's DiffEnd attribute
     * @param diffEnd
     */
    public void setDiffEnd(Date diffEnd) {
        DiffEnd = diffEnd;
    }

    /**
     * Set mcq's CreatedAt attribute
     * @return Date
     */
    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    /**
     * Set mcq's UpdatedAt attribute
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    /**
     * Get mcq's Category attribute
     * @return Category
     */
    public Category getCategory() {
        return Category;
    }

    /**
     * Set mcq's category attribute
     * @param category
     */
    public void setCategory(Category category) {
        Category = category;
    }

    /**
     * Get mcq's Question List collection
     * @return ArrayList
     */
    public ArrayList<Question> getQuestions() {
        return Questions;
    }

    /**
     * Set mcq's Question List collection
     * @param questions
     */
    public void setQuestions(ArrayList<Question> questions) {
        Questions = questions;
    }

    /**
     * Get mcq's Result List collection
     * @return ArrayList
     */
    public ArrayList<Result> getResults() {
        return Results;
    }

    /**
     * Set mcq's Result List collection
     * @param results
     */
    public void setResults(ArrayList<Result> results) {
        Results = results;
    }
    //endregion

    //region CONSTRUCTOR

    /**
     * Mcs's constructor
     * @param id
     * @param idServer
     * @param name
     * @param isActif
     * @param countdown
     * @param diffDeb
     * @param diffEnd
     * @param updatedAt
     * @param category
     */
    public Mcq(int id, int idServer, String name, boolean isActif, int countdown, Date diffDeb, Date diffEnd, Date updatedAt, Category category) {
        Id = id;
        IdServer = idServer;
        Name = name;
        IsActif = isActif;
        Countdown = countdown;
        DiffDeb = diffDeb;
        DiffEnd = diffEnd;
        UpdatedAt = updatedAt;
        Category = category;
    }

    /**
     * Mcq's constructor
     * @param id
     * @param idServer
     * @param name
     * @param countdown
     * @param updatedAt
     * @param category
     */
    public Mcq(int id, int idServer, String name, int countdown, Date updatedAt, Category category){
        Id = id;
        IdServer = idServer;
        Name = name;
        Countdown = countdown;
        UpdatedAt = updatedAt;
        Category = category;
    }
    //endregion

    //region METHOD
    @Override
    public String toString() {
        return Name ;
    }
    //endregion

}
