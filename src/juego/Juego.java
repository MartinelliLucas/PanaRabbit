package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Conejo conejo;
	private Auto[] autosCalle;	
	private Kamehameha kame;
	
	// Variables y métodos propios de cada grupo
	
	boolean kameColision(Auto[] arrAuto, Kamehameha kame) {
		boolean tX = false;
		boolean tY = false;
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i].getX() - (arrAuto[i].getWidth()/2) < kame.getX() &&
			kame.getX() < (arrAuto[i].getX() + (arrAuto[i].getWidth()/2))){
				tX = true;
			}
		}
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i].getY() + (arrAuto[i].getHeight()/2) > kame.getY() - (kame.getAlto()/2)) {
				tY = true;
			}
		}
		if (tX && tY)
			return true;
		return false;
	}
	
	// ...
	public boolean flagKame = false;	
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Boss Rabbit Rabber - Grupo 10 - Juanma, Lucas, Nahuel- v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		
		this.conejo = new Conejo(entorno.getWidth()/2, entorno.getHeight()-100, 40,40);
		this.kame = new Kamehameha(conejo.getX(),conejo.getY()-conejo.getHeight()/2,10,20);
		this.autosCalle = new Auto[3];
		autosCalle[0] = new Auto(0,conejo.getY()-200,60,40);
		autosCalle[1] = new Auto(250,conejo.getY()-200,60,40);
		autosCalle[2] = new Auto(500,conejo.getY()-200,60,40);
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
		
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		//Creacion, movimiento e interacciones del conejo:
		for (int i = 0; i < autosCalle.length; i++) {
			this.autosCalle[i].renderCar(this.entorno);
			this.autosCalle[i].moveForward();
			this.autosCalle[i].fall();
			
			if (autosCalle[i].getX() > entorno.getWidth())
				autosCalle[i].setX(0);
			
			if (autosCalle[i].getY() > entorno.getHeight()-50)
				autosCalle[i].setY(0);
			}
			
		conejo.renderRabbit(this.entorno);
		conejo.fall();
		
				
		if(entorno.sePresiono(entorno.TECLA_ARRIBA) && conejo.getY() > conejo.getHeight())
			conejo.moveFordward();
		if(entorno.sePresiono(entorno.TECLA_DERECHA) && conejo.getX() < entorno.getWidth() - conejo.getWidth())
			conejo.moveRight();
		if(entorno.sePresiono(entorno.TECLA_IZQUIERDA) && conejo.getX()> conejo.getWidth()/2)
			conejo.moveLeft();
		
		
		if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			kame.setX(conejo.getX());
			kame.setY(conejo.getY() - conejo.getHeight());
			flagKame = true;
		}
		if (flagKame) {
			kame.renderKame(this.entorno);
			kame.desplazamiento();
		}
		if (kameColision(autosCalle,kame)) {
			flagKame = false;
		}
	}
				
		// Procesamiento de un instante de tiempo
			// ...
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
