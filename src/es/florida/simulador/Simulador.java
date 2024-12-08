package es.florida.simulador;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Clase principal de la simulación de proteínas. Proporciona una interfaz gráfica
 * para configurar y ejecutar la simulación de diferentes tipos de proteínas utilizando
 * multiproceso y multihilo.
 */
public class Simulador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JLabel lblTiempoMultihilo;
	JLabel lblTiempoMultiproceso;

	private JSpinner spinnerPrimaria;
	private JSpinner spinnerSecundaria;
	private JSpinner spinnerTerciaria;
	private JSpinner spinnerCuaternaria;

	private Process[] bloqueProcesos;
	
	/**
     * Método principal de la aplicación. Inicializa la interfaz gráfica y muestra la ventana.
     * 
     * @param args argumentos de la línea de comandos.
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Simulador frame = new Simulador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
     * Constructor de la clase Simulador. Configura el título y llama al método de inicialización.
     */
	public Simulador() {
		setTitle("Simulación de Proteínas");
		inicialize();
	}
	
	/**
     * Inicializa los componentes de la interfaz gráfica y los listeners de eventos.
     */
	public void inicialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Indica el número de proteinas a simular:");
		lblNewLabel.setBounds(201, 59, 227, 14);
		contentPane.add(lblNewLabel);

		spinnerPrimaria = new JSpinner();
		spinnerPrimaria.setBounds(69, 151, 75, 20);
		contentPane.add(spinnerPrimaria);

		spinnerSecundaria = new JSpinner();
		spinnerSecundaria.setBounds(216, 151, 75, 20);
		contentPane.add(spinnerSecundaria);

		spinnerTerciaria = new JSpinner();
		spinnerTerciaria.setBounds(353, 151, 75, 20);
		contentPane.add(spinnerTerciaria);

		spinnerCuaternaria = new JSpinner();
		spinnerCuaternaria.setBounds(497, 151, 75, 20);
		contentPane.add(spinnerCuaternaria);

		JLabel lblNewLabel_1 = new JLabel("Estructura Primaria");
		lblNewLabel_1.setBounds(54, 115, 118, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Estructura Secundaria");
		lblNewLabel_1_1.setBounds(197, 115, 140, 14);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Estructura Terciaria");
		lblNewLabel_1_1_1.setBounds(338, 115, 125, 14);
		contentPane.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Estructura Cuaternaria");
		lblNewLabel_1_1_1_1.setBounds(473, 115, 140, 14);
		contentPane.add(lblNewLabel_1_1_1_1);

		JLabel lblNewLabel_2 = new JLabel("Tiempo que ha tardado en realizarse la simulacion:");
		lblNewLabel_2.setBounds(177, 297, 361, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("En Multiproceso:");
		lblNewLabel_3.setBounds(146, 337, 99, 14);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_3_1 = new JLabel("En Multihilo:");
		lblNewLabel_3_1.setBounds(349, 337, 75, 14);
		contentPane.add(lblNewLabel_3_1);

		lblTiempoMultiproceso = new JLabel("-");
		lblTiempoMultiproceso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTiempoMultiproceso.setBounds(258, 337, 46, 14);
		contentPane.add(lblTiempoMultiproceso);

		lblTiempoMultihilo = new JLabel("-");
		lblTiempoMultihilo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTiempoMultihilo.setBounds(434, 337, 46, 14);
		contentPane.add(lblTiempoMultihilo);

		// Lanzaremos la simulación con el botón btnSimular
		JButton btnSimular = new JButton("Simular");
		btnSimular.setBounds(273, 210, 89, 23);
		contentPane.add(btnSimular);
		btnSimular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int proteinasPrimaria = Integer.parseInt(spinnerPrimaria.getValue().toString());
				int proteinasSecundaria = Integer.parseInt(spinnerSecundaria.getValue().toString());
				int proteinasTerciaria = Integer.parseInt(spinnerTerciaria.getValue().toString());
				int proteinasCuaternaria = Integer.parseInt(spinnerCuaternaria.getValue().toString());
				
				long tiempoInicioMP = System.nanoTime();
				simularMP(proteinasPrimaria, proteinasSecundaria, proteinasTerciaria, proteinasCuaternaria);
				lblTiempoMultiproceso.setText(duracionSegundosCentesimas(tiempoInicioMP));
				
				long tiempoInicioMT = System.nanoTime();
				simularMT(proteinasPrimaria, proteinasSecundaria, proteinasTerciaria, proteinasCuaternaria);
				lblTiempoMultihilo.setText(duracionSegundosCentesimas(tiempoInicioMT));
			}
		});

	}
	
	/**
     * Ejecuta la simulación en multiproceso.
     *
     * @param proteinasPrimaria número de proteínas primarias.
     * @param proteinasSecundaria número de proteínas secundarias.
     * @param proteinasTerciaria número de proteínas terciarias.
     * @param proteinasCuaternaria número de proteínas cuaternarias.
     */
	private void simularMP(int proteinasPrimaria, int proteinasSecundaria, int proteinasTerciaria, int proteinasCuaternaria) {

		int totalProteinas = proteinasPrimaria + proteinasSecundaria + proteinasTerciaria + proteinasCuaternaria;

		int numCores = Runtime.getRuntime().availableProcessors();

		// Creamos un array (bloque) para controlar posteriormente que procesos están vivos y sinó lanzamos un nuevo proceso en la posición del proceso que ha terminado
		bloqueProcesos = new Process[numCores];

		int ordenProteina = 1;

		while (totalProteinas > 0) {
			for (int i = 0; i < numCores; i++) {

				if (bloqueProcesos[i] == null || !bloqueProcesos[i].isAlive()) {
					// Controlamos el tipo de proteina que tenemos según la cantidad puesta en la interfaz
					int type = 0;
					if (proteinasPrimaria > 0) type = 1;
					else if (proteinasSecundaria > 0) type = 2;
					else if (proteinasTerciaria > 0) type = 3;
					else if (proteinasCuaternaria > 0) type = 4;

					// Se lanza el proceso y se resta de la proteina a simular según su tipo
					lanzarProcesos(type, i, ordenProteina++);
					if (type == 1) proteinasPrimaria--;
					if (type == 2) proteinasSecundaria--;
					if (type == 3) proteinasTerciaria--;
					if (type == 4) proteinasCuaternaria--;
					totalProteinas = proteinasPrimaria + proteinasSecundaria + proteinasTerciaria + proteinasCuaternaria;
					if (totalProteinas == 0) break;
				}
			}	
		}
		
	}

	/**
     * Ejecuta la simulación en multihilo.
     *
     * @param proteinasPrimaria número de proteínas primarias.
     * @param proteinasSecundaria número de proteínas secundarias.
     * @param proteinasTerciaria número de proteínas terciarias.
     * @param proteinasCuaternaria número de proteínas cuaternarias.
     */
	private void simularMT(int proteinasPrimaria, int proteinasSecundaria, int proteinasTerciaria, int proteinasCuaternaria) {
		//JOptionPane.showMessageDialog(null, type, "Alerta", JOptionPane.WARNING_MESSAGE);
		int totalProteinas = proteinasPrimaria + proteinasSecundaria + proteinasTerciaria + proteinasCuaternaria;

		for (int i = 0; i < totalProteinas; i++) {
			
			int type = 0;
			if (proteinasPrimaria > 0) type = 1;
			else if (proteinasSecundaria > 0) type = 2;
			else if (proteinasTerciaria > 0) type = 3;
			else if (proteinasCuaternaria > 0) type = 4;

			SimulacionMT obj = new SimulacionMT(type);
			Thread hilo = new Thread(obj);
			hilo.setName(String.valueOf(i + 1));
			hilo.start();
			if (type == 1) proteinasPrimaria--;
			if (type == 2) proteinasSecundaria--;
			if (type == 3) proteinasTerciaria--;
			if (type == 4) proteinasCuaternaria--;
		}
		
	}
	
	/**
     * Lanza un nuevo proceso para la simulación de una proteína específica.
     *
     * @param type tipo de proteína.
     * @param posicionBloque índice del bloque de procesos.
     * @param ordenProteina orden de la proteína simulada.
     */
	private void lanzarProcesos(int type, int posicionBloque, int ordenProteina) {

		long tiempoInicio = System.currentTimeMillis();

		String clase = "es.florida.simulador.SimulacionMP";
		try {
			String javaHome = System.getProperty("java.home");
			String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
			String classPath = System.getProperty("java.class.path");
			String className = clase;
			List<String> command = new ArrayList<>();
			command.add(javaBin);
			command.add("-cp");
			command.add(classPath);
			command.add(className);
			command.add(String.valueOf(type));
			command.add(String.valueOf(tiempoInicio));

			ProcessBuilder builder = new ProcessBuilder(command);
			builder.redirectOutput(new File("PROT_MP_" + type + "_n" + ordenProteina + "_" + formatearTiempo(tiempoInicio) + ".sim"));
			Process p = builder.start();

			// Agregamos el nuevo proceso en la posición del bloque.
			bloqueProcesos[posicionBloque] = p;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
     * Formatea un tiempo en milisegundos en una cadena legible.
     *
     * @param tiempoActualMilisegundos tiempo en milisegundos.
     * @return tiempo formateado.
     */
	public static String formatearTiempo(long tiempoActualMilisegundos) {
		long centesimas = (tiempoActualMilisegundos % 1000) / 10; // Obtener las centésimas
		// Formatear la fecha y hora AÑOMESDÍA_HORAMINUTOSEGUNDO
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String fechaFormateada = formatoFecha.format(new Date(tiempoActualMilisegundos));
		// Combinar AÑOMESDÍA_HORAMINUTOSEGUNDO_CENTÉSIMAS
		String tiempoActual = String.format("%s_%02d", fechaFormateada, centesimas);
		return tiempoActual;
	}
	
	/**
     * Formatea una diferencia de tiempo en milisegundos como una cadena en el formato
     * "SEGUNDOS_CENTÉSIMAS".
     *
     * @param diferenciaMilisegundos tiempo en milisegundos a formatear.
     * @return una cadena representando el tiempo en formato "SEGUNDOS_CENTÉSIMAS".
     */
	public static String formatearSegundosCentesimas(long diferenciaMilisegundos) {
        long segundos = diferenciaMilisegundos / 1000;
        long centesimas = (diferenciaMilisegundos % 1000) / 10;

        // Formatear como SEGUNDOS_CENTÉSIMAS
        return String.format("%d_%02d", segundos, centesimas);
    }
	
	/**
     * Calcula la duración en segundos y centésimas desde un tiempo inicial. Ejemplo 2,34.
     *
     * @param tiempoInicio tiempo inicial en nanosegundos.
     * @return duración formateada.
     */
	private String duracionSegundosCentesimas(long tiempoInicio){
		long tiempoFin = System.nanoTime();
		double duracion = (tiempoFin - tiempoInicio) / 1_000_000_000.0; // Convertir a segundos con decimales
		String duracionFormateada = String.format("%.2f", duracion);
		return duracionFormateada + " s";
	}




}
