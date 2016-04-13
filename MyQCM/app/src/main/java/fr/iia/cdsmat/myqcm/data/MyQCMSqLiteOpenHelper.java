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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
