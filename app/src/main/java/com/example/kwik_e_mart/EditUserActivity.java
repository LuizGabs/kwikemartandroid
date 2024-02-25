package com.example.kwik_e_mart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EditUserActivity extends AppCompatActivity {
    private UsuarioDAO usuarioDAO;
    private int userId;
    private TextView editName, editLogin, editPassword, editNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editName = findViewById(R.id.addProdutoVendaNome);
        editLogin = findViewById(R.id.addProdutoVendaValor);
        editPassword = findViewById(R.id.addProdutoVendaQuantidade);
        editNotes = findViewById(R.id.addProdutoDesc);
        usuarioDAO = new UsuarioDAO(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        // Verifica se uma senha foi passada como parâmetro
        if (userId != -1) {
            Usuario usuario = usuarioDAO.get(userId);
            editName.setText(usuario.getName());
            editLogin.setText(usuario.getLogin());
            editPassword.setText(usuario.getPassword());
            editNotes.setText(usuario.getNotes());
        }
    }
    public void salvarClicado(View view) {
        Usuario usuario = new Usuario(userId, editName.getText().toString(),
                editLogin.getText().toString(), editPassword.getText().toString(),
                editNotes.getText().toString());

        boolean result;
        if (userId == -1) result = usuarioDAO.add(usuario);
        else                  result = usuarioDAO.update(usuario);

        if (result) finish();
    }

    public void removerClicado(View view){
        Usuario usuario = new Usuario(userId, editName.getText().toString(),
                editLogin.getText().toString(), editPassword.getText().toString(),
                editNotes.getText().toString());
        boolean result = usuarioDAO.remover(usuario);
        if (result) finish();
    }

}