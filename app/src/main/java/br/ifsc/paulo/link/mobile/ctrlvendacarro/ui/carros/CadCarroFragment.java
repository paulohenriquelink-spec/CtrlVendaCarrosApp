package br.ifsc.paulo.link.mobile.ctrlvendacarro.ui.carros;


import android.content.Context;
import android.os.Bundle;


import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


import br.ifsc.paulo.link.mobile.ctrlvendacarro.R;
import br.ifsc.paulo.link.mobile.ctrlvendacarro.model.Carro;
import br.ifsc.paulo.link.mobile.ctrlvendacarro.model.Cliente;




public class CadCarroFragment extends Fragment implements View.OnClickListener {




    private EditText etData_Venda;
    private EditText etProduto_Venda;
    private EditText etpreco_venda;
    private EditText etNotaFiscal;
    private Button button;
    private Spinner splista_cliente;
    private RequestQueue requestQueue;
    private View view;




    public CadCarroFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    private void cadastrarCarro(Carro carro) throws JSONException {
        //requisição para o Rest Server
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2/vendacarro/cadVenda.php",
                carro.toJsonObject(),
                response -> {
                    try {
                        //se o request não veio vazio
                        if (response != null) {
                            Context context = requireContext();
                            if (response.getBoolean("success")) {
                                //limpar campos da tela
                                this.etData_Venda.setText("");
                                this.etProduto_Venda.setText("");
                                this.etpreco_venda.setText("");
                                this.etNotaFiscal.setText("");
                                //primeiro item dos spinners
                                this.splista_cliente.setSelection(0);
                            }
                            //mostrando a mensagem que veio do JSON
                            Toast toast = Toast.makeText(
                                    view.getContext(),
                                    response.getString("message"),
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
//mostrar mensagem do response == null
                            Snackbar mensagem = Snackbar.make(
                                    view,
                                    "A consulta não retornou nada!",


                                    Snackbar.LENGTH_LONG);
                            mensagem.show();
                        }
                    } catch (Exception e) {
                        //mostrar mensagem da exception
                        Snackbar mensagem = Snackbar.make(
                                view,
                                "Ops!Problema com o arquivo JSON: " + e,
                                Snackbar.LENGTH_LONG);
                        mensagem.show();
                    }
                },
                error -> {
                    //mostrar mensagem que veio do servidor
                    Snackbar mensagem = Snackbar.make(
                            view,
                            "Ops! Houve um problema: " + error.toString(),
                            Snackbar.LENGTH_LONG);
                    mensagem.show();
                }
        );
        //colocando nova request para fila de execução
        requestQueue.add(jsonObjectReq);
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
        this.etData_Venda = (EditText) view.findViewById(R.id.etData_Venda);
        this.etProduto_Venda = (EditText) view.findViewById(R.id.etProduto_Venda);
        this.etpreco_venda = (EditText) view.findViewById(R.id.etpreco_venda);
        this.etNotaFiscal = (EditText) view.findViewById(R.id.etNotaFiscal);
        this.button = (Button) view.findViewById(R.id.button);
//definindo o listener do botão
        this.button.setOnClickListener(this);
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
                                for (int pos = 0; pos < response.length(); pos++) {
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
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //colocando nova request para fila de execução
        requestQueue.add(jsonArrayReq);
    }




    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            try {


                //instanciando objeto de negócio
                Carro carro = new Carro();
                //populando objeto com dados da tela


                carro.setData(this.etData_Venda.getText().toString());
                carro.setProduto(this.etProduto_Venda.getText().toString());
                carro.setPreco(this.etpreco_venda.getText().toString());
                carro.setNotafiscal(this.etNotaFiscal.getText().toString());


                //chamada do web service de cadastro
                try {
                    cadastrarCarro(carro);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }
    }
}


