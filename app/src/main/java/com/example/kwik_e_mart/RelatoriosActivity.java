package com.example.kwik_e_mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RelatoriosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewCompras;
    private ComprasAdapter adapter;
    private VendasAdapter adapterVendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        recyclerView = findViewById(R.id.list_vendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCompras = findViewById(R.id.list_compras);
        recyclerViewCompras.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ComprasAdapter(this);
        recyclerViewCompras.setAdapter(adapter);
        adapterVendas = new VendasAdapter(this);
        recyclerView.setAdapter(adapterVendas);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
        adapterVendas.update();
        adapterVendas.notifyDataSetChanged();
    }

    class ComprasAdapter extends RecyclerView.Adapter<ComprasViewHolder> {
        private Context context;
        private ArrayList<Produto> produtos;
        CompraProdutoDAO produtoDAO;

        public ComprasAdapter(Context context) {
            this.context = context;
            produtoDAO = new CompraProdutoDAO(context);
            update();
        }

        public void update() { produtos = produtoDAO.getList(); }

        public ComprasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.layout_item_produto, parent, false);
            ComprasViewHolder vh = new ComprasViewHolder(v, context);
            return vh;
        }

        public void onBindViewHolder(ComprasViewHolder holder, int position) {
            holder.nameDesc.setText(produtos.get(position).getNome() + " Q: " + produtos.get(position).getQuant());
            holder.valor.setText("R$" + produtoDAO.getComprado(produtos.get(position).getId()));
            holder.id = produtos.get(position).getId();
        }

        public int getItemCount() { return produtos.size(); }
    }
    class ComprasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView nameDesc, valor;
        public int id;

        public ComprasViewHolder(ConstraintLayout v, Context context) {
            super(v);
            this.context = context;
            nameDesc = v.findViewById(R.id.productName);
            valor = v.findViewById(R.id.productValor);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {

        }
    }

    class VendasAdapter extends RecyclerView.Adapter<VendasViewHolder> {
        private Context context;
        private ArrayList<ProdutoVenda> vendas;
        ProdutoVendaDAO vendaDAO;

        public VendasAdapter(Context context) {
            this.context = context;
            vendaDAO = new ProdutoVendaDAO(context);
            update();
        }

        public void update() { vendas = vendaDAO.getList(); }

        public VendasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.layout_item_produto, parent, false);
            VendasViewHolder vh = new VendasViewHolder(v, context);
            return vh;
        }

        public void onBindViewHolder(VendasViewHolder holder, int position) {
            holder.nameDesc.setText(vendas.get(position).getNome() + " Q: " + vendas.get(position).getQuant());
            holder.valor.setText("R$ " + vendas.get(position).getValor());
            holder.id = vendas.get(position).getId();
        }

        public int getItemCount() { return vendas.size(); }
    }
    class VendasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView nameDesc, valor;
        public int id;

        public VendasViewHolder(ConstraintLayout v, Context context) {
            super(v);
            this.context = context;
            nameDesc = v.findViewById(R.id.productName);
            valor = v.findViewById(R.id.productValor);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {

        }
    }




}