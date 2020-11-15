package juego;
import java.awt.Color;
import entorno.Entorno;

public class Kamehameha {
	
	private double x ;
	private double y;
	private int ancho;
	private int alto;
	
	public Kamehameha (double x, double y , int ancho, int alto) {
		
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		
	}
	void fall () {
		this.y = y + 0.1;
	}
	void desplazamiento () {
		this.y = y - 10;
	}
	public double getX() {
		return x;
	}

	public void setX(double d) {
		this.x = d;
	}

	public double getY() {
		return y;
	}

	public void setY(double d) {
		this.y = d;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}
	void renderKame(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.RED);
		
	}
	
}
