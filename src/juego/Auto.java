package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Image;
public class Auto {

	private double x;
	private double y;
	private int width;
	private int height;
	private double speed;
	private static Image imgautoder;
	private static Image imgautoizq; 
	
	public Auto(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = 2;
		Auto.imgautoder= Herramientas.cargarImagen("juego/autito5.png");
		Auto.imgautoizq=Herramientas.cargarImagen("imagenes/autoder.png");
	}
	
	void setSpeed(double Speed) {
		this.speed = Speed;
	}
	void moveForward() {
		this.x = x + this.speed;
	}
	
	void moveBackwards() {
		this.x = x - this.speed;
	}
	
	void fall() {
		this.y = y+0.4;
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
		return this.width;
	}
	
	double getHeight() {
		return this.height;
	}
	
	void setX(double value){
		this.x = value;
	}
	
	void setY(double value){
		this.y = value;
	}
}
