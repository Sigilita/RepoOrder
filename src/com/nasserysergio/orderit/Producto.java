package com.nasserysergio.orderit;

public class Producto
{
	private String nombre;
	private int cantidad;
	private int id;


	@Override
	public String toString() {
		return nombre;
	}
	public Producto()
	{
		nombre = "";
		cantidad = 0;
	}
	public Producto(int id, String nombre)
	{
		this.id=id;
		this.nombre =nombre;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Producto(String nombre, int cantidad)
	{
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}