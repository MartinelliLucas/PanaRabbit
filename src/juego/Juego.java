package juego;


import java.util.Timer;
import java.util.TimerTask;

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
	private boolean flagCd = false;
		
	// Variables y métodos propios de cada grupo
	
	// metodo enfriamiento kame y auto ;
	void setflagCd (boolean flagCd) {
		this.flagCd =flagCd;
	}
	void enfriamiento(Kamehameha kame, boolean flagCd) {
		this.flagCd = flagCd;
		Timer timer = new Timer ();
		
		TimerTask tarea = new TimerTask () {

			@Override
			public void run() {
				setflagCd (false);
			}

		};
		timer.schedule(tarea, 5000);
	}
		
	//metodo para respawnear autos:
	void carRespawn(Auto[] arrAuto) {
		Timer carTimer = new Timer();
		
		TimerTask respawn = new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < arrAuto.length; i++) {
					if( i == 0 && arrAuto[i] == null) {
						arrAuto[i] = new Auto(arrAuto[i+1].getX()-250,arrAuto[i+1].getY(),60,40);
					}
					if (arrAuto[i] == null) {
						arrAuto[i] = new Auto(arrAuto[i-1].getX()+250,arrAuto[i-1].getY(),60,40);
					}
				}
			}
		};
		carTimer.schedule(respawn, 5000);
	}
	
	
	//metodo para identificar auto colisionado con el kame:
	
	int colisionAuto (Auto[] arrAuto, Kamehameha kame) {
		if(kame == null) {
			return -1;
		}
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
	
	boolean colisionConejo (Auto[] arrAuto, Conejo conejo) {
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] == null) {
				continue;
			}
			if (conejo.getY() + conejo.getHeight()/2 > arrAuto[i].getY() - arrAuto[i].getHeight()/2 && 
				conejo.getY() - conejo.getHeight()/2 < arrAuto[i].getY() + arrAuto[i].getHeight()/2 &&
				conejo.getX() + conejo.getWidth()/2> arrAuto[i].getX() - arrAuto[i].getWidth()/2 &&
				conejo.getX() - conejo.getWidth()/2< arrAuto[i].getX() + arrAuto[i].getWidth()/2){
				return true;
			}
		}
		return false;
	}
	// ...
	public boolean flagKame = false;	
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Boss Rabbit Rabber - Grupo 10 - Juanma, Lucas, Nahuel- v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		
		this.conejo = new Conejo(entorno.getWidth()/2, entorno.getHeight()-100, 40,40);
		
		this.kame = null; // debe ser null para que no se dispare automaticamente al iniciar.
		
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
		
		if(colisionConejo(autosCalle, conejo) || colisionConejo(autosCalle2, conejo)) {
			this.conejo.setX(400);
			this.conejo.setY(this.entorno.getHeight()-100);
		}
		
		//Creacion, movimiento e interacciones de los autos:
		
		for (int i = 0; i < this.autosCalle.length; i++) {
			if (autosCalle[i] == null)
				continue;
			this.autosCalle[i].renderCar(this.entorno);
			this.autosCalle[i].moveForward();
			this.autosCalle[i].fall();
						
			if (this.autosCalle[i].getX() > entorno.getWidth()) {
				this.autosCalle[i].setX(0);
			}
			if (this.autosCalle[i].getY() > entorno.getHeight()-50) {
				this.autosCalle[i].setY(0);
			}
			
			if (colisionAuto(this.autosCalle, this.kame) != -1){
				this.autosCalle[colisionAuto(this.autosCalle,this.kame)] = null;
				this.kame = null;
				carRespawn(autosCalle);
			}
			
		}
		
		
		
		for (int i = 0; i < this.autosCalle2.length; i++) {
			if (autosCalle2[i] == null) {
				continue;
			}	
			
			this.autosCalle2[i].renderCar(this.entorno);
			this.autosCalle2[i].moveBackwards();
			this.autosCalle2[i].fall();
			this.autosCalle2[i].setSpeed(1.5);
			
						
			if (autosCalle2[i].getX() < 0) {
				autosCalle2[i].setX(entorno.getWidth());
			}
			if (autosCalle2[i].getY() > entorno.getHeight()-50) {
				autosCalle2[i].setY(0);
			}
			if (colisionAuto(this.autosCalle2, this.kame) != -1){
				this.autosCalle2[colisionAuto(this.autosCalle2,this.kame)] = null;
				this.kame = null;
				carRespawn(autosCalle2);
			}			
		}
				
		//Creacion, movimiento e interacciones del Kamehameha:

		//crea el objeto kame (que se inicializa como null), asiga true al flag para dibujar y false al flag del cd.

		//evalua el valor de flagCd que controla el cd y dibuja segun corresponda.
		if (!flagCd) {
			this.circulo.greenKame(this.entorno);
		}
		else {
			this.circulo.redKame(this.entorno);
		}
		
		/*evalua que pasa cuando el kame es null y termino el cd !! esto vale tambien para el comienzo ya que el cd es falso
		y el objeto se inicializo como null!! */

		if (kame == null && !flagCd && entorno.sePresiono(entorno.TECLA_ESPACIO)) { 
					this.kame = new Kamehameha(conejo.getX(),conejo.getY()-conejo.getHeight()/2,10,20);
					this.flagKame = true;
					this.flagCd = true;
					enfriamiento(this.kame, this.flagCd);
		}

		//este if dibuja el kame desde que se presiona espacio hasta que impacta y da lugar al enfriamiento del poder.
		if (flagKame && kame!= null ) {
			this.kame.renderKame(this.entorno);
			this.kame.desplazamiento();
			if (this.kame.getY() <=0 ) { // si se va de la pantalla lo pone como null
				kame = null ;
			}
		}

		// si hay colision o sale de pantalla, deja de dibujar. *si colisiona tambien ya que no puede dibujar un null*
		if (colisionAuto(autosCalle,kame) != -1 || colisionAuto(autosCalle2, kame) != -1 ) {
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
