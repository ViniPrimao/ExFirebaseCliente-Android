package com.example.qi.exfirebasecliente;

import java.io.Serializable;

/**
 * Created by QI on 20/12/2017.
 */

public class Cliente implements Serializable {

    private String nome;
    private String cidade;
    private double renda;
    private String key;

    public Cliente() {
    }

    public Cliente(String nome, String cidade, double renda, String key) {
        this.nome = nome;
        this.cidade = cidade;
        this.renda = renda;
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public double getRenda() {
        return renda;
    }

    public void setRenda(double renda) {
        this.renda = renda;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cidade='" + cidade + '\'' +
                ", renda=" + renda +
                '}';
    }
}//fecha classe
