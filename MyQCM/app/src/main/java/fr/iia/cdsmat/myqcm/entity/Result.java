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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public int getIdMcq() {
        return IdMcq;
    }

    public void setIdMcq(int idMcq) {
        IdMcq = idMcq;
    }

    public ArrayList<Integer> getIdAnswers() {
        return IdAnswers;
    }

    public void setIdAnswers(ArrayList<Integer> idAnswers) {
        IdAnswers = idAnswers;
    }

    //endregion

    //region CONSTRUCTOR
    public Result(int id, int idUser, int idMcq) {
        Id = id;
        IdUser = idUser;
        IdMcq = idMcq;
    }
    //endregion
}
