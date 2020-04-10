package com.euniva.univa_calificaciones;

import android.util.Log;

import com.euniva.univa_calificaciones.Vistas.Fragments.RegistroCal;
import com.euniva.univa_calificaciones.Vistas.LoginDocente;
import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ConexionWSDocente {

    LinkConexion dev = new LinkConexion();
    String developing = dev.url;
    String url = developing + "Docentes/IngresarDocente";
    String urlMateria = developing + "Docentes/getHistorial/";
    String urlAlumnosCal = developing + "Calificaciones/getCalificacionesByGrupo/";
    String urlAlumnos = developing + "Alumnoes/getAll";
    String urlCalificacion = developing + "Calificaciones/PostCalificaciones";
    String urlEditarCalif = developing + "Calificaciones/PutCalificaciones/";
    public String response, responseMateria, responseAlumnosCal, responseAlumnos, responseMandarCal, responseEditarCal;
    public LoginDocente lc;
    public ModeloDocente mc;
    public RegistroCal rc;
    public ArrayList<ModeloDocente> materias = new ArrayList<>();
    public ArrayList<ModeloDocente> alumnoscal = new ArrayList<>();
    public ArrayList<ModeloDocente> alumnos = new ArrayList<>();

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

    public String getServerResponseMateria() {

        HttpGet get = new HttpGet(urlMateria + lc.id);

        try {
            get.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();

            BasicResponseHandler handler = new BasicResponseHandler();

            responseMateria = client.execute(get, handler);

            // Leemos los datos del servicio web
            try {

                JSONArray obj = new JSONArray(responseMateria);

                for (int i = 0; i < obj.length(); i++) {
                    mc = new ModeloDocente();
                    JSONObject elemento;
                    elemento = obj.getJSONObject(i);

                    mc.setIdgrupo(elemento.getInt("IdGrupo"));
                    mc.setGrupo(elemento.getString("Grupo"));
                    mc.setPeriodo(elemento.getString("Periodo"));
                    mc.setMateria(elemento.getString("Materia"));
                    materias.add(i, mc);
                }

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + responseMateria + "\"");
            }
            return responseMateria;

        } catch (UnsupportedEncodingException e) {
            Log.d("JWP", e.toString());
        } catch (ClientProtocolException e) {
            Log.d("JWP", e.toString());
        } catch (IOException e) {
            Log.d("JWP", e.toString());
        }
        return "No se pudo conectar con el servidor";
    }

    public String getServerResponseAlumnosCal() {

        HttpGet get = new HttpGet(urlAlumnosCal + rc.idGrupo);

        try {
            get.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();

            BasicResponseHandler handler = new BasicResponseHandler();

            responseAlumnosCal = client.execute(get, handler);

            // Leemos los datos del servicio web
            try {

                JSONArray obj = new JSONArray(responseAlumnosCal);

                for (int i = 0; i < obj.length(); i++) {
                    mc = new ModeloDocente();
                    JSONObject elemento;
                    elemento = obj.getJSONObject(i);

                    mc.setIdcalgrupo(elemento.getInt("Id"));
                    JSONObject elemento2 = elemento.getJSONObject("IdAlumnos");
                    mc.setAlumnomatricula(elemento2.getString("Matricula"));
                    mc.setAlumnonombre(elemento2.getString("Nombre"));
                    mc.setAlumnoapellidos(elemento2.getString("Apellidos"));
                    mc.setIdalumno(elemento2.getInt("Id"));
                    mc.setCalif(elemento.getInt("Calificacion"));
                    alumnoscal.add(i, mc);
                }

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + responseAlumnosCal + "\"");
            }
            return responseAlumnosCal;

        } catch (UnsupportedEncodingException e) {
            Log.d("JWP", e.toString());
        } catch (ClientProtocolException e) {
            Log.d("JWP", e.toString());
        } catch (IOException e) {
            Log.d("JWP", e.toString());
        }
        return "No se pudo conectar con el servidor";
    }

    public String getServerResponseAlumnos() {

        HttpGet get = new HttpGet(urlAlumnos);

        try {
            get.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();

            BasicResponseHandler handler = new BasicResponseHandler();

            responseAlumnos = client.execute(get, handler);

            // Leemos los datos del servicio web
            try {

                JSONArray obj = new JSONArray(responseAlumnos);

                for (int i = 0; i < obj.length(); i++) {
                    mc = new ModeloDocente();
                    JSONObject elemento;
                    elemento = obj.getJSONObject(i);

                    mc.setIdalumno(elemento.getInt("Id"));
                    mc.setAlumnonombre(elemento.getString("Nombre"));
                    mc.setAlumnoapellidos(elemento.getString("Apellidos"));
                    mc.setAlumnomatricula(elemento.getString("Matricula"));
                    mc.setRfc(elemento.getString("Rfc"));
                    mc.setCurp(elemento.getString("Curp"));
                    mc.setPassword(elemento.getString("Password"));
                    mc.setLogin(elemento.getBoolean("Logueado"));
                    alumnos.add(i, mc);
                }

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + responseAlumnos + "\"");
            }
            return responseAlumnos;

        } catch (UnsupportedEncodingException e) {
            Log.d("JWP", e.toString());
        } catch (ClientProtocolException e) {
            Log.d("JWP", e.toString());
        } catch (IOException e) {
            Log.d("JWP", e.toString());
        }
        return "No se pudo conectar con el servidor";
    }

    public String formatDataAsJson(ModeloDocente data) {

        final JSONObject device = new JSONObject();
        JSONObject subdataAlumno = new JSONObject();
        JSONObject subdataGrupo = new JSONObject();
        try {
            subdataAlumno.put("Id", data.getIdalumno());
            subdataGrupo.put("Id", data.getIdgrupo());
            device.put("Calificacion", data.getCalif());
            device.put("IdAlumnos", subdataAlumno);
            device.put("IdGrupo", subdataGrupo);

            return device.toString(1);
        } catch (JSONException e) {
            Log.d("JWP", "Can't format JSON");
        }
        return null;
    }

    public String getServerResponseMandarCalificacion(String json) {

        HttpPost post = new HttpPost(urlCalificacion);

        try {
            StringEntity entity = new StringEntity(json, "UTF-8");
            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();

            BasicResponseHandler handler = new BasicResponseHandler();

            responseMandarCal = client.execute(post, handler);

            // Leemos los datos del servicio web
            try {

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + responseMandarCal + "\"");
            }
            return responseMandarCal;

        } catch (UnsupportedEncodingException e) {
            Log.d("JWP", e.toString());
        } catch (ClientProtocolException e) {
            Log.d("JWP", e.toString());
        } catch (IOException e) {
            Log.d("JWP", e.toString());
        }
        return "No se pudo conectar con el servidor";
    }

    public String formatDataAsJson2(ModeloDocente data) {

        final JSONObject device = new JSONObject();
        JSONObject subdataAlumno = new JSONObject();
        JSONObject subdataGrupo = new JSONObject();
        try {
            subdataGrupo.put("Id", data.getIdgrupo());
            subdataGrupo.put("Clave", data.getGrupo());
            subdataAlumno.put("Matricula", data.getAlumnomatricula());
            subdataAlumno.put("Id", data.getIdalumno());
            subdataAlumno.put("Nombre", data.getAlumnonombre());
            subdataAlumno.put("Apellidos", data.getAlumnoapellidos());
            subdataAlumno.put("Rfc", data.getRfc());
            subdataAlumno.put("Curp", data.getCurp());
            subdataAlumno.put("Password", data.getPassword());
            subdataAlumno.put("Logueado", data.isLogin());
            device.put("Calificacion", data.getCalif());
            device.put("IdAlumnos", subdataAlumno);
            device.put("IdGrupo", subdataGrupo);

            return device.toString(1);
        } catch (JSONException e) {
            Log.d("JWP", "Can't format JSON");
        }
        return null;
    }

    public String getServerResponseEditarCalificacion(String json) {

        HttpPut put = new HttpPut(urlEditarCalif + rc.Id);

        try {
            StringEntity entity = new StringEntity(json, "UTF-8");
            put.setEntity(entity);
            put.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();

            BasicResponseHandler handler = new BasicResponseHandler();

            responseEditarCal = client.execute(put, handler);

            // Leemos los datos del servicio web
            try {

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + responseEditarCal + "\"");
            }
            return responseEditarCal;

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
