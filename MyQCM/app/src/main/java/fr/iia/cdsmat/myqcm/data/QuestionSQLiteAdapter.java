package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.iia.cdsmat.myqcm.entity.Mcq;
import fr.iia.cdsmat.myqcm.entity.Question;

/**
 * SQLite Adpater managing Question database table
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class QuestionSQLiteAdapter {
    //region ATTRIBUTES
    /**
     * Constant name's database table
     * @see QuestionSQLiteAdapter#getSchema()
     */
    public static final String TABLE_QUESTION   = "question";

    /**
     * name of Question's column id (local database)
     * @see QuestionSQLiteAdapter#getSchema()
     */
    public static final String COL_ID           = "id";

    /**
     * name of Question's column id (distant server database)
     * @see QuestionSQLiteAdapter#getSchema()
     */
    public static final String COL_IDSERVER     = "idServer";

    /**
     * name of Question's column name
     * @see QuestionSQLiteAdapter#getSchema()
     */
    public static final String COL_NAME         = "name";

    /**
     * name of Question's column createdAt
     * @see QuestionSQLiteAdapter#getSchema()
     */
    public static final String COL_CREATEDAT    = "CreatedAt";

    /**
     * name of Question's column updatedAt
     * @see QuestionSQLiteAdapter#getSchema()
     */
    public static final String COL_UPDATEDAT    = "updatedAt";

    /**
     * name of Question's column mediaId
     * @see QuestionSQLiteAdapter#getSchema()
     */
    public static final String COL_MEDIAID      = "mediaId";

    /**
     * name of Question's column mcqId
     * @see QuestionSQLiteAdapter#getSchema()
     */
    public static final String COL_MCQID        = "mcqId";

    /**
     * name of SQLiteDatabase object
     */
    private SQLiteDatabase database;

    /**
     * name of MyQCMSqLiteOpenHelper object
     */
    private MyQCMSqLiteOpenHelper helper;

    /**
     * name of context for QuestionSQLiteAdapter's contructor
     * @see QuestionSQLiteAdapter#QuestionSQLiteAdapter(Context)
     */
    private Context context;
    //endregion

    //region METHOD

    /**
     * QuestionSQLiteAdapter's constructor
     * @param context
     */
    public QuestionSQLiteAdapter(Context context) {
        this.context = context;
        this.helper = new MyQCMSqLiteOpenHelper(context,MyQCMSqLiteOpenHelper.DB_NAME,null,1);
    }

    /**
     * Script SQLite to create Question's table data in my_qcm database stored in device
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_QUESTION + " ("
                + COL_ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IDSERVER      + " INTEGER NOT NULL, "
                + COL_NAME          + " TEXT NOT NULL, "
                + COL_MEDIAID       + " INTEGER NULL, "
                + COL_MCQID         + " INTEGER NOT NULL, "
                + COL_CREATEDAT     + " TEXT NOT NULL, "
                + COL_UPDATEDAT     + " TEXT NULL);";
    }

    /**
     * Open Question database that will be used for reading and writing
     */
    public void open(){
        this.database = this.helper.getWritableDatabase();
    }

    /**
     * Close Question database
     */
    public void close(){
        this.database.close();
    }

    /**
     * Insert question in database
     * @param question
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(Question question){
        return database.insert(TABLE_QUESTION, null, this.questionToContentValues(question));
    }

    /**
     * Delete question
     * @param question
     * @return the number of rows affected
     */
    public long delete(Question question){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(question.getId())};

        return this.database.delete(TABLE_QUESTION, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update question
     * @param question
     * @return the number of rows affected
     */
    public long update(Question question) {
        ContentValues valuesUpdate = this.questionToContentValues(question);
        String whereClausesUpdate = COL_ID + "=?";
        String[] whereArgsUpdate = {String.valueOf(question.getId())};

        return database.update(TABLE_QUESTION, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Get Question by id
     * @param id
     * @return Question object
     */
    public Question getQuestionById(int id){

        //Create SQLite query and execute query
        //-------------------------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_NAME, COL_MEDIAID, COL_MCQID, COL_UPDATEDAT, COL_CREATEDAT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor cursor = database.query(TABLE_QUESTION, columns, whereClausesSelect, whereArgsSelect, null, null, null);

        //Create question object
        //------------------
        Question result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Convert Cursor to Question Object
     * @param cursor
     * @return question
     */
    private Question cursorToItem(Cursor cursor) {
        //Get Question attributes
        //-------------------
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int idServer = cursor.getInt(cursor.getColumnIndex(COL_IDSERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        int idMedia = cursor.getInt(cursor.getColumnIndex(COL_MEDIAID));

        //Get Question's mcq
        //------------------
        int idMcq = cursor.getInt(cursor.getColumnIndex(COL_MCQID));
        McqSQLiteAdapter mcqSQLiteAdapter = new McqSQLiteAdapter(context);
        Mcq mcq = mcqSQLiteAdapter.getMcqById(idMcq);

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

        //Create Question object
        //------------------
        Question result = new Question(id,idServer,name,updatedAt,mcq);
        if (idMedia != 0){
            MediaSQLiteAdapter mediaSQLiteAdapter = new MediaSQLiteAdapter(context);
            result.setMedia(mediaSQLiteAdapter.getMediaById(idMedia));
        }
        return result;
    }

    /**
     * Convert Question to ContentValues
     * @param question
     * @return contentValues
     */
    private ContentValues questionToContentValues(Question question) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, question.getId());
        values.put(COL_IDSERVER, question.getIdServer());
        values.put(COL_NAME, question.getName());
        values.put(COL_MEDIAID, question.getMedia().getId());
        values.put(COL_MCQID, question.getMcq().getId());
        values.put(COL_CREATEDAT, question.getCreatedAt().toString());
        values.put(COL_UPDATEDAT, question.getUpdatedAt().toString());
        return values;
    }
    //endregion
}
