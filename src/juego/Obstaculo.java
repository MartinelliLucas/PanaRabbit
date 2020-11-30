package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Obstaculo {
	private double x;
	private double y;
	private int width;
	private int height;
	private static Image hidrante = Herramientas.cargarImagen("archivos/obstaculo.png");

	public Obstaculo(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void fall() {
		this.y = y+0.2;
	}
	
	public void renderObstaculo(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.width, this.height, 0, Color.RED);
	}

	public void render(Entorno entorno) {
		entorno.dibujarImagen(hidrante, this.x, this.y, 0);
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
