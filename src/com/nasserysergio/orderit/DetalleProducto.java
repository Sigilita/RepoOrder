package com.nasserysergio.orderit;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DetalleProducto extends Activity{
	private Button btnmas;
	private Button btnmenos;
	private EditText descripcion;
	private ImageView imagen;
	private EditText cantidad;
	private Button btnAceptar;
	private Button btnCancelar;
	private String IdProducto;
	private EditText nombreProducto;
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		IdProducto=getIntent().getExtras().getString("id");
		setContentView(R.layout.layoutdetalle);
		btnmas=(Button) findViewById(R.id.btnmas);
		btnmenos=(Button) findViewById(R.id.bntmenos);
		btnAceptar=(Button) findViewById(R.id.btnAceptar);
		btnCancelar=(Button) findViewById(R.id.btnCancelar);
		descripcion=(EditText) findViewById(R.id.etdescripcion);
		descripcion.setFocusable(false);
		descripcion.setClickable(false);
		descripcion.setEnabled(false);
		cantidad=(EditText) findViewById(R.id.etcantidad);
		cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
		imagen=(ImageView) findViewById(R.id.imageView1);
		nombreProducto=(EditText) findViewById(R.id.etNombre);
		nombreProducto.setFocusable(false);
		nombreProducto.setClickable(false);
		nombreProducto.setEnabled(false);
		AssetManager asset= getAssets();
		InputStream is=null;
		
		try{
			is=asset.open(IdProducto + ".txt");
			String text= cargarArchivoTexto(is);
			SetDatosInterface(text);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally{
			if(is!=null)
				try {
					is.close();
				}
			catch (IOException e){
			}
		}
		
		btnAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//enviar informacion de la cantidad
				String cantidadProducto=cantidad.getText().toString();
				String idProdcuto=IdProducto;
			}
		});
		btnCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		btnmas.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String cantidadActual=cantidad.getText().toString();
				try{
					int cantidadActualInteger= Integer.parseInt(cantidadActual);
					cantidadActualInteger=cantidadActualInteger+1;
					cantidad.setText(String.valueOf(cantidadActualInteger));
				}catch (Exception ex) {
				cantidad.setText(String.valueOf(0));
				}
			}
		});
		
		btnmenos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String cantidadActual=cantidad.getText().toString();
				try{
					int cantidadActualInteger= Integer.parseInt(cantidadActual);
					cantidadActualInteger=cantidadActualInteger-1;
					cantidad.setText(String.valueOf(cantidadActualInteger));
				}catch (Exception ex) {
				cantidad.setText(String.valueOf(0));
				}
			}
		});
	}
	public String cargarArchivoTexto(InputStream is) throws IOException{
		ByteArrayOutputStream bs= new ByteArrayOutputStream();
		byte[] bytes= new byte[4096];
		int len=0;
		while((len=is.read(bytes))>0)
			bs.write(bytes, 0, len);
		return new String(bs.toByteArray(),"UTF8");
	}
	
	public void SetDatosInterface(String cadena){
		String descripcionString;
		String imagenString;
		String nombreString;
		int index=cadena.indexOf("descripcion:");
		descripcionString=cadena.substring(index+12);
		descripcionString=descripcionString.substring(0, descripcionString.indexOf("*"));
		
		index=cadena.indexOf("nombre:");
		nombreString=cadena.substring(index+7);
		nombreString=nombreString.substring(0, nombreString.indexOf("*"));
		
		index=cadena.indexOf("imagen:");
		imagenString=cadena.substring(index-7);
		imagenString=imagenString.substring(0, imagenString.indexOf("*"));
		
		descripcion.setText(descripcionString);
		nombreProducto.setText(nombreString);
		/*try {
			SetImagen(imagenString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		SetImagen();
		
	}
	public void SetImagen(String direccion) throws IOException{
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		File file;
		try{
			file= new File(direccion);
			fis=new FileInputStream(file);
			bis= new BufferedInputStream(fis);
			Bitmap bmap= BitmapFactory.decodeStream(bis);
			imagen.setImageBitmap(bmap);
		}catch (Exception ex){}
		finally{
			bis.close();
			fis.close();
		}
	}
	public void SetImagen(){
		imagen.setImageResource(R.drawable.id5656);
	}
	
	
}
