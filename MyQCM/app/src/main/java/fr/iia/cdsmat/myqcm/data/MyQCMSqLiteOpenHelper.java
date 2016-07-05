package fr.iia.cdsmat.myqcm.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper managing Mobile database
 * @author Antoine Trouve <antoinetrouve.france@gmail.com>
 * @version 1.0 - 04/04/2016
 */
public class MyQCMSqLiteOpenHelper extends SQLiteOpenHelper{

    /**
     * Constant name's mobile database
     */
    public static final String DB_NAME = "myqcm.sqlite";

    //region CONSTRUCTOR
    public MyQCMSqLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //endregion

    /**
     * onCreate() overrride method that create Mobile database
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AnswerSQLiteAdapter.getSchema());
        db.execSQL(CategorySQLiteAdapter.getSchema());
        db.execSQL(McqSQLiteAdapter.getSchema());
        db.execSQL(MediaSQLiteAdapter.getSchema());
        db.execSQL(QuestionSQLiteAdapter.getSchema());
        db.execSQL(ResultSQLiteAdapter.getSchema());
        db.execSQL(TeamSQLiteAdapter.getSchema());
        db.execSQL(TypeMediaSQLiteAdapter.getSchema());
        db.execSQL(UserSQLiteAdapter.getSchema());
    }



    /**
     * onUpgrade() override method that update Mobile database
     * @param db
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
