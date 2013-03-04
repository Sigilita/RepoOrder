package com.nasserysergio.orderit;

public class Producto
{
	private String nombre;
	private int cantidad;
	private int id;
	private float precioUnidad;

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
	public Producto(int id, int cantidad,String nombre)
	{
		this.id=id;
		this.cantidad =cantidad;
		this.nombre=nombre;
	}
	public Producto(String nombre, int cantidad, float precioUnidad)
	{
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precioUnidad = precioUnidad;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public float getPrecioUnidad() {
		return precioUnidad;
	}

	public void setPrecioUnidad(float precioUnidad) {
		this.precioUnidad = precioUnidad;
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