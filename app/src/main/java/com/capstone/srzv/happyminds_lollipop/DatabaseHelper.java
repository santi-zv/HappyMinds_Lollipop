package com.capstone.srzv.happyminds_lollipop;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static Context c;
    private static String db_path ="/data/data/"+c.getPackageName()+"/databases/";
    private static String db_nombre ="hMindsLite";
    private static ArrayList<String> tabla_backUp = new ArrayList<String>();
    private static ArrayList<String> tabla_usuario = new ArrayList<String>();
    private static ArrayList<String> tabla_consejo = new ArrayList<String>();
    private static ArrayList<String> tabla_patologia = new ArrayList<String>();
    private static ArrayList<String> tabla_resEmocional = new ArrayList<String>();

    public static void crearTablas() {
        creartbackUp();
        creartUsuario();
        creartConsejo();
        creartPatologia();
        creartresEmocional();
    }
    private static void creartbackUp(){
        tabla_backUp.add(0,"movRegBU");
        tabla_backUp.add(1, "regId");
        tabla_backUp.add(2, "regFecha");
        tabla_backUp.add(3, "usId");
    }
    private static void creartUsuario(){
        tabla_usuario.add(0, "movUsLt");
        tabla_usuario.add(1, "usMail");
        tabla_usuario.add(2, "usId");
        tabla_usuario.add(3, "usPwd");
        tabla_usuario.add(4, "usLstLg");
        tabla_usuario.add(5, "usNom");
    }
    private static void creartConsejo(){
        tabla_consejo.add(0, "movConLt");
        tabla_consejo.add(1, "conId");
        tabla_consejo.add(2, "conTag");
    }
    private static void creartPatologia(){
        tabla_patologia.add(0, "movPatLt");
        tabla_patologia.add(1, "patId");
        tabla_patologia.add(2, "patNom");
        tabla_patologia.add(3, "patDesc");
    }
    private static void creartresEmocional(){
        tabla_resEmocional.add(0, "movResEmo");
        tabla_resEmocional.add(1, "resEmoId");
        tabla_resEmocional.add(2, "resEmoFecha");
        tabla_resEmocional.add(3, "resEmoEstado");
        tabla_resEmocional.add(4, "usId");
    }

    public DatabaseHelper(Context context){
        super(context,db_nombre,null,1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    public void onCreate(SQLiteDatabase db){
        crearTablas();
        try{
            db.openDatabase(db_path+db_nombre,null, SQLiteDatabase.CREATE_IF_NECESSARY);
            //Creacion de tabla movRegBU
            db.execSQL("CREATE TABLE IF NOT EXISTS"+tabla_usuario.get(0)+"("
                    +tabla_usuario.get(1)+"TEXT NOT NULL, "
                    +tabla_usuario.get(2)+"INTEGER PRIMARY KEY NOT NULL, "
                    +tabla_usuario.get(3)+"TEXT NOT NULL, "
                    +tabla_usuario.get(4)+"TEXT NOT NULL, "
                    +tabla_usuario.get(5)+"TEXT NOT NULL);");
            //Creacion de tabla movUsLt
            db.execSQL("CREATE TABLE IF NOT EXISTS"+tabla_backUp.get(0)+"("
                    +tabla_backUp.get(1)+"INTEGER PRIMARY KEY, "
                    +tabla_backUp.get(2)+"TEXT NOT NULL, "
                    +tabla_backUp.get(3)+"INTEGER NOT NULL, "
                    +"FOREIGN KEY("+tabla_backUp.get(3)+")"+" REFERENCES "+tabla_usuario.get(0)+"("+tabla_usuario.get(2)+");");
            //Creacion de tabla movConLt
            db.execSQL("CREATE TABLE IF NOT EXISTS"+tabla_consejo.get(0)+"("
                    +tabla_consejo.get(1)+"INTEGER PRIMARY KEY NOT NULL, "
                    +tabla_consejo.get(2)+"INTEGER NOT NULL);");
            //Creacion de tabla movPatLt
            db.execSQL("CREATE TABLE IF NOT EXISTS"+tabla_patologia.get(0)+"("
                    +tabla_patologia.get(1)+"INTEGER PRIMARY KEY NOT NULL, "
                    +tabla_patologia.get(2)+"TEXT NOT NULL, "
                    +tabla_patologia.get(3)+"TEXT NOT NULL);");
            db.execSQL("CREATE TABLE IF NOT EXISTS"+tabla_resEmocional.get(0)+"("
                    +tabla_resEmocional.get(1)+"INTEGER PRIMARY KEY NOT NULL, "
                    +tabla_resEmocional.get(2)+"TEXT NOT NULL, "
                    +tabla_resEmocional.get(3)+"INTEGER NOT NULL);"
                    +tabla_resEmocional.get(4)+"INTEGER NOT NULL);"
                    +"FOREIGN KEY("+tabla_resEmocional.get(4)+")"+" REFERENCES "+tabla_usuario.get(0)+"("+tabla_usuario.get(2)+");");

            db.close();
        }
        catch (SQLiteException sqlite){ Toast.makeText(c.getApplicationContext(), "Error en creación de base de datos", Toast.LENGTH_SHORT).show();  }
    }

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+tabla_patologia);
        db.execSQL("DROP TABLE IF EXISTS "+tabla_usuario);
        db.execSQL("DROP TABLE IF EXISTS "+tabla_backUp);
        db.execSQL("DROP TABLE IF EXISTS "+tabla_consejo);
        onCreate(db);
    }

    //Usuario
    public boolean insertUsuario(SQLiteDatabase db, String mail, int id, String pwd, String lastLog, String nombre){
        try{
            db = this.getWritableDatabase();
            db.execSQL("INSERT INTO "+tabla_usuario.get(0)
                    +"("+tabla_usuario.get(1)+", "
                    +"("+tabla_usuario.get(2)+", "
                    +"("+tabla_usuario.get(3)+", "
                    +"("+tabla_usuario.get(4)+", "
                    +"("+tabla_usuario.get(5)+") "
                    +"VALUES('"+mail+"', "+id+", '"+pwd+"', '"+lastLog+"', '"+nombre+"');");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean selectUsuario(SQLiteDatabase db){
        try{
            db = this.getWritableDatabase();
            db.execSQL("SELECT * FROM "+tabla_usuario.get(0)+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean selectUsuario(SQLiteDatabase db, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("SELECT * FROM "+tabla_usuario.get(0)+" WHERE "+tabla_usuario.get(2)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean updateUsuarioMail(SQLiteDatabase db, String mail, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("UPDATE INTO "+tabla_usuario.get(0)
                    +" SET "+tabla_usuario.get(1)+" = '"+mail+"',"
                    +" WHERE "+tabla_usuario.get(2)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean updateUsuarioLastLog(SQLiteDatabase db, String lastLog, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("UPDATE INTO "+tabla_usuario.get(0)
                    +" SET "+tabla_usuario.get(4)+" = '"+lastLog+"',"
                    +" WHERE "+tabla_usuario.get(2)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean updateUsuarioNom(SQLiteDatabase db, String nom, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("UPDATE INTO "+tabla_usuario.get(0)
                    +" SET "+tabla_usuario.get(5)+" = '"+nom+"',"
                    +" WHERE "+tabla_usuario.get(2)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    //BackUp
    public boolean insertbackUp(SQLiteDatabase db, int id, String fecha, int usId){
        try{
            db = this.getWritableDatabase();
            db.execSQL("INSERT INTO "+tabla_backUp.get(0)
                    +"("+tabla_backUp.get(1)+", "
                    +"("+tabla_backUp.get(2)+", "
                    +"("+tabla_backUp.get(3)+", "
                    +"VALUES("+id+", '"+fecha+"', "+usId+");");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean selectbackUp(SQLiteDatabase db){
        try{
            db = this.getWritableDatabase();
            db.execSQL("SELECT * FROM "+tabla_backUp.get(0)+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean selectbackUp(SQLiteDatabase db, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("SELECT * FROM "+tabla_backUp.get(0)+" WHERE "+tabla_backUp.get(1)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    //Patologia
    public boolean insertPatologia(SQLiteDatabase db, int id, String nom, String desc){
        try{
            db = this.getWritableDatabase();
            db.execSQL("INSERT INTO "+tabla_patologia.get(0)
                    +"("+tabla_patologia.get(1)+", "
                    +"("+tabla_patologia.get(2)+", "
                    +"("+tabla_patologia.get(3)+", "
                    +"VALUES("+id+", '"+nom+"', '"+desc+"');");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean selectPatologia(SQLiteDatabase db){
        try{
            db = this.getWritableDatabase();
            db.execSQL("SELECT * FROM "+tabla_patologia.get(0)+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean selectPatologia(SQLiteDatabase db, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("SELECT * FROM "+tabla_patologia.get(0)+" WHERE "+tabla_patologia.get(1)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean updatePatologiaNom(SQLiteDatabase db, String nom, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("UPDATE INTO "+tabla_patologia.get(0)
                    +" SET "+tabla_patologia.get(2)+" = '"+nom+"',"
                    +" WHERE "+tabla_patologia.get(1)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean updatePatologiaDesc(SQLiteDatabase db, String desc, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("UPDATE INTO "+tabla_patologia.get(0)
                    +" SET "+tabla_patologia.get(3)+" = '"+desc+"',"
                    +" WHERE "+tabla_patologia.get(1)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    //Consejo
    public boolean insertConsejo(SQLiteDatabase db, int id, int tag){
        try{
            db = this.getWritableDatabase();
            db.execSQL("INSERT INTO "+tabla_consejo.get(0)
                    +"("+tabla_consejo.get(1)+", "
                    +"("+tabla_consejo.get(2)+", "
                    +"VALUES("+id+", "+tag+");");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean selectConsejo(SQLiteDatabase db){
        try{
            db = this.getWritableDatabase();
            db.execSQL("SELECT * FROM "+tabla_consejo.get(0)+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean selectConsejo(SQLiteDatabase db, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("SELECT * FROM "+tabla_consejo.get(0)+" WHERE "+tabla_consejo.get(1)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean updateConsejoTag(SQLiteDatabase db, String tag, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("UPDATE INTO "+tabla_consejo.get(0)
                    +" SET "+tabla_consejo.get(2)+" = "+tag+","
                    +" WHERE "+tabla_consejo.get(1)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    //ResultadosEmocionales
    public boolean insertResEmocional(SQLiteDatabase db, int id, String fecha, int estado, int usId){
        try{
            db = this.getWritableDatabase();
            db.execSQL("INSERT INTO "+tabla_resEmocional.get(0)
                    +"("+tabla_resEmocional.get(1)+", "
                    +"("+tabla_resEmocional.get(2)+", "
                    +"("+tabla_resEmocional.get(3)+", "
                    +"("+tabla_resEmocional.get(4)+", "
                    +"VALUES("+id+", '"+fecha+"', "+estado+", "+usId+");");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean selectResEmocional(SQLiteDatabase db){
        try{
            db = this.getWritableDatabase();
            db.execSQL("SELECT * FROM "+tabla_resEmocional.get(0)+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }
    public boolean selectResEmocional(SQLiteDatabase db, int id){
        try{
            db = this.getWritableDatabase();
            db.execSQL("SELECT * FROM "+tabla_resEmocional.get(0)+" WHERE "+tabla_resEmocional.get(1)+" = "+id+";");
            db.close();
            return true;
        }
        catch(SQLiteException sql){
            if (!db.isOpen())
                db.close();
            return false;
        }
    }

    public Cursor getListadoEmocional(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+tabla_resEmocional.get(0),null);
        return data;
    }
}
