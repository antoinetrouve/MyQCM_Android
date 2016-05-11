package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.iia.cdsmat.myqcm.entity.Category;
import fr.iia.cdsmat.myqcm.entity.Mcq;

/**
 * SQLite Adpater managing Mcq database table
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class McqSQLiteAdapter {

    //region ATTRIBUTES
    /**
     * Constant name's database table
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String TABLE_MCQ        = "mcq";

    /**
     * name of Mcq's column id (local database)
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String COL_ID           = "id";

    /**
     * name of Mcq's column id (distant server database)
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String COL_IDSERVER     = "idServer";

    /**
     * name of Mcq's column name
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String COL_NAME         = "Name";

    /**
     * name of Mcq's column isActif
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String COL_ISACTIF      = "isActif";

    /**
     * name of Mcq's column countdown
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String COL_COUNTDOWN    = "countdown";

    /**
     * name of Mcq's column diffDeb
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String COL_DIFFDEB      = "diffDeb";

    /**
     * name of Mcq's column diffEnd
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String COL_DIFFEND      = "diffEnd";

    /**
     * name of Mcq's column updatedAt
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String COL_UPDATEDAT    = "updatedAt";

    /**
     * name of Mcq's column createdAt
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String COL_CREATEDAT    = "createdAt";

    /**
     * name of Mcq's column categoryId
     * @see McqSQLiteAdapter#getSchema()
     */
    public static final String COL_CATEGORYID   = "categoryId";

    /**
     * name of SQLiteDatabase object
     */
    private SQLiteDatabase database;

    /**
     * name of MyQCMSqLiteOpenHelper object
     */
    private MyQCMSqLiteOpenHelper  helper;

    /**
     * name of context for McqSQLiteAdapter's contructor
     * @see McqSQLiteAdapter#McqSQLiteAdapter(Context)
     */
    private Context context;
    //endregion

    //region METHOD

    /**
     * McqSQLiteAdapter's contructor
     * @param context
     */
    public McqSQLiteAdapter(Context context) {
        this.context = context;
        this.helper = new MyQCMSqLiteOpenHelper(context,MyQCMSqLiteOpenHelper.DB_NAME,null,1);
    }

    /**
     * Script SQLite to create Mcq's table data in my_qcm database stored in device
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_MCQ + " ("
                + COL_ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IDSERVER      + " INTEGER NOT NULL, "
                + COL_NAME          + " TEXT NOT NULL, "
                + COL_ISACTIF       + " INTEGER NOT NULL, "
                + COL_COUNTDOWN     + " INTEGER NOT NULL, "
                + COL_DIFFDEB       + " TEXT NOT NULL, "
                + COL_DIFFEND       + " TEXT NOT NULL, "
                + COL_CREATEDAT     + " TEXT NOT NULL, "
                + COL_CATEGORYID    + " INTEGER NOT NULL, "
                + COL_UPDATEDAT     + " TEXT NULL);";
    }

    /**
     * Open Mcq database that will be used for reading and writing
     */
    public void open(){
        this.database = this.helper.getWritableDatabase();
    }

    /**
     * Close Mcq database
     */
    public void close(){
        this.database.close();
    }

    /**
     * Insert mcq in database
     * @param mcq
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(Mcq mcq){
        return database.insert(TABLE_MCQ, null, this.mcqToContentValues(mcq));
    }

    /**
     * Delete mcq
     * @param mcq
     * @return the number of rows affected
     */
    public long delete(Mcq mcq){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(mcq.getId())};

        return this.database.delete(TABLE_MCQ, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update mcq
     * @param mcq
     * @return the number of rows affected
     */
    public long update(Mcq mcq) {
        ContentValues valuesUpdate = this.mcqToContentValues(mcq);
        String whereClausesUpdate = COL_ID + "=?";
        String[] whereArgsUpdate = {String.valueOf(mcq.getId())};

        return database.update(TABLE_MCQ, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Get Mcq by id
     * @param id
     * @return Mcq object
     */
    public Mcq getMcqById(int id){

        //Create SQLite query and execute query
        //-------------------------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_NAME, COL_ISACTIF, COL_COUNTDOWN,
                COL_DIFFDEB, COL_DIFFEND, COL_CATEGORYID, COL_UPDATEDAT, COL_CREATEDAT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor cursor = database.query(TABLE_MCQ, columns, whereClausesSelect, whereArgsSelect, null, null, null);

        //Create mcq object
        //------------------
        Mcq result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Convert Cursor to Mcq Object
     * @param cursor
     * @return Mcq object
     */
    private Mcq cursorToItem(Cursor cursor) {
        //Get Mcq attributes
        //-------------------
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int idServer = cursor.getInt(cursor.getColumnIndex(COL_IDSERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        boolean isActif = intToBoolean(cursor.getInt(cursor.getColumnIndex(COL_ISACTIF)));
        int countdown = cursor.getInt(cursor.getColumnIndex(COL_COUNTDOWN));

        //Get Mcq's category
        //---------------------
        int idCategory = cursor.getInt(cursor.getColumnIndex(COL_CATEGORYID));
        CategorySQLiteAdapter catSQLiteAdapter = new CategorySQLiteAdapter(context);
        Category category = catSQLiteAdapter.getCategoryById(idCategory);

        //Manage Date format
        //------------------
        Date updatedAt = null;
        Date createdAt = null;
        Date diffDeb = null;
        Date diffEnd = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            diffDeb = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(COL_DIFFDEB)));
            diffEnd = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(COL_DIFFEND)));
            createdAt = simpleDateFormat.parse(cursor.getString((cursor.getColumnIndex(COL_CREATEDAT))));
            updatedAt = simpleDateFormat.parse(cursor.getString((cursor.getColumnIndex(COL_UPDATEDAT))));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Create Mcq object
        //------------------
        Mcq result = new Mcq(id, idServer, name, isActif, countdown, diffDeb, diffEnd, updatedAt, category);
        return result;
    }

    /**
     * Convert Mcq to ContentValues
     * @param mcq
     * @return contentValues
     */
    private ContentValues mcqToContentValues(Mcq mcq) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, mcq.getId());
        values.put(COL_IDSERVER, mcq.getIdServer());
        values.put(COL_NAME, mcq.getName());
        values.put(COL_ISACTIF, mcq.getIsActif());
        values.put(COL_COUNTDOWN, mcq.getCountdown());
        values.put(COL_DIFFDEB, mcq.getDiffDeb().toString());
        values.put(COL_DIFFEND, mcq.getDiffEnd().toString());
        values.put(COL_CATEGORYID, mcq.getCategory().getId());
        values.put(COL_CREATEDAT, mcq.getCreatedAt().toString());
        values.put(COL_UPDATEDAT, mcq.getUpdatedAt().toString());
        return values;
    }

    /**
     * Convert int to Boolean
     * @param columnIndex
     * @return
     */
    private boolean intToBoolean(int columnIndex) {
        if ( columnIndex == 0) {
            return false;
        } else {
            return true;
        }
    }
    //endregion
}
