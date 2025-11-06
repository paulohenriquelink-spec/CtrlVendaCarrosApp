package br.ifsc.paulo.link.mobile.ctrlvendacarro.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Carro {

    //atributos

    private String data ;
    private String produto;
    private String preco;
    private String notafiscal;
    private int idCliente;

    //metodos


    public String getData() {

            return data;
    }

    public void setData(String data) {

        SimpleDateFormat formato =
                new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = (Date) formato.parse(data);

            this.data = String.valueOf(data);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Data inválida!");
        }

        this.data = data;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) throws Exception {
        if(produto.length()>6) {
            this.produto = produto;
        } else {

            throw new Exception("Nome do produto muito curto");
        }
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getNotafiscal() {
        return notafiscal;
    }

    public void setNotafiscal(String notafiscal) {
        this.notafiscal = notafiscal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        if (idCliente > 0) {
            this.idCliente = idCliente;
        } else {
            throw new IllegalArgumentException(
                    "O ID deve ser maior que zero");
        }

        this.idCliente = idCliente;
    }
}
