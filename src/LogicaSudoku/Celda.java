package LogicaSudoku;


public class Celda {
	
	private Integer valor;
	private boolean modificar;
	private EntidadGrafica entidadGrafica;
	
	/**
	 * Constructor, crea una nueva Celda inicialmente sin valor.
	 */
	public Celda() {
		this.valor = null;
		this.entidadGrafica = new EntidadGrafica();
		modificar = true;
	}
	
	/**
	 * Metodo que actualiza el valor de la imagen de la celda, dependiendo si se trata de una celda correcta o incorrecta. 
	 * @param correcto
	 */
	public void actualizar(boolean correcto) {
		if (this.valor != null && this.valor + 1 < this.getCantElementos()) {
			this.valor++;
		}else {
			this.valor = 0;
		}
		if (correcto)
			entidadGrafica.actualizar(this.valor, true);	//Cambio el parametro para que la entidad grafica pueda saber
		else entidadGrafica.actualizar(this.valor, false);	//que imagen tiene que actualizar.
	}
	
	/**
	 * Metodo que retorna la cantidad de elementos que puede tomar una celda.
	 */
	public int getCantElementos() {
		return this.entidadGrafica.getImagenes().length;
	}
	
	/**
	 * Metodo que retorna el valor de una celda en especifico. 
	 */
	public Integer getValor() {
		return this.valor;
	}
	
	/**
	 * Metodo que setea el valor que contiene una celda, dependiendo si su posicion en el tablero es correcta o incorrecta.
	 */
	public void setValor(Integer valor, boolean correcto) {
		if (valor!=null && valor < this.getCantElementos() && modificar) {
			this.valor = valor;
			this.entidadGrafica.actualizar(this.valor, correcto);
		}else {
			this.valor = null;
		}
	}
	
	/**
	 * Metodo que retorna si una celda puede ser modificada o no. Por defecto, toda Celda inicialmente puede ser modificada.
	 * @return
	 */
	public boolean getModificar() {
		return this.modificar;
	}
	
	/**
	 * Metodo que imposibilita modificar el valor de una celda.
	 */
	public void noModificar() {
		this.modificar = false;
	}
	
	/**
	 * Metodo que retorna la entidad grafica asociada a una celda.
	 */
	public EntidadGrafica getEntidadGrafica() {
		return this.entidadGrafica;
	}
	
	/**
	 * Metodo que establece la entidad grafica asociada a una celda.
	 */
	public void setGrafica(EntidadGrafica g) {
		this.entidadGrafica = g;
	}
	
	
}
