package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                + COL_UPDATEDAT     + " TEXT NOT NULL);";
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
        String whereClausesUpdate = COL_IDSERVER + "=?";
        String[] whereArgsUpdate = {String.valueOf(question.getIdServer())};

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
        String[] columns = {COL_ID, COL_IDSERVER, COL_NAME, COL_MEDIAID, COL_MCQID, COL_UPDATEDAT};
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
     * Get Question by idServer
     * @param idServer
     * @return Question object
     */
    public Question getQuestionByIdServer(int idServer){

        //Create SQLite query and execute query
        //-------------------------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_NAME, COL_MEDIAID, COL_MCQID, COL_UPDATEDAT};
        String whereClausesSelect = COL_IDSERVER + "= ?";
        String[] whereArgsSelect = {String.valueOf(idServer)};

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
        mcqSQLiteAdapter.open();
        Mcq mcq = mcqSQLiteAdapter.getMcqByIdServer(idMcq);

        //Manage Date format
        //------------------
        Date updatedAt = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        try {
            updatedAt = simpleDateFormat.parse(cursor.getString((cursor.getColumnIndex(COL_UPDATEDAT))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Create Question object
        //------------------
        Question result = new Question(id,idServer,name,updatedAt,mcq);
        if (idMedia != 0){
            MediaSQLiteAdapter mediaSQLiteAdapter = new MediaSQLiteAdapter(context);
            mediaSQLiteAdapter.open();
            result.setMedia(mediaSQLiteAdapter.getMediaById(idMedia));
            mediaSQLiteAdapter.close();
        }
        mcqSQLiteAdapter.close();
        return result;
    }

    /**
     * Convert Question to ContentValues
     * @param question
     * @return contentValues
     */
    private ContentValues questionToContentValues(Question question) {
        System.out.println("Question to content value" + " idServer = " + question.getIdServer() +
                " question =  " + question.getName() + " mcq " + question.getMcq().getIdServer() +
                " updated_at =  " + question.getUpdatedAt());

        ContentValues values = new ContentValues();
        values.put(COL_IDSERVER, question.getIdServer());
        values.put(COL_NAME, question.getName());

        if(question.getMedia() != null) {
            values.put(COL_MEDIAID, question.getMedia().getIdServer());
        }

        values.put(COL_MCQID, question.getMcq().getIdServer());
        values.put(COL_UPDATEDAT, question.getUpdatedAt().toString());

        return values;
    }

    /**
     * Get all question
     * @return Question list result
     */
    public ArrayList<Question> getAllQuestion() {
        ArrayList<Question> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Question>();
            // add typ into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Get all question cursor
     * @return cursor
     */
    private Cursor getAllCursor() {
        String[] columns = {COL_ID, COL_IDSERVER, COL_NAME, COL_MEDIAID, COL_MCQID, COL_UPDATEDAT};
        Cursor cursor = database.query(TABLE_QUESTION, columns, null, null, null, null, null);
        return cursor;
    }

    /**
     * Get questions by mcq id
     * @param idServerMcq
     * @return Questions list
     */
    public ArrayList<Question> getAllQuestionByIdServerMCQ(int idServerMcq){
        ArrayList<Question> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Question>();
            // add typ into list
            do {
                Question question = this.cursorToItem(cursor);
                System.out.println("question mcq id " + question.getMcq().getName());
                if( question.getMcq().getIdServer() == idServerMcq) {
                    result.add(this.cursorToItem(cursor));
                }
                else {
                    System.out.println("Not link to the MCQ");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    //endregion
}
