package com.euniva.univa_calificaciones.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.euniva.univa_calificaciones.ConexionWSAlumno;
import com.euniva.univa_calificaciones.R;

public class LoginAlumno extends AppCompatActivity {

    private EditText edtMatricula, edtContrasena;
    private Button btnEntrar;
    public static String matricula, contrasena;
    private ProgressBar progressBar;
    public static String usuario, matri;

    ConexionWSAlumno servicio = new ConexionWSAlumno();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_alumno);

        progressBar = findViewById(R.id.progress_circular);

        edtMatricula = findViewById(R.id.edt_matricula);
        edtContrasena = findViewById(R.id.edt_password);
        btnEntrar = findViewById(R.id.btn_entrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean vacio = BuscarVacio();

                progressBar.setVisibility(View.VISIBLE);

                matricula = edtMatricula.getText().toString().trim();
                contrasena = edtContrasena.getText().toString().trim();

                if (!vacio) {
                    new AsyncTask<Void, Void, String>() {

                        @Override
                        protected String doInBackground(Void... voids) {
                            return servicio.getServerResponseLogin();
                        }

                        protected void onPostExecute(String result) {

                            if (servicio.response == null) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Usuario incorrecto, verifique su matrícula y/o contraseña", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), NavAlumno.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                            usuario = servicio.ma.nombre + " " +servicio.ma.apellidos;
                            matri = servicio.ma.matricula;
                        }

                    }.execute();
                }
            }
        });

    }

    private boolean BuscarVacio() {

        boolean vacio = false;
        String matricula = edtMatricula.getText().toString();
        String password = edtContrasena.getText().toString();

        if (matricula.equals("")) {
            vacio = true;
            edtMatricula.setError("Ingresa tu matrícula");
        } else if (password.equals("")) {
            vacio = true;
            edtContrasena.setError("Ingresa tu contraseña");
        }
        return vacio;
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Inicio.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
