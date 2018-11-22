package opciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Menu.Menu;
import entidades.AnoteManager;
import funciones_helper.Contador;
import funciones_helper.Funcion_Helper;
import interfaces.RepoMelodias;
import viewmodels.ViewModelMelodia;
import viewmodels.ViewModelNota;

public class Opcion_Componer_Melodia extends Opcion {
	private static final int OPCION_COMPONER_Y_ADD = 1;
	private static final int OPCION_ELIMINAR_NOTA = 3;
	private static final int OPCION_EDITAR_NOTA = 2;
	private static final int OPCION_LISTAR = 4;
	private static final int OPCION_GUARDAR = 5;
	private static final int OPCION_COMPONER = 1;
	private static final String MENSAJE_NOMBRE = "Ingrese el nombre de la melodia";
	private static final String MENSAJE_NOMBRE_INSTRUMENTO = "Ingrese el nombre del instrumento de la lista, tal como figura en mayúscula";
	private static final String MENSAJE_TEMPO_MELODIA = "Ingrese el tiempo deseado mayor a 0";
	private static final String COMPONER = "Preparado para componer!";
	private static final String ERROR_OPCION_INVALIDA = "Opciones:";
	private static final String DETALLES_NOTAS = "Ingrese detalles de las notas";
	private static final String MENSAJE_NOMBRE_NOTA = "Ingrese el nombre de la nota(DO 'C', Re 'D', Mi 'E', Fa 'F', Sol 'G', Si 'B', Silencio 'r')";
	private static final String MENSAJE_NUMERO_OCTAVA = "Ingrese el numero de octava del 1 al 9";;
	private static final String MENSAJE_NOMBRE_FIGURA = "Ingrese la figura, REDONDA, BLANCA,NEGRA, CORCHEA, SEMICORCHEA, FUSA, SEMIFUSA";
	private static final String MENSAJE_TIPO_ALTERACION = "Ingrese la alteracion, Sotenido '#', bemol 'b', becuadro 'n' ";
	private static final String MENSAJE_PEDIR_ID = "Ingrese el número de la nota a modificar:";
	private static final String ERROR_LISTA_VACIA = null;

	private Contador contador;

	public Opcion_Componer_Melodia(RepoMelodias repositorioMelodia) {
		super(repositorioMelodia);
		this.contador = new Contador();
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		ArrayList<String> canciones = repositoryMelodia.getCanciones();
		Menu menuComponer = new Menu(COMPONER, input);
		// En componer puede agregar notas o editar las que esta componiendo
		menuComponer.register("Componer");
		// En editar modifica de alguna cancion
		ViewModelMelodia modelMelodia = new ViewModelMelodia();
		ViewModelNota modelNota = null;
		ArrayList<ViewModelNota> notascompuestas = new ArrayList<>();
		int pOpcion = 0;
		do {
			try {
				// Puede componer
				pOpcion = menuComponer.choice();
				if (pOpcion == OPCION_COMPONER_Y_ADD) {
					pedirDetallesMelodia(input, modelMelodia, canciones);
					// Pedir notas
					agregarOEditar(input, modelNota, modelMelodia, notascompuestas, manager);
				} else if (pOpcion == menuComponer.exitValue() && notascompuestas.size() > 0) {
					String respuesta = Funcion_Helper.pedirString("Desea Guardar la melodia, SI/No?", input);
					if (respuesta.toLowerCase().equals("si")) {
						manager.save(modelMelodia, notascompuestas);
					}
				}
			} catch (RuntimeException e) {

			}

		} while (pOpcion != menuComponer.exitValue());
		// manager.save(modelMelodia, notascompuestas);
	}

	private void agregarOEditar(Scanner input, ViewModelNota modelNota, ViewModelMelodia modelMelodia,
			ArrayList<ViewModelNota> notascompuestas, AnoteManager manager) {
		int pOpcion = 0;
		Menu menuNota = new Menu(DETALLES_NOTAS, input);
		menuNota.register("Agregar nota");
		menuNota.register("Editar nota");
		menuNota.register("Eliminar nota");
		menuNota.register("Listar notas");
		menuNota.register("Guardar");
		do {
			try {
				pOpcion = menuNota.choice();
				switch (pOpcion) {
				case OPCION_COMPONER_Y_ADD:
					componerYAgregarNota(input, notascompuestas);
					break;
				case OPCION_EDITAR_NOTA:
					if (notascompuestas.isEmpty()) {
						System.out.println("No hay notas compuestas");
					} else {
						editarNota(input, notascompuestas);
					}
					break;
				case OPCION_LISTAR:
					if (notascompuestas.isEmpty()) {
						System.out.println("No hay notas compuestas");
					} else {
						listarNotas(notascompuestas);
					}
					break;
				case OPCION_ELIMINAR_NOTA:
					if (notascompuestas.isEmpty()) {
						System.out.println("No hay notas compuestas");
					} else {
						deleteNota(input, notascompuestas);
					}
					break;
				case OPCION_GUARDAR:
					if (notascompuestas.isEmpty()) {
						System.out.println("No hay notas compuestas");
					} else {
						manager.save(modelMelodia, notascompuestas);
					}
					break;
				default:
					break;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					System.out.println("Presione enter para continuar");

					System.in.read();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} while (pOpcion != menuNota.exitValue());
	}

	private void editarNota(Scanner input, ArrayList<ViewModelNota> notascompuestas) {
		if (notascompuestas.size() == 0) {
			throw new IllegalArgumentException(ERROR_LISTA_VACIA);
		} else {
			listarNotas(notascompuestas);
			String idNota = Funcion_Helper.pedirIdNota(MENSAJE_PEDIR_ID, input, notascompuestas.size());
			ViewModelNota notaVM = getNotaById(Integer.parseInt(idNota), notascompuestas);
			editarNota(notaVM, input);
		}
	}

	private void deleteNota(Scanner input, ArrayList<ViewModelNota> notascompuestas) {
		if (notascompuestas.size() == 0) {
			throw new IllegalArgumentException(ERROR_LISTA_VACIA);
		} else {
			listarNotas(notascompuestas);
			String idNota = Funcion_Helper.pedirIdNota(MENSAJE_PEDIR_ID, input, notascompuestas.size());
			ViewModelNota notaVM = getNotaById(Integer.parseInt(idNota), notascompuestas);
			notascompuestas.remove(notaVM);
			editarIds(notascompuestas);
		}

	}

	private void editarNota(ViewModelNota notaVM, Scanner input) {
		String nombreNota = Funcion_Helper.pedirNombreNota(MENSAJE_NOMBRE_NOTA, input);
		notaVM.setNombre(nombreNota);
		String octava = Funcion_Helper.pedirOctava(MENSAJE_NUMERO_OCTAVA, input);
		notaVM.setOctava(octava);
		String figura = Funcion_Helper.pedirFigura(MENSAJE_NOMBRE_FIGURA, input);
		notaVM.setFigura(figura);
		String alteracion = Funcion_Helper.pedirAlteracion(MENSAJE_TIPO_ALTERACION, input);
		notaVM.setAlteracion(alteracion);
	}

	private void editarIds(ArrayList<ViewModelNota> notascompuestas) {
		this.contador.actualizar();
		for (ViewModelNota notaVM : notascompuestas) {
			notaVM.setId(contador.getValor());
			contador.incrementar();
		}

	}

	private ViewModelNota getNotaById(int notaId, ArrayList<ViewModelNota> notascompuestas) {
		int indice = 0;
		ViewModelNota aux;
		ViewModelNota notaVM = null;
		while (indice < notascompuestas.size() && notaVM == null) {
			aux = notascompuestas.get(indice);
			if (aux.getId() == notaId) {
				notaVM = aux;
			} else {
				indice++;
			}
		}
		return notaVM;
	}

	private void componerYAgregarNota(Scanner input, ArrayList<ViewModelNota> notascompuestas) {
		ViewModelNota modelNota;
		modelNota = new ViewModelNota();
		String nombreNota = Funcion_Helper.pedirNombreNota(MENSAJE_NOMBRE_NOTA, input);
		modelNota.setNombre(nombreNota);
		String octava = Funcion_Helper.pedirOctava(MENSAJE_NUMERO_OCTAVA, input);
		modelNota.setOctava(octava);
		String figura = Funcion_Helper.pedirFigura(MENSAJE_NOMBRE_FIGURA, input);
		modelNota.setFigura(figura);
		String alteracion = Funcion_Helper.pedirAlteracion(MENSAJE_TIPO_ALTERACION, input);
		modelNota.setAlteracion(alteracion);
		modelNota.setId(contador.getValor());
		contador.incrementar();
		notascompuestas.add(modelNota);
	}

	private void listarNotas(ArrayList<ViewModelNota> notascompuestas) {
		System.out.println("Notas de la melodia:");
		int cont = 0;
		notascompuestas.forEach(x -> System.out.println(x.getId() + ":" + x.toString()));
	}

	private void pedirDetallesMelodia(Scanner input, ViewModelMelodia modelMelodia, ArrayList<String> canciones) {
		String nombre = Funcion_Helper.pedirNombreCancion(MENSAJE_NOMBRE, input, canciones);
		modelMelodia.setNombre(nombre);
		Funcion_Helper.listarInstrumentos();
		String instrument = Funcion_Helper.pedirInstrumento(MENSAJE_NOMBRE_INSTRUMENTO, input);
		modelMelodia.setInstrument(instrument);
		String tempo = Funcion_Helper.pedirTempo(MENSAJE_TEMPO_MELODIA, input);
		modelMelodia.setTempo(tempo);
	}

}
