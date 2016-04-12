package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.iia.cdsmat.myqcm.entity.Media;
import fr.iia.cdsmat.myqcm.entity.TypeMedia;

/**
 * Created by Antoine Trouvé on 06/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class MediaSQLiteAdapter {
    //region ATTRIBUTES
    public static final String TABLE_MEDIA      = "media";
    public static final String COL_ID           = "id";
    public static final String COL_IDSERVER     = "idserver";
    public static final String COL_NAME         = "name";
    public static final String COL_URL          = "url";
    public static final String COL_IDTYPEMEDIA  = "idTypeMedia";
    public static final String COL_UPDATEDAT    = "updatedAt";

    private Context                 context;
    private SQLiteDatabase          database;
    private MyQCMSqLiteOpenHelper   helper;
    //endregion

    //region METHOD
    /**
     * Helper object to create access database
     * @param context
     */
    public MediaSQLiteAdapter(Context context){
        helper = new MyQCMSqLiteOpenHelper(context, MyQCMSqLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Script SQLite to create Media's table data in my_qcm database stored in device
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_MEDIA + " ("
                + COL_ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IDSERVER      + " INTEGER NOT NULL, "
                + COL_NAME          + " TEXT NOT NULL, "
                + COL_URL           + " TEXT NOT NULL, "
                + COL_IDTYPEMEDIA   + " INTEGER NOT NULL, "
                + COL_UPDATEDAT     + " TEXT NULL);";
    }

    /**
     * Open a database that will be used for reading and writing
     */
    public void open(){
        this.database = this.helper.getWritableDatabase();
    }

    /**
     * Close a database
     */
    public void close(){
        this.database.close();
    }

    /**
     * Insert media in database
     * @param media
     * @return line number's result
     */
    public long insert(Media media){
        return database.insert(TABLE_MEDIA, null, this.mediaToCntentValues(media));
    }

    /**
     * Delete media
     * @param media
     * @return line number's result
     */
    public long delete(Media media){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(media.getId())};

        return this.database.delete(TABLE_MEDIA, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update media
     * @param media
     * @return line number's result
     */
    public long update(Media media) {
        ContentValues valuesUpdate = this.mediaToCntentValues(media);
        String whereClausesUpdate = COL_ID + "=?";
        String[] whereArgsUpdate = {String.valueOf(media.getId())};

        return database.update(TABLE_MEDIA, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Get media by id
     * @param id
     * @return Media object
     */
    public Media getMediaById(int id){

        //Create SQLite query and execute query
        //---------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_NAME, COL_URL , COL_IDTYPEMEDIA, COL_UPDATEDAT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor cursor = database.query(TABLE_MEDIA, columns, whereClausesSelect, whereArgsSelect, null, null, null);

        //Create media object
        //------------------
        Media result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Convert Cursor to Media object
     * @param cursor
     * @return Media object
     */
    private Media cursorToItem(Cursor cursor) {
        //Get Media attributes
        //-------------------
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int idServer = cursor.getInt(cursor.getColumnIndex(COL_IDSERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        String url = cursor.getString(cursor.getColumnIndex(COL_URL));

        //Get TypeMedia's Media
        //---------------------
        int idTypeMedia = cursor.getInt(cursor.getColumnIndex(COL_IDTYPEMEDIA));
        TypeMediaSQLiteAdapter typeMediaAdpater = new TypeMediaSQLiteAdapter(context);
        TypeMedia typeMedia = typeMediaAdpater.getTypeMediaById(idTypeMedia);

        //Manage Date format
        //------------------
        Date updatedAt = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            updatedAt = simpleDateFormat.parse(cursor.getString((cursor.getColumnIndex(COL_UPDATEDAT))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Create Media object
        //------------------
        Media result = new Media(id, idServer,name,url,updatedAt,typeMedia);
        return result;
    }

    /**
     * Convert media to ContentValues
     * @param media
     * @return ContentValue
     */
    private ContentValues mediaToCntentValues(Media media) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, media.getId());
        values.put(COL_IDSERVER, media.getIdServer());
        values.put(COL_NAME, media.getName());
        values.put(COL_URL, media.getUrl());
        values.put(COL_IDTYPEMEDIA, media.getTypeMedia().getId());
        values.put(COL_UPDATEDAT, media.getUpdatedAt().toString());
        return values;
    }


    //endregion
}
