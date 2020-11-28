package juego;

import java.util.Timer;
import java.util.TimerTask;

import com.sun.tools.javac.code.Type.ForAll;

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
	private Calle calle1;
	private Calle calle2;
	private static Image icono = Herramientas.cargarImagen("juego/conejo.png");
	private Timer carTimer;
	private TimerTask respawn;
	private Kamehameha [] kames;
	private Rayo rayo;
	private long timerRayo;
	private Zanahoria zanahoria ;
	private int contadorKame; //entero qe controla los lanzamientos del kame
	private int salto;
	private int puntaje;
	

	// Variables y métodos propios de cada grupo
	
	// banderas necesarias para evitar que el tick renderize objetos no deseados
	private boolean isStartScreenActive = true;	//indica si se debe mostrar la pantalla inicial o no
	private boolean isGameOver = false; // indica si se debe mostrar la pantalla final o no
	private boolean isRayoAvailable = true;
	
	void hayColision (Auto[] arrAuto, Kamehameha[] arrKames) {
		double xGuardado;
		double yGuardado;
		long timerSpawn;
		
		for (int k = 0; k < arrKames.length; k++) {
			int indiceColision = colisionAuto(arrAuto,arrKames[k]);
			if (indiceColision == -1){
				continue;
			}
			else{
				arrKames[k] = null;
				xGuardado = arrAuto[indiceColision].getX();
				yGuardado = arrAuto[indiceColision].getY();
				
				arrAuto[indiceColision] = null;
				timerSpawn = System.currentTimeMillis();
				puntaje += 5;
				contadorKame -= 1;
				System.out.println(xGuardado + yGuardado);
				
				}
			if (System.currentTimeMillis() - timerSpawn > 2000) {
				arrAuto[indiceColision] = new Auto(xGuardado,yGuardado,100,60);
			}
		}
	}
		
	//metodo para respawnear autos:
	void carRespawn(Auto[] arrAuto) {
		carTimer = new Timer();
		respawn = new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < arrAuto.length; i++) {
					if( i == 0 && arrAuto[i] == null) {
						if (arrAuto[i+1] == null) {
							arrAuto[i] = new Auto(arrAuto[arrAuto.length-1].getX()- 200,arrAuto[arrAuto.length-1].getY(),100,60);
						}
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
	boolean colisionConejo (Auto [] arrAuto, Conejo conejo) {
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

// metodo para settear todos los autos null para reiniciar el juego	

	
{
		// Inicializa el objeto entorno

		this.entorno = new Entorno(this, "Boss Rabbit Rabber - Grupo 10 - Juanma, Lucas, Nahuel- v1", 800, 600);

		// Inicializar lo que haga falta para el juego
		
		this.conejo = new Conejo(entorno.getWidth()/2, entorno.getHeight()-100, 32,50);	
		this.kames = new Kamehameha [3];
		this.contadorKame = 0;
		this.calle1 = new Calle(this.entorno.getWidth()/2,this.entorno.getHeight()/2-250,this.entorno.getWidth(),220);
		this.calle2 = new Calle(this.entorno.getWidth()/2,this.entorno.getHeight()-300,this.entorno.getWidth(),220);
		this.rayo = null;
		this.timerRayo = 0;
		this.zanahoria = null;
		this.salto=0;
		this.puntaje=0;
		
		
		
	// Inicia el juego!
		this.entorno.iniciar();	
		this.entorno.setIconImage(icono);
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
			this.calle1.renderCalle(this.entorno);
			this.calle1.fall();
			
			this.calle2.renderCalle(this.entorno);
			this.calle2.fall();
			
			//Creacion, movimiento e interacciones del conejo:
			
			conejo.renderRabbit(this.entorno);
			conejo.fall();
						
			if(entorno.sePresiono(entorno.TECLA_ARRIBA) && conejo.getY() > conejo.getHeight()) {
				conejo.moveForward();
				salto++;
			}	
			if(entorno.sePresiono(entorno.TECLA_DERECHA) && conejo.getX() < entorno.getWidth()- this.conejo.getWidth()-5)
				conejo.moveRight();
			if(entorno.sePresiono(entorno.TECLA_IZQUIERDA) && conejo.getX() > 0 + this.conejo.getWidth())
				conejo.moveLeft();
				
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
					if (this.kames[i].getY() < 0 ) {
						this.kames[i] = null;
						contadorKame -= 1;
					}
				}		
			}	
		// creacion e interacciones del rayo

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
				if (this.zanahoria.getY() > entorno.getHeight() -50 ) {
					this.zanahoria = null;
				}
				if (conejo.comer(this.zanahoria)) {
					puntaje += 25;
					this.zanahoria = null;
				}
			}
		}

		entorno.cambiarFont("arial", 18, Color.white);
		entorno.escribirTexto("Saltos: " + salto, 50,15);
		entorno.escribirTexto("Puntaje: "+ puntaje, 50, 30);
		entorno.escribirTexto("Rayo Zanahorificador", 560, 40);

			
		//codigo para terminar el juego si el conejo sale por el limite inferior o choca:
		if 	(this.conejo.getY()+conejo.getHeight()/2 > entorno.getHeight() 	||
			colisionConejo(this.calle1.getCarril()[0].arrAuto, this.conejo) || 
			colisionConejo(this.calle1.getCarril()[1].arrAuto, this.conejo) || 
			colisionConejo(this.calle1.getCarril()[2].arrAuto, this.conejo) || 
			colisionConejo(this.calle1.getCarril()[3].arrAuto, this.conejo) ||
			colisionConejo(this.calle2.getCarril()[0].arrAuto, this.conejo) || 
			colisionConejo(this.calle2.getCarril()[1].arrAuto, this.conejo) || 
			colisionConejo(this.calle2.getCarril()[2].arrAuto, this.conejo) || 
			colisionConejo(this.calle2.getCarril()[3].arrAuto, this.conejo)){		
				
				this.isGameOver = true;
				this.conejo.setY(2000);
				Image imagenFin = Herramientas.cargarImagen("imagenes/fin.jpg");
				entorno.dibujarImagen(imagenFin, 400,300, 0);
				entorno.escribirTexto("¿Desea continuar? \n Pulse Y o N",entorno.getWidth()-500, entorno.getHeight()-100);
				
				if (entorno.sePresiono ('y')) 
				{// si apreta y cierro ventana y vuelvo a iniciar
					this.calle1 = new Calle(this.entorno.getWidth()/2,this.entorno.getHeight()/2-250,this.entorno.getWidth(),220);
					this.calle2 = new Calle(this.entorno.getWidth()/2,this.entorno.getHeight()-300,this.entorno.getWidth(),220);
					this.isRayoAvailable = true;
					this.conejo.setX(entorno.getWidth()/2);
					this.conejo.setY(entorno.getHeight()-100);
					this.contadorKame= 0;
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
		Herramientas.loop("juego/music.wav");
	}
}
