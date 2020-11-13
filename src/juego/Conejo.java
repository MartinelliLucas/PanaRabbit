package juego;

public class Conejo {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	
	public Conejo(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
	}
	
	void moveFordward() {
		this.y = y - 10;
	}
	void moveLeft() {
		this.x = x - 10;
	}
	
	void moveRight() {
		this.x = x + 10;
	}
	
	void renderRabbit(int x, int y, int ancho, int alto) {
		
	}
	
	int getX() {
		return this.x;
	}
	
	int getY() {
		return this.y;
	}
	
}
