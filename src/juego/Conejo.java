package juego;

import java.awt.Color;//sacarlo xq no se usa
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Conejo {
	private double x;
	private double y;
	private int width;
	private int height;
	private static Image imgConejo;
		
	
	public Conejo(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.width = ancho;
		this.height = alto;
		Conejo.imgConejo= Herramientas.cargarImagen("juego/rogerchico.png");
	}
	
	void fall() {
		this.y = y+0.4;
	}
	
	void moveFordward() {
		this.y = y - 30;
	}
	void moveLeft() {
		this.x = x - this.width/2;
	}
	
	void moveRight() {
		this.x = x + this.width/2;
	}
	
	void renderRabbit(Entorno entorno) {
		entorno.dibujarImagen(imgConejo, this.x, this.y, 0);
	}
	
	double getX() {
		return this.x;
	}
	
	double getY() {
		return this.y;
	}
	double getWidth() {
		return this.width;
	}
	
	double getHeight() {
		return this.height;
	}

	void setX(double x) {
		this.x = x;
	}

	void setY(double y) {
		this.y = y;
	}

	
}
