package com.euniva.univa_calificaciones.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.euniva.univa_calificaciones.R;
import com.euniva.univa_calificaciones.Vistas.FragmentAlumno.Calificacion.CalificacionFragment;
import com.euniva.univa_calificaciones.Vistas.FragmentAlumno.Historial.HistorialFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class NavAlumno extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    LoginAlumno la = new LoginAlumno();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_alumno);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView usuario = (TextView) hView.findViewById(R.id.txt_user);
        usuario.setText(la.usuario);
        TextView matricula=(TextView) hView.findViewById(R.id.txt_matricula);
        matricula.setText(la.matri);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalificacionFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_calificacion);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_calificacion:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalificacionFragment()).commit();
                break;
            case R.id.nav_historial:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistorialFragment()).commit();
                break;
            case R.id.nav_salir:
                Intent intent = new Intent(getApplicationContext(), Inicio.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}