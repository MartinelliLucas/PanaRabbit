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
	private static Image[] coloresAutosDer = new Image[3];
	private static Image[] coloresAutosIzq = new Image[3];
	private Image imagenAuto;
	
	static {
		coloresAutosDer[0] = Herramientas.cargarImagen("archivos/autoAmarilloDer.png");
		coloresAutosDer[1] = Herramientas.cargarImagen("archivos/autoAzulDer.png");
		coloresAutosDer[2] = Herramientas.cargarImagen("archivos/autoVerdeDer.png");
		coloresAutosIzq[0] = Herramientas.cargarImagen("archivos/autoAmarilloIzq.png");
		coloresAutosIzq[1] = Herramientas.cargarImagen("archivos/autoRojoIzq.png");
		coloresAutosIzq[2] = Herramientas.cargarImagen("archivos/autoVioletaIzq.png");
	}
	public Auto(double x, double y, double speed, boolean direccion) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		int indiceImagen = (int)(Math.random()*3); 
		if (direccion) {
			this.imagenAuto = coloresAutosDer[indiceImagen];
		} else {
			this.imagenAuto = coloresAutosIzq[indiceImagen];
		}
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
		entorno.dibujarImagen(this.imagenAuto, this.x, this.y, 0);
	}
	void renderCarDer (Entorno entorno) {
		entorno.dibujarImagen(this.imagenAuto, this.x, this.y, 0);
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
