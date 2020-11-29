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
	private static int anchoAuto = 50;
	private static int altoAuto = 22;

	public Carril(double y, int cantAutos, boolean direccion) {
		this.width = 800;
		this.height = 60;
		this.x = 400;
		this.y = y;
		this.arrAuto = new Auto[cantAutos];
		this.dirDerecha = direccion;
		for (int i = 0; i < cantAutos; i++) {
			this.arrAuto[i] = new Auto(i * 180, this.y, anchoAuto, altoAuto);
		}
	}

	void fall() {
		this.y += 0.4;
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] != null) {
				arrAuto[i].fall();
			}
		}
	}

	void renderCarril(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.width, this.height, 0, Color.darkGray);
		if (this.y > entorno.getHeight() - 50) {
			this.y = 0;
		}
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] != null) {
				if (this.dirDerecha) {
					arrAuto[i].renderCarDer(entorno);
					arrAuto[i].moveForward();
					if (arrAuto[i].getX() > entorno.getWidth()) {
						arrAuto[i].setX(0);
					}
					if (arrAuto[i].getY() > entorno.getHeight() - 50) {
						arrAuto[i].setY(0);
					}
				} else {
					arrAuto[i].renderCar(entorno);
					arrAuto[i].moveBackwards();
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
	public void removerAuto(int indice) {
		this.arrAuto[indice] = null;
	}

	// crea un nuevo auto dentro del array, en la posicion indicada por el
	// parametro.

	public void agregarAuto(int indice, Entorno entorno) {
		boolean sePuedeCrear = true;
		if (this.dirDerecha) {
			for (int a = 0; a < arrAuto.length; a++) {
				if (arrAuto[a] != null && (arrAuto[a].getX() > 700 || arrAuto[a].getX() < 180)) {
					sePuedeCrear = false;
				}
			}
			if (sePuedeCrear) {
				this.arrAuto[indice] = new Auto(0 - anchoAuto / 2, this.y, anchoAuto, altoAuto);
			}
		} else {
			for (int a = 0; a < arrAuto.length; a++) {
				if (arrAuto[a] != null && (arrAuto[a].getX() < 180 || arrAuto[a].getX() > 700)) {
					sePuedeCrear = false;
				}
			}
			if (sePuedeCrear) {
				this.arrAuto[indice] = new Auto(entorno.getWidth() + anchoAuto / 2, this.y, anchoAuto, altoAuto);
			}
		}
	}
	
	
}
