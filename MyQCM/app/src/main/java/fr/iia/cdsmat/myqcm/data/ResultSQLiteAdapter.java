package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fr.iia.cdsmat.myqcm.entity.Result;
import fr.iia.cdsmat.myqcm.entity.User;

/**
 * SQLite Adpater managing Result database table
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class ResultSQLiteAdapter {

    //region ATTRIBUTES
    /**
     * Constant name's database table
     * @see ResultSQLiteAdapter#getSchema()
     */
    public static final String TABLE_RESULT = "result";

    /**
     * name of Result's column id (local database)
     * @see ResultSQLiteAdapter#getSchema()
     */
    public static final String COL_ID = "id";

    /**
     * name of Result's column userId
     * @see ResultSQLiteAdapter#getSchema()
     */
    public static final String COL_USERID = "userId";

    /**
     * name of Result's column mcqId
     * @see ResultSQLiteAdapter#getSchema()
     */
    public static final String COL_MCQID = "mcqId";

    /**
     * name of SQLiteDatabase object
     */
    private SQLiteDatabase database;

    /**
     * name of MyQCMSqLiteOpenHelper object
     */
    private MyQCMSqLiteOpenHelper helper;

    /**
     * name of context for ResultSQLiteAdapter's contructor
     * @see ResultSQLiteAdapter#ResultSQLiteAdapter(Context)
     */
    private Context context;
    //endregion

    //region METHOD

    /**
     * ResultSQLiteAdapter's constructor
     * @param context
     */
    public ResultSQLiteAdapter(Context context) {
        this.context = context;
        this.helper = new MyQCMSqLiteOpenHelper(context,MyQCMSqLiteOpenHelper.DB_NAME,null,1);
    }

    /**
     * Script SQLite to create Result's table data in my_qcm database stored in device
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_RESULT + " ("
                + COL_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USERID   + " INTEGER NOT NULL, "
                + COL_MCQID    + " INTEGER NOT NULL);";
    }

    /**
     * Open Result database that will be used for reading and writing
     */
    public void open(){
        this.database = this.helper.getWritableDatabase();
    }

    /**
     * Close Result database
     */
    public void close(){
        this.database.close();
    }

    /**
     * Insert result in database
     * @param result
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(Result result){
        return database.insert(TABLE_RESULT, null, this.resultToContentValues(result));
    }

    /**
     * Delete result
     * @param result
     * @return the number of rows affected
     */
    public long delete(Result result){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(result.getId())};

        return this.database.delete(TABLE_RESULT, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update result
     * @param result
     * @return the number of rows affected
     */
    public long update(Result result) {
        ContentValues valuesUpdate = this.resultToContentValues(result);
        String whereClausesUpdate = COL_ID + "=?";
        String[] whereArgsUpdate = {String.valueOf(result.getId())};

        return database.update(TABLE_RESULT, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Get Result by id
     * @param id
     * @return Result object
     */
    public Result getResultById(int id){

        //Create SQLite query and execute query
        //-------------------------------------
        String[] columns = {COL_ID, COL_USERID, COL_MCQID };
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor cursor = database.query(TABLE_RESULT, columns, whereClausesSelect, whereArgsSelect, null, null, null);

        //Create result object
        //------------------
        Result result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all Result
     * @return ArrayList<Result>
     */
    public ArrayList<Result> getAllResult(){
        ArrayList<Result> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Result>();
            // add result into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Get all cursor in Result table
     * @return cursor
     */
    private Cursor getAllCursor() {
        String[] columns = {COL_ID, COL_MCQID, COL_USERID};
        Cursor cursor = database.query(TABLE_RESULT, columns, null, null, null, null, null);
        return cursor;
    }

    /**
     * Convert Cursor to Result Object
     * @param cursor
     * @return Result object
     */
    private Result cursorToItem(Cursor cursor) {
        //Get Result attributes
        //-------------------
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int idUser = cursor.getInt(cursor.getColumnIndex(COL_USERID));
        int idMcq = cursor.getInt(cursor.getColumnIndex(COL_MCQID));

        //Create Result object
        //------------------
        Result result = new Result(id,idUser,idMcq);
        return result;
    }

    /**
     * Convert Result to ContentValues
     * @param result
     * @return contentValues
     */
    private ContentValues resultToContentValues(Result result) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, result.getId());
        values.put(COL_MCQID, result.getIdMcq());
        values.put(COL_USERID, result.getIdUser());
        return values;
    }
    //endregion
}
