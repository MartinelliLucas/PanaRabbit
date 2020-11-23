package juego;
import java.awt.Color;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import entorno.Entorno;
import entorno.Herramientas;

public class Kamehameha {
	private int z;
	private int c;
	private int diametro;
	private double x ;
	private double y;
	private int ancho;
	private int alto;
	private Image kameha= Herramientas.cargarImagen("imagenes/kame.png"); 

	public Kamehameha (double x, double y , int ancho, int alto) {		
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;

	}
	public Kamehameha (int z, int c , int diametro) {
		this.z = z;
		this.c = c;
		this.diametro = diametro;
	}
	
	
	void desplazamiento () {
		this.y = y - 5;
	}

	public void setX(double d) {
		this.x = d;
	}

	public void setY(double d) {
		this.y = d;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}
	void renderKame(Entorno entorno) {
		entorno.dibujarImagen(kameha, this.x, this.y, 0);	
	}
	void greenKame (Entorno entorno) {
		entorno.dibujarCirculo(this.z, this.c, this.diametro, Color.GREEN);
	}
	void redKame (Entorno entorno) {
		entorno.dibujarCirculo(this.z, this.c, this.diametro, Color.RED);
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
}
