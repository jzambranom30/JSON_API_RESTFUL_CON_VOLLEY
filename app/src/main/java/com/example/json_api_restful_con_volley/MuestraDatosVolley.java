package com.example.json_api_restful_con_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MuestraDatosVolley extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_datos_volley);

        TextView respuesta = findViewById(R.id.twVistaV);
        Bundle bundle = getIntent().getExtras();
        JSONObject jsonDatos = new JSONObject();
        try {
            jsonDatos.put("fuente","1");
        } catch (JSONException e) {}

        RequestQueue queue = Volley.newRequestQueue(this);
        String url= "https://api.uealecpeterson.net/public/productos/search";
        JsonObjectRequest Request1 = new JsonObjectRequest(Request.Method.POST, url, jsonDatos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String prod="";
                        JSONArray productos = null;
                        try {
                            productos = response.getJSONArray("productos");
                            for (int i = 0; i < productos.length(); i++) {
                                JSONObject producto = productos.getJSONObject(i);
                                prod= prod + "\n ********************************" + "\n BARCODE: " + producto.getString("barcode")
                                        + " " + " " + "\n DESCRIPCION: " + producto.getString("descripcion")
                                        + "\n PRECIO POR UNIDAD: " + producto.getString("precio_unidad");
                            }
                            respuesta.setText(prod);
                        } catch (JSONException e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        respuesta.setText("Error: " + error.networkResponse.statusCode);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + bundle.getString("Token"));
                return headers;
            }
        };
        queue.add(Request1);
    }
}