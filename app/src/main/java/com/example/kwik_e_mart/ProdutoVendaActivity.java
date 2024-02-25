package com.example.kwik_e_mart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ProdutoVendaActivity extends AppCompatActivity {

    private ProdutoDAO produtoDAO;
    private ProdutoVendaDAO produtoVendaDAO;
    private ProdutoVendaArrayDAO produtoVendaArrayDAO;
    private int produtoId;
    private TextView editNome, editValor, editQuantidade, editCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_venda);

        editNome = findViewById(R.id.addProdutoVendaNome);
        editValor = findViewById(R.id.addProdutoVendaValor);
        editQuantidade = findViewById(R.id.addProdutoVendaQuantidade);
        editCodigo = findViewById(R.id.addProdutoVendaCodigo);
        produtoDAO = new ProdutoDAO(this);
        produtoVendaDAO = new ProdutoVendaDAO(this);
        produtoVendaArrayDAO = new ProdutoVendaArrayDAO(this);

        Intent intent = getIntent();
        produtoId = intent.getIntExtra("produtoId", -1);

        // Verifica se um produto foi passado como parâmetro
        if (produtoId != -1) {
            Produto produto = produtoDAO.get(produtoId);
            editNome.setText(produto.getNome());
            String value = String.valueOf(produto.getValor());
            editValor.setText(value);
            String valueCod = String.valueOf(produto.getCodbarras());
            editCodigo.setText(valueCod);
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuvenda, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save){
            int codbarras = Integer.parseInt(editCodigo.getText().toString());
            double valorProd = Double.parseDouble(editValor.getText().toString())*Double.parseDouble(editQuantidade.getText().toString());
            int quant = produtoDAO.getQuant(Integer.parseInt(editCodigo.getText().toString())) - Integer.parseInt(editQuantidade.getText().toString());
            String desc = produtoDAO.getDesc(Integer.parseInt(editCodigo.getText().toString()));
            String unid = produtoDAO.getUnid(Integer.parseInt(editCodigo.getText().toString()));
            String nome = editNome.getText().toString();
            int quan = Integer.parseInt(editQuantidade.getText().toString());
            double valorInic = Double.parseDouble(editValor.getText().toString());
            ProdutoVenda produto = new ProdutoVenda(produtoId, codbarras, nome, valorProd, quan);
            ProdutoVenda produtoum = new ProdutoVenda(produtoId, codbarras, nome, valorInic, quan);
            Produto produtonew = new Produto(produtoId, codbarras, nome, valorInic, desc, quant, unid);
            produtoDAO.update(produtonew);
            boolean result;
            result = produtoVendaDAO.add(produtoum);
            produtoVendaArrayDAO.addVendaAtual(produto);
            if (result) finish();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

}