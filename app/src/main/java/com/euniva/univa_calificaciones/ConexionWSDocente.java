package com.euniva.univa_calificaciones;

import android.util.Log;

import com.euniva.univa_calificaciones.Vistas.LoginDocente;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ConexionWSDocente {

    LinkConexion dev = new LinkConexion();
    String developing = dev.url;
    String url = developing + "Docentes/IngresarDocente";
    public String response;
    public LoginDocente lc;
    public ModeloDocente mc;

    public String getServerResponseLogin() {


        HttpPost post = new HttpPost(url + "?usuario=" + lc.clave + "&contrasena=" + lc.contrasena);

        try {
            post.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();

            BasicResponseHandler handler = new BasicResponseHandler();

            response = client.execute(post, handler);

            // Leemos los datos del servicio web
            try {
                mc = new ModeloDocente();

                JSONObject obj = new JSONObject(response);

                mc.setNombre(obj.getString("Nombre"));
                mc.setApellidos(obj.getString("Apellidos"));
                mc.setClabe(obj.getString("Clabe"));
                mc.setUsuario(obj.getString("Usuario"));
                mc.setId(obj.getInt("Id"));

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
