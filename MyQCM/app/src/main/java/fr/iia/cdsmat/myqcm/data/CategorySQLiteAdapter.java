package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.iia.cdsmat.myqcm.entity.Category;

/**
 * SQLite Adpater managing Category database table
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class CategorySQLiteAdapter {

    //region ATTRIBUTES
    /**
     * Constant name's database table
     * @see CategorySQLiteAdapter#getSchema()
     */
    public static final String TABLE_CATEGORY   = "category";

    /**
     * name of Category's column id (local database)
     * @see CategorySQLiteAdapter#getSchema()
     */
    public static final String COL_ID           = "id";

    /**
     * name of Category's column id (distant server database)
     * @see CategorySQLiteAdapter#getSchema()
     */
    public static final String COL_IDSERVER     = "idServer";

    /**
     * name of Category's column name
     * @see CategorySQLiteAdapter#getSchema()
     */
    public static final String COL_NAME         = "name";

    /**
     * name of Category's column updatedAt
     * @see CategorySQLiteAdapter#getSchema()
     */
    public static final String COL_UPDATEDAT    = "updatedAt";

    /**
     * name of context for CategorySQLiteAdapter's contructor
     * @see CategorySQLiteAdapter#CategorySQLiteAdapter(Context)
     */
    private Context context;

    /**
     * name of SQLiteDatabase object
     */
    private MyQCMSqLiteOpenHelper helper;

    /**
     * name of MyQCMSqLiteOpenHelper object
     */
    private SQLiteDatabase database;
    //endregion

    //region METHOD

    /**
     * CategorySQLiteAdapter's constructor
     * @param context
     */
    public CategorySQLiteAdapter(Context context) {
        this.context = context;
        this.helper = new MyQCMSqLiteOpenHelper(context,MyQCMSqLiteOpenHelper.DB_NAME,null,1);
    }

    /**
     * Script SQLite to create Category's table data in my_qcm database stored in device
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_CATEGORY + " ("
                + COL_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IDSERVER  + " INTEGER NOT NULL, "
                + COL_NAME      + " TEXT NOT NULL, "
                + COL_UPDATEDAT + " TEXT NULL);";
    }

    /**
     * Open Category database that will be used for reading and writing
     */
    public void open(){
        this.database = this.helper.getWritableDatabase();
    }

    /**
     * Close Category database
     */
    public void close(){
        this.database.close();
    }

    /**
     * Insert category in database
     * @param category
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(Category category){
        return database.insert(TABLE_CATEGORY, null, this.categoryToContentValues(category));
    }

    /**
     * Delete category
     * @param category
     * @return the number of rows affected
     */
    public long delete(Category category){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(category.getId())};
        return this.database.delete(TABLE_CATEGORY, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update category
     * @param category
     * @return the number of rows affected
     */
    public long update(Category category) {
        ContentValues valuesUpdate = this.categoryToContentValues(category);
        String whereClausesUpdate = COL_ID + "=?";
        String[] whereArgsUpdate = {String.valueOf(category.getId())};
        return database.update(TABLE_CATEGORY, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Get category by id
     * @param id
     * @return category object
     */
    public Category getCategoryById(int id){

        //Create SQLite query and execute query
        //---------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_NAME, COL_UPDATEDAT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor cursor = database.query(TABLE_CATEGORY, columns, whereClausesSelect, whereArgsSelect, null, null, null);

        //Create category object
        //------------------
        Category result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Convert Cursor to Category Object
     * @param cursor
     * @return Category object
     */
    private Category cursorToItem(Cursor cursor) {
        //Get Category attributes
        //-------------------
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int idServer = cursor.getInt(cursor.getColumnIndex(COL_IDSERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));

        //Manage Date format
        //------------------
        Date updatedAt = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            updatedAt = simpleDateFormat.parse(cursor.getString((cursor.getColumnIndex(COL_UPDATEDAT))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Create Category object
        //------------------
        Category result = new Category(idServer,name,updatedAt);
        return result;
    }

    /**
     * Convert category to ContentValues
     * @param category
     * @return contentValue
     */
    private ContentValues categoryToContentValues(Category category) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, category.getId());
        values.put(COL_IDSERVER, category.getIdServer());
        values.put(COL_NAME, category.getName());
        values.put(COL_UPDATEDAT, category.getUpdatedAt().toString());
        return values;
    }

    //endregion



}
