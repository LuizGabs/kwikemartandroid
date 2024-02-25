package com.example.kwik_e_mart;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class UsuarioDAO {
    private Context context;
    private SQLiteDatabase database;

    public UsuarioDAO(Context context) {
        this.context = context;
        this.database = (new DatabaseUsers(context)).getWritableDatabase();
    }
    public ArrayList<Usuario> getList() {
        ArrayList<Usuario> result = new ArrayList<Usuario>();
        String sql = "SELECT * FROM usuarios ORDER BY nome";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            String login = cursor.getString(2);
            String senha = cursor.getString(3);
            String notas = cursor.getString(4);
            result.add(new Usuario(id, nome, login, senha, notas));
        }

        return result;
    }
    public boolean add(Usuario usuario) {
        String sql = "INSERT INTO usuarios VALUES (NULL, "
                + "'" + usuario.getName() + "', "
                + "'" + usuario.getLogin() + "', "
                + "'" + usuario.getPassword() + "', "
                + "'" + usuario.getNotes() + "')";
        try {
            database.execSQL(sql);
            Toast.makeText(context, "Usuario salvo!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean update(Usuario usuario) {
        String sql = "UPDATE usuarios SET "
                + "nome='" + usuario.getName() + "', "
                + "login='" + usuario.getLogin() + "', "
                + "senha='" + usuario.getPassword() + "', "
                + "notas='" + usuario.getNotes() + "' "
                + "WHERE id=" + usuario.getId();
        try {
            database.execSQL(sql);
            Toast.makeText(context, "Usuario atualizado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean remover(Usuario usuario){
        String sql = "DELETE FROM usuarios WHERE id=" + usuario.getId();
        try {
            database.execSQL(sql);
            Toast.makeText(context, "Usuario removido!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public Usuario get(int id) {
        String sql = "SELECT * FROM usuarios WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            String nome = cursor.getString(1);
            String login = cursor.getString(2);
            String senha = cursor.getString(3);
            String notas = cursor.getString(4);
            return new Usuario(id, nome, login, senha, notas);
        }

        return null;
    }
    public class DatabaseUsers extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 6;
        public static final String DATABASE_NAME = "Usuarios.db";
        private static final String SQL_CREATE_PASS = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, login TEXT," +
                "senha TEXT, notas TEXT)";
        private static final String SQL_POPULATE_PASS = "INSERT INTO usuarios VALUES " +
                "(NULL, 'Admin', 'admin', 'admin123', 'Nota de Teste')";
        private static final String SQL_DELETE_PASS = "DROP TABLE IF EXISTS usuarios";


        public DatabaseUsers(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_PASS);
            db.execSQL(SQL_POPULATE_PASS);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_PASS);
            onCreate(db);
        }
    }

}
