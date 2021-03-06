package fr.iia.cdsmat.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import fr.iia.cdsmat.myqcm.entity.User;

/**
 * SQLite Adpater managing User database table
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class UserSQLiteAdapter {

    //region ATTRIBUTE
    /**
     * Constant name's database table
     * @see UserSQLiteAdapter#getSchema()
     */
    public static final String TABLE_USER    = "user";

    /**
     * name of User's column id (local database)
     * @see UserSQLiteAdapter#getSchema()
     */
    public static final String COL_ID        = "id";

    /**
     * name of USer's column id (distant server database)
     * @see UserSQLiteAdapter#getSchema()
     */
    public static final String COL_IDSERVER  = "idserver";

    /**
     * name of User's column username
     * @see UserSQLiteAdapter#getSchema()
     */
    public static final String COL_USERNAME  = "username";

    /**
     * name of User's column email
     * @see UserSQLiteAdapter#getSchema()
     */
    public static final String COL_EMAIL     = "email";

    /**
     * name of User's column lastlogin
     * @see UserSQLiteAdapter#getSchema()
     */
    public static final String COL_LASTLOGIN = "lastlogin";

    /**
     * name of User's column password
     * @see UserSQLiteAdapter#getSchema()
     */
    public static final String COL_PASSWORD  = "password";

    /**
     * name of User's column teamId
     * @see UserSQLiteAdapter#getSchema()
     */
    public static final String COL_TEAMID    = "teamId";

    /**
     * name of User's column createdAt
     * @see UserSQLiteAdapter#getSchema()
     */
    public static final String COL_CREATEDAT = "createdAt";

    /**
     * name of User's column updatedAt
     * @see UserSQLiteAdapter#getSchema()
     */
    public static final String COL_UPDATEDAT = "updatedAt";

    /**
     * name of SQLiteDatabase object
     */
    private SQLiteDatabase          database;

    /**
     * name of MyQCMSqLiteOpenHelper object
     */
    private MyQCMSqLiteOpenHelper   helper;

    /**
     * name of context for UserSQLiteAdapter's contructor
     * @see UserSQLiteAdapter#UserSQLiteAdapter(Context)
     */
    private Context context;
    //endregion

    //region METHOD

    /**
     * Helper object to create database
     * @param context
     */
    public UserSQLiteAdapter(Context context){
        this.context = context;
        this.helper = new MyQCMSqLiteOpenHelper(context,MyQCMSqLiteOpenHelper.DB_NAME,null,1);
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
                + COL_UPDATEDAT + " TEXT NOT NULL);";
    }

    /**
     * Open User database that will be used for reading and writing
     */
    public void open(){
        this.database = this.helper.getWritableDatabase();
    }

    /**
     * Close User database
     */
    public void close(){
        this.database.close();
    }

    /**
     * Insert user in database
     * @param user
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(User user){
        return database.insert(TABLE_USER, null, this.userToContentValues(user));
    }

    /**
     * Delete user
     * @param user
     * @return the number of rows affected
     */
    public long delete(User user){
        String whereClausesDelete = COL_ID + "=?";
        String[] whereArgsDelete = {String.valueOf(user.getId())};

        return this.database.delete(TABLE_USER, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update user
     * @param user
     * @return the number of rows affected
     */
    public long update(User user) {
        ContentValues valuesUpdate = this.userToContentValues(user);
        String whereClausesUpdate = COL_IDSERVER + "=?";
        String[] whereArgsUpdate = {String.valueOf(user.getIdServer())};

        return database.update(TABLE_USER, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
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
                COL_LASTLOGIN, COL_TEAMID, COL_UPDATEDAT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor cursor = database.query(TABLE_USER, columns, whereClausesSelect, whereArgsSelect, null, null, null);

        //Create user object
        //------------------
        User result = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get User by idServer
     * @param idServer
     * @return User
     */
    public User getUserByIdServer(int idServer){
        //Create SQLite query and execute query
        //-------------------------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_USERNAME, COL_EMAIL, COL_PASSWORD,
                COL_LASTLOGIN, COL_TEAMID, COL_UPDATEDAT};
        String whereClausesSelect = COL_IDSERVER + "= ?";
        String[] whereArgsSelect = {String.valueOf(idServer)};

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

    /**
     * Get User by login and password
     * @param login
     * @param password
     * @return user object
     */
    public User getUserByLoginPassword(String login, String password){

        //Create SQLite query and execute query
        //-------------------------------------
        String[] columns = {COL_ID, COL_IDSERVER, COL_USERNAME, COL_EMAIL, COL_PASSWORD,
                COL_LASTLOGIN, COL_TEAMID, COL_UPDATEDAT};
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

        return result;
    }

    /**
     * Get all User
     * @return ArrayList<>
     */
    public ArrayList<User> getAllUser(){
        ArrayList<User> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<User>();
            // add user into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Get all Cursor in User Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_IDSERVER, COL_USERNAME, COL_EMAIL,
                COL_LASTLOGIN, COL_PASSWORD,COL_TEAMID,COL_UPDATEDAT};
        Cursor cursor = database.query(TABLE_USER, cols, null, null, null, null, null);
        return cursor;
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        try {
            lastlogin = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(COL_LASTLOGIN)));
            updatedAt = simpleDateFormat.parse(cursor.getString((cursor.getColumnIndex(COL_UPDATEDAT))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Create User object
        //------------------
        User result = new User(idServer,username,password,email,lastlogin,updatedAt);
        if (teamId != 0){
            TeamSQLiteAdapter teamSQLiteAdapter = new TeamSQLiteAdapter(context);
            result.setTeam(teamSQLiteAdapter.getTeamById(teamId));
        }
        return result;
    }

    /**
     * Convert user to ContentValues
     * @param user
     * @return ContentValue
     */
    private ContentValues userToContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_IDSERVER, user.getIdServer());
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_LASTLOGIN, user.getLastLogin().toString());
        values.put(COL_UPDATEDAT, user.getUpdatedAt().toString());

        return values;
    }
    //endregion
}
