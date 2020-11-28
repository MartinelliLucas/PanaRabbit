package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;


public class Conejo {
	
	private double x;
	private double y;
	private int width;
	private int height;
	private Image imgConejo= Herramientas.cargarImagen("juego/rogerchico.png");
	

	
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
		this.y = y - 30;
	}
	void moveLeft() {
		this.x = x - this.width;
	}
	
	void moveRight() {
		this.x = x + this.width;
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
	public Kamehameha disparar () {	
		Kamehameha kame = new Kamehameha(this.x, this.y, 40, 70);
		return kame ;		
	}
	public Rayo rayo() {
		Rayo rayo = new Rayo (this.x, this.y, 20 ,100);
		return rayo;
	}
	public boolean comer (Zanahoria zanahoria) {
		return (this.getY() + this.getHeight()/2 > zanahoria.getY() - zanahoria.getAlto() &&
				this.getY() - this.getHeight()/2 < zanahoria.getY() + zanahoria.getAlto() &&
				this.getX() + this.getWidth() /2 > zanahoria.getX() - zanahoria.getAncho() &&
				this.getX() - this.getWidth() /2 < zanahoria.getX() + zanahoria.getAncho());
	}
}
