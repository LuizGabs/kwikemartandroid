package com.example.kwik_e_mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SellActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProdutosSellAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        recyclerView = findViewById(R.id.produtosVendaList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProdutosSellAdapter(this);
        recyclerView.setAdapter(adapter);

    }
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }

    public void finalizarClicado(View view){
        Intent intent = new Intent(this, VendaFinalActivity.class);
        startActivity(intent);
    }

    class ProdutosSellAdapter extends RecyclerView.Adapter<ProdutosVendaViewHolder> {
        private Context context;
        private ArrayList<Produto> produtos;
        ProdutoDAO produtoDAO;

        public ProdutosSellAdapter(Context context) {
            this.context = context;
            produtoDAO = new ProdutoDAO(context);
            update();
        }

        public void update() { produtos = produtoDAO.getList(); }

        public ProdutosVendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.venda_produto_item, parent, false);
            ProdutosVendaViewHolder vh = new ProdutosVendaViewHolder(v, context);
            return vh;
        }

        public void onBindViewHolder(ProdutosVendaViewHolder holder, int position) {
            holder.name.setText(produtos.get(position).getNome());
            holder.desc.setText(produtos.get(position).getDesc());
            holder.id = produtos.get(position).getId();
        }

        public int getItemCount() { return produtos.size(); }
    }
    class ProdutosVendaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView desc, name;
        public int id;

        public ProdutosVendaViewHolder(ConstraintLayout v, Context context) {
            super(v);
            this.context = context;
            name = v.findViewById(R.id.productName);
            desc = v.findViewById(R.id.productValor);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            Intent intent = new Intent(context, ProdutoVendaActivity.class);
            intent.putExtra("produtoId", this.id);
            context.startActivity(intent);
        }

    }
}