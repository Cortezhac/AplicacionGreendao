package com.cortezhac.greendaoaplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cortezhac.greendaoaplication.adaptadores.AdaptadorSpinner;
import com.cortezhac.greendaoaplication.dao.ArticulosDAO;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ListarSpinerActivity extends AppCompatActivity {

    private Spinner spin;
    private AdaptadorSpinner listaString = new AdaptadorSpinner(); // Objeto que procesa los datos a una lista de Strings
    private TextView textoCodigo, textoDescripcion, textoPrecio;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_spiner);

        textoCodigo = findViewById(R.id.SpinCodigo);
        textoDescripcion = findViewById(R.id.SpinDescrip);
        textoPrecio = findViewById(R.id.SpinPrecio);

        spin  = findViewById(R.id.spinner);
        // Base de datos 448 451 230 2n21f
        final ArticulosDAO gestor = new ArticulosDAO(this);
        //preprara adaptador
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listaString.listaArticulos(this));
        spin.setAdapter(adaptador);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position-=1;
                if(position >= 0){
                    textoCodigo.setText(String.valueOf(gestor.listarArticulos().get(position).getCodigo()));
                    textoDescripcion.setText(gestor.listarArticulos().get(position).getDescripcion());
                    textoPrecio.setText(String.valueOf(gestor.listarArticulos().get(position).getPrecio()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    // chinque a su madre
            }
        });
    }


}