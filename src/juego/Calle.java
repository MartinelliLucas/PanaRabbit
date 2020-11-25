package juego;

import java.awt.Color;

import entorno.Entorno;

public class Calle {
	
	private double x;
	private double y;
	private int width;
	private int height;
	
	public Calle(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void fall() {
		this.y = y+0.4;
	}
	
	public void renderCalle(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.width, this.height, 0, Color.darkGray);
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
	
}
