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
 * Created by Antoine Trouvé on 06/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class CategorySQLiteAdapter {

    //region ATTRIBUTES
    public static final String TABLE_CATEGORY   = "category";
    public static final String COL_ID           = "id";
    public static final String COL_IDSERVER     = "idServer";
    public static final String COL_NAME         = "name";
    public static final String COL_UPDATEDAT    = "updatedAt";

    private MyQCMSqLiteOpenHelper helper;
    private SQLiteDatabase database;
    //endregion

    //region METHOD

    /**
     * CategorySQLiteAdapter's constructor
     * @param context
     */
    public CategorySQLiteAdapter(Context context) {
        helper = new MyQCMSqLiteOpenHelper(context,MyQCMSqLiteOpenHelper.DB_NAME,null,1);
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
     * Insert category in database
     * @param category
     * @return line number's result
     */
    public long insert(Category category){
        return database.insert(TABLE_CATEGORY, null, this.categoryToContentValues(category));
    }

    /**
     * Delete category
     * @param category
     * @return line number's result
     */
    public long delete(Category category){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(category.getId())};

        return this.database.delete(TABLE_CATEGORY, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update category
     * @param category
     * @return line number's result
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
        Category result = new Category(id, idServer,name,updatedAt);
        return result;
    }

    /**
     * Convert category to COntentValues
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
