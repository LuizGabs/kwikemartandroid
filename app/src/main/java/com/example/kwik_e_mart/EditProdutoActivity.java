package com.example.kwik_e_mart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EditProdutoActivity extends AppCompatActivity {
    private ProdutoDAO produtoDAO;
    private CompraProdutoDAO compraDAO;
    private int produtoId;
    private TextView editNome, editValor, editQuantidade, editCodigo, editUnidade, editDesc, editValorComprado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produto);

        editNome = findViewById(R.id.addProdutoVendaNome);
        editValor = findViewById(R.id.addProdutoVendaValor);
        editQuantidade = findViewById(R.id.addProdutoVendaQuantidade);
        editCodigo = findViewById(R.id.addProdutoVendaCodigo);
        editUnidade = findViewById(R.id.addProdutoUnidade);
        editDesc = findViewById(R.id.addProdutoDesc);
        editValorComprado = findViewById(R.id.inputValorComprado);
        compraDAO = new CompraProdutoDAO(this);
        produtoDAO = new ProdutoDAO(this);

        Intent intent = getIntent();
        produtoId = intent.getIntExtra("produtoId", -1);

        // Verifica se um produto foi passado como parâmetro
        if (produtoId != -1) {
            Produto produto = produtoDAO.get(produtoId);
            editNome.setText(produto.getNome());
            String value = String.valueOf(produto.getValor());
            editValor.setText(value);
            String valueQ = String.valueOf(produto.getQuant());
            editQuantidade.setText(valueQ);
            String valueCod = String.valueOf(produto.getCodbarras());
            editCodigo.setText(valueCod);
            editUnidade.setText(produto.getUnidade());
            editDesc.setText(produto.getDesc());
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.estoque, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.remove){
            Produto produto = new Produto(produtoId, Integer.parseInt(editCodigo.getText().toString()), editNome.getText().toString(),
                    Double.parseDouble(editValor.getText().toString()), editDesc.getText().toString(),
                    Integer.parseInt(editQuantidade.getText().toString()), editUnidade.getText().toString());
            boolean result = produtoDAO.remover(produto);
            if (result) finish();
            return true;
        }
        else if (item.getItemId() == R.id.save){
            Produto produto = new Produto(produtoId, Integer.parseInt(editCodigo.getText().toString()), editNome.getText().toString(),
                    Double.parseDouble(editValor.getText().toString()), editDesc.getText().toString(),
                    Integer.parseInt(editQuantidade.getText().toString()), editUnidade.getText().toString());

            boolean result;
            if (produtoId == -1){
                if (produtoDAO.getQuant(Integer.parseInt(editCodigo.getText().toString())) != 0){
                    Produto compraexistente = new Produto(produtoId, Integer.parseInt(editCodigo.getText().toString()), editNome.getText().toString(),
                            Double.parseDouble(editValor.getText().toString()), editDesc.getText().toString(),
                            (Integer.parseInt(editQuantidade.getText().toString()) + produtoDAO.getQuant(Integer.parseInt(editCodigo.getText().toString()))), editUnidade.getText().toString());
                    produtoDAO.update(compraexistente);
                    result = compraDAO.add(produto, Double.parseDouble(editValorComprado.getText().toString()));
                }else{
                    produtoDAO.add(produto);
                    result = compraDAO.add(produto, Double.parseDouble(editValorComprado.getText().toString()));
                }
            }
            else                  result = produtoDAO.update(produto);

            if (result) finish();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }
}