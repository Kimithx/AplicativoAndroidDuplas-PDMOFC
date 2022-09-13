package com.example.aplicativoandroid_duplas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class Corrida extends AppCompatActivity {

    private TextView txtLatitude;
    private TextView txtLongitude;
    private TextView txtCidade;
    private TextView txtEstado;
    private TextView txtPais;

    private Location location;
    private LocationManager locationManager;

    private Address endereco;

    Button voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrida);

        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        txtCidade = (TextView) findViewById(R.id.txtCidade);
        txtEstado = (TextView) findViewById(R.id.txtEstado);
        txtPais = (TextView) findViewById(R.id.txtPais);

        double latitude = 0.0;
        double longitude = 0.0;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){

        } else {
            locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null){
            longitude = location.getLongitude();
                    latitude = location.getLatitude();
        }
        txtLongitude.setText("Longitude: " + longitude);
        txtLatitude.setText("Latitude: " + latitude);

        try {
            endereco = buscarEndereco(latitude, longitude);

            txtCidade.setText("Cidade: " + endereco.getLocality());
            txtEstado.setText("Estado " + endereco.getAdminArea());
            txtPais.setText("País..: " + endereco.getCountryName());

        } catch (IOException e) {

            Log.e("GPS", e.getMessage());
        }


        voltar = findViewById(R.id.voltar3);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Corrida.this, MainActivity.class);
                startActivity(it);
            }
        });
    }

    public Address buscarEndereco(double latitude, double longitude)
     throws IOException {

        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());

        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        if(addresses.size() > 0) {
            address = addresses.get(0);
        }

        return address;
    }
}