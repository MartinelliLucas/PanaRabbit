package juego;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	
	private Entorno entorno;
	private Conejo conejo;
	private Auto[] autosCalle;	
	private Auto[] autosCalle2;
	private Kamehameha kame;
	private int salto;
	private int puntaje;
	
	// Variables y métodos propios de cada grupo
	// banderas necesarias para evitar que el tick renderize objetos no deseados
	private boolean isStartScreenActive=true;	//indica si se debe mostrar la pantalla inicial o no
	private boolean isKameAvailable=true; 		//si es true deja lanzar si es false debe esperar al enfriamiento
	
	// metodo enfriamiento kame 
	void setFlagKame(boolean flagKame) {
		this.isKameAvailable = flagKame;
	}
	void enfriamiento(boolean flagKame) {
		this.isKameAvailable = flagKame;
		Timer timer = new Timer ();
		
		TimerTask tarea = new TimerTask () {

			@Override
			public void run() {
				setFlagKame (true);
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
						arrAuto[i] = new Auto(arrAuto[i+1].getX()-250,arrAuto[i+1].getY(),100,60);
					}
					if (arrAuto[i] == null) {
						arrAuto[i] = new Auto(arrAuto[i-1].getX()+250,arrAuto[i-1].getY(),100,60);
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
	
	// metodo de colision auto con conejo
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

// metodo para iniciar el jueego, lo implementamos para poder reiniciar el juego si el jugador pierde	
	void inicio(){
		// Inicializa el objeto entorno

		this.entorno = new Entorno(this, "Boss Rabbit Rabber - Grupo 10 - Juanma, Lucas, Nahuel- v1", 800, 600);

		// Inicializar lo que haga falta para el juego
	
		this.conejo = new Conejo(entorno.getWidth()/2, entorno.getHeight()-100, 80,40);	
		
		this.kame = null ;
		this.isKameAvailable= true;
		this.autosCalle = new Auto[3];
		for (int i = 0; i < this.autosCalle.length; i++) {
			this.autosCalle[i] = new Auto(i*250,conejo.getY()-200,100,60);
		}	
		
		this.autosCalle2 = new Auto[4];
		for (int i = 0; i < this.autosCalle2.length; i++) {
			this.autosCalle2[i] = new Auto(i*180,conejo.getY()-400,60,40);
		}	
				
	// Inicia el juego!
		this.entorno.iniciar();	
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
public void tick()	// Procesamiento de un instante de tiempo
{		

		if (entorno.sePresiono(entorno.TECLA_ENTER)) 
		{
			this.isStartScreenActive=false;
		}
			
		if (!this.isStartScreenActive) 
		{
			//Creacion, movimiento e interacciones del conejo:
			conejo.renderRabbit(this.entorno);
			conejo.fall();
						
			if(entorno.sePresiono(entorno.TECLA_ARRIBA) && conejo.getY() > conejo.getHeight())
				conejo.moveFordward();
			if(entorno.sePresiono(entorno.TECLA_DERECHA) && conejo.getX() < entorno.getWidth() - 85)
				conejo.moveRight();
			if(entorno.sePresiono(entorno.TECLA_IZQUIERDA) && conejo.getX()> 85)
				conejo.moveLeft();
			if(colisionConejo(autosCalle, conejo) || colisionConejo(autosCalle2, conejo)) {
				this.conejo.setX(400);
				this.conejo.setY(this.entorno.getHeight()+50);
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
					//this.flagKame= false;//deja de dibujarlo
					this.kame = null;
					carRespawn(autosCalle);
				} 
				
			}
	
			for (int i = 0; i < this.autosCalle2.length; i++) {
				if (autosCalle2[i] == null) {
					continue;
				}	
				
				this.autosCalle2[i].renderCarDer(this.entorno);
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
					//this.flagKame= false;//deja de dibujarlo
					this.kame = null;
					carRespawn(autosCalle2);
				}	
			}
					
			//Creacion, movimiento e interacciones del Kamehameha:
			entorno.cambiarFont("arial", 16, Color.CYAN);
			entorno.escribirTexto("KameHameHa", 625, 40);
			// indicador de uso del kame
			if (this.isKameAvailable) {
				entorno.dibujarCirculo(750, 35, 20, Color.GREEN);
			}
			else {
				entorno.dibujarCirculo(750, 35, 20, Color.RED);
			}
			
			if (entorno.sePresiono(entorno.TECLA_ESPACIO) && this.isKameAvailable) {
				this.kame = conejo.disparar();
				Herramientas.play("juego/disparo.wav");
				this.isKameAvailable = false;
			}
			/*evalua que pasa cuando el kame es null y termino el cd !! esto vale tambien para el comienzo ya que el cd es falso
			y el objeto se inicializo como null!! */
				
			if (!this.isKameAvailable && this.kame != null) {
				this.kame.renderKame(this.entorno);
				this.kame.desplazamiento();
				this.enfriamiento(false);
			}
		
			//codigo para terminar el juego si el conejo sale por el limite inferior o choca:
			if (this.conejo.getY()+conejo.getHeight()/2 > entorno.getHeight() 
					|| this.colisionConejo(autosCalle, conejo)|| this.colisionConejo(autosCalle2, conejo)) 
			{
					this.conejo.setY(2000);
					Image imagenFin = Herramientas.cargarImagen("imagenes/fin.jpg");
					entorno.dibujarImagen(imagenFin, entorno.getX()-152,entorno.getY()+100, 0);
					entorno.escribirTexto("¿Desea continuar? \n Pulse Y o N",entorno.getWidth()-500, entorno.getHeight()-100);
					
					if (entorno.sePresiono ('y')) 
					{// si apreta y cierro ventana y vuelvo a iniciar
						this.entorno.dispose();
						this.inicio();
					}
					if (entorno.sePresiono('n')) {
						// cierro ventana 
						this.entorno.dispose();
					}	
			}
	}	
		//flaginicio			
	if (this.isStartScreenActive) 
	{		
		Image imagenInicio = Herramientas.cargarImagen("imagenes/inicio.jpg");
		entorno.dibujarImagen(imagenInicio, entorno.getX()-155, entorno.getY()+100, 0);		
	}		
		
}

public static void main(String[] args)
	{
		Juego juego = new Juego();
		juego.inicio();
	}
}
