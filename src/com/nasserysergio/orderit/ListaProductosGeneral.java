package com.nasserysergio.orderit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListaProductosGeneral extends Activity{
	String IdProductosGeneral="IdProductosGeneral";
	ArrayList<Producto> ListaProductos;
	String text;
	ListView lista;
	Producto[] arrayProductos;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		ListaProductos= new ArrayList<Producto>();
		RellenarDeTxT();
		CrearListaProductos(text);
		setContentView(R.layout.activity_listaproductosgeneral);
		lista=(ListView) findViewById(R.id.listViewProductosGeneral);
		arrayProductos=ListaProductos.toArray(new Producto[ListaProductos.size()]);
		AdaptadorProducto ap= new AdaptadorProducto(this, arrayProductos);
		lista.setAdapter(ap);
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> ListaProductos, View view, int pos,
					long id) {
				Producto productoSeleccionado;
				productoSeleccionado=(Producto)ListaProductos.getItemAtPosition(pos);
				int idP=productoSeleccionado.getId();
				Intent i= new Intent(getApplicationContext(),ListaProductos.class);
				i.putExtra("id", String.valueOf(idP));
				startActivity(i);
			}
		});
		
	}
	
	private void RellenarDeTxT(){
		
	AssetManager asset= getAssets();
	InputStream is=null;
	
	try{
		is=asset.open(IdProductosGeneral + ".txt");
		text= cargarArchivoTexto(is);
		
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
	}
	
	public String cargarArchivoTexto(InputStream is) throws IOException{
		ByteArrayOutputStream bs= new ByteArrayOutputStream();
		byte[] bytes= new byte[4096];
		int len=0;
		while((len=is.read(bytes))>0)
			bs.write(bytes, 0, len);
		return new String(bs.toByteArray(),"UTF8");
	}
	
	public void CrearListaProductos (String cadena){
		String nombreProducto;
		String idProducto;
		String[] cargaProductos;
		cargaProductos=cadena.split("/");
		Producto p;
		for(String elemento : cargaProductos){
			if (elemento.indexOf("nombre")!=-1){
			int index=elemento.indexOf("nombre:");
			nombreProducto=elemento.substring(index+8);
			
			index=elemento.indexOf("id:");
			int b=elemento.indexOf("nombre:");
			
			idProducto=elemento.substring(index+3,elemento.indexOf("nombre")-2);
			p= new Producto(Integer.parseInt(idProducto),nombreProducto);
			ListaProductos.add(p);
			}
		}
		
		
	}
}
