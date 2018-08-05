package com.example.eddie.miapplicacionprueba;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

public class Consulta extends AsyncTask<String, Integer, JSONArray> {


    @Override
    protected JSONArray doInBackground(String... stringss) {

        JSONArray jsonArrays=null;
        //Se crea un cliente http para hacer la consulta a traves del metodo GET
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://www.levelgas.com/Tr4ck3r/testInterview.php");
        request.setHeader("content-type", "application/json");
        Log.d("RESULT","--->RESULTADO");
        try {
            //Se intenta ejecutar la petición, en caso de que no se pueda, se maneja la excepción
            HttpResponse response = client.execute(request);
            // El resultado de la consulta del JSON se asigna a un String:
            String result = EntityUtils.toString(response.getEntity());
            //Se crea un objeto JSON Object usando el String obtenido:
            JSONObject jsonObject = new JSONObject(result);
            //jsonArrays obtiene todos los arrays dentro del Json
            jsonArrays = jsonObject.getJSONArray("suppliers");

            //user almacena el objeto user dentro del JsOn
            //JSONObject user = jsonObject.getJSONObject("user");
            //array1 contiene solo el primer array encontrado
            //JSONObject array1 = jsonArrays.getJSONObject(0);

        } catch (Exception e) {

        }finally {
            return jsonArrays;//retorna los jsonArrays
        }
    }
}
/*NOTA:

Para hacer la peticion http utilicé Loopj
 Loopj es una librería para Android que permite realizar peticiones HTTP de manera asíncrona.
 se copia la dependencia en el archivo Gradle :compile 'com.loopj.android:android-async-http:1.4.9'
 y se importan las librerias: cz.msebera.android.httpclient.....
 */
