package com.cortezhac.greendaoaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.cortezhac.greendaoaplication.adaptadores.AdaptadorSpinner;

public class ListarListViewActivity extends AppCompatActivity {
    private SearchView buscador;
    private ListView listaproductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_list_view);
        buscador = findViewById(R.id.buscador);
        listaproductos = findViewById(R.id.listaProducos);

        AdaptadorSpinner productos = new AdaptadorSpinner();
        final ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1, productos.listaArticulosList(this));
        listaproductos.setAdapter(adaptador);

        //Filtrar busqueda
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            // Cada que cambai el texto
            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtra los datos que obtiene el adaptador
                adaptador.getFilter().filter(newText);
                return false;
            }
        });
    }
}