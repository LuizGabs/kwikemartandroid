package com.example.kwik_e_mart;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class ProdutoVendaDAO {
    private Context context;
    private SQLiteDatabase database;

    public ProdutoVendaDAO(Context context) {
        this.context = context;
        this.database = (new DatabaseVendasProdutos(context)).getWritableDatabase();
    }
    public ArrayList<ProdutoVenda> getList() {
        ArrayList<ProdutoVenda> result = new ArrayList<ProdutoVenda>();
        String sql = "SELECT * FROM vendasprodutos";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int codbarras = cursor.getInt(1);
            String nome = cursor.getString(2);
            Double valor = cursor.getDouble(3);
            int quant = cursor.getInt(4);
            result.add(new ProdutoVenda(id, codbarras, nome, valor, quant));
        }

        return result;
    }

    public double lucro(){
        String sql = "SELECT * FROM vendasprodutos";
        Cursor cursor = database.rawQuery(sql, null);

        double lucros=0;
        while (cursor.moveToNext()) {
            lucros += cursor.getDouble(3);
        }

        return lucros;
    }

    public double lucroEspecifico(int codbarras){
        String sql = "SELECT * FROM vendasprodutos WHERE codbarras=" + codbarras;
        Cursor cursor = database.rawQuery(sql, null);
        double lucros = 0;
        while (cursor.moveToNext()){
            lucros += cursor.getDouble(3);
        }
        return lucros;
    }

    public int getCodbarras(int id){
        String sql = "SELECT * FROM vendasprodutos WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        int codbarras=0;
        if (cursor.moveToNext()) {
            codbarras = cursor.getInt(1);
        }

        return codbarras;
    }
    public boolean add(ProdutoVenda produto) {
        String sql = "INSERT INTO vendasprodutos VALUES (NULL, '" + produto.getCodbarras()
                +"', '" + produto.getNome() + "', '" + produto.getValor()*produto.getQuant()
                + "', '" + produto.getQuant() + "')";

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

    public ProdutoVenda get(int id) {
        String sql = "SELECT * FROM vendasprodutos WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            int codbarras = cursor.getInt(1);
            String nome = cursor.getString(2);
            Double valor = cursor.getDouble(3);
            int quant = cursor.getInt(4);
            return new ProdutoVenda(id, codbarras, nome, valor, quant);
        }

        return null;
    }


    public class DatabaseVendasProdutos extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 6;
        public static final String DATABASE_NAME = "VendasProdutos.db";
        private static final String SQL_CREATE_PASS = "CREATE TABLE vendasprodutos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, codbarras INTEGER, nome TEXT," +
                "valor REAL, quantidade INTEGER)";
        private static final String SQL_POPULATE_PASS = "INSERT INTO vendasprodutos VALUES " +
                "(NULL, 123, 'Teste', 12.34, 1234)";
        private static final String SQL_DELETE_PASS = "DROP TABLE IF EXISTS vendasprodutos";

        public DatabaseVendasProdutos(Context context) {
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
