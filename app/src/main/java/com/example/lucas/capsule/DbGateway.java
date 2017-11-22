package com.example.lucas.capsule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lucas on 20/11/2017.
 */
// DbGateway, utilizado para realizar conexoes com a base de dados (Bruxaria? Vai saber xD)
    // Utilizado Design Pattern Singleton, pois ele garante que exista somente um cliente de banco de dados
public class DbGateway {

    private static DbGateway gw;
    private SQLiteDatabase db;

    private DbGateway(Context ctx){
        DbHelper helper = new DbHelper(ctx);
        db = helper.getWritableDatabase();
    }

    public static DbGateway getInstance(Context ctx){
        if(gw == null)
            gw = new DbGateway(ctx);
        return gw;
    }

    public SQLiteDatabase getDatabase(){
        return this.db;
    }
}
