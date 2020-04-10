package com.euniva.univa_calificaciones.Vistas.Fragments;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.euniva.univa_calificaciones.ConexionWSDocente;
import com.euniva.univa_calificaciones.ModeloDocente;
import com.euniva.univa_calificaciones.R;
import com.euniva.univa_calificaciones.Table;

import java.util.ArrayList;

public class RegistroCal extends Fragment {

    Spinner spnMateria, spnAlumno;
    public static int idGrupo, idAlumno, calif, Id;
    public static String clvGrupo, matricula, rfc, curp, pass, nombreAlumno, apellidosAlumno;
    public static boolean logueado;
    public ArrayList materias, alumnos;
    private TextView txtGrupo, txtPeriodo;
    private TableLayout tblAlumnoscalificaciones;
    private String[] header = {"Matrícula", "Nombre", "Calif"};
    private ArrayList<String[]> rows = new ArrayList<>();
    Table tablaAlumnosCalificaciones;
    private Button btnNuevo, btnEditar;
    public String formatDataAsJson, formatDataAsJson2;
    public EditText edtCalif;

    ConexionWSDocente servicio = new ConexionWSDocente();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro_cal, container, false);

        txtGrupo = view.findViewById(R.id.txt_grupo);
        txtPeriodo = view.findViewById(R.id.txt_periodo);
        spnMateria = view.findViewById(R.id.spn_materia);
        btnEditar = view.findViewById(R.id.btn_editar);
        btnNuevo = view.findViewById(R.id.btn_nuevo);

        tblAlumnoscalificaciones = view.findViewById(R.id.tbl_alumnoscalificaciones);

        tablaAlumnosCalificaciones = new Table(tblAlumnoscalificaciones, getActivity().getApplicationContext());

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                return servicio.getServerResponseMateria();
            }

            protected void onPostExecute(String result) {
                materias = new ArrayList();
                for (int i = 0; i < servicio.materias.size(); i++) {
                    materias.add(servicio.materias.get(i).materia);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, materias);
                spnMateria.setAdapter(arrayAdapter);

                int idgrp = spnMateria.getSelectedItemPosition();

                idGrupo = servicio.materias.get(idgrp).idgrupo;
                clvGrupo = servicio.materias.get(idgrp).grupo;

                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected String doInBackground(Void... voids) {
                        return servicio.getServerResponseAlumnosCal();
                    }

                    protected void onPostExecute(String result) {
                        tablaAlumnosCalificaciones.addHeader(header);
                        tablaAlumnosCalificaciones.addData(getAlumnosCalificaciones());
                        tablaAlumnosCalificaciones.backgroundHeader(R.color.blue);
                        tblAlumnoscalificaciones.removeAllViewsInLayout();
                        tblAlumnoscalificaciones.removeAllViews();
                        rows.removeAll(rows);

                        Id = servicio.mc.idcalgrupo;
                    }
                }.execute();

            }
        }.execute();

        spnMateria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                tblAlumnoscalificaciones.removeAllViewsInLayout();
                tblAlumnoscalificaciones.removeAllViews();

                txtGrupo.setText(servicio.materias.get(i).grupo);
                txtPeriodo.setText(servicio.materias.get(i).periodo);
                idGrupo = servicio.materias.get(i).idgrupo;
                clvGrupo = servicio.materias.get(i).grupo;

                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected String doInBackground(Void... voids) {
                        return servicio.getServerResponseAlumnosCal();
                    }

                    protected void onPostExecute(String result) {
                        tablaAlumnosCalificaciones.addHeader(header);
                        tablaAlumnosCalificaciones.addData(getAlumnosCalificaciones());
                        tablaAlumnosCalificaciones.backgroundHeader(R.color.blue);
                        rows.removeAll(rows);
                        Id = servicio.mc.idcalgrupo;
                    }
                }.execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicio.alumnos.clear();
                NuevoCalificacion();

            }
        });

        btnEditar.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                servicio.alumnos.clear();
                EditarCalificacion();
            }
        }));

        return view;
    }

    private ArrayList<String[]> getAlumnosCalificaciones() {

        for (int i = 0; i < servicio.alumnoscal.size(); i++) {
            rows.add(new String[]{servicio.alumnoscal.get(i).alumnomatricula, servicio.alumnoscal.get(i).alumnonombre + " " + servicio.alumnoscal.get(i).alumnoapellidos, String.valueOf(servicio.alumnoscal.get(i).calif)});
        }
        servicio.alumnoscal.clear();
        return rows;
    }

    public void NuevoCalificacion() {
        final Dialog alerta = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
        alerta.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        alerta.setContentView(R.layout.nuevo_calificacion);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 0.84);
        int height = (int) (displaymetrics.heightPixels * 0.84);
        alerta.getWindow().setLayout(width, height);

        TextView titulo = (TextView) alerta.findViewById(R.id.titulo);
        titulo.setText("Nueva Califiación Alumno");

        spnAlumno = alerta.findViewById(R.id.spn_alumno);

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                return servicio.getServerResponseAlumnos();
            }

            protected void onPostExecute(String result) {
                alumnos = new ArrayList();
                for (int i = 0; i < servicio.alumnos.size(); i++) {
                    alumnos.add(servicio.alumnos.get(i).alumnonombre + " " + servicio.alumnos.get(i).alumnoapellidos);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, alumnos);
                spnAlumno.setAdapter(arrayAdapter);

                int idalum = spnAlumno.getSelectedItemPosition();

                idAlumno = servicio.alumnos.get(idalum).idalumno;

            }
        }.execute();

        spnAlumno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idAlumno = servicio.alumnos.get(i).idalumno;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtCalif = alerta.findViewById(R.id.edt_calif);


        Button btnGuardar = (Button) alerta.findViewById(R.id.btn_guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calif = Integer.parseInt(edtCalif.getText().toString().trim());
                mandarDatosPost();
                final String json = formatDataAsJson;

                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected String doInBackground(Void... voids) {
                        return servicio.getServerResponseMandarCalificacion(json);
                    }

                    protected void onPostExecute(String result) {
                        alerta.cancel();

                        new AsyncTask<Void, Void, String>() {

                            @Override
                            protected String doInBackground(Void... voids) {
                                return servicio.getServerResponseAlumnosCal();
                            }

                            protected void onPostExecute(String result) {
                                tblAlumnoscalificaciones.removeAllViewsInLayout();
                                tblAlumnoscalificaciones.removeAllViews();
                                tablaAlumnosCalificaciones.addHeader(header);
                                tablaAlumnosCalificaciones.addData(getAlumnosCalificaciones());
                                tablaAlumnosCalificaciones.backgroundHeader(R.color.blue);
                                rows.removeAll(rows);

                            }
                        }.execute();
                    }
                }.execute();

            }
        });
        alerta.show();
    }

    public void EditarCalificacion() {
        final Dialog alerta = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
        alerta.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        alerta.setContentView(R.layout.nuevo_calificacion);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 0.84);
        int height = (int) (displaymetrics.heightPixels * 0.84);
        alerta.getWindow().setLayout(width, height);

        TextView titulo = (TextView) alerta.findViewById(R.id.titulo);
        titulo.setText("Editar Calificación Alumno");

        spnAlumno = alerta.findViewById(R.id.spn_alumno);

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                return servicio.getServerResponseAlumnos();
            }

            protected void onPostExecute(String result) {
                alumnos = new ArrayList();
                for (int i = 0; i < servicio.alumnos.size(); i++) {
                    alumnos.add(servicio.alumnos.get(i).alumnonombre + " " + servicio.alumnos.get(i).alumnoapellidos);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, alumnos);
                spnAlumno.setAdapter(arrayAdapter);

                int idalum = spnAlumno.getSelectedItemPosition();
                idAlumno = servicio.alumnos.get(idalum).idalumno;
                matricula = servicio.alumnos.get(idalum).alumnomatricula;
                nombreAlumno = servicio.alumnos.get(idalum).alumnonombre;
                apellidosAlumno = servicio.alumnos.get(idalum).alumnoapellidos;
                rfc = servicio.alumnos.get(idalum).rfc;
                curp = servicio.alumnos.get(idalum).curp;
                pass = servicio.alumnos.get(idalum).password;
                logueado = servicio.alumnos.get(idalum).login;
            }
        }.execute();

        spnAlumno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idAlumno = servicio.alumnos.get(i).idalumno;
                matricula = servicio.alumnos.get(i).alumnomatricula;
                nombreAlumno = servicio.alumnos.get(i).alumnonombre;
                apellidosAlumno = servicio.alumnos.get(i).alumnoapellidos;
                rfc = servicio.alumnos.get(i).rfc;
                curp = servicio.alumnos.get(i).curp;
                pass = servicio.alumnos.get(i).password;
                logueado = servicio.alumnos.get(i).login;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtCalif = alerta.findViewById(R.id.edt_calif);


        Button btnGuardar = (Button) alerta.findViewById(R.id.btn_guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calif = Integer.parseInt(edtCalif.getText().toString().trim());
                mandarDatosPut();
                final String json = formatDataAsJson2;

                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected String doInBackground(Void... voids) {
                        return servicio.getServerResponseEditarCalificacion(json);
                    }

                    protected void onPostExecute(String result) {
                        alerta.cancel();

                        new AsyncTask<Void, Void, String>() {

                            @Override
                            protected String doInBackground(Void... voids) {
                                return servicio.getServerResponseAlumnosCal();
                            }

                            protected void onPostExecute(String result) {
                                tblAlumnoscalificaciones.removeAllViewsInLayout();
                                tblAlumnoscalificaciones.removeAllViews();
                                tablaAlumnosCalificaciones.addHeader(header);
                                tablaAlumnosCalificaciones.addData(getAlumnosCalificaciones());
                                tablaAlumnosCalificaciones.backgroundHeader(R.color.blue);
                                rows.removeAll(rows);

                            }
                        }.execute();
                    }
                }.execute();

            }
        });
        alerta.show();
    }

    public void mandarDatosPost() {

        ModeloDocente cp = new ModeloDocente();
        cp.setCalif(calif);
        cp.setIdalumno(idAlumno);
        cp.setIdgrupo(idGrupo);

        ConexionWSDocente p = new ConexionWSDocente();
        formatDataAsJson = p.formatDataAsJson(cp);
    }

    public void mandarDatosPut() {

        ModeloDocente cp = new ModeloDocente();
        cp.setCalif(calif);
        cp.setIdalumno(idAlumno);
        cp.setIdgrupo(idGrupo);
        cp.setGrupo(clvGrupo);
        cp.setAlumnomatricula(matricula);
        cp.setAlumnonombre(nombreAlumno);
        cp.setAlumnoapellidos(apellidosAlumno);
        cp.setRfc(rfc);
        cp.setCurp(curp);
        cp.setPassword(pass);
        cp.setLogin(logueado);

        ConexionWSDocente p = new ConexionWSDocente();
        formatDataAsJson2 = p.formatDataAsJson2(cp);
    }
}

