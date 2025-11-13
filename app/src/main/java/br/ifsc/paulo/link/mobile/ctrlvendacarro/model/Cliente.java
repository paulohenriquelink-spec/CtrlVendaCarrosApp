package br.ifsc.paulo.link.mobile.ctrlvendacarro.model;

public class Cliente {

    private int     Idcliente;
    private String  nmCliente;
    private String  tlCliente;

    //metodo toString() para o adapter do spinner

    @Override
    public String toString() {return this.nmCliente;}

    public int getIdcliente() {
        return Idcliente;
    }

    public void setIdcliente(int idcliente) {
        Idcliente = idcliente;
    }

    public String getNmCliente() {
        return nmCliente;
    }

    public void setNmCliente(String nmCliente) {
        this.nmCliente = nmCliente;
    }

    public String getTlCliente() {
        return tlCliente;
    }

    public void setTlCliente(String tlCliente) {
        this.tlCliente = tlCliente;
    }
}
