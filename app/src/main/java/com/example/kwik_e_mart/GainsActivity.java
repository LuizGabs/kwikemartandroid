package com.example.kwik_e_mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GainsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LucrosAdapter adapter;

    private ProdutoVendaDAO vendaDAO;
    private  CompraProdutoDAO compraDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gains);


        recyclerView = findViewById(R.id.list_lucros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LucrosAdapter(this);
        recyclerView.setAdapter(adapter);
        }

    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }

    public void buttonLucros(View view){
        vendaDAO = new ProdutoVendaDAO(this);
        compraDAO = new CompraProdutoDAO(this);

        double value = vendaDAO.lucro() - compraDAO.gastos();
        if (value >= 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Lucro de R$" + value)
                    .setNeutralButton("Ok", null)
                    .show();
        }else{
            value = Math.abs(value);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Prejuízo de R$" + value)
                    .setNeutralButton("Ok", null)
                    .show();
        }
    }
    class LucrosAdapter extends RecyclerView.Adapter<LucrosViewHolder> {
        private Context context;
        private ArrayList<ProdutoVenda> vendas;
        ProdutoVendaDAO vendaDAO;

        public LucrosAdapter(Context context) {
            this.context = context;
            vendaDAO = new ProdutoVendaDAO(context);
            update();
        }

        public void update() { vendas = vendaDAO.getList(); }

        public LucrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.layout_item_produto, parent, false);
            LucrosViewHolder vh = new LucrosViewHolder(v, context);
            return vh;
        }

        public void onBindViewHolder(LucrosViewHolder holder, int position) {
            holder.nameDesc.setText(vendas.get(position).getNome() + " Q: " + vendas.get(position).getQuant());
            holder.valor.setText("R$ " + vendas.get(position).getValor());
            holder.id = vendas.get(position).getId();
        }

        public int getItemCount() { return vendas.size(); }
    }
    class LucrosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView nameDesc, valor;
        public int id;
        ProdutoVendaDAO vendaDAO;
        CompraProdutoDAO compraDAO;

        public LucrosViewHolder(ConstraintLayout v, Context context) {
            super(v);
            this.context = context;
            nameDesc = v.findViewById(R.id.productName);
            valor = v.findViewById(R.id.productValor);
            v.setOnClickListener(this);
            vendaDAO = new ProdutoVendaDAO(context);
            compraDAO = new CompraProdutoDAO(context);
        }


        public void onClick(View v) {
            double valor = (vendaDAO.lucroEspecifico(vendaDAO.getCodbarras(this.id))) - (compraDAO.gastoEspecifico(vendaDAO.getCodbarras(this.id)));
            if (valor >=0){
                Toast.makeText(context, "Lucro de: R$" + valor, Toast.LENGTH_LONG)
                        .show();
            }
            else{
                valor = Math.abs(valor);
                Toast.makeText(context, "Prejuízo de: R$" + valor, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}