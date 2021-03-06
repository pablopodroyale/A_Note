package opciones;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import Menu.Menu;
import entidades.AnoteManager;
import entidades.Cancion;
import funciones_helper.Contador;
import funciones_helper.Funcion_Helper;
import funciones_helper.Mapper;
import viewmodels.ViewModelPista;
import viewmodels.ViewModelCancion;
import viewmodels.ViewModelNota;

public class OpcionConsola_Componer extends Opcion {
	// Menu inicial
	private static final int OPCION_COMPONER = 1;
	private static final int OPCION_EDITAR = 2;
	// Submenu
	private static final int OPCION_AGREGAR_PISTA = 1;
	private static final int OPCION_ADD_NOTA = 2;
	private static final int OPCION_EDITAR_NOTA_EN_PISTA = 3;
	private static final int OPCION_ELIMINAR_NOTA = 4;
	private static final int OPCION_ELIMINAR_PISTA = 5;
	private static final int OPCION_CAMBIAR_NOMBRE_PISTA = 6;
	private static final int OPCION_CAMBIAR_INSTRUMENTO_PISTA = 7;
	private static final int OPCION_DETALLES_CANCION = 8;
	private static final int OPCION_LISTAR_PISTAS = 9;
	private static final int OPCION_LISTAR_NOTAS = 10;
	private static final int OPCION_SALVAR = 11;
	private static final int OPCION_EXPORTAR = 12;
	private static final int OPCION_REPRODUCIR = 13;
	private static final String MENSAJE_NOMBRE = "Ingrese el nombre de la canci�n";
	private static final String MENSAJE_NOMBRE_INSTRUMENTO = "Ingrese el nombre del instrumento de la lista, tal como figura en may�scula";
	private static final String MENSAJE_TEMPO_CANCION = "Ingrese el tiempo deseado mayor a 0";
	private static final String COMPONER = "Preparado para componer!";
	private static final String DETALLES_NOTAS = "Ingrese detalles de las notas";
	private static final String MENSAJE_NOMBRE_NOTA = "Ingrese el nombre de la nota(DO 'C', Re 'D', Mi 'E', Fa 'F', Sol 'G', Si 'B', Silencio 'r')";
	private static final String MENSAJE_NUMERO_OCTAVA = "Ingrese el numero de octava del 1 al 9";;
	private static final String MENSAJE_NOMBRE_FIGURA = "Ingrese la figura, REDONDA, BLANCA,NEGRA, CORCHEA, SEMICORCHEA, FUSA, SEMIFUSA";
	private static final String MENSAJE_TIPO_ALTERACION = "Ingrese la alteracion, Sotenido '#', bemol 'b', becuadro 'n' ";
	private static final String MENSAJE_PEDIR_ID = "Ingrese el n�mero de la nota a modificar:";
	private static final String ERROR_LISTA_VACIA = "No hay canciones compuestas";
	private static final String MENSAJE_PEDIR_TIPO_PISTA = "Ingrese el nombre de la pista (Verso, estribillo,etc)";
	private static final String MENSAJE_PEDIR_PISTA = "Ingrese el nombre de la pista";
	private static final String ERROR_PISTA_INEXISTENTE = "Error, no existe la pista";
	private static final String ERROR_PISTA_SIN_NOTAS = "Error, no hay notas compuestas";
	private static final String ERROR_CANCION_VACIA = "La canci�n no tiene pistas";

	private Contador contador;

	public OpcionConsola_Componer() {
		this.contador = new Contador();
	}

	@Override
	public void ejecutar(AnoteManager manager, Scanner input) {
		ArrayList<String> canciones = manager.getCanciones();
		Menu menuComponer = new Menu(COMPONER, input);
		// En componer puede agregar notas o editar las que esta componiendo
		menuComponer.register("Componer");
		menuComponer.register("Editar");
		// En editar modifica de alguna cancion
		ViewModelCancion cancionVM = new ViewModelCancion();
		int pOpcion = 0;
		do {
			try {
				// Puede componer
				pOpcion = menuComponer.choice();
				switch (pOpcion) {
				case OPCION_COMPONER:
					pedirDetallesCancion(input, cancionVM, canciones);
					// Pedir notas
					componer(input, cancionVM, manager);
					break;
				case OPCION_EDITAR:
					pedirCancionGuardada(manager, input, cancionVM, canciones);
					componer(input, cancionVM, manager);
				default:
					break;
				}
			} catch (RuntimeException e) {
				throw new RuntimeException(e.getMessage());
			}
		} while (pOpcion != menuComponer.exitValue());
		// manager.save(modelMelodia, notascompuestas);
	}

	private void pedirCancionGuardada(AnoteManager manager, Scanner input, ViewModelCancion cancionVM,
			ArrayList<String> canciones) {
		ArrayList<String> cancionesGuardadas = manager.getCanciones();
		if (canciones.isEmpty()) {
			throw new NullPointerException(ERROR_LISTA_VACIA);
		} else {
			Funcion_Helper.listarCanciones(cancionesGuardadas);
			String sCancion = Funcion_Helper.pedirNombreCancionEdicion("Seleccione una canci�n para editar", input, canciones);
			Cancion cancion = manager.loadCancion(sCancion);
			Mapper.MapToVm(cancion, cancionVM);
		}

	}

	private void componer(Scanner input, ViewModelCancion cancionVM, AnoteManager manager) {
		int pOpcion = 0;
		Menu menuNota = new Menu(DETALLES_NOTAS, input);
		ViewModelPista pistaVM;
		String sPista;
		menuNota.register("Agregar Pista");
		menuNota.register("Agregar nota a pista");
		menuNota.register("Editar nota");
		menuNota.register("Eliminar nota");
		menuNota.register("Eliminar Pista");
		menuNota.register("Cambiar nombre Pista");
		menuNota.register("Cambiar instrumento Pista");
		menuNota.register("Detalles canci�n");
		menuNota.register("Listar Pistas");
		menuNota.register("Listar notas");
		menuNota.register("Guardar");
		menuNota.register("Exportar");
		menuNota.register("Reproducir");
		do {
			try {

				pOpcion = menuNota.choice();
				switch (pOpcion) {
				case OPCION_AGREGAR_PISTA:
					agregarPista(input, cancionVM);
					break;
				case OPCION_ADD_NOTA:
					listarPistas(cancionVM.getPistas());
					sPista = Funcion_Helper.pedirPista(input, MENSAJE_PEDIR_PISTA, cancionVM.getPistas());
					pistaVM = getPista(sPista, cancionVM.getPistas());
					if (pistaVM == null) {
						System.out.println(ERROR_PISTA_INEXISTENTE);
					} else {
						addNota(input, pistaVM, cancionVM);
					}
					break;
				case OPCION_EDITAR_NOTA_EN_PISTA:
					listarPistas(cancionVM.getPistas());
					sPista = Funcion_Helper.pedirPista(input, MENSAJE_PEDIR_PISTA, cancionVM.getPistas());
					pistaVM = getPista(sPista, cancionVM.getPistas());
					if (pistaVM == null) {
						System.out.println(ERROR_PISTA_INEXISTENTE);
					} else if (!pistaVM.hasNotes()) {
						System.out.println(ERROR_PISTA_SIN_NOTAS);
					} else {
						editarNota(input, pistaVM);
					}
					break;
				case OPCION_ELIMINAR_NOTA:
					sPista = Funcion_Helper.pedirNuevaPista(input, MENSAJE_PEDIR_PISTA, cancionVM.getPistas());
					pistaVM = getPista(sPista, cancionVM.getPistas());
					if (pistaVM == null) {
						System.out.println(ERROR_PISTA_INEXISTENTE);
					} else if (pistaVM.hasNotes()) {
						System.out.println(ERROR_PISTA_SIN_NOTAS);
						// editarNota(input, notascompuestas);
					} else {
						pistaVM.listarNotas();
						int idNota = Funcion_Helper.pedirIdNota(MENSAJE_PEDIR_ID, input, pistaVM);
						pistaVM.removeNotaById(idNota);
					}
					break;
				case OPCION_ELIMINAR_PISTA:
					eliminarPista(input, cancionVM);
					break;
				case OPCION_CAMBIAR_NOMBRE_PISTA:
					cambiarNombrePista(input, cancionVM);
					break;
				case OPCION_CAMBIAR_INSTRUMENTO_PISTA:
					cambiarInstrumentoPista(input, cancionVM);
					break;
				case OPCION_DETALLES_CANCION:
					cancionVM.listar();
					break;
				case OPCION_LISTAR_PISTAS:
					listarPistas(cancionVM.getPistas());
					break;
				case OPCION_LISTAR_NOTAS:
					listarPistas(cancionVM.getPistas());
					sPista = Funcion_Helper.pedirPista(input, MENSAJE_PEDIR_PISTA, cancionVM.getPistas());
					pistaVM = getPista(sPista, cancionVM.getPistas());
					if (pistaVM == null) {
						System.out.println(ERROR_PISTA_INEXISTENTE);
					} else if (!pistaVM.hasNotes()) {
						System.out.println(ERROR_PISTA_SIN_NOTAS);
					} else {
						pistaVM.listarNotas();
					}
					break;
				case OPCION_SALVAR:
					if (!cancionVM.hasPistas()) {
						System.out.println(ERROR_CANCION_VACIA);
					} else {
						manager.save(cancionVM);
					}
					break;
				case OPCION_EXPORTAR:
					if (!cancionVM.hasPistas()) {
						System.out.println(ERROR_CANCION_VACIA);
					} else {
						manager.exportar(cancionVM);
					}
					break;
			case OPCION_REPRODUCIR:
					cancionVM.play();
					break;
				default:
					break;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					if (pOpcion == menuNota.exitValue()) {
						if (!cancionVM.IsEmpty()) {
							String sRes = Funcion_Helper.pedirString("Desea guardar la canci�n? Si/No", input);
							if (sRes.equalsIgnoreCase("si")) {
								manager.save(cancionVM);
							}
						}
					}
					System.out.println("Presione enter para continuar");
					System.in.read();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} while (pOpcion != menuNota.exitValue());
	}

	private void cambiarInstrumentoPista(Scanner input, ViewModelCancion cancionVM) {
		listarPistas(cancionVM.getPistas());
		String sPista = Funcion_Helper.pedirPista(input, MENSAJE_PEDIR_PISTA, cancionVM.getPistas());
		ViewModelPista pistaVM = getPista(sPista, cancionVM.getPistas());
		String nombreNuevo = Funcion_Helper.pedirPista(input, MENSAJE_PEDIR_PISTA, cancionVM.getPistas());
		pistaVM.setInstrument(nombreNuevo);

	}

	private void cambiarNombrePista(Scanner input, ViewModelCancion cancionVM) {
		listarPistas(cancionVM.getPistas());
		String sPista = Funcion_Helper.pedirPista(input, MENSAJE_PEDIR_PISTA, cancionVM.getPistas());
		ViewModelPista pistaVM = getPista(sPista, cancionVM.getPistas());
		String nombreNuevo = Funcion_Helper.pedirPista(input, MENSAJE_PEDIR_PISTA, cancionVM.getPistas());
		pistaVM.setNombre(nombreNuevo);
	}

	private void eliminarPista(Scanner input, ViewModelCancion cancionVM) {
		String sPista = Funcion_Helper.pedirPista(input, MENSAJE_PEDIR_PISTA, cancionVM.getPistas());
		cancionVM.deletePistaById(sPista);
	}

	private ViewModelPista getPista(String sPista, ArrayList<ViewModelPista> pistasVM) {
		ViewModelPista aux;
		ViewModelPista pista = null;
		int indice = 0;
		while (indice < pistasVM.size() && pista == null) {
			aux = pistasVM.get(indice);
			if (aux.getNombre().equalsIgnoreCase(sPista)) {
				pista = aux;
			} else {
				indice++;
			}
		}
		return pista;
	}

	private void listarPistas(ArrayList<ViewModelPista> pistasVM) {
		int i = 0;
		if (pistasVM.size() == 0) {
			System.out.println(ERROR_CANCION_VACIA);
		}
		for (ViewModelPista viewModelPista : pistasVM) {
			System.out.println((i += 1) + "." + viewModelPista.getNombre());
		}

	}

	private boolean hasNotes(ArrayList<ViewModelPista> pistasVM) {
		int indice = 0;
		boolean seguir = true;
		while (indice < pistasVM.size() && seguir == true) {
			if (pistasVM.get(indice).hasNotes()) {
				seguir = false;
			} else {
				indice++;
			}
		}
		return !seguir;
	}

	private void addNota(Scanner input, ViewModelPista pistaVM, ViewModelCancion cancionVM) {
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
		pistaVM.addNota(modelNota);
		// cancionVM.addPista(pistaVM);
	}

	private void agregarPista(Scanner input, ViewModelCancion cancionVM) {
		ViewModelPista pistaVM = new ViewModelPista();
		String tipoPista = Funcion_Helper.pedirNuevaPista(input, MENSAJE_PEDIR_TIPO_PISTA, cancionVM.getPistas());
		pistaVM.setNombre(tipoPista);
		pistaVM.setTempo(cancionVM.getTempo());
		Funcion_Helper.listarInstrumentos();
		String instrumento = Funcion_Helper.pedirInstrumento(MENSAJE_NOMBRE_INSTRUMENTO, input);
		pistaVM.setInstrument(instrumento);
		cancionVM.addPista(pistaVM);
	}

	private void editarNota(Scanner input, ViewModelPista pistaVM) {
		if (!pistaVM.hasNotes()) {
			throw new IllegalArgumentException(ERROR_LISTA_VACIA);
		} else {
			pistaVM.listarNotas();
			int idNota = Funcion_Helper.pedirIdNota(MENSAJE_PEDIR_ID, input, pistaVM);
			ViewModelNota notaVM = pistaVM.getNotaById(idNota);
			editarNota(notaVM, input);
		}
	}

	private void deleteNota(Scanner input, ViewModelPista pistaVM, ArrayList<ViewModelPista> pistasVM) {
		pistaVM.listarNotas();
		int idNota = Funcion_Helper.pedirIdNota(MENSAJE_PEDIR_ID, input, pistaVM);
		pistaVM.removeNotaById(idNota);
		// pistaVM.remove(notaVM);
		editarIds(pistaVM.getNotas());

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

	/*
	 * private ViewModelNota getNotaById(int notaId, ArrayList<ViewModelNota>
	 * notascompuestas) { int indice = 0; ViewModelNota aux; ViewModelNota notaVM =
	 * null; while (indice < notascompuestas.size() && notaVM == null) { aux =
	 * notascompuestas.get(indice); if (aux.getId() == notaId) { notaVM = aux; }
	 * else { indice++; } } return notaVM; }
	 * 
	 * 
	 * private void listarNotas(ArrayList<ViewModelNota> notascompuestas) {
	 * System.out.println("Notas de la melodia:"); notascompuestas.forEach(x ->
	 * System.out.println(x.getId() + ":" + x.toString())); }
	 */

	private void pedirDetallesCancion(Scanner input, ViewModelCancion cancionVM, ArrayList<String> canciones) {
		String nombre = Funcion_Helper.pedirNombreCancion(MENSAJE_NOMBRE, input, canciones);
		cancionVM.setNombreCancion(nombre);
		String tempo = Funcion_Helper.pedirTempo(MENSAJE_TEMPO_CANCION, input);
		cancionVM.setTempo(tempo);
	}

}
