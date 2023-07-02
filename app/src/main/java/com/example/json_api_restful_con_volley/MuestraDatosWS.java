package com.example.json_api_restful_con_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.json_api_restful_con_volley.WebService.Asynchtask;
import com.example.json_api_restful_con_volley.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MuestraDatosWS extends AppCompatActivity implements Asynchtask{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_datos_ws);

        Bundle bundle = getIntent().getExtras();
        String url="https://api.uealecpeterson.net/public/productos/search";
        Map<String, String> datos =new HashMap<>();
        datos.put("fuente","1");
        WebService ws = new WebService(url,datos,MuestraDatosWS.this,MuestraDatosWS.this);
        ws.execute("POST","Authorization", "Bearer " + bundle.getString("Token"));
    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView respuesta = findViewById(R.id.twVista);
        JSONObject response= new JSONObject(result);
        JSONArray productos = response.getJSONArray("productos");
        String prod="";
        for (int i = 0; i < productos.length(); i++) {
            JSONObject producto = productos.getJSONObject(i);
            prod= prod + "\n ********************************" + "\n BARCODE: " + producto.getString("barcode")
                       + " " + " " + "\n DESCRIPCION: " + producto.getString("descripcion")
                       + "\n PRECIO POR UNIDAD: " + producto.getString("precio_unidad");
            }
        respuesta.setText(prod);
    }
}