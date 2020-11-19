import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendrá que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author Jennyfer Perianes Nieto
 **
 */
public class ActionBoton implements ActionListener{
	private final static int MINA = -1;
	private VentanaPrincipal ventana;
	private int vertical;
	private int horizontal;
	


	public ActionBoton(VentanaPrincipal ventana, int vertical, int horizontal) {
		this.ventana = ventana;
		this.vertical = vertical;
		this.horizontal = horizontal;
	}
	
	/**
	 *Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(ventana.getJuego().tablero[vertical][horizontal] == MINA){
			ventana.mostrarFinJuego(true);
		}else if(ventana.getJuego().esFinJuego()){
			ventana.mostrarFinJuego(false);
		}else{
			ventana.actualizarPuntuacion();
			ventana.mostrarNumMinasAlrededor(vertical, horizontal);
		}
	}

}
