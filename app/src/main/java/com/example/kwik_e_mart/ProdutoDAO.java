package com.example.kwik_e_mart;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class ProdutoDAO {
    private Context context;
    private SQLiteDatabase database;


    public ProdutoDAO(Context context) {
        this.context = context;
        this.database = (new DatabaseProdutos(context)).getWritableDatabase();
    }
    public ArrayList<Produto> getList() {
        ArrayList<Produto> result = new ArrayList<Produto>();
        String sql = "SELECT * FROM produtos ORDER BY nome";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int codbarras = cursor.getInt(1);
            String nome = cursor.getString(2);
            Double valor = cursor.getDouble(3);
            String desc = cursor.getString(4);
            int quant = cursor.getInt(5);
            String unidade = cursor.getString(6);
            result.add(new Produto(id, codbarras, nome, valor, desc, quant, unidade));
        }

        return result;
    }

    public boolean add(Produto produto) {
        String sql = "INSERT INTO produtos VALUES (NULL, '" + produto.getCodbarras()
                +"', '" + produto.getNome() + "', '" + produto.getValor() + "', '" + produto.getDesc()
                + "', '" + produto.getQuant() + "', '" + produto.getUnidade() + "')";

        try {
            database.execSQL(sql);
            Toast.makeText(context, "Produto salvo!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean update(Produto produto) {
        String sql = "UPDATE produtos SET "
                + "nome='" + produto.getNome() + "', "
                + "valor='" + produto.getValor() + "', "
                + "descricao='" + produto.getDesc() + "', "
                + "quantidade='" + produto.getQuant() + "', "
                + "unidade='" + produto.getUnidade() + "' "
                + "WHERE codbarras=" + produto.getCodbarras();
        try {
            database.execSQL(sql);
            Toast.makeText(context, "Produto atualizado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public int getQuant(int codbarras){
        String sql = "SELECT * FROM produtos WHERE codbarras=" + codbarras;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()){
            return cursor.getInt(5);
        }
        else{
            return 0;
        }
    }

    public String getDesc(int codbarras){
        String sql = "SELECT * FROM produtos WHERE codbarras=" + codbarras;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()){
            return cursor.getString(4);
        }else{
            return null;
        }
    }
    public String getUnid(int codbarras){
        String sql = "SELECT * FROM produtos WHERE codbarras=" + codbarras;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()){
            return cursor.getString(6);
        }else{
            return null;
        }
    }
    public boolean remover(Produto produto){
        String sql = "DELETE FROM produtos WHERE id=" + produto.getId();
        try {
            database.execSQL(sql);
            Toast.makeText(context, "Produto removido!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public Produto get(int id) {
        String sql = "SELECT * FROM produtos WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            int codbarras = cursor.getInt(1);
            String nome = cursor.getString(2);
            Double valor = cursor.getDouble(3);
            String desc = cursor.getString(4);
            int quant = cursor.getInt(5);
            String unidade = cursor.getString(6);
            return new Produto(id, codbarras, nome, valor, desc, quant, unidade);
        }

        return null;
    }


    public class DatabaseProdutos extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 6;
        public static final String DATABASE_NAME = "Produtos.db";
        private static final String SQL_CREATE_PASS = "CREATE TABLE produtos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, codbarras INTEGER, nome TEXT," +
                "valor REAL, descricao TEXT, quantidade INTEGER, unidade TEXT)";
        private static final String SQL_POPULATE_PASS = "INSERT INTO produtos VALUES " +
                "(NULL, 123, 'Teste', 12.34, 'Nota de Teste', 1234, 'Teste')";
        private static final String SQL_DELETE_PASS = "DROP TABLE IF EXISTS produtos";

        public DatabaseProdutos(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_PASS);
            //db.execSQL(SQL_POPULATE_PASS);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_PASS);
            onCreate(db);
        }
    }


}
