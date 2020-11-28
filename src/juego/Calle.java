package juego;

import java.awt.Color;

import entorno.Entorno;

public class Calle {
	
	private double x;
	private double y;
	private int width;
	private int height;
	private Carril[] carriles;
	
	public Calle(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.carriles = new Carril[4];
		this.carriles[0] = new Carril(y+100, 3, true);
		this.carriles[1] = new Carril(y+45, 4, false);
		this.carriles[2] = new Carril(y-10, 3, true);
		this.carriles[3] = new Carril(y-65, 5, false);
	}
	
	public void fall() {
		this.y = y+0.4;
		for (int i = 0; i < carriles.length; i++) {
			carriles[i].fall();
		}
	}
	
	public void renderCalle(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.width, this.height, 0, Color.darkGray);
		for (int i = 0; i < carriles.length; i++) {
			carriles[i].renderCarril(entorno);
		}
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
