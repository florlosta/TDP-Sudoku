package GUISudoku;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;

public class CronometroGUI extends JPanel implements ActionListener {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private ImageIcon[] imagenesReloj;
	private int horas, minutos, segundos = 0;
	private JLabel digitoHora1, digitoHora2, dosPuntos1, dosPuntos2, digitoMinuto1, digitoMinuto2, digitoSegundo1,
			digitoSegundo2;

	/**
	 * Creo un nuevo cronometro con su respetiva interfaz.
	 */
	public CronometroGUI() {
		setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		setSize(167, 90);
		setBackground(new Color(209, 239, 236));

		imagenesReloj = establecerImagenes();	//Incializo las ImageIcon que luego utilizaran los digitos del reloj.
		setLayout(null);

		JPanel panelBotones = new JPanel();		//Creo un nuevo panel para los botones inicar, detener y reiniciar.
		panelBotones.setBounds(2, 47, 164, 41);
		add(panelBotones);
		panelBotones.setOpaque(false);

		JPanel panelHora = new JPanel();	//Creo un nuevo panel para los digitos del cronometro.
		panelHora.setBounds(2, 2, 164, 86);
		add(panelHora);

		// Boton iniciar

		JButton botonIniciar = new JButton("I");	//Creo el boton de iniciar y establezco su comportamiento.
		botonIniciar.setBounds(53, 7, 58, 27);
		botonIniciar.addActionListener(this);
		panelBotones.setLayout(null);
		panelBotones.add(botonIniciar);
		botonIniciar.setFont(new Font("Tahoma", Font.PLAIN, 15));

		// Boton reiniciar inicia nuevamente desde 0
		JButton botonReiniciar = new JButton("R");	//Misma situacion con boton reinicar.
		botonReiniciar.setBounds(0, 7, 58, 27);
		botonReiniciar.addActionListener(this);
		panelBotones.add(botonReiniciar);
		botonReiniciar.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JButton botonDetener = new JButton("D");	//Y por ultimo, boton detener. 
		botonDetener.setFont(new Font("Tahoma", Font.PLAIN, 15));
		botonDetener.addActionListener(this);
		botonDetener.setBounds(106, 7, 58, 27);
		panelBotones.add(botonDetener);
		panelHora.setOpaque(false);
		panelHora.setLayout(null);

		//Creo los labels correspondientes a cada digito y a los dos puntos separadores.
		digitoHora1 = new JLabel();
		digitoHora1.setBounds(2, 0, 20, 44);
		panelHora.add(digitoHora1);

		digitoHora2 = new JLabel();
		digitoHora2.setBounds(22, 0, 20, 44);
		panelHora.add(digitoHora2);

		dosPuntos1 = new JLabel();
		dosPuntos1.setBounds(42, 0, 20, 44);
		dosPuntos1.setIcon(new ImageIcon(CronometroGUI.class.getResource("/ImagenesNumeros/R10.png")));
		panelHora.add(dosPuntos1);

		digitoMinuto1 = new JLabel();
		digitoMinuto1.setBounds(62, 0, 20, 44);
		panelHora.add(digitoMinuto1);

		digitoMinuto2 = new JLabel();
		digitoMinuto2.setBounds(82, 0, 20, 44);
		panelHora.add(digitoMinuto2);

		dosPuntos2 = new JLabel();
		dosPuntos2.setBounds(102, 0, 20, 44);
		dosPuntos2.setIcon(new ImageIcon(CronometroGUI.class.getResource("/ImagenesNumeros/R10.png")));
		panelHora.add(dosPuntos2);

		digitoSegundo1 = new JLabel();
		digitoSegundo1.setBounds(122, 0, 20, 44);
		panelHora.add(digitoSegundo1);

		digitoSegundo2 = new JLabel();
		digitoSegundo2.setBounds(142, 0, 20, 44);
		panelHora.add(digitoSegundo2);

		//Creo un nuevo objeto de tipo Timer y establezco su funcionamiento. 
		timer = new Timer(1000, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				segundos++;
				if (segundos == 60) {
					minutos++;
					segundos = 0;

					if (minutos == 60) {
						horas++;
						minutos = 0;
					}
				}
				
				//Actualizo las imagenes de cada JLabel con el transurso del tiempo.
				dosPuntos1.setIcon(imagenesReloj[10]);
				reDimensionar(dosPuntos1, imagenesReloj[10]);

				dosPuntos2.setIcon(imagenesReloj[10]);
				reDimensionar(dosPuntos2, imagenesReloj[10]);

				digitoSegundo1.setIcon(imagenesReloj[segundos / 10]);
				reDimensionar(digitoSegundo1, imagenesReloj[segundos / 10]);

				digitoSegundo2.setIcon(imagenesReloj[segundos % 10]);
				reDimensionar(digitoSegundo2, imagenesReloj[segundos % 10]);

				digitoMinuto1.setIcon(imagenesReloj[minutos / 10]);
				reDimensionar(digitoMinuto1, imagenesReloj[minutos / 10]);

				digitoMinuto2.setIcon(imagenesReloj[minutos % 10]);
				reDimensionar(digitoMinuto2, imagenesReloj[minutos % 10]);

				digitoHora1.setIcon(imagenesReloj[horas / 10]);
				reDimensionar(digitoHora1, imagenesReloj[horas / 10]);

				digitoHora2.setIcon(imagenesReloj[horas % 10]);
				reDimensionar(digitoHora2, imagenesReloj[horas % 10]);

			}
		});

	}

	/**
	 * Metodo que establece el comportamiento de los tres botones que controlan el cronometro.
	 */
	public void actionPerformed(ActionEvent e) {
		JButton boton = (JButton) e.getSource();
		if (boton.getText() == "I") {
			this.iniciar();
		} else if (boton.getText() == "D") {
			this.detener();
		} else
			this.reiniciar();
	}

	/**
	 * Metodo que inicia el cronometro.
	 */
	public void iniciar() {
		timer.start();
	}

	/**
	 * Metodo que detine el cronometro.
	 */
	public void detener() {
		timer.stop();
	}

	/**
	 * Metodo que reinicia el cronometro.
	 */
	public void reiniciar() {
		horas = 0;
		minutos = 0;
		segundos = 0;
		timer.restart();
	}
	

	/**
	 * Metodo privado que asigna para cada digito del reloj, su correspondiente numero en imagen, y retorna el arreglo creado.
	 */
	private ImageIcon[] establecerImagenes() {
		ImageIcon[] imagenes = new ImageIcon[11];
		for (int i = 0; i < imagenes.length; i++) {
			imagenes[i] = new ImageIcon(this.getClass().getResource("/ImagenesNumeros/R" + i + ".png"));
		}
		return imagenes;
	}

	public void reDimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(), java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();
		}
	}
}
