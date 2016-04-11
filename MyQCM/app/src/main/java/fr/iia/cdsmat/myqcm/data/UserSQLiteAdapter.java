package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import fr.iia.cdsmat.myqcm.entity.User;

/**
 * Created by Antoine Trouvé on 06/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class UserSQLiteAdapter {

    //region ATTRIBUTE
    public static final String TABLE_USER    = "user";
    public static final String COL_ID        = "id";
    public static final String COL_IDSERVER  = "idserver";
    public static final String COL_USERNAME  = "username";
    public static final String COL_EMAIL     = "email";
    public static final String COL_LASTLOGIN = "lastlogin";
    public static final String COL_PASSWORD  = "password";
    public static final String COL_TEAMID    = "teamId";
    public static final String COL_CREATEDAT = "createdAt";
    public static final String COL_UPDATEDAT = "updatedAt";

    private SQLiteDatabase          database;
    private MyQCMSqLiteOpenHelper   helper;
    //endregion

    //region METHOD

    /**
     * Helper object to create database
     * @param context
     */
    public UserSQLiteAdapter(Context context){
        helper = new MyQCMSqLiteOpenHelper(context,MyQCMSqLiteOpenHelper.DB_NAME,null,1);
    }

    /**
     * Script SQLite to create User's table data in my_qcm database stored in device
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_USER + " ("
                + COL_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IDSERVER  + " INTEGER NOT NULL, "
                + COL_USERNAME  + " TEXT NOT NULL, "
                + COL_EMAIL     + " TEXT NOT NULL, "
                + COL_LASTLOGIN + " TEXT NOT NULL, "
                + COL_PASSWORD  + " TEXT NOT NULL, "
                + COL_TEAMID    + " INTEGER NULL, "
                + COL_CREATEDAT + " TEXT NOT NULL, "
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
     * Insert user in database
     * @param user
     * @return line number's result
     */
    public long insert(User user){
        return database.insert(TABLE_USER, null, this.userToCntentValues(user));
    }

    /**
     * Delete user
     * @param user
     * @return line number's result
     */
    public long delete(User user){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(user.getId())};

        return this.database.delete(TABLE_USER, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Get User by id
     * @param id
     * @return User object
     */
    public User getUserById(int id){

        //Create SQLite query and execute query
        //---------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_USERNAME, COL_EMAIL, COL_PASSWORD,
            COL_LASTLOGIN, COL_TEAMID, COL_UPDATEDAT, COL_CREATEDAT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor cursor = database.query(TABLE_USER,columns,whereClausesSelect,whereArgsSelect,null,null,null);

        //Create user object
        //------------------
        User result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    public User getUserByLoginPassword(String login, String password){

        //Create SQLite query and execute query
        //-------------------------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_USERNAME, COL_EMAIL, COL_PASSWORD,
                COL_LASTLOGIN, COL_TEAMID, COL_UPDATEDAT, COL_CREATEDAT};
        String whereClauseSelect = COL_USERNAME + " =? AND " + COL_PASSWORD + " =?";
        String[] whereArgsSelect = {String.valueOf(login), String.valueOf(password)};

        Cursor cursor = database.query(TABLE_USER,columns,whereClauseSelect,whereArgsSelect,null,null,null);

        //Create User object
        //------------------
        User result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }

        return null;
    }

    /**
     * Convert Cursor to User object
     * @param cursor
     * @return User object
     */
    private User cursorToItem(Cursor cursor) {

        //Get user attributes
        //-------------------
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int idServer = cursor.getInt(cursor.getColumnIndex(COL_IDSERVER));
        int teamId = cursor.getInt(cursor.getColumnIndex(COL_TEAMID));
        String username = cursor.getString(cursor.getColumnIndex(COL_USERNAME));
        String email = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
        String password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));

        //Manage Date format
        //------------------
        Date createdAt = null;
        Date lastlogin = null;
        Date updatedAt = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            lastlogin = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(COL_LASTLOGIN)));
            createdAt = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(COL_CREATEDAT)));
            updatedAt = simpleDateFormat.parse(cursor.getString((cursor.getColumnIndex(COL_UPDATEDAT))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Create User object
        //------------------
        User result = new User(id, idServer,username,password,email,lastlogin,createdAt,updatedAt);

        return result;
    }

    /**
     * Convert user to ContentValues
     * @param user
     * @return ContentValue
     */
    private ContentValues userToCntentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, user.getId());
        values.put(COL_IDSERVER, user.getIdServer());
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_LASTLOGIN, user.getLastLogin().toString());
        values.put(COL_TEAMID, user.getTeam().getId());
        values.put(COL_CREATEDAT, user.getCreatedAt().toString());
        values.put(COL_UPDATEDAT, user.getUpdatedAt().toString());

        return values;
    }
    //endregion
}
