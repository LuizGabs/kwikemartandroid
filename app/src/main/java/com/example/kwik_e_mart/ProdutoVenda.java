package com.example.kwik_e_mart;

public class ProdutoVenda {
    private int id;
    private int codbarras;
    private String nome;
    private double valor;
    private int quant;

    public ProdutoVenda(int id, int codbarras, String nome, double valor, int quant) {
        this.id = id;
        this.codbarras = codbarras;
        this.nome = nome;
        this.valor = valor;
        this.quant = quant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodbarras() {
        return codbarras;
    }

    public void setCodbarras(int codbarras) {
        this.codbarras = codbarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

}
