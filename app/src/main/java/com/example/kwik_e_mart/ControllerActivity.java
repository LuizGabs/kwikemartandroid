package com.example.kwik_e_mart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ControllerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Sistema Kwik-E-Mart v1.0 desenvolvido por: Luiz Gabriel")
                    .setNeutralButton("Ok", null)
                    .show();
            return true;
        }
        else if (item.getItemId() == R.id.configs){
            Intent intent = new Intent(this, UsersActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.relatorio){
            Intent intent = new Intent(this, RelatoriosActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.gains){
            Intent intent = new Intent(this, GainsActivity.class);
            startActivity(intent);
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void estoqueClicado(View view){
        Intent intent = new Intent(this, EstoqueActivity.class);
        startActivity(intent);
    }

    public void venderClicado(View view){
        Intent intent = new Intent(this, SellActivity.class);
        startActivity(intent);
    }

    public void adicionarCompraClicado(View view){
        Intent intent = new Intent(this, EditProdutoActivity.class);
        startActivity(intent);
    }

    public void etiquetagemClicado(View view){
        Intent intent = new Intent(this, EtiquetagemActivity.class);
        startActivity(intent);
    }

}