package com.cortezhac.greendaoaplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cortezhac.greendaoaplication.adaptadores.RecyclerAdapterCustom;
import com.cortezhac.greendaoaplication.dao.ArticulosDAO;
import com.cortezhac.greendaoaplication.database.Articulos;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class ReciclerViewActivity extends AppCompatActivity {
    ArrayList<Articulos> listaProdctos;
    private RecyclerView RecyclerProductos;
    private FloatingActionMenu menu;
    private FloatingActionButton item1, item2, item3;
    AlertDialog.Builder mensajeEmergente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recicler_view);
        ArticulosDAO gestor = new ArticulosDAO(this);
        listaProdctos = gestor.listarArticulos();

        RecyclerProductos = findViewById(R.id.ReciclerProductos);
        RecyclerProductos.setLayoutManager(new LinearLayoutManager(this));
        // Utilizar adaptador personlizado
        RecyclerAdapterCustom adapterCustom = new RecyclerAdapterCustom(listaProdctos);
        RecyclerProductos.setAdapter(adapterCustom);
        ativarMenu();
    }

    public void ativarMenu(){
        menu = findViewById(R.id.menu_fab);
        item1 = findViewById(R.id.Recycle_item1);
        item2 = findViewById(R.id.Recycle_item2);
        item3 = findViewById(R.id.Recycle_item3);

        menu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if(opened){
                    Toast.makeText(ReciclerViewActivity.this, "Menu activado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ReciclerViewActivity.this, "Menu desactivado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ReciclerViewActivity.this , AcercaDeActivity.class);
                startActivity(intent);
            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confiramcionEmergente();
            }
        });
    }

    public void confiramcionEmergente(){
        // setiar ventana emergente
        mensajeEmergente = new AlertDialog.Builder(this);
        mensajeEmergente.setTitle("Precaucion");
        mensajeEmergente.setMessage("Seguro que desea salir?");
        mensajeEmergente.setIcon(R.drawable.ic_baseline_exit_to_app);
        mensajeEmergente.setCancelable(false);

        mensajeEmergente.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Terminar actividad
                ReciclerViewActivity.this.finishAffinity();
            }
        });
        mensajeEmergente.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mensajeEmergente.show();
    }
}