package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.iia.cdsmat.myqcm.entity.Team;

/**
 * SQLite Adpater managing Team database table
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class TeamSQLiteAdapter {

    //region ATTRIBUTE
    /**
     * Constant name's database table
     * @see TeamSQLiteAdapter#getSchema()
     */
    public static final String TABLE_TEAM   = "team";

    /**
     * name of Team's column id (local database)
     * @see TeamSQLiteAdapter#getSchema()
     */
    public static final String COL_ID       = "id";

    /**
     * name of Team's column id (distant server database)
     * @see TeamSQLiteAdapter#getSchema()
     */
    public static final String COL_IDSERVER = "idserver";

    /**
     * name of Team's column name
     * @see TeamSQLiteAdapter#getSchema()
     */
    public static final String COL_NAME     = "name";

    /**
     * name of Team's column UpdatedAt
     * @see TeamSQLiteAdapter#getSchema()
     */
    public static final String COL_UPDATEDAT = "UpdatedAt";

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
     * TeamSQLiteAdapter's Constructor
     * @param context
     */
    public TeamSQLiteAdapter(Context context){
        helper = new MyQCMSqLiteOpenHelper(context,MyQCMSqLiteOpenHelper.DB_NAME,null,1);
    }

    /**
     * Script SQLite to create Team's table data in my_qcm database stored in device
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_TEAM + " ("
                + COL_ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IDSERVER      + " INTEGER NOT NULL, "
                + COL_NAME          + " TEXT NOT NULL, "
                + COL_UPDATEDAT     + " TEXT NULL);";
    }

    /**
     * Open Team database that will be used for reading and writing
     */
    public void open(){
        this.database = this.helper.getWritableDatabase();
    }

    /**
     * Close Team database
     */
    public void close(){
        this.database.close();
    }

    /**
     * Insert team in database
     * @param team
     * @return line number's result
     */
    public long insert(Team team){
        return database.insert(TABLE_TEAM, null, this.teamToCntentValues(team));
    }

    /**
     * Delete team
     * @param team
     * @return line number's result
     */
    public long delete(Team team){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(team.getId())};

        return this.database.delete(TABLE_TEAM, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update team
     * @param team
     * @return line number's result
     */
    public long update(Team team) {
        ContentValues valuesUpdate = this.teamToCntentValues(team);
        String whereClausesUpdate = COL_ID + "=?";
        String[] whereArgsUpdate = {String.valueOf(team.getId())};

        return database.update(TABLE_TEAM, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Get team by id
     * @param id
     * @return Team object
     */
    public Team getTeamById(int id){

        //Create SQLite query and execute query
        //---------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_NAME, COL_UPDATEDAT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor cursor = database.query(TABLE_TEAM, columns, whereClausesSelect, whereArgsSelect, null, null, null);

        //Create team object
        //------------------
        Team result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Convert Cursor to Team object
     * @param cursor
     * @return Team object
     */
    private Team cursorToItem(Cursor cursor) {
        //Get Team attributes
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

        //Create Team object
        //------------------
        Team result = new Team(id, idServer,name,updatedAt);
        return result;
    }

    /**
     * Convert team to ContentValues
     * @param team
     * @return ContentValue
     */
    private ContentValues teamToCntentValues(Team team) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, team.getId());
        values.put(COL_IDSERVER, team.getIdServer());
        values.put(COL_NAME, team.getName());
        values.put(COL_UPDATEDAT, team.getUpdatedAt().toString());
        return values;
    }
    //endregion
}
