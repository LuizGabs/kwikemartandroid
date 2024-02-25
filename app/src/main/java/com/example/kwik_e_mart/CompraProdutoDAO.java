package com.example.kwik_e_mart;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class CompraProdutoDAO {
    private Context context;
    private SQLiteDatabase database;

    public CompraProdutoDAO(Context context) {
        this.context = context;
        this.database = (new DatabaseComprasProdutos(context)).getWritableDatabase();
    }
    public ArrayList<Produto> getList() {
        ArrayList<Produto> result = new ArrayList<Produto>();
        String sql = "SELECT * FROM comprasprodutos";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int codbarras = cursor.getInt(1);
            String nome = cursor.getString(2);
            Double valor = cursor.getDouble(3);
            String desc = cursor.getString(5);
            int quant = cursor.getInt(6);
            String unidade = cursor.getString(7);
            result.add(new Produto(id, codbarras, nome, valor, desc, quant, unidade));
        }

        return result;
    }

    public double getComprado(int id){
        String sql = "SELECT * FROM comprasprodutos WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            double comprado = cursor.getDouble(4);
            return comprado;
        }else{
            return 0;
        }
    }

    public double gastos(){
        String sql = "SELECT * FROM comprasprodutos";
        Cursor cursor = database.rawQuery(sql, null);

        double gastos = 0;
        while (cursor.moveToNext()) {
            gastos += cursor.getDouble(4);
        }
        return gastos;
    }
    public int getCodbarras(int id){
        String sql = "SELECT * FROM comprasprodutos WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        int codbarras=0;
        if (cursor.moveToNext()) {
            codbarras = cursor.getInt(1);
        }

        return codbarras;
    }

    public double gastoEspecifico(int codbarras){
        String sql = "SELECT * FROM comprasprodutos WHERE codbarras=" + codbarras;
        Cursor cursor = database.rawQuery(sql, null);
        double gastos = 0;
        while (cursor.moveToNext()){
            gastos += cursor.getDouble(4);
        }
        return gastos;
    }
    public boolean add(Produto produto, double comprado) {
        String sql = "INSERT INTO comprasprodutos VALUES (NULL, '" + produto.getCodbarras()
                +"', '" + produto.getNome() + "', '" + produto.getValor() + "', '" + comprado*produto.getQuant() + "', '"+ produto.getDesc()
                + "', '" + produto.getQuant() + "', '" + produto.getUnidade() + "')";

        try {
            database.execSQL(sql);
            Toast.makeText(context, "Produto Adicionado as Compras!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean update(Produto produto, double comprado) {
        String sql = "UPDATE comprasprodutos SET "
                + "codbarras='" + produto.getCodbarras() + "', "
                + "nome='" + produto.getNome() + "', "
                + "valor='" + produto.getValor() + "', "
                + "comprado='" + comprado + "', "
                + "descricao='" + produto.getDesc() + "', "
                + "quantidade='" + produto.getQuant() + "', "
                + "unidade='" + produto.getUnidade() + "' "
                + "WHERE id=" + produto.getId();
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

    public Produto get(int id) {
        String sql = "SELECT * FROM comprasprodutos WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            int codbarras = cursor.getInt(1);
            String nome = cursor.getString(2);
            Double valor = cursor.getDouble(3);
            String desc = cursor.getString(5);
            int quant = cursor.getInt(6);
            String unidade = cursor.getString(7);
            return new Produto(id, codbarras, nome, valor, desc, quant, unidade);
        }

        return null;
    }


    public class DatabaseComprasProdutos extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 5;
        public static final String DATABASE_NAME = "ComprasProdutos.db";
        private static final String SQL_CREATE_PASS = "CREATE TABLE comprasprodutos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, codbarras INTEGER, nome TEXT," +
                "valor REAL, comprado REAL, descricao TEXT, quantidade INTEGER, unidade TEXT)";
        private static final String SQL_POPULATE_PASS = "INSERT INTO comprasprodutos VALUES " +
                "(NULL, 123, 'Teste', 12.34, 12.3, 'Nota de Teste', 1234, 'Teste')";
        private static final String SQL_DELETE_PASS = "DROP TABLE IF EXISTS comprasprodutos";

        public DatabaseComprasProdutos(Context context) {
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
