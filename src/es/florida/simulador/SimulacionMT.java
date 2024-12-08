package es.florida.simulador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que implementa `Runnable` para realizar simulaciones multihilo de proteínas.
 * Escribe los resultados de la simulación en un archivo de salida.
 */
public class SimulacionMT implements Runnable {
	
	private int type;
	
	/**
     * Constructor que inicializa el tipo de simulación.
     * 
     * @param tipo el tipo de estructura de proteína (1: primaria, 2: secundaria, etc.).
     */
	public SimulacionMT(int tipo) {
		this.type = tipo;
	}
	
	/**
     * Realiza una simulación basada en el tipo de proteína.
     * 
     * @param type el tipo de estructura de proteína.
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
     * Método que se ejecuta cuando se lanza el hilo.
     * Realiza la simulación, calcula la duración y escribe los resultados en un archivo.
     */
	@Override
	public void run() {
		long tiempoInicio = System.currentTimeMillis();
		double valor = simulation(type);
		long tiempoFinal = System.currentTimeMillis();	
		long duracion = tiempoFinal - tiempoInicio;
		
		try {
			FileWriter fw = new FileWriter("PROT_MT_" + type + "_n" + Thread.currentThread().getName() + "_" + Simulador.formatearTiempo(tiempoInicio) + ".sim");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(Simulador.formatearTiempo(tiempoInicio) + "\n");
			bw.append(Simulador.formatearTiempo(tiempoFinal) + "\n");
			bw.append(Simulador.formatearSegundosCentesimas(duracion) + "\n");
			bw.append(String.valueOf(valor));
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
}
