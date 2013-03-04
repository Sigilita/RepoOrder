package com.nasserysergio.orderit;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityProductos extends Activity
{
	ListView listView;
	ArrayList<Producto> datos;
	AdaptadorProductos adaptador;
	TextView textViewTotal;
	Producto producto;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productos);
		textViewTotal = (TextView)findViewById(R.id.textViewTotal);
		listView = (ListView)findViewById(R.id.listView1);
		datos = new ArrayList<Producto>();
		adaptador = new AdaptadorProductos(this);
        listView.setAdapter(adaptador);
        registerForContextMenu(listView);
        try{
    		String [] datosPedido=getIntent().getExtras().getStringArray("datos");
    		producto= new Producto(Integer.parseInt(datosPedido[0]), Integer.parseInt(datosPedido[1]),datosPedido[2]);
    		datos.add(producto);
    		}catch (Exception ex){}
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.activity_productos_contextmenu, menu);
		String[] datos=getIntent().getExtras().getStringArray("datos");
	}
	
	
	@SuppressLint("ShowToast")
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Toast toast;
		
		switch (item.getItemId()) 
		{
			case R.id.borrar:
				//TODO - Borrar items
				datos.remove(info.position);
				mostrarToast("Producto borrado de la cuenta.");
				adaptador.notifyDataSetChanged();	
				actualizarPrecioTotal();
				return true;
				
			case R.id.modificar:
				//TODO - Modificar items
				AlertDialog.Builder editalert = new AlertDialog.Builder(this);
				editalert.setMessage("Introduce la nueva cantidad");
				editalert.setCancelable(false);

				final EditText input = new EditText(this);
				input.setInputType(InputType.TYPE_CLASS_NUMBER);
				
				@SuppressWarnings("deprecation")
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
				input.setLayoutParams(lp);
				editalert.setView(input);

				editalert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener()
				{
				    public void onClick(DialogInterface dialog, int whichButton)
				    {
				    	String nuevaCantidad = input.getText().toString();
				
				    	if(nuevaCantidad.equals(""))
				    		mostrarToast("No se ha podido modificar.");
				    	else
				    	{
				    		try
				    		{
					    		int cantidad = Integer.parseInt(nuevaCantidad);
					    		
					    		if (cantidad <= 0)
					    			mostrarToast("Cantidad no puede ser cero.");
					    		else
					    		{
					    			datos.get(info.position).setCantidad(cantidad);
					    			mostrarToast("Producto modificado.");
					    			adaptador.notifyDataSetChanged();
					    			actualizarPrecioTotal();
					    		}
				    		}
				    		catch(NumberFormatException e)
				    		{
				    			mostrarToast("Número demasiado largo.");
				    		}
				    	}
				    }
				});
				
				editalert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						closeOptionsMenu();
					}
				});
				
				editalert.show();
				return true;
				
			default:
				return super.onContextItemSelected(item);
		}
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
		
		MenuItem item2 = menu.add(0,1,1,"Pedir Cuenta");
		{
			item2.setIcon(R.drawable.btn_cuenta);
			item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		
		MenuItem item3 = menu.add(0,2,2,"Rellenar");
		/*{
			item3.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}*/
	}
	
	private void Rellenar()
	{
		for(int i=1;i<=3;i++)
		{
			datos.add(new Producto("Producto " + i, i, i));
		}
		
		adaptador.notifyDataSetChanged();
		actualizarPrecioTotal();
	}
	
	private void actualizarPrecioTotal()
	{
		textViewTotal.setText("Total pedido: " + calcularTotal() + "€");
	}
	
	//Método que controla la pulsación sobre los items del menú
	private boolean menuSelecciona(MenuItem item)
	{
		switch(item.getItemId())
		{
			case 0:	
				Intent i= new Intent(getApplicationContext(),ListaProductosGeneral.class);
				startActivity(i);
				return true;
			case 1:	//Opción Pedir cuenta
				return true;
			case 2:	//Opción Rellenar
				Rellenar();
				/*datos.get(4).setCantidad(5000);
				adaptador.notifyDataSetChanged();*/
				return true;
		}
		
		return false;
	}
	
	
	@SuppressLint("ShowToast")
	private void mostrarToast(String mensaje)
	{
		Toast toast = Toast.makeText(this, mensaje, 2);
		toast.show();
	}
	
	private float calcularTotal()
	{
		float total = 0;
		
		for(int i=0; i<datos.size(); i++)
		{
			total += (datos.get(i).getPrecioUnidad() * datos.get(i).getCantidad());
		}		
		
		return total;
	}
	
	//Adaptador para lista de productos.
	class AdaptadorProductos extends ArrayAdapter<Producto>
	{
		Activity context;
		
		AdaptadorProductos(Activity context)
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