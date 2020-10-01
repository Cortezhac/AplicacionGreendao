package com.cortezhac.greendaoaplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.cortezhac.greendaoaplication.dao.ArticulosDAO;
import com.cortezhac.greendaoaplication.database.Articulos;
import com.cortezhac.greendaoaplication.ventana.Modal;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText campoCodigo, campoDescripcion, campoPrecio;
    private ArticulosDAO gestorDB; // Funciones de la DB
    private Articulos datos; // ObjDatosArticulos o Entidad
    AlertDialog.Builder mensajeEmergente; // Mensaje emergente
    Modal ventana = new Modal();

    private FABToolbarLayout fabtoolbar;

    public boolean comprobarCampo(EditText campo){
        boolean estado = true;
        if(!(campo.getText().toString().length() > 0)){
            campo.setError("Campo obligatorio");
            estado = false;
        }else{
            estado = true;
        }
        return estado;
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
                finish();
            }
        });
        mensajeEmergente.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mensajeEmergente.show();
    }

    @Override
    public boolean onKeyDown(int Keycode, KeyEvent event){
        if(Keycode == event.KEYCODE_BACK){
            confiramcionEmergente();
            return true;
        }
        return super.onKeyDown(Keycode,event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Cofigurar Toolbar barra superior evento click back boton
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confiramcionEmergente();
            }
        });

        // Utilizar conexion
        gestorDB = new ArticulosDAO(getApplicationContext());
        FloatingActionButton fab = findViewById(R.id.fab);
        fabtoolbar = findViewById(R.id.fab_toolbar); // contenedor principal

        ImageView uno, dos , tres, cuatro, cinco;

        uno = findViewById(R.id.fabuno);
        dos = findViewById(R.id.fabdos);
        tres = findViewById(R.id.fabtres);
        cuatro = findViewById(R.id.fabcuatro);
        cinco = findViewById(R.id.fabcinco);

        // Usa el elemento View.onClickListener implmentando en la clase
        fab.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);

        /*Sustituido por un implemet
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                ventana.search(MainActivity.this);
            }
        });*/

        campoCodigo = findViewById(R.id.contentMain_editIdCodigo);
        campoDescripcion = findViewById(R.id.contentMain_editDescripcion);
        campoPrecio = findViewById(R.id.contentMain_editPrecio);

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                String Senal = bundle.getString("senal");
                String Codigo = bundle.getString("codigo");
                String Descripcion = bundle.getString("descripcion");
                String Precio = bundle.getString("precio");

                if(Senal.equals("1")){
                    campoCodigo.setText(Codigo);
                    campoDescripcion.setText(Descripcion);
                    campoPrecio.setText(Precio);
                }
            }
        }catch (Exception ex){
            Log.i("Error intent: " , ex.toString());
        }
    }
    // Metodo implementado de la clase View.onClickListener
    @Override
    public void  onClick(View v){
        if(v.getId() == R.id.fab){
            fabtoolbar.show();
        }

        switch (v.getId()){
            case R.id.fabuno:
                showToast("Modificar");
                modificar(v);
                fabtoolbar.hide();
                break;
            case R.id.fabcinco:
                showToast("Guardar");
                guardar(v);
                fabtoolbar.hide();
                break;
            case R.id.fabdos:
                showToast("Buscar descripcion");
                consultarDescripcion(v);
                fabtoolbar.hide();
                break;
            case R.id.fabtres:
               //showToast("Buscar codigo");
                ventana.search(MainActivity.this);
                fabtoolbar.hide();
                break;
            case R.id.fabcuatro:
                showToast("Eliminar");
                eliminar(v);
                fabtoolbar.hide();
                break;
            default:
                fabtoolbar.hide();
                break;
        }
    }

    public void showToast(String mensaje){
        Toast.makeText(getApplicationContext(), mensaje,Toast.LENGTH_LONG).show();
    }

    public void guardar(View app){
        if((comprobarCampo(campoCodigo) && comprobarCampo(campoDescripcion)) && comprobarCampo(campoPrecio)){
            datos = new Articulos(Long.parseLong(campoCodigo.getText().toString()),
                                    campoDescripcion.getText().toString(),
                                    Double.parseDouble(campoPrecio.getText().toString()));
            if(gestorDB.insertarDatos(datos)){
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El id del registro ya existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Introduzca los datos correspondientes", Toast.LENGTH_SHORT).show();
        }
    }

    public void consultarCodigo(View app){
        if(comprobarCampo(campoCodigo)){
            datos = gestorDB.getArticulo(Long.parseLong(campoCodigo.getText().toString()));
            if(datos == null){
                Toast.makeText(this, "No se ha encontrado ningun registro", Toast.LENGTH_SHORT).show();
            }else{
                campoCodigo.setText(String.valueOf(datos.getCodigo()));
                campoDescripcion.setText(datos.getDescripcion());
                campoPrecio.setText(String.valueOf(datos.getPrecio()));
                Toast.makeText(this, "Registro encontrado", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Introduza un codigo", Toast.LENGTH_SHORT).show();
        }
    }

    public void consultarDescripcion(View v){
        if(comprobarCampo(campoDescripcion)){
            datos = gestorDB.getArticulo(campoDescripcion.getText().toString());
            if(datos == null){
                Toast.makeText(this, "No se ha encontrado ningun registro", Toast.LENGTH_SHORT).show();
            }else{
                campoCodigo.setText(String.valueOf(datos.getCodigo()));
                campoDescripcion.setText(datos.getDescripcion());
                campoPrecio.setText(String.valueOf(datos.getPrecio()));
                Toast.makeText(this, "Registro encontrado", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Introduzca una descripcion", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificar(View v){
        if(comprobarCampo(campoCodigo) && comprobarCampo(campoDescripcion) && comprobarCampo(campoPrecio)){
            ArticulosDAO gestor = new ArticulosDAO(this);
            Articulos producto = new Articulos(Long.parseLong(campoCodigo.getText().toString()),
                                                campoDescripcion.getText().toString(),
                                                Double.parseDouble(campoPrecio.getText().toString()));
            gestor.actualizarProducto(producto);
            Toast.makeText(this, "Registro actualizadod", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Introduzca los datos correspondientes", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar(View v){
        if(comprobarCampo(campoCodigo)){
            // Mensaje emergente TODO intentar crear ventana emergente automatica
            final AlertDialog.Builder mensajeconfirmacion = new AlertDialog.Builder(this);
            mensajeconfirmacion.setTitle("Eliminar");
            mensajeconfirmacion.setMessage("Esta seguro de eliminar este registro?");
            mensajeconfirmacion.setIcon(R.drawable.ic_baseline_delete_forever_24);
            mensajeconfirmacion.setPositiveButton("Si, Seguro", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArticulosDAO gestor = new ArticulosDAO(getApplicationContext());
                    Articulos producto = new Articulos();
                    producto.setCodigo(Long.parseLong(campoCodigo.getText().toString()));
                    gestor.eliminarProducto(producto);
                    Toast mensajito = new Toast(getApplicationContext());
                    mensajito.makeText(getApplicationContext(),"Se elimino", Toast.LENGTH_SHORT).show();
                }
            });
            mensajeconfirmacion.setNegativeButton("No lo hagas", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // no hacer nada
                }
            });
            mensajeconfirmacion.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.MenuLimpiar:
                    campoCodigo.setText(null);
                    campoDescripcion.setText(null);
                    campoPrecio.setText(null);
                break;
            case R.id.MenuListarSpin:
                Intent intent = new Intent(getApplicationContext(), ListarSpinerActivity.class);
                startActivity(intent);
                break;
            case R.id.MenuListaListar:
                Intent intent1 = new Intent(getApplicationContext(), ListarListViewActivity.class);
                startActivity(intent1);
                break;
            case R.id.MenuRecycler:
                Intent intent2 = new Intent(getApplicationContext(), ReciclerViewActivity.class);
                startActivity(intent2);
                break;
            case R.id.MenuAcercade:
                Intent intent3 = new Intent(getApplicationContext(), AcercaDeActivity.class);
                startActivity(intent3);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}