package fr.iia.cdsmat.myqcm.entity;

import java.sql.Date;

/**
 * Created by Antoine Trouvé on 04/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class Result {
    //region ATTRIBUTE
    private int Id;
    private int IdServer;
    private int Score;
    private boolean IsCompleted;
    private Date CreatedAt;
    private Date UpdatedAt;
    private User User;
    private Mcq Mcq;
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

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public boolean isCompleted() {
        return IsCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        IsCompleted = isCompleted;
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

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public Mcq getMcq() {
        return Mcq;
    }

    public void setMcq(Mcq mcq) {
        Mcq = mcq;
    }

    //endregion

    //region CONSTRUCTOR
    public Result(int id, int idServer, int score, boolean isCompleted, Date updatedAt, User user, Mcq mcq) {
        Id = id;
        IdServer = idServer;
        Score = score;
        IsCompleted = isCompleted;
        UpdatedAt = updatedAt;
        User = user;
        Mcq = mcq;
    }
    //endregion
}
