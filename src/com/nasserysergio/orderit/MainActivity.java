package com.nasserysergio.orderit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	Button btnValidar;
	EditText editText;
	private LocationManager locManager;
	private LocationListener locListener;
	private double lat;
	private double lon;
	private boolean isGps=false;;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		
		btnValidar = (Button)findViewById(R.id.button1);
		editText = (EditText)findViewById(R.id.editText1);
		
		btnValidar.setOnClickListener(this); 
		
		locManager= (LocationManager) getSystemService(LOCATION_SERVICE);
		isGps=locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if(!isGps){
			activarGps();
		}else{
			isGps=true;
		}
		
		Location lastLoc=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(lastLoc!=null){
			lat=lastLoc.getLatitude();
			lon=lastLoc.getLongitude();
		}
		
		locListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				cambioPosicion(location);
				
			}
		};
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000,0,locListener);
		
		
	}
	public void activarGps(){
		Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
		/*AlertDialog.Builder alerta= new AlertDialog.Builder(getApplicationContext());
		alerta.setTitle("Encendiendo GPS");
		alerta.setMessage("El GPS no esta conectado. Conectelo Ahora.");
		alerta.setPositiveButton("Configuracion", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
		});
		/*AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
		 
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
 
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
 
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getApplicationContext().startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });*/
       /* alerta.show();*/
	}
	public void cambioPosicion(Location loc)
	{
		if(loc!=null){
			lat=loc.getLatitude();
			lon=loc.getLongitude();
		}else{
			lat=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
			lon=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
		}
	}
	
	public void ejecutarActivityProductos()
	{
		Intent intent = new Intent(this, ActivityProductos.class);
        startActivity(intent);
	}
	private void isGpsEnabled(){
		isGps=locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	

	@Override
	public void onClick(View v) 
	{
		isGpsEnabled();
		if(isGps){
		if (comprobarCodigo(editText.getText().toString()))
		{
			ejecutarActivityProductos();
			editText.setText("");
		}
		else
			mostrarToast("Código introducido no válido");
		}else{
			mostrarToast("Ha de conectar el gps");
			activarGps();
		}
		
	}
	
	@SuppressLint("ShowToast")
	private void mostrarToast(String mensaje)
	{
		Toast toast = Toast.makeText(this, mensaje, 2);
		toast.show();
	}
	
	private boolean comprobarCodigo(String codigo)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(this.getAssets().open("validacion.txt")));
			String codigoFichero = br.readLine();
			br.close();
			
			if(codigoFichero.equals(codigo))
				return true;
			else
				return false;
		} 
		catch (IOException e)
		{
			mostrarToast("Error lectura fichero");
			return false;
		}	
	}
	
}