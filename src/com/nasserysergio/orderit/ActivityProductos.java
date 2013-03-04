package com.nasserysergio.orderit;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityProductos extends Activity 
{
	ListView listView;
	ArrayList<Producto> datos;
	AdaptadorSimple adaptador;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productos);	
		

		listView = (ListView)findViewById(R.id.listView1);
		datos = new ArrayList<Producto>();
		adaptador = new AdaptadorSimple(this);
        listView.setAdapter(adaptador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		creaMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return menuSelecciona(item);
	}
	
	//Crear menú de la interfaz
	@SuppressLint("NewApi")
	private void creaMenu(Menu menu)
	{
		MenuItem item1 = menu.add(0,0,0,"Agregar Producto");
		{
			item1.setIcon(R.drawable.btn_add);
			item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		
		MenuItem item2 = menu.add(0,1,1,"Rellenar");
		{
			item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	}
	
	private void Rellenar()
	{
		for(int i=1;i<=20;i++)
		{
			datos.add(new Producto("Producto " + i, i));
		}
		
		adaptador.notifyDataSetChanged();
	}
	
	//Método que controla la pulsación sobre los items del menú
	private boolean menuSelecciona(MenuItem item)
	{
		switch(item.getItemId())
		{
			case 0:	//Opción Agregar producto
				return true;
			case 1:	//Opción Salir
				Rellenar();
				/*datos.get(4).setCantidad(5000);
				adaptador.notifyDataSetChanged();*/
				return true;
		}
		
		return false;
	}
	
	//Adaptador para lista de productos.
	class AdaptadorSimple extends ArrayAdapter<Producto>
	{
		Activity context;
		
		AdaptadorSimple(Activity context)
		{
			super(context, R.layout.listitem_productos, datos);
			this.context = context;
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.listitem_productos, null);
			
			TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
			lblTitulo.setText(datos.get(position).getNombre());
			
			TextView lblSubtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
			lblSubtitulo.setText("Cantidad : " + datos.get(position).getCantidad());
			
			return item;
		}
	}
}