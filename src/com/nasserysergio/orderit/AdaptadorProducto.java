package com.nasserysergio.orderit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorProducto extends ArrayAdapter<Producto> {
	  private final Context context;
	  private final Producto[] values;

	  public AdaptadorProducto(Context context, Producto[] values) {
	    super(context, R.layout.rowlayout, values);
	    this.context = context;
	    this.values = values;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.elementoLista);
	    textView.setText(values[position].toString());
	    return rowView;
	  }
	} 