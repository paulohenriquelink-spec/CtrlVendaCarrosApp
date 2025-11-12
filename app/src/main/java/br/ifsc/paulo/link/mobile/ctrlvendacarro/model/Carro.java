package br.ifsc.paulo.link.mobile.ctrlvendacarro.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Carro {

    //atributos
    private int idVenda ;
    private String data ;
    private String produto;
    private String preco;
    private String notafiscal;
    private int idCliente;

    public Carro (){
        this.idVenda = 0;
        this.data = "1900-12-31";
        this.produto = "";
        this.preco = "";
        this.notafiscal = "";
        this.idCliente = 0;
    }

    //CONSTRUTOR - inicializa atributos de um arquivo JSon
    public Carro (JSONObject jp) {

        try {
            this.setIdVenda(jp.getInt("idVenda"));
            this.setData(jp.getString("dtVenda"));
            this.setProduto(jp.getString("produtoVenda"));
            this.setPreco(jp.getString("precoVenda"));
            this.setNotafiscal(jp.getString("ntFiscal"));
            this.setIdCliente(jp.getInt("idCliente"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo retorna o objeto com dados no formato JSON
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("idVenda", this.idVenda);
            json.put("dtVenda", this.data);
            json.put("produtoVenda", this.produto);
            json.put("precoVenda", this.preco);
            json.put("ntFiscal", this.notafiscal);
            json.put("dtNascimento", this.data);
            json.put("idCliente", this.idCliente);

        } catch (JSONException e) {



            e.printStackTrace();
        }
        return json;
    }



    //metodos


    public String getData() {

            return data;
    }



    public void setData(String dt) {

        SimpleDateFormat formato =
                new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = (Date) formato.parse(dt);
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

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
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
