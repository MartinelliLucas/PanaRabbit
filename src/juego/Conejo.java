package juego;
import java.awt.Color;

import entorno.Entorno;

public class Conejo {
	private int x;
	private double y;
	private int ancho;
	private int alto;
	private int velocidad;
	
	
	public Conejo(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y+velocidad;
		this.ancho = ancho;
		this.alto = alto;
		this.velocidad = 2;
	}
	
	void caerse() {
		this.y = y+0.1;
	}
	
	void moveFordward() {
		this.y = y - 10;
	}
	void moveLeft() {
		this.x = x - 10;
	}
	
	void moveRight() {
		this.x = x + 10;
	}
	
	void renderRabbit(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.ORANGE);
	}
	
	int getX() {
		return this.x;
	}
	
	double getY() {
		return this.y;
	}
	
}
