package es.florida.simulador;

/**
 * Clase para realizar simulaciones de proteínas en un contexto de multiproceso.
 * Ejecuta una simulación basada en un tipo de estructura proteica especificado.
 */
public class SimulacionMP {
	
	/**
     * Realiza una simulación basada en el tipo de proteína.
     * 
     * @param type el tipo de estructura de proteína (1: primaria, 2: secundaria, etc.).
     * @return un valor calculado durante la simulación (resultado del cálculo).
     */
	public static double simulation (int type) { 
		double calc = 0.0;
		double simulationTime = Math.pow(5, type);
		double startTime = System.currentTimeMillis();
		double endTime = startTime + simulationTime;
		while (System.currentTimeMillis() < endTime) {
			calc = Math.sin(Math.pow(Math.random(),2));
		}
		return calc;
	}
	
	/**
     * Método principal que ejecuta la simulación para un tipo de proteína dado.
     * 
     * @param args argumentos pasados por la línea de comandos.
     *             args[0]: tipo de proteína.
     *             args[1]: tiempo de inicio en milisegundos.
     */
	public static void main(String[] args) {
		double valor = simulation(Integer.parseInt(args[0]));
		long tiempoInicio = Long.parseLong(args[1]);
		long tiempoFinal = System.currentTimeMillis();	
		long duracion = tiempoFinal - tiempoInicio;
		
		System.out.println(Simulador.formatearTiempo(tiempoInicio));
		System.out.println(Simulador.formatearTiempo(tiempoFinal));
		System.out.println(Simulador.formatearSegundosCentesimas(duracion));
		System.out.println(valor);

	}

}
