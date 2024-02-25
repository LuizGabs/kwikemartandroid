package com.example.kwik_e_mart;

public class Produto {
    private int id;
    private int codbarras;
    private String nome;
    private double valor;
    private String desc;
    private int quant;
    private String unidade;

    public Produto(int id, int codbarras, String nome, double valor, String desc, int quant, String unidade) {
        this.id = id;
        this.codbarras = codbarras;
        this.nome = nome;
        this.valor = valor;
        this.desc = desc;
        this.quant = quant;
        this.unidade = unidade;
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


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}
