package juego;

import entorno.Entorno;

public class Calle {
	
	private double x;
	private double y;
	private Carril[] carriles;
	
	public Calle(double x, double y) {
		this.x = x;
		this.y = y;
		this.carriles = new Carril[4];
		this.carriles[0] = new Carril(y+100, 3, true, 2);
		this.carriles[1] = new Carril(y+45, 4, false, 1.5);
		this.carriles[2] = new Carril(y-10, 3, true, 2.5);
		this.carriles[3] = new Carril(y-65, 5, false, 3);
		
	}
	
	public void fall() {
		for (int i = 0; i < carriles.length; i++) {
			carriles[i].fall();
		}
	}
	
	public void renderCalle(Entorno entorno) {
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

	public Carril getCarril(int index){
		return this.carriles[index];
	}
	
	public int getCantCarriles() {
		return carriles.length;
	}
	
}
