package fr.iia.cdsmat.myqcm.entity;

import java.util.ArrayList;

/**
 * Class managing Result object
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class Result {
    //region ATTRIBUTE
    private int Id;
    private int IdUser;
    private int IdMcq;
    private ArrayList<Integer> IdAnswers;
    //endregion

    //region GETTER and SETTER

    /**
     * Get id's result attribute
     * @return int
     */
    public int getId() {
        return Id;
    }

    /**
     * Set id's result attribute
     * @return int
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Get user's id attribute
     * @return int
     */
    public int getIdUser() {
        return IdUser;
    }

    /**
     * Set user's id attribute
     * @return int
     */
    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    /**
     * Get mcq's id attribute
     * @return int
     */
    public int getIdMcq() {
        return IdMcq;
    }

    /**
     * Set mcq's id attribute
     * @return int
     */
    public void setIdMcq(int idMcq) {
        IdMcq = idMcq;
    }

    /**
     * Get list id's answer attribute
     * @return arrayList of answers
     */
    public ArrayList<Integer> getIdAnswers() {
        return IdAnswers;
    }

    /**
     * Set list id's answer attribute
     * @return arrayList of answers
     */
    public void setIdAnswers(ArrayList<Integer> idAnswers) {
        IdAnswers = idAnswers;
    }

    //endregion

    //region CONSTRUCTOR

    /**
     * Result's constructor
     * @param id
     * @param idUser
     * @param idMcq
     */
    public Result(int id, int idUser, int idMcq) {
        Id = id;
        IdUser = idUser;
        IdMcq = idMcq;
    }
    //endregion
}
