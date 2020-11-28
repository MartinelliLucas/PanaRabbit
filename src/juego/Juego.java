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
	private Kamehameha [] kames;
	private Rayo rayo;
	private long timerRayo;
	private Zanahoria zanahoria ;
	int contadorKame; //entero qe controla los lanzamientos del kame
	private int salto;
	private int puntaje;

	// Variables y métodos propios de cada grupo
	// banderas necesarias para evitar que el tick renderize objetos no deseados
	private boolean isStartScreenActive = true;	//indica si se debe mostrar la pantalla inicial o no
	private boolean isGameOver = false;
	private boolean isRayoAvailable = true;
	// indica si se debe mostrar la pantalla final o no
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
// metodo para settear todos los autos null para reiniciar el juego	

	
{
		// Inicializa el objeto entorno

		this.entorno = new Entorno(this, "Boss Rabbit Rabber - Grupo 10 - Juanma, Lucas, Nahuel- v1", 800, 600);

		// Inicializar lo que haga falta para el juego
		
		this.conejo = new Conejo(entorno.getWidth()/2, entorno.getHeight()-100, 32,50);	
		this.kames = new Kamehameha [3];
		this.contadorKame = 0;
		this.Calles1 = new Calle[2];
		this.Calles1[0] = new Calle(this.entorno.getWidth()/2,this.entorno.getHeight()/2-150,this.entorno.getWidth(),250);
		this.Calles1[1] = new Calle(this.entorno.getWidth()/2,-135,this.entorno.getWidth(),250);
		this.rayo = null;
		this.timerRayo = 0;
		this.zanahoria = null;
		this.autosCalle = new Auto[3];
		for (int i = 0; i < this.autosCalle.length; i++) {
			this.autosCalle[i] = new Auto(i*250,this.Calles1[0].getY()+100,50,20);
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
			
		if (!this.isStartScreenActive && !this.isGameOver) 
		{	
			Image grass = Herramientas.cargarImagen("imagenes/grass.jpg");
			entorno.dibujarImagen(grass, 400, 300, 0);
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

			//Creacion, movimiento e interacciones de los autos:
			crearAutosDer(autosCalle);
//			if (colisionAuto(this.autosCalle, this.kame) != -1){
//				this.autosCalle[colisionAuto(this.autosCalle,this.kame)] = null;
//				this.kame = null;
//				carRespawn(autosCalle);
//				} 
						
			crearAutosIzq(autosCalle2);
//			if (colisionAuto(this.autosCalle2, this.kame) != -1){
//				this.autosCalle2[colisionAuto(this.autosCalle2,this.kame)] = null;
//				this.kame = null;
//				carRespawn(autosCalle2);
//				}	
						
			crearAutosDer(autosCalle3);
//			if (colisionAuto(this.autosCalle3, this.kame) != -1){
//				this.autosCalle3[colisionAuto(this.autosCalle3,this.kame)] = null;
//				this.kame = null;
//				carRespawn(autosCalle3);
//				} 
				
			crearAutosIzq(autosCalle4);	
//			if (colisionAuto(this.autosCalle4, this.kame) != -1){
//				this.autosCalle4[colisionAuto(this.autosCalle4,this.kame)] = null;
//				this.kame = null;
//				carRespawn(autosCalle4);
//				} 
			crearAutosDer(autosCalle5);	
//			if (colisionAuto(this.autosCalle5, this.kame) != -1){
//				this.autosCalle5[colisionAuto(this.autosCalle5,this.kame)] = null;
//				this.kame = null;
//				carRespawn(autosCalle5);
//				} 
				
			//Creacion, movimiento e interacciones del Kamehameha:
			// si se presiona espacio entra en el ciclo y busca la primera posicion null y coloca el kame ahi
			if (entorno.sePresiono(entorno.TECLA_ESPACIO) && contadorKame < 3 ) {
				for (int i = 0; i < this.kames.length; i++) {
					if (this.kames[i] == null) {
						this.kames[i] = conejo.disparar();
						Herramientas.play("juego/disparo.wav");
						contadorKame += 1;
						break; // encuentra un kame == null y sale del for
					}
				}
			}
			//recorre el array de kames y renderiza 1 por 1 
			for (int i = 0; i < this.kames.length; i++) {
				if (this.kames[i] != null) {
					this.kames[i].renderKame(this.entorno);
					this.kames[i].desplazamiento();
					if (this.kames[i].getY() < 0) {
						this.kames[i] = null;
						contadorKame -= 1;
					}
				}		
			}	
		// creacion e interacciones del rayo
			entorno.cambiarFont("console", 18, Color.white);
			entorno.escribirTexto("Rayo Zanahorificador", 560, 40);
			if (this.isRayoAvailable) {
				Image iconoRayo = Herramientas.cargarImagen("imagenes/rayoAvailable.png");
				entorno.dibujarImagen(iconoRayo, 750, 35, 0);
			}

			if (entorno.sePresiono(entorno.TECLA_SHIFT) && this.isRayoAvailable) {
				this.rayo = conejo.rayo();
				this.timerRayo = System.currentTimeMillis();
				this.isRayoAvailable = false;
			}
			if (this.rayo != null && !this.isRayoAvailable) {
				this.rayo.renderRayo(this.entorno);
				this.rayo.desplazamiento();
				if (this.rayo.getY() < 0) {
					this.rayo = null;

				}
				if (System.currentTimeMillis() - this.timerRayo >= 20000) {
					this.isRayoAvailable = true;
				}
			}
			
			//creacion e interacciones de la zanahoria
			if (this.zanahoria == null)
				this.zanahoria = new Zanahoria(entorno.getWidth()/2,entorno.getHeight()/2,50,20);
			if (zanahoria != null) {
				this.zanahoria.renderZanahoria(this.entorno);
				this.zanahoria.fall();
				if (this.zanahoria.getY() > entorno.getHeight() -50 || conejo.comer(this.zanahoria)) {
					this.zanahoria = null;
				}
			}
	}	
		
		//codigo para terminar el juego si el conejo sale por el limite inferior o choca:
	if (this.conejo.getY()+conejo.getHeight()/2 > entorno.getHeight() 
			|| colisionConejo(this.autosCalle, this.conejo)|| colisionConejo(this.autosCalle2, this.conejo) || colisionConejo(this.autosCalle3, this.conejo)
			|| colisionConejo(this.autosCalle4, this.conejo) || colisionConejo(this.autosCalle5,this.conejo)){		
				
				this.isGameOver = true;
				this.conejo.setY(2000);
				Image imagenFin = Herramientas.cargarImagen("imagenes/fin.jpg");
				entorno.dibujarImagen(imagenFin, 400,300, 0);
				entorno.escribirTexto("¿Desea continuar? \n Pulse Y o N",entorno.getWidth()-500, entorno.getHeight()-100);
				
				if (entorno.sePresiono ('y')) 
				{// si apreta y cierro ventana y vuelvo a iniciar
					this.isRayoAvailable = true;
					this.conejo.setX(entorno.getWidth()/2);
					this.conejo.setY(entorno.getHeight()-100);
					this.contadorKame= 0;
					this.Calles1 = new Calle[2];
					this.Calles1[0] = new Calle(this.entorno.getWidth()/2,this.entorno.getHeight()/2-150,this.entorno.getWidth(),250);
					this.Calles1[1] = new Calle(this.entorno.getWidth()/2,-135,this.entorno.getWidth(),250);
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
					this.isGameOver = false;
					this.isStartScreenActive= true;
				}
				if (entorno.sePresiono('n')) {
					// termino el juego
					System.exit(0);
				}	
		}
		//flaginicio			
	if (this.isStartScreenActive && !this.isGameOver) 
	{		
		Image imagenInicio = Herramientas.cargarImagen("imagenes/inicio.jpg");
		entorno.dibujarImagen(imagenInicio, 400, 300, 0);		
	}		
		
}

@SuppressWarnings("unused")
public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
