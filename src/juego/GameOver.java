package juego;


import juego.Juego;
import java.awt.Image;
import entorno.Entorno;
import javax.swing.ImageIcon;

	public class GameOver {
	
		private String fin = "/imagenes/fin.jpg";
		private int x;
		private int y;
		private Image imagen;
		
		public GameOver () {

			ImageIcon img = new ImageIcon (this.getClass().getResource(this.fin));
			this.imagen= img.getImage();
		}
		
		void renderGameOver (Entorno entorno) {
			entorno.dibujarImagen(this.imagen, this.x + 400, this.y + 300, 0);
	}

	
}
