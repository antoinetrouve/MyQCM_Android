package fr.iia.cdsmat.myqcm.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Antoine Trouvé on 06/04/2016.
 * antoinetrouve.france@gmail.com
 */
public class MyQCMSqLiteOpenHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "myqcm.sqlite";

    //region CONSTRUCTOR
    public MyQCMSqLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyQCMSqLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    //endregion

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL(AnswerSQLiteAdapter.getschema());
        db.execSQL(CategorySQLiteAdapter.getschema());
        db.execSQL(McqSQLiteAdapter.getschema());
        db.execSQL(MediaSQLiteAdapter.getschema());
        db.execSQL(QuestionSQLiteAdapter.getschema());
        db.execSQL(ResultSQLiteAdapter.getschema());
        db.execSQL(TeamSQLiteAdapter.getschema());
        db.execSQL(TypeMediaSQLiteAdapter.getschema());
        db.execSQL(UserSQLiteAdapter.getschema());*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
