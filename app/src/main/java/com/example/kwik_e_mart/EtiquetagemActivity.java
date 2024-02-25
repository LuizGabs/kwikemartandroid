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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EtiquetagemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EtiquetasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etiquetagem);

        recyclerView = findViewById(R.id.lista_etiquetas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EtiquetasAdapter(this);
        recyclerView.setAdapter(adapter);
    }
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }
    class EtiquetasAdapter extends RecyclerView.Adapter<EtiquetasViewHolder> {
        private Context context;
        private ArrayList<Produto> produtos;
        ProdutoDAO produtoDAO;

        public EtiquetasAdapter(Context context) {
            this.context = context;
            produtoDAO = new ProdutoDAO(context);
            update();
        }

        public void update() { produtos = produtoDAO.getList(); }

        public EtiquetasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.layout_item_produto, parent, false);
            EtiquetasViewHolder vh = new EtiquetasViewHolder(v, context);
            return vh;
        }

        public void onBindViewHolder(EtiquetasViewHolder holder, int position) {
            holder.name.setText(produtos.get(position).getNome() + " Código: " + produtos.get(position).getCodbarras());
            holder.valor.setText("R$ " + produtos.get(position).getValor());
            holder.id = produtos.get(position).getId();
        }

        public int getItemCount() { return produtos.size(); }
    }
    class EtiquetasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView name, valor;
        public int id;

        public EtiquetasViewHolder(ConstraintLayout v, Context context) {
            super(v);
            this.context = context;
            name = v.findViewById(R.id.productName);
            valor = v.findViewById(R.id.productValor);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            Toast.makeText(context, "Produto: " + this.name.getText().toString(), Toast.LENGTH_LONG)
                    .show();
        }
    }


}