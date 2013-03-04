package com.nasserysergio.orderit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		
		btnValidar = (Button)findViewById(R.id.button1);
		editText = (EditText)findViewById(R.id.editText1);
		
		btnValidar.setOnClickListener(this); 
	}
	
	public void ejecutarActivityProductos()
	{
		Intent intent = new Intent(this, ActivityProductos.class);
        startActivity(intent);
	}

	@Override
	public void onClick(View v) 
	{
		if (comprobarCodigo(editText.getText().toString()))
		{
			ejecutarActivityProductos();
			editText.setText("");
		}
		else
			mostrarToast("Código introducido no válido");
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