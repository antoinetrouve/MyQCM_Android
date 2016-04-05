package fr.iia.cdsmat.myqcm.entity;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Antoine Trouvé on 04/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class Mcq {

    //region ATTRIBUTES
    private int Id;
    private int IdServer;
    private String Name;
    private boolean IsActif;
    private int Countdown;
    private Date DiffDeb;
    private Date DiffEnd;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Category Category;
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
    public boolean isActif() {
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
     * Get mcq's CreatedAt attribute
     * @return Date
     */
    public Date getCreatedAt() {
        return CreatedAt;
    }

    /**
     * Set mcq's CreatedAt attribute
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
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
    public Mcq(int id, int idServer, String name, boolean isActif, int countdown, Date diffDeb, Date diffEnd, Date updatedAt, Category category, ArrayList<Question> questions) {
        Id = id;
        IdServer = idServer;
        Name = name;
        IsActif = isActif;
        Countdown = countdown;
        DiffDeb = diffDeb;
        DiffEnd = diffEnd;
        UpdatedAt = updatedAt;
        Category = category;
        Questions = questions;
    }
    //endregion

    //region METHOD
    @Override
    public String toString() {
        return "Mcq{" +
                "Name='" + Name + '\'' +
                ", IsActif=" + IsActif +
                ", Countdown=" + Countdown +
                ", DiffDeb=" + DiffDeb +
                ", DiffEnd=" + DiffEnd +
                '}';
    }
    //endregion

}
