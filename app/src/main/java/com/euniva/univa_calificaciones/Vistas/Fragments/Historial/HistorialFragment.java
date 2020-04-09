package com.euniva.univa_calificaciones.Vistas.Fragments.Historial;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.euniva.univa_calificaciones.ConexionWSAlumno;
import com.euniva.univa_calificaciones.R;
import com.euniva.univa_calificaciones.Table;

import java.util.ArrayList;

public class HistorialFragment extends Fragment {

    Spinner spnCarrera;
    public ArrayList carrera;
    private TableLayout tblHistorial;
    private String[] header = {"Grupo", "Materia", "Calif"};
    private ArrayList<String[]> rows = new ArrayList<>();
    Table tablaHistorial;

    ConexionWSAlumno servicio = new ConexionWSAlumno();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        tblHistorial= view.findViewById(R.id.tbl_historial);

        tablaHistorial = new Table(tblHistorial, getActivity().getApplicationContext());

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

        tblHistorial.removeAllViewsInLayout();
        tblHistorial.removeAllViews();

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                return servicio.getServerResponseHistorial();
            }

            protected void onPostExecute(String result) {
                tablaHistorial.addHeader(header);
                tablaHistorial.addData(getHistorial());
                tablaHistorial.backgroundHeader(R.color.blue);
                rows.removeAll(rows);
            }
        }.execute();

        return view;
    }

    private ArrayList<String[]> getHistorial() {

        for (int i = 0; i < servicio.historial.size(); i++) {
            rows.add(new String[]{servicio.historial.get(i).grupo, servicio.historial.get(i).materia, String.valueOf(servicio.historial.get(i).calificacion)});
        }
        servicio.historial.clear();
        return rows;
    }
}
