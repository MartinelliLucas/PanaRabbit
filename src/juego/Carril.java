package juego;

import java.awt.Color;

import entorno.Entorno;

public class Carril {
	int width;
	int height;
	double x;
	double y;
	Auto[] arrAuto;
	boolean dirDerecha;


	public Carril(double y, int cantAutos, boolean direccion) {
		this.width = 800;
		this.height = 60;
		this.x = 400;
		this.y = y;
		this.arrAuto = new Auto[cantAutos];
		this.dirDerecha = direccion;
		for (int i = 0; i < cantAutos; i++) {
			this.arrAuto[i] = new Auto(i * 180, this.y, 50, 22);
		}
	}

	public double getY() {
		return y;
	}

	void fall() {
		this.y += 0.4;
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] != null) {
				arrAuto[i].fall();
			}
		}
	}
	public Auto [] arrAuto() {
		return this.arrAuto;
	}
	void renderCarril(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.width, this.height, 0, Color.darkGray);
		if (this.y > entorno.getHeight()-50) {
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
					if (arrAuto[i].getY() > entorno.getHeight()-50) {
						arrAuto[i].setY(0);
					}
				} else {
					arrAuto[i].renderCar(entorno);
					arrAuto[i].moveBackwards();
					if (arrAuto[i].getX() < 0) {
						arrAuto[i].setX(entorno.getWidth());
					}
					if (arrAuto[i].getY() > entorno.getHeight()-50) {
						arrAuto[i].setY(0);
					}
				}
			}
		}
	}
	
	public Auto getAuto(int index) {
		return this.arrAuto[index];
	}
	
	public int getCantAutos() {
		return arrAuto.length;
	}
	
	public void removerAuto(int indice) {
		this.arrAuto[indice] = null;
	}
	
	
}
