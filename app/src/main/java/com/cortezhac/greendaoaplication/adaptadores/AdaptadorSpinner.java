package com.cortezhac.greendaoaplication.adaptadores;

import android.content.Context;

import com.cortezhac.greendaoaplication.dao.ArticulosDAO;
import com.cortezhac.greendaoaplication.database.Articulos;

import java.util.ArrayList;

public class AdaptadorSpinner {

    public AdaptadorSpinner(){

    }

    public ArrayList<String> listaArticulos(Context contextoApp){
        ArrayList<String> resultadoArticulos = new ArrayList<>();
        resultadoArticulos.add("Selecccione una opcion");
        ArticulosDAO gestor =  new ArticulosDAO(contextoApp);
        ArrayList<Articulos>  lista = gestor.listarArticulos();
        for(int i = 0; i < lista.size(); i++){
            resultadoArticulos.add(lista.get(i).getDescripcion());
        }
        return resultadoArticulos;
    }

    public ArrayList<String> listaArticulosList(Context app){
        ArrayList<String> resultadoArticulos = new ArrayList<>();
        ArticulosDAO gestor =  new ArticulosDAO(app);
        ArrayList<Articulos>  lista = gestor.listarArticulos();
        for(int i = 0; i < lista.size(); i++){
            resultadoArticulos.add(lista.get(i).getDescripcion());
        }
        return resultadoArticulos;
    }
}
