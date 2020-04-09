package com.euniva.univa_calificaciones;

import android.util.Log;

import com.euniva.univa_calificaciones.Vistas.Fragments.Calificacion.CalificacionFragment;
import com.euniva.univa_calificaciones.Vistas.LoginAlumno;
import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ConexionWSAlumno {

    LinkConexion dev = new LinkConexion();
    String developing = dev.url;
    String url = developing + "Alumnoes/IngresarAlumno";
    String urlCarrera = developing + "alumnoes/mostrarCarrerasAlumno/";
    String urlPeriodo = developing + "Periodoes/GetAll";
    String urlCalificacion = developing + "alumnoes/getCalificacionPeriodo/";
    public String response, responseCarrera, responsePeriodo, responseCalificacion;
    public LoginAlumno la;
    public ModeloAlumno ma;
    public CalificacionFragment cf;
    public ArrayList<ModeloAlumno> carrera = new ArrayList<>();
    public ArrayList<ModeloAlumno> periodo = new ArrayList<>();
    public ArrayList<ModeloAlumno> calificacion = new ArrayList<>();

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

    public String getServerResponseCarrera() {


        HttpGet get = new HttpGet(urlCarrera + la.id);

        try {
            get.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();

            BasicResponseHandler handler = new BasicResponseHandler();

            responseCarrera = client.execute(get, handler);

            // Leemos los datos del servicio web
            try {

                JSONArray obj = new JSONArray(responseCarrera);

                for (int i = 0; i < obj.length(); i++) {
                    ma = new ModeloAlumno();
                    JSONObject elemento;
                    elemento = obj.getJSONObject(i);

                    ma.setCarrera(elemento.getString("Nombre"));
                    carrera.add(i, ma);
                }

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + responseCarrera + "\"");
            }
            return responseCarrera;

        } catch (UnsupportedEncodingException e) {
            Log.d("JWP", e.toString());
        } catch (ClientProtocolException e) {
            Log.d("JWP", e.toString());
        } catch (IOException e) {
            Log.d("JWP", e.toString());
        }
        return "No se pudo conectar con el servidor";
    }

    public String getServerResponsePeriodo() {

        HttpGet get = new HttpGet(urlPeriodo);

        try {
            get.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();

            BasicResponseHandler handler = new BasicResponseHandler();

            responsePeriodo = client.execute(get, handler);

            // Leemos los datos del servicio web
            try {

                JSONArray obj = new JSONArray(responsePeriodo);

                for (int i = 0; i < obj.length(); i++) {
                    ma = new ModeloAlumno();
                    JSONObject elemento;
                    elemento = obj.getJSONObject(i);

                    ma.setPeriodo(elemento.getString("Nombre"));
                    ma.setIdperiodo(elemento.getInt("Id"));
                    periodo.add(i, ma);
                }

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + responsePeriodo + "\"");
            }
            return responsePeriodo;

        } catch (UnsupportedEncodingException e) {
            Log.d("JWP", e.toString());
        } catch (ClientProtocolException e) {
            Log.d("JWP", e.toString());
        } catch (IOException e) {
            Log.d("JWP", e.toString());
        }
        return "No se pudo conectar con el servidor";
    }

    public String getServerResponseCalificacion() {

        HttpGet get = new HttpGet(urlCalificacion + "?idPeriodo=" + cf.idPeriodo + "&idAlumno=" + la.id);

        try {
            get.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();

            BasicResponseHandler handler = new BasicResponseHandler();

            responseCalificacion = client.execute(get, handler);

            // Leemos los datos del servicio web
            try {

                JSONArray obj = new JSONArray(responseCalificacion);

                for (int i = 0; i < obj.length(); i++) {
                    ma = new ModeloAlumno();
                    JSONObject elemento;
                    elemento = obj.getJSONObject(i);

                    ma.setMateria(elemento.getString("Materia"));
                    ma.setCalificacion(elemento.getInt("Calificacion"));
                    ma.setGrupo(elemento.getString("Grupo"));
                    calificacion.add(i, ma);
                }

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + responseCalificacion + "\"");
            }
            return responseCalificacion;

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

