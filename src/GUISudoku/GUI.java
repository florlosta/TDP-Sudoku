package GUISudoku;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import LogicaSudoku.Celda;
import LogicaSudoku.Matriz;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel[][] matriz;
	private JPanel contentPane;
	private Matriz juego;
	private JPanel panelDerecho;
	private JPanel panelBotones;
	private JPanel panelMatrizTablero;
	private JButton botonRendirse, botonSolucion;
	private CronometroGUI cronometro;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		
		setTitle ("SUDOKU");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 491);
		contentPane = new PanelImagen();	//Creo un nuevo panel de tipo PanelImagen() que tiene una imagen de fondo.
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setOpaque(false);		//Hago que no sea opaco para poder ver el fondo correctamente.
		contentPane.setLayout(null);

		panelDerecho = new JPanel();		//Panel en el que luego iran los botones y el cronometro.
		panelDerecho.setBounds(527, 0, 222, 424);
		contentPane.add(panelDerecho);
		panelDerecho.setOpaque(false);
		panelDerecho.setLayout(null);

		cronometro = new CronometroGUI();	//Creo un nuevo cronometro para el juego de tipo CronometroGUI()
		cronometro.setBounds(20, 20, 166, 82);
		panelDerecho.add(cronometro);		//Agrego mi cronometro al juego.
		cronometro.setLayout(null);

		panelBotones = new JPanel();		//Creo el panel donde van a ir los dos botones.
		panelBotones.setBounds(10, 161, 186, 82);
		panelDerecho.add(panelBotones);
		panelBotones.setOpaque(false);
		panelBotones.setLayout(null);

		botonSolucion = new JButton("Verificar soluci\u00F3n");	//Creo mi boton que verifica la solucion parcial o total del juego.
		botonSolucion.setBounds(0, 5, 176, 27);
		botonSolucion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		botonSolucion.setEnabled(true);
		panelBotones.add(botonSolucion);
		botonSolucion.addActionListener(new ActionListener() {	//Establezco el comportamiento de mi boton.
			public void actionPerformed(ActionEvent e) {

				if (juego.gano()) {		//Con un metodo de la clase Matriz, chequeo la logica de si el jugador gano.
					botonSolucion.setEnabled(false);	//Deshabilito ambos botones.
					botonRendirse.setEnabled(false);
					cronometro.detener();				//Detengo el cronometro.
					actualizarJuegoGanado();			//Y muestro graficamente que el jugador gano.
				} else
					actualizarIncorrectas();		//En caso contrario, verifico si alguna celda no cumple las condiciones.
			}
		});

		botonRendirse = new JButton("Rendirse");	//Creo mi boton que permite al jugador rendirse y ver la solucion correcta.
		botonRendirse.setBounds(0, 37, 176, 27);
		panelBotones.add(botonRendirse);
		botonRendirse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		botonRendirse.setEnabled(true);
		botonRendirse.addActionListener(new ActionListener() {	//Establezco el comportamiento de mi boton.

			public void actionPerformed(ActionEvent e) {	
				botonSolucion.setEnabled(false);	//Deshabilito los botones.
				botonRendirse.setEnabled(false);
				cronometro.detener();				//Detengo el cronometro.
				actualizarRendirse();				//Actualizo mi tablero de juego con la opcion de rendirse.
			}
		});

		
		panelMatrizTablero = new JPanel();		//Creo el panel donde va a ir la grilla de juego.
		panelMatrizTablero.setBounds(10, 20, 517, 424);
		contentPane.add(panelMatrizTablero);
		panelMatrizTablero.setOpaque(false);
		panelMatrizTablero.setLayout(new GridLayout(9, 9, 0, 0));	//Establezco que mi tablero sea de 9x9
		
		matriz = new JLabel[9][9];	//Creo una matriz de 9x9 labels donde iran las celdas.
		inicializarJuego();			//Inicializo el tablero de labels.

	}

	/**
	 * Metodo que crea un nuevo juego Sudoku.
	 */
	public void inicializarJuego() {
		juego = new Matriz("LogicaSudoku/SolucionSudoku.txt");	//Le paso el archivo a la parte logica.
		if (juego.getTablero() == null) {	//En caso de ocurrir un error con el archivo, informo al jugador con un nuevo JOptionPane.
			panelDerecho.setVisible(false);
			JOptionPane.showConfirmDialog(new JFrame(), "Error al inicializar el juego. Por favor, reintete. ",
					"Te dejo un tipo: verifica el archivo y volve a intentar! ", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		} else
			inicializarTablero();	//Si el archivo es correcto, establezco los valores iniciales del tablero.

	}

	/**
	 * Metodo privado que inicializa el tablero de juego inicial.
	 */
	private void inicializarTablero() {
		cronometro.iniciar();	//Inicio el cronometro.
		for (int i = 0; i < juego.getCantFilas(); i++) {
			for (int j = 0; j < juego.getCantFilas(); j++) {
				Celda celda = juego.getCelda(i, j);
				ImageIcon grafico = celda.getEntidadGrafica().getGrafico();	//Recupero la imagen asociada a la celda.
				JLabel label = new JLabel();
				label.setOpaque(true);
				matriz[i][j] = label;

				if (!celda.getModificar()) {	//En caso de ser una celda inicial, cambio el color de la celda para que sea mas sencillo diferenciarlo. 
					label.setBackground(new Color(248, 111, 130));
				}
				label.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

				//Crea los bordes para los cuadrantes.
				if (j == 2) {
					label.setBorder(new MatteBorder(1, 1, 1, 4, Color.BLACK));
				}
				if (j == 5) {
					label.setBorder(new MatteBorder(1, 1, 1, 4, Color.BLACK));
				}
				if (i == 2) {
					label.setBorder(new MatteBorder(1, 1, 4, 1, Color.BLACK));
					if (j == 2 || j == 5) {
						label.setBorder(new MatteBorder(1, 1, 4, 4, Color.BLACK));
					}
				}
				if (i == 5) {
					if (j == 2 || j == 5) {
						label.setBorder(new MatteBorder(1, 1, 4, 4, Color.BLACK));
					} else
						label.setBorder(new MatteBorder(1, 1, 4, 1, Color.BLACK));
				}

				panelMatrizTablero.add(label);

				//Establezco el comportamiento de cada label.
				label.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						reDimensionar(label, grafico);
						label.setIcon(grafico);
					}
				});
				
				//Indico al label como reacciona la celda ante un click del jugador.
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (celda.getModificar()) {
							juego.accionar(celda, true);
							reDimensionar(label, grafico);
						}
					}
				});
			}
		}
		
	}

	/**
	 * Metodo que cambia la imagen correcta por una incorrecta en caso de no cumplir con las reglas del juego.
	 */
	public void actualizarIncorrectas() {
		juego.chequearTablero();	//Actualizo los valores en mi parte logica.
		for (int i = 0; i < juego.getCantFilas(); i++) {	//Recorro todo el tablero.
			for (int j = 0; j < juego.getCantFilas(); j++) {

				Celda celda = juego.getCelda(i, j);
				ImageIcon grafico = celda.getEntidadGrafica().getGrafico();
				reDimensionar(matriz[i][j], grafico);
				matriz[i][j].setIcon(grafico);	//Establezco el icono. Si es incorrecta, cambio la imagen. Si es correcta, queda la misma.
			}
		}
	}

	/**
	 * Metodo que le indica a la GUI como compartarse cuando un jugador gana.
	 */
	public void actualizarJuegoGanado() {

		JLabel labelGif = new JLabel("label gif");	//Creo un nuevo label al que le asocio un GIF.
		labelGif.setSize(417, 305);
		ImageIcon gif = new ImageIcon(GUI.class.getResource("/ImagenesNumeros/theofficegano.gif"));
		Icon icono = new ImageIcon(gif.getImage().getScaledInstance(labelGif.getWidth(), labelGif.getHeight(), Image.SCALE_DEFAULT));	

		JOptionPane.showMessageDialog(rootPane, "Felicitaciones! Ganaste el juego ", "YES BABY",
				JOptionPane.PLAIN_MESSAGE, icono);	//Muestro un JOptionPane con el GIF y un mensaje para el jugador.

	}

	/**
	 * Metodo que le indica a la GUI como comportarse cuando un jugador se rinde. 
	 */
	public void actualizarRendirse() {
		juego.rendirse();	//Actualizo la parte logica de mi tablero. 
		for (int i = 0; i < juego.getCantFilas(); i++) {
			for (int j = 0; j < juego.getCantFilas(); j++) {
				Celda celda = juego.getCelda(i, j);
				ImageIcon grafico = celda.getEntidadGrafica().getGrafico();
				reDimensionar(matriz[i][j], grafico);
				matriz[i][j].setIcon(grafico);	//Establezco la imagen del JLabel con el numero correcto. 
			}
		}

		JLabel labelGif = new JLabel("label gif");	//Al rendirse, creo un nuevo JOptionPane alentando al jugador para una nueva partida.
		labelGif.setSize(417, 305);
		ImageIcon gif = new ImageIcon(GUI.class.getResource("/ImagenesNumeros/theofficerendirse.gif"));
		Icon icono = new ImageIcon(
				gif.getImage().getScaledInstance(labelGif.getWidth(), labelGif.getHeight(), Image.SCALE_DEFAULT));

		JOptionPane.showMessageDialog(rootPane, "El próximo será mas fácil, lo prometo ", "MICHAEL SCOTT DISAPPROVES",
				JOptionPane.PLAIN_MESSAGE, icono);
	}

	void reDimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(), java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();
		}
	}


}