import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Ventana principal del Buscaminas
 * 
 * @author Jennyfer Perianes Nieto
 */
public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;

	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;

	protected int puntuacion;

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame("Buscaminas");
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(10, 10));

		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Paneles
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[10][10];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		// BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j].addActionListener(new ActionBoton(this, i, j));
			}
		}

		botonEmpezar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getJuego().inicializarPartida();
				ventana.remove(panelJuego);
				panelJuego = new JPanel();
				panelJuego.setLayout(new GridLayout(10, 10));
				panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
				panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

				GridBagConstraints settings = new GridBagConstraints();
				settings.gridx = 0;
				settings.gridy = 1;
				settings.weightx = 1;
				settings.weighty = 10;
				settings.gridwidth = 3;
				settings.fill = GridBagConstraints.BOTH;
				ventana.add(panelJuego, settings);

				// Paneles
				panelesJuego = new JPanel[10][10];
				for (int i = 0; i < panelesJuego.length; i++) {
					for (int j = 0; j < panelesJuego[i].length; j++) {
						panelesJuego[i][j] = new JPanel();
						panelesJuego[i][j].setLayout(new GridLayout(1, 1));
						panelJuego.add(panelesJuego[i][j]);
					}
				}

				// Botones
				botonesJuego = new JButton[10][10];
				for (int i = 0; i < botonesJuego.length; i++) {
					for (int j = 0; j < botonesJuego[i].length; j++) {
						botonesJuego[i][j] = new JButton("-");
						panelesJuego[i][j].add(botonesJuego[i][j]);
					}
				}
				inicializarListeners();
				puntuacion = 0;
				pantallaPuntuacion.setText(Integer.toString(puntuacion));
				refrescarPantalla();
			}

		});
	}

	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda Saca el
	 * botón que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el número de minas alrededor. Se pinta el color del texto según
	 * la siguiente correspondecia (consultar la variable correspondeciaColor): - 0
	 * : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 ó más : rojo
	 * 
	 * @param i: posición vertical de la celda.
	 * @param j: posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		GridBagConstraints settings = new GridBagConstraints();

		JLabel nuevoPanel = new JLabel(Integer.toString(getJuego().getMinasAlrededor(i, j)));
		settings.anchor = GridBagConstraints.CENTER;
		settings.insets = new Insets(10, 0, 0, 0);
		panelesJuego[i][j].remove(botonesJuego[i][j]);
		panelesJuego[i][j].add(nuevoPanel, settings);
		panelesJuego[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
		if (getJuego().getMinasAlrededor(i, j) == 1) {
			panelesJuego[i][j].setBackground(correspondenciaColores[1]);
		}
		if (getJuego().getMinasAlrededor(i, j) == 2) {
			panelesJuego[i][j].setBackground(correspondenciaColores[2]);
		}
		if (getJuego().getMinasAlrededor(i, j) == 3) {
			panelesJuego[i][j].setBackground(correspondenciaColores[3]);
		}
		if (getJuego().getMinasAlrededor(i, j) >= 4) {
			panelesJuego[i][j].setBackground(correspondenciaColores[4]);
		}
		if (getJuego().getMinasAlrededor(i, j) == 0) {
			abrirAdyacentes(i, j);
		}
		refrescarPantalla();
	}

	/**
	 * Comprueba las casillas de alrededor y, en caso de que sea 0, las abre
	 * 
	 * @param vertical
	 * @param horizontal
	 */
	public void abrirAdyacentes(int vertical, int horizontal) {
		for (int i = vertical - 1; i <= vertical + 1; i++) {
			for (int j = horizontal - 1; j <= horizontal + 1; j++) {
				if ((i >= 0) && (j >= 0) && (i < juego.LADO_TABLERO) && (j < juego.LADO_TABLERO)) {
					if (panelesJuego[i][j].getComponent(0).getClass() == JButton.class) {
						botonesJuego[i][j].doClick();
					}
				}
			}
		}
	}

	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param porExplosion : Un booleano que indica si es final del juego porque ha
	 *                     explotado una mina (true) o bien porque hemos desactivado
	 *                     todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		if (porExplosion == true) {
			JOptionPane.showMessageDialog(ventana, "FIN DEL JUEGO", "HAS PULSADO UNA MINA", 1);
		} else {
			JOptionPane.showMessageDialog(ventana, "ENHORABUENA", "HAS GANADO", 1);
		}
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j].setEnabled(false);
				refrescarPantalla();
			}
		}
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		puntuacion++;
		pantallaPuntuacion.setText(Integer.toString(puntuacion));
	}

	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

}
