package br.ifsc.paulo.link.mobile.ctrlvendacarro.ui.carros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.ifsc.paulo.link.mobile.ctrlvendacarro.R;



public class CadCarroFragment extends Fragment {

    private EditText   etData_Venda;
    private EditText   etProduto_Venda;
    private EditText   etpreco_venda;
    private EditText   etNotaFiscal;
    private EditText splista_cliente;

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
        return inflater.inflate(R.layout.fragment_cad_carro, container, false);
    }
}