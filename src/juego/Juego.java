package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Conejo conejo;
	private Auto[] autosCalle;	
	private Auto[] autosCalle2;
	private Kamehameha kame;
	private Kamehameha circulo;
	// Variables y métodos propios de cada grupo
	
	//Metodo para evaluar si el kame colisiona con un auto:
	boolean kameColision(Auto[] arrAuto, Kamehameha kame) {
		boolean tX = false;
		boolean tY = false;
		
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] == null)
				continue;
			if (arrAuto[i].getX() - (arrAuto[i].getWidth()/2) < kame.getX() &&
			kame.getX() < (arrAuto[i].getX() + (arrAuto[i].getWidth()/2))){
				tX = true;
			}
		}
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] == null)
				continue;
			if (arrAuto[i].getY() + (arrAuto[i].getHeight()/2) > kame.getY() - (kame.getAlto()/2) &&
					kame.getY() > (arrAuto[i].getY() - arrAuto[i].getHeight()/2)) {
				tY = true;
			}
		}
		
		if (tX && tY)
			return true;
		return false;
	}
	
	//metodo para identificar auto colisionado:
	
	int colisionAuto (Auto[] arrAuto, Kamehameha kame) {
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] == null)
				continue;
			if (arrAuto[i].getX() - (arrAuto[i].getWidth()/2) < kame.getX() &&
					kame.getX() < (arrAuto[i].getX() + (arrAuto[i].getWidth()/2)) &&
					arrAuto[i].getY() + (arrAuto[i].getHeight()/2) > kame.getY() - (kame.getAlto()/2) &&
					kame.getY() > (arrAuto[i].getY() - arrAuto[i].getHeight()/2)) {
				return i;
			}
		}
		
		
		return -1;
	}
	
	// ...
		
	public boolean flagKame = false;	
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Boss Rabbit Rabber - Grupo 10 - Juanma, Lucas, Nahuel- v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		
		this.conejo = new Conejo(entorno.getWidth()/2, entorno.getHeight()-100, 40,40);
		
		this.kame = new Kamehameha(conejo.getX(),conejo.getY()-conejo.getHeight()/2,10,20);
		
		this.circulo = new Kamehameha (entorno.getX()+200, 30 , 20);
		
		this.autosCalle = new Auto[3];
		
		for (int i = 0; i < this.autosCalle.length; i++) {
			this.autosCalle[i] = new Auto(i*250,conejo.getY()-200,60,40);
		}
		
		this.autosCalle2 = new Auto[4];
		for (int i = 0; i < this.autosCalle2.length; i++) {
			this.autosCalle2[i] = new Auto(i*180,conejo.getY()-400,60,40);
		}
		
		
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
		
		conejo.renderRabbit(this.entorno);
		conejo.fall();
		
				
		if(entorno.sePresiono(entorno.TECLA_ARRIBA) && conejo.getY() > conejo.getHeight())
			conejo.moveFordward();
		if(entorno.sePresiono(entorno.TECLA_DERECHA) && conejo.getX() < entorno.getWidth() - conejo.getWidth())
			conejo.moveRight();
		if(entorno.sePresiono(entorno.TECLA_IZQUIERDA) && conejo.getX()> conejo.getWidth()/2)
			conejo.moveLeft();
		
		//codigo para terminar el juego si el conejo sale por el limite inferior:
//		if(this.conejo.getY()+conejo.getHeight()/2 > entorno.getHeight()) {
//			
//		}
		
		//Creacion, movimiento e interacciones de los autos:
		
		for (int i = 0; i < this.autosCalle.length; i++) {
			if (autosCalle[i] == null)
				continue;
			this.autosCalle[i].renderCar(this.entorno);
			this.autosCalle[i].moveForward();
			this.autosCalle[i].fall();
			
			if (colisionAuto(this.autosCalle, this.kame) != -1){
				this.autosCalle[colisionAuto(this.autosCalle,this.kame)] = null;				
			}
			
			if (this.autosCalle[i].getX() > entorno.getWidth())
				this.autosCalle[i].setX(0);
			
			if (this.autosCalle[i].getY() > entorno.getHeight()-50)
				this.autosCalle[i].setY(0);
			}
			
		for (int i = 0; i < this.autosCalle2.length; i++) {
			if (autosCalle2[i] == null)
				continue;
			this.autosCalle2[i].renderCar(this.entorno);
			this.autosCalle2[i].moveBackwards();
			this.autosCalle2[i].fall();
			this.autosCalle2[i].setSpeed(1.5);
			
						
			if (autosCalle2[i].getX() < 0)
				autosCalle2[i].setX(entorno.getWidth());
			
			if (autosCalle2[i].getY() > entorno.getHeight()-50)
				autosCalle2[i].setY(0);
			}
		
		
		//Creacion, movimiento e interacciones del Kamehameha:
		if (kame.isUsado()) {
			circulo.redKame(this.entorno);
		}
		else {
			circulo.greenKame(this.entorno);
		}
		if (entorno.sePresiono(entorno.TECLA_ESPACIO) && !kame.isUsado()) {
			kame.setX(conejo.getX());
			kame.setY(conejo.getY() - conejo.getHeight()/2);
			flagKame = true;
			kame.enfriamiento(true);
			
		}
		if (flagKame) {
			kame.renderKame(this.entorno);
			kame.desplazamiento();
		}
		if (colisionAuto(autosCalle,kame) != -1 || colisionAuto(autosCalle2, kame) != -1) {
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
