package juego;

import java.awt.Color;
import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros

	private Entorno entorno;
	private Conejo conejo;
	private Calle calle1;
	private Calle calle2;
	private static Image icono = Herramientas.cargarImagen("archivos/conejo.png");
	private Kamehameha[] kames;
	private Rayo rayo;
	private Zanahoria zanahoria;
	private int contadorKame; // entero que controla los lanzamientos del kame
	private int contadorRayo;
	private int salto;
	private int puntaje;

	// Variables y métodos propios de cada grupo

	// banderas necesarias para evitar que el tick renderize objetos no deseados
	private boolean isStartScreenActive = true; // indica si se debe mostrar la pantalla inicial o no
	private boolean isGameOver = false; // indica si se debe mostrar la pantalla final o no
	private boolean isRayoAvailable = false;
	private boolean victory= false;

	// metodo para respawnear autos:
	void respawnCars(Calle calle) {
		for (int c = 0; c < calle.getCantCarriles(); c++) {
			for (int a = 0; a < calle.getCarril(c).getCantAutos(); a++) {
				Auto autoActual = calle.getCarril(c).getAuto(a);
				if (autoActual == null) {
					calle.getCarril(c).agregarAuto(a, this.entorno);
				}
			}
		}
	}

	// metodo para identificar auto colisionado con el kame y eliminar ambos
	// objetos, ademas de sumar puntaje.
	void colisionAutoKame(Calle calle, Kamehameha[] kames) {
		for (int k = 0; k < kames.length; k++) {
			Kamehameha kame = kames[k];
			if (kame != null) {
				for (int c = 0; c < calle.getCantCarriles(); c++) {
					for (int a = 0; a < calle.getCarril(c).getCantAutos(); a++) {
						Auto autoActual = calle.getCarril(c).getAuto(a);
						if (autoActual != null) {
							if (autoActual.getX() - (autoActual.getWidth() / 2) < kame.getX()
									&& kame.getX() < (autoActual.getX() + (autoActual.getWidth() / 2))
									&& autoActual.getY() + (autoActual.getHeight() / 2) > kame.getY()
											- (kame.getAlto() / 2)
									&& kame.getY() > (autoActual.getY() - autoActual.getHeight() / 2)) {

								calle.getCarril(c).removerAuto(a);
								kames[k] = null;
								puntaje += 5;
								contadorKame -= 1;
							}
						}
					}
				}
			}
		}
	}

	// metodo de colision auto con conejo
	boolean colisionAutoConejo(Calle calle, Conejo conejo) {
		for (int c = 0; c < calle.getCantCarriles(); c++) {
			for (int a = 0; a < calle.getCarril(c).getCantAutos(); a++) {
				Auto autoActual = calle.getCarril(c).getAuto(a);
				if (autoActual != null) {
					if (conejo.getY() + conejo.getHeight() / 2 > autoActual.getY() - autoActual.getHeight() / 2
							&& conejo.getY() - conejo.getHeight() / 2 < autoActual.getY() + autoActual.getHeight() / 2
							&& conejo.getX() + conejo.getWidth() / 2 > autoActual.getX() - autoActual.getWidth() / 2
							&& conejo.getX() - conejo.getWidth() / 2 < autoActual.getX() + autoActual.getWidth() / 2) {
						return true;
					}
				}
			}
		}
		return false;
	}



	boolean colisionAutoRayo(Calle calle, Rayo rayo) {
		if (rayo != null) {
			for (int c = 0; c < calle.getCantCarriles(); c++) {
				for (int a = 0; a < calle.getCarril(c).getCantAutos(); a++) {
					Auto autoActual = calle.getCarril(c).getAuto(a);
					if (autoActual != null) {
						if (rayo.getY() + rayo.getAlto() / 2 > autoActual.getY() - autoActual.getHeight() / 2
								&& rayo.getY() - rayo.getAlto() / 2 < autoActual.getY() + autoActual.getHeight() / 2
								&& rayo.getX() + rayo.getAncho() / 2 > autoActual.getX() - autoActual.getWidth() / 2
								&& rayo.getX() - rayo.getAncho() / 2 < autoActual.getX() + autoActual.getWidth() / 2) {
							this.zanahoria = new Zanahoria(autoActual.getX(), autoActual.getY(), 50, 20);
							calle.getCarril(c).removerAuto(a);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	{

		// Inicializa el objeto entorno

		this.entorno = new Entorno(this, "Boss Rabbit Rabber - Grupo 10 - Juanma, Lucas, Nahuel- v1", 800, 600);

		// Inicializar lo que haga falta para el juego

		this.conejo = new Conejo(entorno.getWidth() / 2, entorno.getHeight() - 100, 32, 45);
		this.kames = new Kamehameha[3];
		this.contadorKame = 0;
		this.calle1 = new Calle(this.entorno.getWidth() / 2, this.entorno.getHeight() / 2 - 250,
				this.entorno.getWidth(), 220);
		this.calle2 = new Calle(this.entorno.getWidth() / 2, this.entorno.getHeight() - 300, this.entorno.getWidth(),
				220);
		this.rayo = null;
		this.zanahoria = null;
		this.salto = 0;
		this.puntaje = 0;
		this.contadorRayo =0 ;

		// Inicia el juego!
		this.entorno.iniciar();
		this.entorno.setIconImage(icono);
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */

	public void tick() // Procesamiento de un instante de tiempo
	{

		if (entorno.sePresiono(entorno.TECLA_ENTER)) {
			this.isStartScreenActive = false;
		}

		if (!this.isStartScreenActive && !this.isGameOver && !this.victory) {

			Image grass = Herramientas.cargarImagen("archivos/grass.jpg");
			entorno.dibujarImagen(grass, 400, 300, 0);

			this.calle1.renderCalle(this.entorno);
			this.calle1.fall();
			colisionAutoKame(this.calle1, this.kames);
			if (this.calle1.getY() > this.entorno.getHeight() - 60) {
				this.calle1.setY(-10);
			}
			respawnCars(this.calle1);

			this.calle2.renderCalle(this.entorno);
			this.calle2.fall();
			colisionAutoKame(this.calle2, this.kames);
			if (this.calle2.getY() > this.entorno.getHeight() - 60) {
				this.calle2.setY(-10);
			}
			respawnCars(this.calle2);

			// Creacion, movimiento e interacciones del conejo:

			conejo.renderRabbit(this.entorno);
			conejo.fall();

			if (entorno.sePresiono(entorno.TECLA_ARRIBA) && conejo.getY() > conejo.getHeight()) {
				conejo.moveForward();
				salto++;
				contadorRayo ++ ;
			}
			if (entorno.sePresiono(entorno.TECLA_DERECHA)
					&& conejo.getX() < entorno.getWidth() - this.conejo.getWidth() - 5)
				conejo.moveRight();
			if (entorno.sePresiono(entorno.TECLA_IZQUIERDA) && conejo.getX() > 0 + this.conejo.getWidth())
				conejo.moveLeft();

			if (entorno.sePresiono(entorno.TECLA_ESPACIO) && contadorKame < 3) {
				for (int i = 0; i < this.kames.length; i++) {
					if (this.kames[i] == null) {
						this.kames[i] = conejo.disparar();
						Herramientas.play("archivos/disparo.wav");
						contadorKame += 1;
						break; // encuentra un kame == null y sale del for
					}
				}

			}
			// recorre el array de kames y renderiza 1 por 1
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


			if (this.contadorRayo == 10) {
				this.isRayoAvailable = true;
			}
			if (this.isRayoAvailable) {
				Image iconoRayo = Herramientas.cargarImagen("archivos/rayoAvailable.png");
				entorno.dibujarImagen(iconoRayo, 750, 35, 0);
			}

			if (entorno.sePresiono(entorno.TECLA_SHIFT) && this.isRayoAvailable) {
				this.rayo = conejo.rayo();
				this.contadorRayo = 0;
				this.isRayoAvailable = false;
			}
			if (this.rayo != null && !this.isRayoAvailable) {
				this.rayo.renderRayo(this.entorno);
				this.rayo.desplazamiento();
				if (this.rayo.getY() < 0 || colisionAutoRayo(this.calle1, this.rayo)
						|| colisionAutoRayo(this.calle2, this.rayo)) {
					this.rayo = null;
				}
			}

			// creacion e interacciones de la zanahoria
			if(this.zanahoria != null) {
				this.zanahoria.renderZanahoria(this.entorno);
				this.zanahoria.fall();
				if (this.zanahoria.getY() > entorno.getHeight() - 50) {
					this.zanahoria = null;
				}
				if (conejo.comer(this.zanahoria)) {
					puntaje += 25;
					this.zanahoria = null;
				}
			}
			if(this.zanahoria != null) {
				this.zanahoria.renderZanahoria(this.entorno);
				this.zanahoria.fall();
				if (this.zanahoria.getY() > entorno.getHeight() - 50) {
					this.zanahoria = null;
				}
				if (conejo.comer(this.zanahoria)) {
					puntaje += 25;
					this.zanahoria = null;
				}
			}

		}

		entorno.cambiarFont("arial", 18, Color.white);
		entorno.escribirTexto("Saltos: " + salto, 50, 15);
		entorno.escribirTexto("Puntaje: " + puntaje, 50, 30);
		entorno.escribirTexto("Rayo Zanahorificador", 560, 40);
		entorno.cambiarFont("console", 18, Color.white);
		entorno.escribirTexto("Rayo Zanahorificador", 560, 40);
		
		//codigo para terminar el juego si se gano,se gana llegando a 100 puntos
		if(this.puntaje>=100) {
			this.victory=true;
			Image imagenWin= Herramientas.cargarImagen("archivos/victory2.jpg");
			entorno.dibujarImagen(imagenWin, 400, 300, 0);
			entorno.cambiarFont("arial", 18, Color.WHITE);
			entorno.escribirTexto("¿Desea continuar? \n Pulse Y o N", entorno.getWidth() - 560,
					entorno.getHeight() - 100);
			entorno.escribirTexto("Su puntaje es: "+ this.puntaje, entorno.getWidth()-500,entorno.getHeight()-120);
			
			if (entorno.sePresiono('y')) {// si apreta y cierro ventana y vuelvo a iniciar
				this.calle1 = new Calle(this.entorno.getWidth() / 2, this.entorno.getHeight() / 2 - 250,
						this.entorno.getWidth(), 220);
				this.calle2 = new Calle(this.entorno.getWidth() / 2, this.entorno.getHeight() - 300,
						this.entorno.getWidth(), 220);
				this.zanahoria = null;
				for (int i = 0; i < kames.length; i++) {
					kames[i]= null;
				}
				this.isRayoAvailable = false;
				this.conejo.setX(entorno.getWidth() / 2);
				this.conejo.setY(entorno.getHeight() - 100);
				this.contadorKame = 0;
				this.contadorRayo = 0;
				this.puntaje = 0;
				this.salto =0;
				this.isGameOver = false;
				this.isStartScreenActive = true;
				this.victory=false;
			}
			if (entorno.sePresiono('n')) {
				// termino el juego
				System.exit(0);
			}
		}
		
		
		// codigo para terminar el juego si el conejo sale por el limite inferior o
		// choca:
		if (this.conejo.getY() + conejo.getHeight() / 2 > entorno.getHeight()
				|| colisionAutoConejo(this.calle1, this.conejo) || colisionAutoConejo(this.calle2, this.conejo)) {
			this.isGameOver = true;
			this.conejo.setY(2000);
			Image imagenFin = Herramientas.cargarImagen("archivos/fin.jpg");
			entorno.dibujarImagen(imagenFin, 400, 300, 0);
			//entorno.cambiarFont("arial", 18, Color.white);
			entorno.escribirTexto("¿Desea continuar? \n Pulse Y o N", entorno.getWidth() - 560,
					entorno.getHeight() - 100);

			if (entorno.sePresiono('y')) {// si apreta y cierro ventana y vuelvo a iniciar
				this.calle1 = new Calle(this.entorno.getWidth() / 2, this.entorno.getHeight() / 2 - 250,
						this.entorno.getWidth(), 220);
				this.calle2 = new Calle(this.entorno.getWidth() / 2, this.entorno.getHeight() - 300,
						this.entorno.getWidth(), 220);
				this.zanahoria = null;
				for (int i = 0; i < kames.length; i++) {
					kames[i]= null;
				}
				this.isRayoAvailable = false;
				this.conejo.setX(entorno.getWidth() / 2);
				this.conejo.setY(entorno.getHeight() - 100);
				this.contadorKame = 0;
				this.contadorRayo = 0;
				this.puntaje = 0;
				this.salto =0;
				this.isGameOver = false;
				this.isStartScreenActive = true;
			}
			if (entorno.sePresiono('n')) {
				// termino el juego
				System.exit(0);
			}
		}

		// flaginicio
		if (this.isStartScreenActive && !this.isGameOver) {
			Image imagenInicio = Herramientas.cargarImagen("archivos/inicio.jpg");
			entorno.dibujarImagen(imagenInicio, 400, 300, 0);
		}

		// flaginicio
		if (this.isStartScreenActive && !this.isGameOver) {
			Image imagenInicio = Herramientas.cargarImagen("archivos/inicio.jpg");
			entorno.dibujarImagen(imagenInicio, 400, 300, 0);
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
		Herramientas.loop("archivos/musica.wav");	
	}
}
