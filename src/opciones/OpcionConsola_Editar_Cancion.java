package opciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Menu.Menu;
import entidades.AnoteManager;
import entidades.Pista;
import entidades.PlayerSingleton;
import funciones_helper.Contador;
import funciones_helper.Funcion_Helper;
import funciones_helper.Mapper;
import interfaces.RepoMelodias;
import repository.RepoCancionesMixto;
import viewmodels.ViewModelPista;
import viewmodels.ViewModelNota;

public class OpcionConsola_Editar_Cancion extends Opcion {
	private static final int OPCION_EDITAR = 1;
	private static final int OPCION_EDITAR_NOMBRE = 1;
	private static final int OPCION_EDITAR_INSTRUMENTO = 2;
	
	private static final int OPCION_EDITAR_NOTA = 4;
	private static final int OPCION_ELIMINAR_NOTA = 5;
	private static final int OPCION_LISTAR_NOTAS = 6;
	private static final int OPCION_LISTAR_DETALLES = 7;
	private static final int OPCION_GUARDAR = 8;
	private static final int OPCION_REPRODUCIR = 9;
	private static final String MENSAJE_INSTRUMENTO_MELODIA = "Ingrese el instrumento deseado de la lista";
	private static final String MENSAJE_NOMBRE = "Ingrese el nombre de la melodia";
	private static final String MENSAJE_NOMBRE_INSTRUMENTO = "Ingrese el nombre del instrumento de la lista, tal como figura en mayúscula";
	private static final String MENSAJE_TEMPO_MELODIA = "Ingrese el tiempo deseado mayor a 0";
	private static final String EDITAR = "Edición";
	private static final String DETALLES_NOTAS = "Ingrese detalles de las notas";
	private static final String MENSAJE_NOMBRE_NOTA = "Ingrese el nombre de la nota(DO 'C', Re 'D', Mi 'E', Fa 'F', Sol 'G', Si 'B', Silencio 'r')";
	private static final String MENSAJE_NUMERO_OCTAVA = "Ingrese el numero de octava del 1 al 9";;
	private static final String MENSAJE_NOMBRE_FIGURA = "Ingrese la figura, REDONDA, BLANCA,NEGRA, CORCHEA, SEMICORCHEA, FUSA, SEMIFUSA";
	private static final String MENSAJE_TIPO_ALTERACION = "Ingrese la alteracion, Sotenido '#', bemol 'b', becuadro 'n' ";
	private static final String MENSAJE_PEDIR_ID = "Ingrese el número de la nota a modificar:";
	
	// private static final String ERROR_LISTA_VACIA = null;
	private Contador contador;
	private ArrayList<String> canciones;

	public OpcionConsola_Editar_Cancion() {
		this.contador = new Contador();
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		/*
		Menu menuEditar = new Menu(EDITAR, input);
		// En componer puede agregar notas o editar las que esta componiendo
		menuEditar.register("Editar");
		// En editar modifica de alguna cancion
		ViewModelPista modelMelodia = new ViewModelPista();
		ViewModelNota modelNota = null;
		ArrayList<ViewModelNota> notascompuestas = new ArrayList<>();
		int pOpcion = 0;
		do {
			try {
				// Puede componer
				pOpcion = menuEditar.choice();
				switch (pOpcion) {
				case OPCION_EDITAR:
					editarMelodia(input, modelMelodia, modelNota, canciones, manager, notascompuestas);
					break;
				default:
					break;
				}
			} catch (RuntimeException e) {
				throw new RuntimeException(e.getMessage());
			}
		} while (pOpcion != menuEditar.exitValue());
	}

	private void editar(Scanner input, ViewModelNota modelNota, ViewModelPista modelMelodia,
			ArrayList<ViewModelNota> notascompuestas, AnoteManager manager) {
		int pOpcion = 0;
		Menu menuNota = new Menu(DETALLES_NOTAS, input);
		menuNota.register("Editar nombre canción");
		menuNota.register("Editar tiempo canción");
		menuNota.register("Editar nombre pista");
		menuNota.register("Editar Instrumento pista");
		menuNota.register("Eliminar pista");
		menuNota.register("Editar nota");
		menuNota.register("Eliminar nota");
		menuNota.register("Detalles cancion");
		menuNota.register("Listar notas");
		menuNota.register("Listar pistas");
		menuNota.register("Guardar");
		menuNota.register("Exportar");
		menuNota.register("Reproducir");
		do {
			try {
				pOpcion = menuNota.choice();
				switch (pOpcion) {
				case OPCION_EDITAR_NOMBRE:
					// TODO
					editarNombre(input, modelMelodia);
					break;
				case OPCION_EDITAR_INSTRUMENTO:
					editarInstrumento(input, modelMelodia, manager);
					break;
				case OPCION_EDITAR_TEMPO:
					if (notascompuestas.isEmpty()) {
						System.out.println("No hay notas compuestas");
					} else {
						editarTempo(input, modelMelodia, manager);
					}
					break;
				case OPCION_EDITAR_NOTA:
					if (notascompuestas.isEmpty()) {
						System.out.println("No hay notas compuestas");
					} else {
						editarNota(input, notascompuestas);
					}
					break;
				case OPCION_ELIMINAR_NOTA:
					if (notascompuestas.isEmpty()) {
						System.out.println("No hay notas compuestas");
					} else {
						deleteNota(input, notascompuestas);
					}
					break;
				case OPCION_LISTAR_NOTAS:
					if (notascompuestas.isEmpty()) {
						System.out.println("No hay notas compuestas");
					} else {
						listarNotas(notascompuestas);
					}
					break;
				case OPCION_LISTAR_DETALLES:
					listarDetalles(modelMelodia);
					break;
				case OPCION_GUARDAR:
					if (notascompuestas.isEmpty()) {
						System.out.println("No hay notas compuestas");
					} else {
						manager.save(modelMelodia, notascompuestas);
					}
					break;
				case OPCION_REPRODUCIR:
					modelMelodia.play(PlayerSingleton.getInstance(), notascompuestas);
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

	private void listarDetalles(ViewModelPista modelMelodia) {
		System.out.println(modelMelodia.toString());

	}

	private void editarTempo(Scanner input, ViewModelPista modelMelodia, AnoteManager manager) {
		Funcion_Helper.listarInstrumentos();
		String tempo = Funcion_Helper.pedirTempo(MENSAJE_TEMPO_MELODIA, input);
		modelMelodia.setTempo(tempo);
	}

	private void editarInstrumento(Scanner input, ViewModelPista modelMelodia, AnoteManager manager) {
		Funcion_Helper.listarInstrumentos();
		String instrument = Funcion_Helper.pedirInstrumento(MENSAJE_INSTRUMENTO_MELODIA, input);
		modelMelodia.setInstrument(instrument);
	}

	private void editarNombre(Scanner input, ViewModelPista modelMelodia) {
		System.out.println("TODO");

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

	private void editar(Scanner input, ArrayList<ViewModelNota> notascompuestas) {
		ViewModelNota modelNota = new ViewModelNota();
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

	private int incrementar(int cont) {
		return cont += 1;
	}

	private void editarMelodia(Scanner input, ViewModelPista modelMelodia, ViewModelNota modelNota,
			ArrayList<String> canciones, AnoteManager manager, ArrayList<ViewModelNota> notascompuestas) {
		repositoryMelodia.listarMelodias();
		System.out.println("Seleccione una melodía para editar:");
		String nombre = Funcion_Helper.pedirNombreCancionEdicion(MENSAJE_NOMBRE, input, canciones);
		Pista melodia = repositoryMelodia.loadMelodia(nombre);
		Mapper.MapToVm(melodia, modelMelodia, notascompuestas);
		editar(input, modelNota, modelMelodia, notascompuestas, manager);

		modelMelodia.setNombre(nombre);
		Funcion_Helper.listarInstrumentos();
		String instrument = Funcion_Helper.pedirInstrumento(MENSAJE_NOMBRE_INSTRUMENTO, input);
		modelMelodia.setInstrument(instrument);
		String tempo = Funcion_Helper.pedirTempo(MENSAJE_TEMPO_MELODIA, input);
		modelMelodia.setTempo(tempo);
		 */
	}
	
}
