package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Conejo conejo;
	private Auto auto1;
	// Variables y métodos propios de cada grupo
	// ...
	boolean flag = true;
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Boss Rabbit Rabber - Grupo 10 - Juanma, Lucas, Nahuel- v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		this.conejo = new Conejo(entorno.getWidth()/2, entorno.getHeight()-100, 40,40);
		this.auto1 = new Auto(0,entorno.getHeight()-150,60,40);
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
		
		// bloque para finalizar el juego si el conejo sale por el limite inferior:
				/*if(conejo.getY() >= entorno.getHeight()) {
					
					}
				}*/
		
		//Creacion, movimiento e interacciones del auto:
		auto1.renderCar(this.entorno);
		auto1.moveForward();
		auto1.fall();
		
		if (auto1.getX() > entorno.getWidth()) {
			auto1.setX(0);
		}
		if (auto1.getY() > entorno.getHeight()) {
			auto1.setY(0+auto1.getHeight());
		}
		// Procesamiento de un instante de tiempo
				// ...
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
