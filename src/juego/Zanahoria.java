package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Zanahoria {
	
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private static Image zanahoria = Herramientas.cargarImagen("imagenes/zanahoria.png");
	
	public Zanahoria (double x, double y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto =alto;
	}
	void fall () {
		this.y = y+0.4;
	}
	void renderZanahoria (Entorno entorno) {
		entorno.dibujarImagen(zanahoria, this.x, this.y, 0);
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
