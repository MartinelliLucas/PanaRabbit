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
	private Calle[] Calles1;
	private Auto[] autosCalle;	
	private Auto[] autosCalle2;
	private Auto[] autosCalle3;
	private Auto[] autosCalle4;
	private Auto[] autosCalle5;
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
						arrAuto[i] = new Auto(arrAuto[i+1].getX()- 200,arrAuto[i+1].getY(),100,60);
					}
					if (arrAuto[i] == null) {
						arrAuto[i] = new Auto(arrAuto[i-1].getX()+arrAuto[i-1].getWidth()+50,arrAuto[i-1].getY(),100,60);
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

	void crearAutosDer(Auto[] arrAuto) {
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] == null)
				continue;
			arrAuto[i].renderCarDer(this.entorno);
			arrAuto[i].moveForward();
			arrAuto[i].fall();
						
			if (arrAuto[i].getX() > entorno.getWidth()) {
				arrAuto[i].setX(0);
			}
			if (arrAuto[i].getY() > entorno.getHeight()-50) {
				arrAuto[i].setY(0);
			}
		}
	}
	void crearAutosIzq(Auto[] arrAuto) {
		for (int i = 0; i < arrAuto.length; i++) {
			if (arrAuto[i] == null)
				continue;
			arrAuto[i].renderCar(this.entorno);
			arrAuto[i].moveBackwards();
			arrAuto[i].fall();
						
			if (arrAuto[i].getX() < 0) {
				arrAuto[i].setX(entorno.getWidth());
			}
			if (arrAuto[i].getY() > entorno.getHeight()-50) {
				arrAuto[i].setY(0);
			}
		}
	}
// metodo para iniciar el juego, lo implementamos para poder reiniciar el juego si el jugador pierde	
	void inicio(){
		// Inicializa el objeto entorno

		this.entorno = new Entorno(this, "Boss Rabbit Rabber - Grupo 10 - Juanma, Lucas, Nahuel- v1", 800, 600);

		// Inicializar lo que haga falta para el juego
	
		this.conejo = new Conejo(entorno.getWidth()/2, entorno.getHeight()-100, 32,50);	

		this.Calles1 = new Calle[2];
		this.Calles1[0] = new Calle(this.entorno.getWidth()/2,this.entorno.getHeight()/2-150,this.entorno.getWidth(),250);
		this.Calles1[1] = new Calle(this.entorno.getWidth()/2,-135,this.entorno.getWidth(),250);
		this.kame = null; // debe ser null para que no se dispare automaticamente al iniciar.	
		this.isKameAvailable= true;
		this.autosCalle = new Auto[3];
		for (int i = 0; i < this.autosCalle.length; i++) {
			this.autosCalle[i] = new Auto(i*250,this.Calles1[0].getY()+100,59,20);
		}	
		this.autosCalle2 = new Auto[4];
		for (int i = 0; i < this.autosCalle2.length; i++) {
			this.autosCalle2[i] = new Auto(i*180,this.autosCalle[0].getY()-55,50,22);
			
		}	
		this.autosCalle3 = new Auto[3];
		for (int i = 0; i < this.autosCalle3.length; i++) {
			this.autosCalle3[i] = new Auto(i*250,this.autosCalle2[0].getY()-55,50,22);
		}
		
		this.autosCalle4 = new Auto[5];
		for (int i = 0; i < this.autosCalle4.length; i++) {
			this.autosCalle4[i] = new Auto(i*180,this.autosCalle3[0].getY()-55,50,22);
		}
		this.autosCalle5 = new Auto[4];
		for (int i = 0; i < this.autosCalle5.length; i++) {
			this.autosCalle5[i] = new Auto(i*180,this.autosCalle4[0].getY()-55,50,22);
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
			// Creacion y movimiento de las calles:
			this.Calles1[0].renderCalle(this.entorno);
			this.Calles1[1].renderCalle(this.entorno);
			this.Calles1[0].fall();
			if (this.Calles1[0].getY() + this.Calles1[0].getHeight()/2 +50 > this.entorno.getHeight()) {
				this.Calles1[1].fall();
			}
			if (this.Calles1[1].getY() + this.Calles1[1].getHeight()/2 +50 > this.entorno.getHeight()) {
				this.Calles1[0].setY(-135);
			}
			//Creacion, movimiento e interacciones del conejo:
			conejo.renderRabbit(this.entorno);
			conejo.fall();
						
			if(entorno.sePresiono(entorno.TECLA_ARRIBA) && conejo.getY() > conejo.getHeight())
				conejo.moveFordward();
			if(entorno.sePresiono(entorno.TECLA_DERECHA) && conejo.getX() < entorno.getWidth()- this.conejo.getWidth()-5)
				conejo.moveRight();
			if(entorno.sePresiono(entorno.TECLA_IZQUIERDA) && conejo.getX() > 0 + this.conejo.getWidth())
				conejo.moveLeft();
			if(colisionConejo(autosCalle, conejo) || colisionConejo(autosCalle2, conejo)) {
				this.conejo.setX(400);
				this.conejo.setY(this.entorno.getHeight()+50);
			}
			
			//Creacion, movimiento e interacciones de los autos:
			
			crearAutosDer(autosCalle);
			
			if (colisionAuto(this.autosCalle, this.kame) != -1){
				this.autosCalle[colisionAuto(this.autosCalle,this.kame)] = null;
				this.kame = null;
				carRespawn(autosCalle);
				} 
						
			crearAutosIzq(autosCalle2);
				
			if (colisionAuto(this.autosCalle2, this.kame) != -1){
				this.autosCalle2[colisionAuto(this.autosCalle2,this.kame)] = null;
				this.kame = null;
				carRespawn(autosCalle2);
				}	
						
			crearAutosDer(autosCalle3);
			if (colisionAuto(this.autosCalle3, this.kame) != -1){
				this.autosCalle3[colisionAuto(this.autosCalle3,this.kame)] = null;
				this.kame = null;
				carRespawn(autosCalle3);
				} 
				
			crearAutosIzq(autosCalle4);	
			if (colisionAuto(this.autosCalle4, this.kame) != -1){
				this.autosCalle4[colisionAuto(this.autosCalle4,this.kame)] = null;
				this.kame = null;
				carRespawn(autosCalle4);
				} 
			crearAutosDer(autosCalle5);	
			if (colisionAuto(this.autosCalle5, this.kame) != -1){
				this.autosCalle5[colisionAuto(this.autosCalle5,this.kame)] = null;
				this.kame = null;
				carRespawn(autosCalle5);
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
				if (this.kame.getY() < 0) {
					this.kame = null;
				}
			}

		
			//codigo para terminar el juego si el conejo sale por el limite inferior o choca:
			if (this.conejo.getY()+conejo.getHeight()/2 > entorno.getHeight() 
					|| this.colisionConejo(autosCalle, conejo)|| this.colisionConejo(autosCalle2, conejo) || this.colisionConejo(this.autosCalle3, this.conejo)) 
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
