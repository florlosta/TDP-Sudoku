package LogicaSudoku;

import javax.swing.ImageIcon;

public class EntidadGrafica {
	private ImageIcon grafico;
	private String[] imagenes;
	private String[] imagenesIncorrectas;
	
	/**
	 * Inicializa las imagenes tanto correctas como incorrectas, que luego seran utilizadas por las celdas.
	 */
	public EntidadGrafica() {
		grafico = new ImageIcon();
		imagenes = new String[] {"/ImagenesNumeros/numero1.png", "/ImagenesNumeros/numero2.png", "/ImagenesNumeros/numero3.png", "/ImagenesNumeros/numero4.png",
				"/ImagenesNumeros/numero5.png", "/ImagenesNumeros/numero6.png", "/ImagenesNumeros/numero7.png", "/ImagenesNumeros/numero8.png", "/ImagenesNumeros/numero9.png"};
		imagenesIncorrectas = new String[] {"/ImagenesNumeros/numeroIn1.png", "/ImagenesNumeros/numeroIn2.png", "/ImagenesNumeros/numeroIn3.png", 
				"/ImagenesNumeros/numeroIn4.png", "/ImagenesNumeros/numeroIn5.png", "/ImagenesNumeros/numeroIn6.png", "/ImagenesNumeros/numeroIn7.png", "/ImagenesNumeros/numeroIn8.png", "/ImagenesNumeros/numeroIn9.png"};
	}
	
	/**
	 * Metodo que modifica la imagen que posee una celda, ya sea correcta o incorrecta.
	 */
	public void actualizar(int indice, boolean correcto) {
		if (correcto) {
			if (indice < imagenes.length) {
				ImageIcon imagen = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));	//Si mi imagen es correcta,
				grafico.setImage(imagen.getImage());													//busco el valor en mi arreglo de imagenes.
			}
		} else {
			if (indice < imagenesIncorrectas.length) {
			ImageIcon imagen = new ImageIcon(this.getClass().getResource(this.imagenesIncorrectas[indice]));//En cambio, si se trata de una imagen correcta,
			grafico.setImage(imagen.getImage());															//busco el valor en mi arreglo de imagenesIncorrectas. 
			}
		}
	}

	/**
	 * Metodo que retorna el ImageIcon de la entidad grafica.
	 */
	public ImageIcon getGrafico() {
		return this.grafico;
	}
	
	/**
	 * Metodo que setea el ImageIcon de la entidad grafica.
	 */
	public void setGrafico(ImageIcon grafico) {
		this.grafico = grafico;
	}
	
	/**
	 * Metodo que retorna el arreglo con las rutas de las imagenes.
	 */
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	/**
	 * Metodo que setea el arreglo de imagenes con sus respetivas rutas.
	 * @param imagenes
	 */
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
}
