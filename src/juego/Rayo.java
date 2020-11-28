package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Rayo {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private static Image rayo = Herramientas.cargarImagen("imagenes/rayo.png");

	
	public Rayo (double x, double y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
	}
	
	void desplazamiento () {
		this.y -= 5;
	}


	void renderRayo(Entorno entorno) {
		entorno.dibujarImagen(rayo, this.x, this.y, 0);
	}


	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public int getAncho() {
		return ancho;
	}


	public int getAlto() {
		return alto;
	}
	
	
}
