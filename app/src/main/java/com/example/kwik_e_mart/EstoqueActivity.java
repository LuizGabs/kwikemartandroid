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

public class EstoqueActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProdutosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque);

        recyclerView = findViewById(R.id.list_estoque);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProdutosAdapter(this);
        recyclerView.setAdapter(adapter);

    }
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }
    public void buttonAddProduto(View view) {
        Intent intent = new Intent(this, EditProdutoActivity.class);
        startActivity(intent);
    }


    class ProdutosAdapter extends RecyclerView.Adapter<ProdutosViewHolder> {
        private Context context;
        private ArrayList<Produto> produtos;
        ProdutoDAO produtoDAO;

        public ProdutosAdapter(Context context) {
            this.context = context;
            produtoDAO = new ProdutoDAO(context);
            update();
        }

        public void update() { produtos = produtoDAO.getList(); }

        public ProdutosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_estoque_item, parent, false);
            ProdutosViewHolder vh = new ProdutosViewHolder(v, context);
            return vh;
        }

        public void onBindViewHolder(ProdutosViewHolder holder, int position) {
            holder.name.setText(produtos.get(position).getNome());
            holder.desc.setText(produtos.get(position).getDesc());
            holder.id = produtos.get(position).getId();
        }

        public int getItemCount() { return produtos.size(); }
    }
    class ProdutosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView desc, name;
        public int id;

        public ProdutosViewHolder(ConstraintLayout v, Context context) {
            super(v);
            this.context = context;
            name = v.findViewById(R.id.productName);
            desc = v.findViewById(R.id.productValor);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            Intent intent = new Intent(context, EditProdutoActivity.class);
            intent.putExtra("produtoId", this.id);
            context.startActivity(intent);
        }

    }


}