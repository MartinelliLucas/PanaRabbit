package juego;
import java.awt.Image;
import entorno.Entorno;
import javax.swing.ImageIcon;

public class Inicio {



	
		private String inicio = "/imagenes/inicio.jpg";
		private int x;
		private int y;
		private Image imagen;
		
		public Inicio () {

			ImageIcon img = new ImageIcon (this.getClass().getResource(this.inicio));
			this.imagen= img.getImage();
		}
		
		void renderInicio (Entorno entorno) {
			entorno.dibujarImagen(this.imagen, this.x + 400, this.y + 300, 0);
	}
		public void setX(int x) {
			this.x = x;
		}
		public void setY(int y) {
			this.y = y;
		}

	
}


