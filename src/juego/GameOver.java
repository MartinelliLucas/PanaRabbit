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
			this.x = 0 ;
			this.y = 0 ;
			ImageIcon img = new ImageIcon (this.getClass().getResource(this.fin));
			this.imagen= img.getImage();
		}
		
		void renderGameOver (Entorno entorno) {
			entorno.dibujarImagen(this.imagen, entorno.getX()- 150, entorno.getY()+100, 0);
	}

	
}
