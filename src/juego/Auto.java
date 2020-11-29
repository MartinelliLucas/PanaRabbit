package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Image;
public class Auto {

	private double x;
	private double y;
	public static int width = 50;
	public static int height = 22;
	private double speed;

	private static Image imgautoizq= Herramientas.cargarImagen("archivos/autito5.png");
	private static Image imgautoder= Herramientas.cargarImagen("archivos/autoder.png");


	
	public Auto(double x, double y, double speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	void moveForward() {
		this.x = x + this.speed;
	}
	
	void moveBackwards() {
		this.x = x - this.speed;
	}
	
	void fall() {
		this.y = y+0.2;
	}
	
	void renderCar(Entorno entorno) {
		entorno.dibujarImagen(imgautoizq, this.x, this.y, 0);
	}
	void renderCarDer (Entorno entorno) {
		entorno.dibujarImagen(imgautoder, this.x, this.y, 0);
	}
	
	double getX() {
		return this.x;
	}
	
	double getY() {
		return this.y;
	}
	double getWidth() {
		return width;
	}
	
	double getHeight() {
		return height;
	}
	
	void setX(double value){
		this.x = value;
	}
	
	void setY(double value){
		this.y = value;
	}
}
