package juego;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import entorno.Entorno;

public class Kamehameha {
	private int z;
	private int c;
	private int diametro;
	private double x ;
	private double y;
	private int ancho;
	private int alto;
	private boolean usado;

	public Kamehameha (double x, double y , int ancho, int alto) {		
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.usado = false;
	}
	public Kamehameha (int z, int c , int diametro) {
		this.z = z;
		this.c = c;
		this.diametro = diametro;
	}
	void enfriamiento(boolean usado) {
		this.usado = usado;
		Timer timer = new Timer ();
		TimerTask tarea = new TimerTask () {

			@Override
			public void run() {
				setUsado(false);
			}

		};
		timer.schedule(tarea, 10000);
	}		
	public boolean isUsado() {
		return usado;
	}

	public void setUsado(boolean usado) {
		this.usado = usado;
	}

	void fall () {
		this.y = y + 0.1;
	}
	
	void desplazamiento () {
		this.y = y - 5;
	}

	public void setX(double d) {
		this.x = d;
	}

	public void setY(double d) {
		this.y = d;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}
	void renderKame(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.CYAN);	
	}
	void greenKame (Entorno entorno) {
		entorno.dibujarCirculo(this.z, this.c, this.diametro, Color.GREEN);
	}
	void redKame (Entorno entorno) {
		entorno.dibujarCirculo(this.z, this.c, this.diametro, Color.RED);
	}
}
