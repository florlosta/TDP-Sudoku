package LogicaSudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Matriz {
	private Celda[][] tablero;
	private int[][] tableroAuxiliar;
	private final int cantFilasColumnas = 9;

	/**
	 * Constructor del tablero, se encarga de chequear si el archivo cumple con los
	 * requisitos y de inicializar el tablero.
	 * 
	 * @param path, ruta del archivo.
	 */
	public Matriz(String path) {
		boolean solucionCorrecta;
		solucionCorrecta = chequearArchivo(path);

		if (solucionCorrecta) {
			inicializarTablero();
		}
	}

	/**
	 * Metodo privado que se encarga de inicializar los valores aleatorios del tablero de juego.
	 */
	private void inicializarTablero() {
		int numeroLeido;
		tablero = new Celda[this.cantFilasColumnas][this.cantFilasColumnas];

		for (int i = 0; i < this.cantFilasColumnas; i++) {
			for (int j = 0; j < this.cantFilasColumnas; j++) {
				numeroLeido = tableroAuxiliar[i][j];

				Random random = new Random();	//Genero un nuevo numero de forma aleatoria
				int value = random.nextInt(3);	//con posibilidad de que salga un 0, 1 o 2.
				tablero[i][j] = new Celda();

				if (value == 0) {
					tablero[i][j].setValor(numeroLeido - 1, true);	//Seteo el valor de la celda con el numero leido de mi archivo.
					tablero[i][j].noModificar();					//Y como es una celda inicial, no quiero que sea modificada en el transcurso del juego.
				} else {
					tablero[i][j].setValor(null, true);				//Si mi numero random fue un 1 o 2, la celda tiene un valor inicial de nulo. 
				}
			}
		}

	}

	/**
	 * Metodo privado que lee y procesa el archivo para saber si cumple con las restricciones de tamaño.
	 * @param path, ruta del archivo.
	 * @return boolean, verdadero si se trata de un archivo soudoku valido, falso en caso contrario.
	 */
	private boolean chequearArchivo(String path) {
		int contador = 0;
		String[] aux;
		String linea;
		boolean correcto = true;

		try {
			
			tableroAuxiliar = new int[this.cantFilasColumnas][this.cantFilasColumnas];		//Creo un nuevo tablero de control.

			InputStream input = Matriz.class.getClassLoader().getResourceAsStream(path);
			InputStreamReader inputRe = new InputStreamReader(input);
			BufferedReader buffer = new BufferedReader(inputRe);

			linea = buffer.readLine();

			while (linea != null) {			//Mientras que mi archivo tenga lineas por procesar.
				aux = linea.split(" ");

				if (aux.length != 9) { // Controla que la cantidad de columnas sea igual a 9
					correcto = false; // Ademas controlo que lo que separa a los numeros sea estrictamente un espacio.
				}

				if (contador > 8)
					correcto = false; // Controla que la cantidad de filas no sea mayor a 9

				for (int i = 0; correcto && i < this.cantFilasColumnas; i++) {	
					tableroAuxiliar[contador][i] = Integer.valueOf(aux[i]);			//Establezco el valor de mi tablero auxiliar leyendo desde el archivo.
				}

				linea = buffer.readLine();
				contador++;															//Mi contador controla la cantidad de filas leidas. 
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; correcto && i < this.cantFilasColumnas; i++) {
			for (int j = 0; correcto && j < this.cantFilasColumnas; j++) {
				correcto = chequearElementosArchivo(i, j, tableroAuxiliar[i][j]);		//Una vez inicializado el archivo, controlo que cumpla con las reglas del sudoku.
			}
		}

		return correcto;
	}
	
	/**
	 * Metodo privado que controla que los elementos del archivo cumplan las restricciones del sudoku.
	 */
	private boolean chequearElementosArchivo(int fila, int columna, int elemento) {
		boolean correcto = true;
		int filaInicio = 0;
		int columnaInicio = 0;
		int filaFin = 0;
		int columnaFin = 0;

		if (elemento < 1 || elemento >9) 
			correcto = false;
		
		// Si el digito no esta en la fila
		for (int col = columna + 1; correcto && col < 9; col++) { // comienzo desde columna+1 para que el numero no se
																	// compare con si mismo
			if (tableroAuxiliar[fila][col] == elemento) { // ademas ahorro comparar con los numeros recorridos
															// previamente
				correcto = false;
			}
		}

		// Si el digito no esta en la columna
		for (int fil = fila + 1; correcto && fil < 9; fil++) { // misma situacion, comienzo en fila+1 y no se chequea
																// con si mismo
			if (tableroAuxiliar[fil][columna] == elemento) {
				correcto = false;
			}
		}

		if (fila >= 0 && fila <= 2) {
			filaInicio = 0;
			filaFin = 2;
		} else if (fila >= 3 && fila <= 5) {
			filaInicio = 3;
			filaFin = 5;
		} else {
			filaInicio = 6;
			filaFin = 8;
		}

		// Coordenadas cuadrante para la columna
		if (columna >= 0 && columna <= 2) {
			columnaInicio = 0;
			columnaFin = 2;
		} else if (columna >= 3 && columna <= 5) {
			columnaInicio = 3;
			columnaFin = 5;
		} else {
			columnaInicio = 6;
			columnaFin = 8;
		}

		for (int i = filaInicio; correcto && i <= filaFin; i++) {
			for (int j = columnaInicio; correcto && j <= columnaFin; j++) {
				if (tableroAuxiliar[i][j] == elemento && i != fila && j != columna) {
					correcto = false;
				}
			}
		
		}
		return correcto;
	}


	/**
	 * Metodo que recorre el tablero de mi juego y controla si se cumplen las reglas del sudoku.
	 */
	public void chequearTablero() {
		boolean correcto = true;

		for (int i = 0; i < this.cantFilasColumnas; i++) {
			for (int j = 0; j < this.cantFilasColumnas; j++) {
				if (tablero[i][j].getValor() != null && tablero[i][j].getModificar()) { //Si mi celda no es nula, y puede ser modificada, controlo el elemento.
					correcto = chequearElementoTablero(i, j, tablero[i][j].getValor());		//Chequeo fila, columna y cuadrante, con otro metodo privado. 
				}

		
				if (!correcto) {											//Si algun elemento incumple las reglas, modifico su estado interno.
					if (tablero[i][j].getModificar()) {	
						tablero[i][j].setValor(tablero[i][j].getValor(), false);	//Con un parametro falso, indica que debe ser utilizada la imagen incorrecta.
					}
				}
			}
		}
	}

	/**
	 * Metodo que chequea si el tablero de juego respeta las reglas del sudoku en un momento determinado. 
	 */
	public boolean chequearElementoTablero(int fila, int columna, Integer valorCelda) {
		boolean correcto = true;
		int filaInicio = 0;
		int columnaInicio = 0;
		int filaFin = 0;
		int columnaFin = 0;

		if (valorCelda != null) {
			// Si el digito no esta en la fila
			for (int col = 0; correcto && col < 9; col++) { 
																	
				if (tablero[fila][col].getValor() == valorCelda && col != columna) { 
					System.out.println (tablero[fila][col].getModificar());
													
					correcto = false;
				}
			}
			

			// Si el digito no esta en la columna
			for (int fil = 0; correcto && fil < 9; fil++) { 
														
				if (tablero[fil][columna].getValor() == valorCelda && fila != fil) {
					correcto = false;
				}
			}

			// Coordenadas cuadrante para la fila
			if (fila >= 0 && fila <= 2) {
				filaInicio = 0;
				filaFin = 2;
			} else if (fila >= 3 && fila <= 5) {
				filaInicio = 3;
				filaFin = 5;
			} else {
				filaInicio = 6;
				filaFin = 8;
			}

			// Coordenadas cuadrante para la columna
			if (columna >= 0 && columna <= 2) {
				columnaInicio = 0;
				columnaFin = 2;
			} else if (columna >= 3 && columna <= 5) {
				columnaInicio = 3;
				columnaFin = 5;
			} else {
				columnaInicio = 6;
				columnaFin = 8;
			}

			for (int i = filaInicio; correcto && i <= filaFin; i++) {
				for (int j = columnaInicio; correcto && j <= columnaFin; j++) {
					if (tablero[i][j].getValor() == valorCelda && i != fila && j != columna) {
						correcto = false;
					}
				}
			}
		}
		return correcto;
	}
	
	/**
	 * Metodo que chequea si el jugador gano la partida.
	 */
	public boolean gano() {
		boolean gano = true;

		for (int i = 0; gano && i < this.cantFilasColumnas; i++) {		//Recorro todo el tablero
			for (int j = 0; gano && j < this.cantFilasColumnas; j++) {
				gano = this.chequearElementoTablero(i, j, tablero[i][j].getValor());	//Controlo que ningun elemento incumpla las reglas
				if (tablero[i][j].getValor() == null) {	//Y controlo que no queden celdas vacias.
					gano = false;
				}
			}
		}
		return gano;
	}

	/**
	 * Metodo que completa el tablero en caso de que el jugador se rinda.
	 */
	public void rendirse() {
		for (int i = 0; i < this.cantFilasColumnas; i++) {	//Recorro todo el tablero
			for (int j = 0; j < this.cantFilasColumnas; j++) {
				tablero[i][j].setValor(tableroAuxiliar[i][j] - 1, true);	//Recupero los valores de mi archivo original y seteo los valores en el tablero de juego.
				tablero[i][j].noModificar();				//Evito que el jugador modifique las celdas una vez que se rindio.
			}
		}
	}

	/**
	 * Metodo que actualiza el estado interno de las celdas.
	 * @param correcto, parametro para indicar si es necesario una actualizacion con imagen correcta o incorrecta.
	 */
	public void accionar(Celda c, boolean correcto) {
		if (correcto)
			c.actualizar(true);
		else
			c.actualizar(false);
	}

	/**
	 * Metodo que retorna la Celda en una posicion indicada por la fila y la columna.
	 */
	public Celda getCelda(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Metodo que retorna la cantidad de filas y columnas.
	 */
	public int getCantFilas() {
		return cantFilasColumnas;
	}

	/**
	 * Metodo que retorna el tablero de juego en su estado actual.
	 */
	public Celda[][] getTablero() {
		return tablero;
	}

}
