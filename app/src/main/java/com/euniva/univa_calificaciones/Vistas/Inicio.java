package com.euniva.univa_calificaciones.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.euniva.univa_calificaciones.R;

public class Inicio extends AppCompatActivity {

    RadioButton rbtAlumno, rbtDocente;
    Spinner campus;
    private Button btnSiguiente;
    private RadioGroup rgpGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        campus = findViewById(R.id.spn_campus);
        rbtAlumno = findViewById(R.id.rbt_Alumno);
        rbtDocente = findViewById(R.id.rbt_Docente);
        btnSiguiente = findViewById(R.id.btn_siguiente);
        rgpGrupo = findViewById(R.id.rgp_Grupo);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (campus.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Seleccione un campus", Toast.LENGTH_SHORT).show();
                } else {
                    if (rgpGrupo.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getApplicationContext(), "Seleccione un tipo de usuario", Toast.LENGTH_SHORT).show();
                    } else {
                        if (rbtAlumno.isChecked()) {
                            Intent intent = new Intent(getApplicationContext(), LoginAlumno.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else if (rbtDocente.isChecked()) {
                            Intent intent = new Intent(getApplicationContext(), LoginDocente.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });
    }
}

