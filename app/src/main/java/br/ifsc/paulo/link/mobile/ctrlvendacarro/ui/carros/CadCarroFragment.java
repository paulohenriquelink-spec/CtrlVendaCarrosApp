package br.ifsc.paulo.link.mobile.ctrlvendacarro.ui.carros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.ifsc.paulo.link.mobile.ctrlvendacarro.R;
import br.ifsc.paulo.link.mobile.ctrlvendacarro.model.Cliente;


public class CadCarroFragment extends Fragment {


    private EditText   etData_Venda;
    private EditText   etProduto_Venda;
    private EditText   etpreco_venda;
    private EditText   etNotaFiscal;
    private Button     button ;
    private Spinner    splista_cliente;
    private RequestQueue requestQueue;



    private View view;

    public CadCarroFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_cad_carro, container, false);

        //spinner
        this.splista_cliente = (Spinner) view.findViewById(R.id.splista_cliente);
        //instanciando a fila de requests - caso o objeto seja o view
        this.requestQueue = Volley.newRequestQueue(view.getContext());
//inicializando a fila de requests do SO
        this.requestQueue.start();

        try {
            this.consultaCliente();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        //return defalut
        return this.view;
    }

    private void consultaCliente() throws JSONException {
        //requisição para o Rest Server
        JsonArrayRequest jsonArrayReq = null;
        try {
            jsonArrayReq = new JsonArrayRequest(
                    Request.Method.POST,
                    "http://10.0.2.2/vendacarro/conCliente.php",
                    new JSONArray("[{}]"),
                    response -> {
                        try {
                            //se a consulta não veio vazia
                            if (response != null) {
                                //array list para receber a resposta
                                ArrayList<Cliente> lista = new ArrayList<>();
                                //preenchendo ArrayList com JSONArray recebido
                                for (int pos = 0;pos < response.length();pos++){
                                    JSONObject jo = response.getJSONObject(pos);
                                    Cliente cliente = new Cliente();
                                    cliente.setIdcliente(jo.getInt("idCliente"));
                                    cliente.setNmCliente(jo.getString("nmCliente"));
                                    cliente.setTlCliente(jo.getString("tlCliente"));
                                    lista.add(pos, cliente);
                                }
                                //Criando um adapter para o spinner
                                ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(
                                        requireContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        lista);

//colocando o adapter no spinner

                                this.splista_cliente.setAdapter(adapter);
                            } else {
                                Snackbar mensagem = Snackbar.make(view,
                                        "A consulta não retornou nenhum registro!",
                                        Snackbar.LENGTH_LONG);
                                mensagem.show();
                            }
                        } catch (JSONException e) {
                            Snackbar mensagem = Snackbar.make(view,
                                    "Ops!Problema com o arquivo JSON: " + e,
                                    Snackbar.LENGTH_LONG);
                            mensagem.show();
                        }
                    },
                    error -> {
                        //mostrar mensagem que veio do servidor
                        Snackbar mensagem = Snackbar.make(view,
                                "Ops! Houve um problema ao realizar a consulta: " +
                                        error.toString(), Snackbar.LENGTH_LONG);
                        mensagem.show();
                    }
            );
        } catch (JSONException e) {throw new RuntimeException(e);}
        //colocando nova request para fila de execução
        requestQueue.add(jsonArrayReq);
    }


}