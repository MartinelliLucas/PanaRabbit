package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Kamehameha {

	private double x ;
	private double y;
	private int ancho;
	private int alto;
	private static Image kameha= Herramientas.cargarImagen("archivos/kame.png"); 

	
	public Kamehameha (double x, double y , int ancho, int alto) {		
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto; // 
	}

	void desplazamiento () {
		this.y = y - 5;
	}
	
	void renderKame(Entorno entorno) {
		entorno.dibujarImagen(kameha, this.x, this.y, 0);	
	}

	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public int getAlto() {
		return this.alto;
	}
	public int getAncho() {
		return this.ancho;
	}
}
