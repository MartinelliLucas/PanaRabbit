package juego;

import java.awt.Color;

import entorno.Entorno;

public class Carril {

	private int width;
	private int height;
	private double x;
	private double y;
	private Auto[] arrAuto;
	private boolean dirDerecha; // true para la derecha, false para la izquierda.
	private double speed;
	
	
	public Carril(double y, int cantAutos, boolean direccion, double speed) {

		this.width = 800;
		this.height = 60;
		this.x = 400;
		this.y = y;
		this.arrAuto = new Auto[cantAutos];
		this.dirDerecha = direccion;
		this.speed = speed;
		for (int i = 0; i < cantAutos; i++) {
			this.arrAuto[i] = new Auto(i * 180, this.y,this.speed,direccion);
		}
		
	}

	void fall() {
		//hace caer el carril.
		this.y += 0.2;
		//llama el metodo fall de cada auto.
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] != null) {
				arrAuto[i].fall();
			}
		}
	}

	void renderCarril(Entorno entorno) {
		// Dibuja un rectangulo gris que representa el carril que compone una calle. 
		entorno.dibujarRectangulo(this.x, this.y, this.width, this.height, 0, Color.darkGray);
		//setea el carril en y = 0 cuando sale por el limite inferior de pantalla
		if (this.y > entorno.getHeight() - 50) {
			this.y = 0;
		}
		//delega la responsabilidad de dibujarse a cada auto y llama a los 
		//metodos correspondientes para su movimiento y aparicion en pantalla.
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] != null) {
				if (this.dirDerecha) {
					arrAuto[i].renderCarDer(entorno);
					arrAuto[i].moveRight();
					if (arrAuto[i].getX() > entorno.getWidth()) {
						arrAuto[i].setX(0);
					}
					if (arrAuto[i].getY() > entorno.getHeight() - 50) {
						arrAuto[i].setY(0);
					}
				} else {
					arrAuto[i].renderCarIzq(entorno);
					arrAuto[i].moveLeft();
					if (arrAuto[i].getX() < 0) {
						arrAuto[i].setX(entorno.getWidth());
					}
					if (arrAuto[i].getY() > entorno.getHeight() - 50) {
						arrAuto[i].setY(0);
					}
				}
			}
		}
	}

	public double getY() {
		return y;
	}

	public Auto getAuto(int index) {
		return this.arrAuto[index];
	}

	public int getCantAutos() {
		return arrAuto.length;
	}

	// setea en null un auto dentro del array, en la posicion indicada por el
	// parametro.
	public void removerAuto(int index) {
		this.arrAuto[index] = null;
	}

	// crea un nuevo auto dentro del array, en la posicion indicada por el
	// parametro.
	public void agregarAuto(int index, Entorno entorno) {
		boolean sePuedeCrear = true;
		if (this.dirDerecha) {
			for (int a = 0; a < arrAuto.length; a++) {
				if (arrAuto[a] != null && (arrAuto[a].getX() > 700 || arrAuto[a].getX() < 180)) {
					sePuedeCrear = false;
				}
			}
			if (sePuedeCrear) {
				this.arrAuto[index] = new Auto(0 - Auto.width / 2, this.y,this.speed,this.dirDerecha);
			}
		} else {
			for (int a = 0; a < arrAuto.length; a++) {
				if (arrAuto[a] != null && (arrAuto[a].getX() < 180 || arrAuto[a].getX() > 700)) {
					sePuedeCrear = false;
				}
			}
			if (sePuedeCrear) {
				this.arrAuto[index] = new Auto(entorno.getWidth() + Auto.width / 2, this.y,this.speed,this.dirDerecha);
			}
		}
	}
	
	
}
