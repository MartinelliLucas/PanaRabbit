package juego;

import java.awt.Color;
import entorno.Entorno;

public class Auto {

	private double x;
	private double y;
	private int width;
	private int height;
	private double speed;
	
	public Auto(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = 2;
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
		this.y = y+0.1;
	}
	
	void renderCar(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.width, this.height, 0, Color.BLUE);
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
