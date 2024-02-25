package com.example.kwik_e_mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VendaFinalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProdutosFinalAdapter adapter;
    private ProdutoVendaArrayDAO vendaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda_final);

        recyclerView = findViewById(R.id.produtosFinalList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProdutosFinalAdapter(this);
        recyclerView.setAdapter(adapter);

    }
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }

    public void totalClicado(View view){
        vendaDAO = new ProdutoVendaArrayDAO(this);
        double valor = vendaDAO.vendaTotal(vendaDAO.getListVendaAtual());
        Toast.makeText(this, "Valor Total: R$ " + valor, Toast.LENGTH_LONG)
                .show();
    }
    public void finalVendaClicado(View view){
        EditText editText = findViewById(R.id.inputValor);
        vendaDAO = new ProdutoVendaArrayDAO(this);
        double valor = vendaDAO.vendaTotal(vendaDAO.getListVendaAtual());
        double valorRecebido = Double.parseDouble(editText.getText().toString());
        double troco = valorRecebido - valor;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Venda Finalzada. Troco: " + troco)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        // Clique no botão "Ok" para encerrar a Activity
                        finish();
                    }
                })
                .show();
        vendaDAO.limparLista();
    }

    class ProdutosFinalAdapter extends RecyclerView.Adapter<ProdutosVendaFinalViewHolder> {
        private Context context;
        private ArrayList<ProdutoVenda> produtos;
        ProdutoVendaArrayDAO produtoDAO;

        public ProdutosFinalAdapter(Context context) {
            this.context = context;
            produtoDAO = new ProdutoVendaArrayDAO(context);
            update();
        }

        public void update() { produtos = produtoDAO.getListVendaAtual(); }

        public ProdutosVendaFinalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.venda_finalizada_item, parent, false);
            ProdutosVendaFinalViewHolder vh = new ProdutosVendaFinalViewHolder(v, context);
            return vh;
        }

        public void onBindViewHolder(ProdutosVendaFinalViewHolder holder, int position) {
            holder.name.setText(produtos.get(position).getNome());
            holder.value.setText(Double.toString(produtos.get(position).getValor()));
            holder.id = produtos.get(position).getId();
        }

        public int getItemCount() { return produtos.size(); }
    }
    class ProdutosVendaFinalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView name, value;
        public int id;

        public ProdutosVendaFinalViewHolder(ConstraintLayout v, Context context) {
            super(v);
            this.context = context;
            name = v.findViewById(R.id.productName);
            value = v.findViewById(R.id.productValor);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {
        }

    }
}