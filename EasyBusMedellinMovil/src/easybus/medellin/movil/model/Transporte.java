package easybus.medellin.movil.model;

/**
 * 
 * @author Mateo
 * 
 */
public class Transporte {

	private String nombre;
	private int precio;
	private Ruta ruta;
	private String tipo;

	/**
	 * Inicializa la ruta de cada transporte
	 * 
	 * @param Nombre
	 * @param precio
	 * @param tipo
	 */
	public Transporte(String Nombre, int precio, String tipo) {
		this.nombre = Nombre;
		this.precio = precio;
		this.tipo = tipo;
	}

	/**
	 * Devuleve el nombre de cada transporte
	 * 
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve el tipo del transporte
	 * 
	 * @return the Tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Asigna el nombre al transporte
	 * 
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devulve le precio del trasnporte
	 * 
	 * @return the precio
	 */
	public int getPrecio() {
		return precio;
	}

	/**
	 * Pone el precio del transporte
	 * 
	 * @param precio
	 *            the precio to set
	 */
	public void setPrecio(int precio) {
		this.precio = precio;
	}

	/**
	 * Poner el tipo al trasporte
	 * 
	 * @param nombre
	 *            the nombre to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Asignar una ruta al trasporte
	 * 
	 * @param ruta
	 *            the ruta to set
	 */
	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	/**
	 * Devolver la ruta del transporte
	 * 
	 * @return the ruta
	 */
	public Ruta getRuta() {
		return ruta;
	}

	/*
	 * Se usa para que no devuelva la posicion de memoria del nombre y precio
	 * sino la cadena de caracteres que los componen
	 */
	@Override
	public String toString() {
		return (nombre + "\n" + " $" + precio);
	}

}
