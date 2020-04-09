package com.euniva.univa_calificaciones.Vistas.Fragments.Calificacion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.euniva.univa_calificaciones.ConexionWSAlumno;
import com.euniva.univa_calificaciones.R;
import com.euniva.univa_calificaciones.Table;

import java.util.ArrayList;

public class CalificacionFragment extends Fragment {

    Spinner spnCarrera;
    public static Spinner spnPeriodo;
    public ArrayList carrera, periodo;
    public static int idPeriodo;
    private TableLayout tblCalificaciones;
    private String[] header = {"Grupo", "Materia", "Calif"};
    private ArrayList<String[]> rows = new ArrayList<>();
    Table tablaCalificaciones;

    ConexionWSAlumno servicio = new ConexionWSAlumno();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calificacion, container, false);

        spnPeriodo = view.findViewById(R.id.spn_periodo);

        tblCalificaciones = view.findViewById(R.id.tbl_calificaciones);

        tablaCalificaciones = new Table(tblCalificaciones, getActivity().getApplicationContext());


        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                return servicio.getServerResponseCarrera();
            }

            protected void onPostExecute(String result) {
                carrera = new ArrayList();
                for (int i = 0; i < servicio.carrera.size(); i++) {
                    carrera.add(servicio.carrera.get(i).carrera);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, carrera);
                spnCarrera = getView().findViewById(R.id.spn_carrera);
                spnCarrera.setAdapter(arrayAdapter);


            }
        }.execute();

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                return servicio.getServerResponsePeriodo();
            }

            protected void onPostExecute(String result) {
                periodo = new ArrayList();
                for (int i = 0; i < servicio.periodo.size(); i++) {
                    periodo.add(servicio.periodo.get(i).periodo);
                }

                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, periodo);
                spnPeriodo.setAdapter(arrayAdapter2);

                int idperio = spnPeriodo.getSelectedItemPosition();

                idPeriodo = servicio.periodo.get(idperio).idperiodo;

                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected String doInBackground(Void... voids) {
                        return servicio.getServerResponseCalificacion();
                    }

                    protected void onPostExecute(String result) {
                        tablaCalificaciones.addHeader(header);
                        tablaCalificaciones.addData(getCalificaciones());
                        tablaCalificaciones.backgroundHeader(R.color.blue);
                        tblCalificaciones.removeAllViewsInLayout();
                        tblCalificaciones.removeAllViews();
                        rows.removeAll(rows);
                    }
                }.execute();
            }
        }.execute();

        spnPeriodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                tblCalificaciones.removeAllViewsInLayout();
                tblCalificaciones.removeAllViews();
               idPeriodo=servicio.periodo.get(i).idperiodo;

                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected String doInBackground(Void... voids) {
                        return servicio.getServerResponseCalificacion();
                    }

                    protected void onPostExecute(String result) {
                        tablaCalificaciones.addHeader(header);
                        tablaCalificaciones.addData(getCalificaciones());
                        tablaCalificaciones.backgroundHeader(R.color.blue);
                        rows.removeAll(rows);
                    }
                }.execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private ArrayList<String[]> getCalificaciones() {

        for (int i = 0; i < servicio.calificacion.size(); i++) {
            rows.add(new String[]{servicio.calificacion.get(i).grupo, servicio.calificacion.get(i).materia, String.valueOf(servicio.calificacion.get(i).calificacion)});
        }
        servicio.calificacion.clear();
        return rows;
    }
}


