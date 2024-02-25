package com.example.kwik_e_mart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void entrarClicado(View view) {
        usuarioDAO = new UsuarioDAO(this);
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios = usuarioDAO.getList();
        int flag = 0;
        String login = ((EditText) findViewById(R.id.loginInput)).getText().toString();
        String pass  = ((EditText) findViewById(R.id.senhaInput)).getText().toString();
        for (Usuario user:usuarios){
            if (user.getLogin().equals(login)){
                if (user.getPassword().equals(pass)){
                    Intent intent = new Intent(this, ControllerActivity.class);
                    startActivity(intent);
                    flag = 1;
                }
                else{
                    Toast.makeText(this, "Senha Incorreta!", Toast.LENGTH_SHORT).show();
                    flag = 1;
                }
            }
        }
        if (flag == 0){
            Toast.makeText(this, "Login/Senha Inválidos!", Toast.LENGTH_SHORT).show();
        }
    }
}