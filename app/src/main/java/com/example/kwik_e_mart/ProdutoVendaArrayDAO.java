package com.example.kwik_e_mart;

import android.content.Context;

import java.util.ArrayList;

public class ProdutoVendaArrayDAO {
    private Context context;
    private static ArrayList<ProdutoVenda> produtosVendasList = new ArrayList<>();
    public ProdutoVendaArrayDAO(Context context){
        this.context = context;
    }

    public ArrayList<ProdutoVenda> getListVendaAtual(){
        return produtosVendasList;
    }
    public boolean addVendaAtual(ProdutoVenda produtoVenda) {
        produtoVenda.setId(produtosVendasList.size());
        produtosVendasList.add(produtoVenda);
        return true;
    }

    public double vendaTotal(ArrayList<ProdutoVenda> produtos){
        double total = 0;
        for (ProdutoVenda prod : produtos){
            total += prod.getValor();
        }
        return total;
    }

    public void limparLista(){
        produtosVendasList.clear();
    }
}
