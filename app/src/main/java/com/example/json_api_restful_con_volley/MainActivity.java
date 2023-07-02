package com.example.json_api_restful_con_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.json_api_restful_con_volley.WebService.Asynchtask;
import com.example.json_api_restful_con_volley.WebService.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btIngresarVolley(View view) {
        EditText user = (EditText) findViewById(R.id.txtUsuario);
        EditText pass = (EditText) findViewById(R.id.txtPass);
        TextView respuesta = (TextView)findViewById(R.id.twValidar);

        JSONObject jsonDatos = new JSONObject();
        try {
            jsonDatos.put("correo", user.getText().toString());
            jsonDatos.put("clave", pass.getText().toString());
        } catch (JSONException e) {}

        RequestQueue queue = Volley.newRequestQueue(this);
        String url= "https://api.uealecpeterson.net/public/login";

        JsonObjectRequest Request1 = new JsonObjectRequest(Request.Method.POST, url,jsonDatos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Bundle b=new Bundle();
                            b.putString("Token", response.getString("access_token"));
                            Intent intent= new Intent(MainActivity.this, MuestraDatosVolley.class);
                            intent.putExtras(b);
                            startActivity(intent);
                        } catch (JSONException e) {}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        respuesta.setText("Código de Error: " + error.networkResponse.statusCode);
                    }
                });
        queue.add(Request1);
    }

    public void btIngresarws(View view){
        EditText user = findViewById(R.id.txtUsuario);
        EditText pass = findViewById(R.id.txtPass);
        String url="https://api.uealecpeterson.net/public/login";
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("correo",user.getText().toString());
        datos.put("clave",pass.getText().toString());
        WebService ws= new WebService(url,datos, MainActivity.this, MainActivity.this);
        ws.execute("POST");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView txtResp=(TextView) findViewById(R.id.twValidar);
        JSONObject jsonResp = new JSONObject(result);
        if(jsonResp.has("error")) {
            txtResp.setText("Error: " + jsonResp.getString("error"));
        }else{
            Bundle b=new Bundle();
            b.putString("Token", jsonResp.getString("access_token"));
            Intent intent= new Intent(MainActivity.this, MuestraDatosWS.class);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}