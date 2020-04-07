package com.euniva.univa_calificaciones;

import android.util.Log;

import com.euniva.univa_calificaciones.Vistas.LoginAlumno;
import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ConexionWSAlumno {

    LinkConexion dev = new LinkConexion();
    String developing = dev.url;
    String url = developing + "Alumnoes/IngresarAlumno";
    public String response;
    public LoginAlumno la;
    public ModeloAlumno ma;

    public String getServerResponseLogin() {


        HttpGet get = new HttpGet(url + "?matricula=" + la.matricula + "&contrasena=" + la.contrasena);

        try {
            get.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();

            BasicResponseHandler handler = new BasicResponseHandler();

            response = client.execute(get, handler);

            // Leemos los datos del servicio web
            try {
                ma = new ModeloAlumno();

                JSONObject obj = new JSONObject(response);

                ma.setNombre(obj.getString("Nombre"));
                ma.setApellidos(obj.getString("Apellidos"));
                ma.setMatricula(obj.getString("Matricula"));
                ma.setId(obj.getInt("Id"));

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
            }
            return response;

        } catch (UnsupportedEncodingException e) {
            Log.d("JWP", e.toString());
        } catch (ClientProtocolException e) {
            Log.d("JWP", e.toString());
        } catch (IOException e) {
            Log.d("JWP", e.toString());
        }
        return "No se pudo conectar con el servidor";
    }

}

