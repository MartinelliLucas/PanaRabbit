package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Conejo conejo;
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Boss Rabbit Rabber - Grupo 10 - Juanma, Lucas, Nahuel- v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		this.conejo = new Conejo(400, 500, 50,50);
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
		conejo.renderRabbit(this.entorno);
		this.conejo.caerse();
		// Procesamiento de un instante de tiempo
		// ...
		if(entorno.sePresiono(entorno.TECLA_ARRIBA))
				conejo.moveFordward();
		if(entorno.sePresiono(entorno.TECLA_DERECHA))
			conejo.moveRight();
		if(entorno.sePresiono(entorno.TECLA_IZQUIERDA))
			conejo.moveLeft();
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
