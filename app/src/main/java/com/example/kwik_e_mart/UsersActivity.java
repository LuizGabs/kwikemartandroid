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

public class UsersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = findViewById(R.id.list_usuario);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UsersAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    public void buttonAddUserClick(View view) {
        Intent intent = new Intent(this, EditUserActivity.class);
        startActivity(intent);
    }

    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }

    class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {
        private Context context;
        private ArrayList<Usuario> usuarios;
        UsuarioDAO usuarioDAO;

        public UsersAdapter(Context context) {
            this.context = context;
            usuarioDAO = new UsuarioDAO(context);
            update();
        }

        public void update() {
            usuarios = usuarioDAO.getList();
        }

        public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_usuario_item, parent, false);
            UsersViewHolder vh = new UsersViewHolder(v, context);
            return vh;
        }

        public void onBindViewHolder(UsersViewHolder holder, int position) {
            holder.name.setText(usuarios.get(position).getName());
            holder.login.setText(usuarios.get(position).getLogin());
            holder.id = usuarios.get(position).getId();
        }

        public int getItemCount() {
            return usuarios.size();
        }
    }

    class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView login, name;
        public int id;

        public UsersViewHolder(ConstraintLayout v, Context context) {
            super(v);
            this.context = context;
            name = v.findViewById(R.id.productName);
            login = v.findViewById(R.id.productValor);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            Intent intent = new Intent(context, EditUserActivity.class);
            intent.putExtra("userId", this.id);
            context.startActivity(intent);
        }

    }
}