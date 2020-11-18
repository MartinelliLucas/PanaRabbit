package juego;

import java.awt.Color;
import entorno.Entorno;

public class Conejo {
	private int x;
	private double y;
	private int width;
	private int height;
		
	
	public Conejo(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.width = ancho;
		this.height = alto;
	}
	
	void fall() {
		this.y = y+0.4;
	}
	
	void moveFordward() {
		this.y = y - 50;
	}
	void moveLeft() {
		this.x = x - this.width;
	}
	
	void moveRight() {
		this.x = x + this.width;
	}
	
	void renderRabbit(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.width, this.height, 0, Color.WHITE);
		
	}
	
	int getX() {
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
}
