package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.iia.cdsmat.myqcm.entity.Answer;
import fr.iia.cdsmat.myqcm.entity.Question;

/**
 * SQLite Adpater managing Answer database table
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class AnswerSQLiteAdapter {

    //region ATTRIBUTES
    /**
     * Constant name's database table
     * @see AnswerSQLiteAdapter#getSchema()
     */
    protected static final String TABLE_ANSWER     = "answer";

    /**
     * name of Answer's column id (local database)
     */
    protected static final String COL_ID           = "id";

    /**
     * name of Answer's column id (distant server database)
     * @see AnswerSQLiteAdapter#getSchema()
     */
    protected static final String COL_IDSERVER     = "idServer";

    /**
     * name of Value's column(distant server database)
     * @see AnswerSQLiteAdapter#getSchema()
     */
    protected static final String COL_VALUE        = "value";

    /**
     * name of isValid's column (distant server database)
     * @see AnswerSQLiteAdapter#getSchema()
     */
    protected static final String COL_ISVALID      = "isValid";

    /**
     * name of createdAt's column (distant server database)
     * @see AnswerSQLiteAdapter#getSchema()
     */
    protected static final String COL_CREATEDAT    = "createdAt";

    /**
     * name of UpdatedAt's column (distant server database)
     * @see AnswerSQLiteAdapter#getSchema()
     */
    protected static final String COL_UPDATEDAT    = "updatedAt";

    /**
     * name of Question's id's column (distant server database)
     * @see AnswerSQLiteAdapter#getSchema()
     */
    protected static final String COL_QUESTIONID   = "question";

    /**
     * name of context for AnswerSQLiteAdapter's contructor
     * @see AnswerSQLiteAdapter#AnswerSQLiteAdapter(Context)
     */
    private Context context;

    /**
     * name of SQLiteDatabase object
     */
    private SQLiteDatabase database;

    /**
     * name of MyQCMSqLiteOpenHelper object
     */
    private MyQCMSqLiteOpenHelper helper;
    //endregion

    //region METHOD

    /**
     * AnswerSQLiteAdapter's contructor
     * @param context
     */
    public AnswerSQLiteAdapter(Context context) {
        this.context = context;
        this.helper = new MyQCMSqLiteOpenHelper(context, MyQCMSqLiteOpenHelper.DB_NAME,null,1);
    }

    /**
     * Script SQLite to create Answer's local table database
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_ANSWER + " ("
                + COL_ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IDSERVER      + " INTEGER NOT NULL, "
                + COL_VALUE         + " TEXT NOT NULL, "
                + COL_ISVALID       + " INTEGER NOT NULL, "
                + COL_QUESTIONID    + " INTEGER NOT NULL, "
                + COL_CREATEDAT     + " TEXT NOT NULL, "
                + COL_UPDATEDAT     + " TEXT NULL);";
    }

    /**
     * Open Answer's database that will be used for reading and writing
     */
    public void open(){
        this.database = this.helper.getWritableDatabase();
    }

    /**
     * Close a Answer's database
     */
    public void close(){
        this.database.close();
    }

    /**
     * Insert answer in database
     * @param answer
     * @return the row ID of the newly inserted row, or -1 if an error occurred
    */
    public long insert(Answer answer){
        return database.insert(TABLE_ANSWER, null, this.answerToContentValues(answer));
    }

    /**
     * Delete answer
     * @param answer
     * @return the number of rows affected
     */
    public long delete(Answer answer){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(answer.getId())};
        return this.database.delete(TABLE_ANSWER, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update answer
     * @param answer
     * @return the number of rows affected
     */
    public long update(Answer answer) {
        ContentValues valuesUpdate = this.answerToContentValues(answer);
        String whereClausesUpdate = COL_ID + "=?";
        String[] whereArgsUpdate = {String.valueOf(answer.getId())};
        return database.update(TABLE_ANSWER, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Get Answer by id
     * @param id
     * @return Answer object
     */
    public Answer getAnswerById(int id){

        //Create SQLite query and execute query
        //-------------------------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_VALUE, COL_ISVALID, COL_QUESTIONID, COL_UPDATEDAT, COL_CREATEDAT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor cursor = database.query(TABLE_ANSWER, columns, whereClausesSelect, whereArgsSelect, null, null, null);

        //Create answer object
        //------------------
        Answer result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Convert Cursor to Answer Object
     * @param cursor
     * @return Answer object
     */
    private Answer cursorToItem(Cursor cursor) {
        //Get Answer attributes
        //-------------------
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int idServer = cursor.getInt(cursor.getColumnIndex(COL_IDSERVER));
        String value = cursor.getString(cursor.getColumnIndex(COL_VALUE));
        boolean isValid = intToBoolean(cursor.getInt(cursor.getColumnIndex(COL_ISVALID)));

        //Get Answer's question
        //---------------------
        int idQuestion = cursor.getInt(cursor.getColumnIndex(COL_QUESTIONID));
        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(context);
        Question question = questionSQLiteAdapter.getQuestionById(idQuestion);

        //Manage Date format
        //------------------
        Date updatedAt = null;
        Date createdAt = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            createdAt = simpleDateFormat.parse(cursor.getString((cursor.getColumnIndex(COL_CREATEDAT))));
            updatedAt = simpleDateFormat.parse(cursor.getString((cursor.getColumnIndex(COL_UPDATEDAT))));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Create Answer object
        //------------------
        Answer result = new Answer(id, idServer, value, isValid, updatedAt, question);
        return result;
    }

    /**
     * Convert Answer to ContentValues
     * @param answer
     * @return contentValues
     */
    private ContentValues answerToContentValues(Answer answer) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, answer.getId());
        values.put(COL_IDSERVER, answer.getIdServer());
        values.put(COL_VALUE, answer.getValue());
        values.put(COL_ISVALID, answer.getIsValid());
        values.put(COL_QUESTIONID, answer.getQuestion().getId());
        values.put(COL_CREATEDAT, answer.getCreatedAt().toString());
        values.put(COL_UPDATEDAT, answer.getUpdatedAt().toString());
        return values;
    }

    /**
     * Convert int to boolean
     * @param columnIndex
     * @return boolean
     */
    private boolean intToBoolean(int columnIndex){
        if ( columnIndex == 0) {
            return false;
        } else {
            return true;
        }
    }
    //endregion
}
