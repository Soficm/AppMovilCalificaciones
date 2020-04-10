package com.euniva.univa_calificaciones.Vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.euniva.univa_calificaciones.ConexionWSDocente;
import com.euniva.univa_calificaciones.R;


public class LoginDocente extends AppCompatActivity {

    private EditText edtClave, edtContrasena;
    private Button btnEntrar;
    public static String clave, contrasena;
    private ProgressBar progressBar;
    public static String usuario, clav;
    public static int id;

    ConexionWSDocente servicio = new ConexionWSDocente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_docente);

        progressBar = findViewById(R.id.progress_circular);

        edtClave = findViewById(R.id.edt_clave);
        edtContrasena = findViewById(R.id.edt_password);
        btnEntrar = findViewById(R.id.btn_entrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean vacio = BuscarVacio();

                progressBar.setVisibility(View.VISIBLE);

                clave = edtClave.getText().toString().trim();
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
                                Toast.makeText(getApplicationContext(), "Usuario incorrecto, verifique su clave y/o contraseña", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), NavDocente.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                usuario = servicio.mc.nombre + " " + servicio.mc.apellidos;
                                clav = servicio.mc.usuario;
                                id = servicio.mc.id;
                            }

                        }

                    }.execute();
                }
            }
        });

    }

    private boolean BuscarVacio() {

        boolean vacio = false;
        String clave = edtClave.getText().toString();
        String password = edtContrasena.getText().toString();

        if (clave.equals("")) {
            vacio = true;
            edtClave.setError("Ingresa tu clave");
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