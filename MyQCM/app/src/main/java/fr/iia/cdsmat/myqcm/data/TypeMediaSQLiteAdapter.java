package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.iia.cdsmat.myqcm.entity.TypeMedia;

/**
 * SQLite Adpater managing TypeMedia database table
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class TypeMediaSQLiteAdapter {

    //region ATTRIBUTE
    /**
     * Constant name's database table
     * @see TypeMediaSQLiteAdapter#getSchema()
     */
    public static final String TABLE_TYPEMEDIA = "type_media";

    /**
     * name of TypeMedia's column id (local database)
     * @see TypeMediaSQLiteAdapter#getSchema()
     */
    public static final String COL_ID        = "id";

    /**
     * name of TypeMedia's column id (distant server database)
     * @see TypeMediaSQLiteAdapter#getSchema()
     */
    public static final String COL_IDSERVER  = "idserver";

    /**
     * name of TypeMedia's column name
     * @see TypeMediaSQLiteAdapter#getSchema()
     */
    public static final String COL_NAME      = "name";

    /**
     * name of TypeMedia's column updatedAt
     * @see TypeMediaSQLiteAdapter#getSchema()
     */
    public static final String COL_UPDATEDAT = "updatedAt";

    /**
     * name of context for TypeMediaSQLiteAdapter's contructor
     * @see TypeMediaSQLiteAdapter#TypeMediaSQLiteAdapter(Context)
     */
    private Context context;

    /**
     * name of SQLiteDatabase object
     */
    private SQLiteDatabase          database;

    /**
     * name of MyQCMSqLiteOpenHelper object
     */
    private MyQCMSqLiteOpenHelper   helper;
    //endregion

    //region METHOD

    /**
     * Helper object to create access database
     * @param context
     */
    public TypeMediaSQLiteAdapter(Context context){
        this.context = context;
        this.helper = new MyQCMSqLiteOpenHelper(context, MyQCMSqLiteOpenHelper.DB_NAME,null,1);
    }

    /**
     * Script SQLite to create Type Media's table data in my_qcm database stored in device
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_TYPEMEDIA + " ("
                + COL_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IDSERVER  + " INTEGER NOT NULL, "
                + COL_NAME      + " TEXT NOT NULL, "
                + COL_UPDATEDAT + " TEXT NOT NULL);";
    }

    /**
     * Open TypeMedia database that will be used for reading and writing
     */
    public void open(){
        this.database = this.helper.getWritableDatabase();
    }

    /**
     * Close TypeMedia database
     */
    public void close(){
        this.database.close();
    }

    /**
     * Insert typeMedia in database
     * @param typeMedia
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(TypeMedia typeMedia){
        return database.insert(TABLE_TYPEMEDIA, null, this.typeMediaToCntentValues(typeMedia));
    }

    /**
     * Delete typeMedia
     * @param typeMedia
     * @return the number of rows affected
     */
    public long delete(TypeMedia typeMedia){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(typeMedia.getId())};

        return this.database.delete(TABLE_TYPEMEDIA, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update typeMedia
     * @param typeMedia
     * @return the number of rows affected
     */
    public long update(TypeMedia typeMedia) {
        ContentValues valuesUpdate = this.typeMediaToCntentValues(typeMedia);
        String whereClausesUpdate = COL_ID + "=?";
        String[] whereArgsUpdate = {String.valueOf(typeMedia.getId())};

        return database.update(TABLE_TYPEMEDIA, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Get typeMedia by id
     * @param id
     * @return TypeMedia object
     */
    public TypeMedia getTypeMediaById(int id){

        //Create SQLite query and execute query
        //---------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_NAME, COL_UPDATEDAT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor cursor = database.query(TABLE_TYPEMEDIA, columns, whereClausesSelect, whereArgsSelect, null, null, null);

        //Create typeMedia object
        //------------------
        TypeMedia result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all TypeMedia
     * @return ArrayList<TypeMedia>
     */
    public ArrayList<TypeMedia> getAllTypeMedia(){
        ArrayList<TypeMedia> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<TypeMedia>();
            // add typ into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Get all TypeMedia cursor
     * @return
     */
    private Cursor getAllCursor() {
        String[] columns = {COL_ID, COL_IDSERVER, COL_NAME, COL_UPDATEDAT};
        Cursor cursor = database.query(TABLE_TYPEMEDIA, columns, null, null, null, null, null);
        return cursor;
    }

    /**
     * Convert Cursor to TypeMedia object
     * @param cursor
     * @return TypeMedia object
     */
    private TypeMedia cursorToItem(Cursor cursor) {
        //Get TypeMedia attributes
        //-------------------
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int idServer = cursor.getInt(cursor.getColumnIndex(COL_IDSERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));

        //Manage Date format
        //------------------
        Date updatedAt = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            updatedAt = simpleDateFormat.parse(cursor.getString((cursor.getColumnIndex(COL_UPDATEDAT))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Create TypeMedia object
        //------------------
        TypeMedia result = new TypeMedia(id, idServer,name,updatedAt);
        return result;
    }

    /**
     * Convert typeMedia to ContentValues
     * @param typeMedia
     * @return ContentValue
     */
    private ContentValues typeMediaToCntentValues(TypeMedia typeMedia) {
        ContentValues values = new ContentValues();
        values.put(COL_IDSERVER, typeMedia.getIdServer());
        values.put(COL_NAME, typeMedia.getName());
        values.put(COL_UPDATEDAT, typeMedia.getUpdatedAt().toString());
        return values;
    }
    //endregion
}
