package com.example.team;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBgo extends SQLiteOpenHelper {//DB 클래스
    public static DBgo DBgo1=null;
    private SQLiteDatabase db;

    public static DBgo getInstance(Context context){ // 싱글턴 패턴으로 구현하였다. 이것을 통해서 어디든 사용가능
        if(DBgo1 == null){
            DBgo1 = new DBgo(context);
        }
        return DBgo1;
    }

    private DBgo(Context context) {
        super(context, "UserDB", null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IdPassword(gId CHAR(20) PRIMARY KEY,gPassword CHAR(20), gbuy CHAR(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
