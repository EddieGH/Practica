package com.example.eddie.miapplicacionprueba;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.example.eddie.miapplicacionprueba.R.id.action_settings;

public class MainActivity extends AppCompatActivity {
    JSONObject jsonObject;
    JSONArray jsonArrays=null;
    TextView txvNombre, txvTelefono, txvCelular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //consulta almacena el JsonArray obtenido de la consulta:
                    jsonObject = new Consulta().execute().get();
                    jsonArrays = jsonObject.getJSONArray("suppliers");
                    //user contiene el objeto user del Json
                    JSONObject user = jsonObject.getJSONObject("user");
                    txvNombre = findViewById(R.id.txvNombre);
                    txvTelefono = findViewById(R.id.txvTelefono);
                    txvCelular = findViewById(R.id.txvCelular);

                    txvNombre.setText(user.optString("name").concat(" "+user.optString("lastName")));
                    txvTelefono.setText(user.optString("telephone"));
                    txvCelular.setText(user.optString("celphone"));

                    //se guarda en una lista para poder mostrar los datos en el ListView
                    ArrayList<String> listaSuppliers = new ArrayList<>();
                    for (int c =0; c < jsonArrays.length(); c++){
                        //json es un objeto de cada array dentro del Json:
                        JSONObject json = jsonArrays.getJSONObject(c);
                        //dir contiene el objeto address dentro de cada JsonArray
                        JSONObject dir = (JSONObject) jsonArrays.getJSONObject(c).get("address");
                        //se obtienen los datos de cada objeto para formar un item de la lista:
                        String itemsLista = "ID: "+json.optString("id")
                                .concat("\nName: "+json.optString("name"))
                                .concat("\nRating: "+json.optString("rating"))
                                .concat("\nDireccion: "+dir.optString("street").concat(" "+dir.optString("number").concat(", C.P: "+dir.optString("postalCode").concat(", "+dir.optString("colony")))));
                        //se añaden los items a la lista
                        listaSuppliers.add(itemsLista);
                    }

                    ListView myListView = findViewById(R.id.listView);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listaSuppliers);
                    myListView.setAdapter(arrayAdapter);

                    myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getApplicationContext(),"item "+i,Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception e){
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == action_settings) {
            Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
/*...E..*/