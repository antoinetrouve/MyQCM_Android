package fr.iia.cdsmat.myqcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.iia.cdsmat.myqcm.data.MyQCMSqLiteOpenHelper;
import fr.iia.cdsmat.myqcm.entity.Category;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * SQLite Adpater managing Category database table
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class CategorySQLiteAdapterTest {

    //region ATTRIBUTES
    /**
     * Constant name's database table
     * @see CategorySQLiteAdapterTest#getSchema()
     */
    public static final String TABLE_CATEGORY   = "category";

    /**
     * name of Category's column id (local database)
     * @see CategorySQLiteAdapterTest#getSchema()
     */
    public static final String COL_ID           = "id";

    /**
     * name of Category's column id (distant server database)
     * @see CategorySQLiteAdapterTest#getSchema()
     */
    public static final String COL_IDSERVER     = "idServer";

    /**
     * name of Category's column name
     * @see CategorySQLiteAdapterTest#getSchema()
     */
    public static final String COL_NAME         = "name";

    /**
     * name of Category's column updatedAt
     * @see CategorySQLiteAdapterTest#getSchema()
     */
    public static final String COL_UPDATEDAT    = "updatedAt";

    /**
     * name of SQLiteDatabase object
     */
    private MyQCMSqLiteOpenHelper helper;

    /**
     * name of MyQCMSqLiteOpenHelper object
     */
    private SQLiteDatabase database;
    //endregion

    /**
     * Test Insert category in database
     * @return Category' id inserted
     */
   /* @Test
    public void insert(){
        Date date = new Date();
        Category category = new Category(1,1,"categoryName",date);

        ContentValues values = new ContentValues();
        values.put(COL_ID, category.getId());
        values.put(COL_IDSERVER, category.getIdServer());
        values.put(COL_NAME, category.getName());
        values.put(COL_UPDATEDAT, category.getUpdatedAt().toString());

        long id = database.insert(TABLE_CATEGORY, null, values);
        assertEquals(id,category.getId());
    }*/

    /**
     * Test Convert category to ContentValues
     */
    /*@Test
    public void categoryToContentValues() {
        Date date = new Date();
        Category cat = new Category(1,1,"NameCategory",date);

        ContentValues values = new ContentValues();
        values.put(COL_ID, cat.getId());
        values.put(COL_IDSERVER, cat.getIdServer());
        values.put(COL_NAME, cat.getName());
        values.put(COL_UPDATEDAT, cat.getUpdatedAt().toString());

        assertNull(values);
    }*/
    //endregion



}
