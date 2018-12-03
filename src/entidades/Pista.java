package entidades;

import java.util.ArrayList;

import org.jfugue.pattern.PatternProducer;

import funciones_helper.Contador;
import funciones_helper.Funcion_Helper;
import interfaces.ISonable;

public class Pista implements ISonable {

	public void setNotas(ArrayList<Nota> notas) {
		this.notas = notas;
	}

	private static final int TEMPO_MAXIMO = Integer.MAX_VALUE;
	private static final int TEMPO_MINIMO = 1;
	private static final String MASCARA_TEMPO = " T%s ";
	private static final String MASCARA_INSTRUMENT = " I[%s] ";
	private static final int INSTRUMENTO_MIN = 0;
	private static final int INSTRUMENTO_MAX = 127;
	private static final String TEMPO_CONST_STRING = "Tiempo";
	private static final String INSTRUMENT = "Instrumento";
	private static final String CANCIONES = "Canciones";
	private static final String ERROR_NOTA_INVALIDA = "Error, la nota es invalida";
	private static final String ERROR_MELODIA_VACIA = "No, se puede reproducir, la melodía no tiene notas";
	private int pistaID;
	private int idCancion;
	private String nombreCancion;
	private String nombrePista;
	private String tempo;
	private String instrumento;
	private ArrayList<Nota> notas;
	private Contador contadorNotas;
	private MyPattern pattern;

	
	public Pista() {
		this("");
	}

	/***
	 * Orden de parametros: nombre, instrument, tempo
	 * 
	 * @param nombrePista
	 */
	public Pista(String nombrePista) {
		this(nombrePista, "", "");
	}

	public Pista(String nombrePista, String instrumento, String tempo) {
		this.nombrePista = nombrePista;
		this.instrumento = instrumento;
		this.contadorNotas = new Contador();
		this.pattern = new MyPattern();
		this.notas = new ArrayList<>();

	}

	public String getNombreCancion() {
		return nombreCancion;
	}

	public void setNombreCancion(String nombreCancion) {
		this.nombreCancion = nombreCancion;
	}

	public int getPistaID() {
		return pistaID;
	}

	public void setPistaID(int pistaID) {
		this.pistaID = pistaID;
	}

	public int getIdCancion() {
		return idCancion;
	}

	public void setIdCancion(int idCancion) {
		this.idCancion = idCancion;
	}

	public String getNombrePista() {
		return nombrePista;
	}

	public void setNombrePista(String nombrePista) {
		this.nombrePista = nombrePista;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		try {
			Funcion_Helper.validarRango(TEMPO_MINIMO, TEMPO_MAXIMO, Integer.parseInt(tempo));
			this.tempo = tempo;
			// this.tempo = String.format(MASCARA_TEMPO, tempo);
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}

	}

	@Override
	public void play(PlayerSingleton player) {
		if (notas.isEmpty()) {
			throw new IllegalArgumentException(ERROR_MELODIA_VACIA);
		} else {
			if (!this.instrumento.isEmpty()) {
				this.pattern.add(toJFugueInstrument(instrumento));
			}
			if (!this.tempo.isEmpty()) {
				this.pattern.add(toJfugueTempo(tempo));
			}

			notas.forEach(x -> {
				pattern.add(" " + x.getNombre() + x.getOctava() + x.getFigura() + x.getAlteracion() + " ");
			});
			try {
				player.play(pattern);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	private String toJfugueTempo(String tempo2) {
		return String.format(MASCARA_TEMPO, tempo2);
	}

	private String toJFugueInstrument(String instrument2) {
		return String.format(MASCARA_INSTRUMENT, instrument2);
	}

	public void setNote(Nota nota) {
		asignarIdANota(nota);
		this.notas.add(nota);
	}

	private void asignarIdANota(Nota nota) {
		nota.setId(contadorNotas.getValor());
		contadorNotas.incrementar();
	}

	public int getTempoMaximo() {
		return TEMPO_MAXIMO;
	}

	public int getTempoMinimo() {
		return TEMPO_MINIMO;
	}

	public String getMascaraTempo() {
		return MASCARA_TEMPO;
	}

	public String getMascaraInstrument() {
		return MASCARA_INSTRUMENT;
	}

	public int getInstrumentoMin() {
		return INSTRUMENTO_MIN;
	}

	public int getInstrumentoMax() {
		return INSTRUMENTO_MAX;
	}

	public String getInstrumentConst() {
		return INSTRUMENT;
	}

	public String getCancionesConst() {
		return CANCIONES;
	}

	public ArrayList<Nota> getNotas() {
		return notas;
	}

	public String getNombre() {
		return nombrePista;
	}

	public String getTempoConstString() {
		return TEMPO_CONST_STRING;
	}

	public void listarNotas() {
		notas.forEach(x -> System.out.println(x.toStringConId()));
	}

	public void setNombre(String nombrePista) {
		this.nombrePista = nombrePista;

	}

	public void updateNota(String idNota, String nombreNota, String octava, String figura, String alteracion) {
		try {
			Funcion_Helper.validarRango(0, notas.size(), Integer.parseInt(idNota));
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
		Nota nota = getNotaById(idNota);
		nota.setNombre(nombreNota);
		nota.setOctava(octava);
		nota.setFigura(figura);
		nota.setAlteracion(alteracion);
	}

	private Nota getNotaById(String idNota) {
		Nota aux;
		Nota nota = null;
		int indice = 0;
		int idNot = Integer.parseInt(idNota);
		while (indice < notas.size() && nota == null) {
			aux = notas.get(indice);
			if (aux.getId() == idNot) {
				nota = aux;
			} else {
				indice++;
			}
		}
		if (nota == null) {
			throw new IllegalArgumentException(ERROR_NOTA_INVALIDA);
		}
		return nota;
	}

	public void removeNotaById(String idNota) {
		notas.remove(getNotaById(idNota));
	}

	@Override
	public String toString() {
		return "Pista [pistaID=" + pistaID + ", idCancion=" + idCancion + ", nombreCancion=" + nombreCancion
				+ ", nombrePista=" + nombrePista + ", tempo=" + tempo + ", instrumento=" + instrumento + ", notas="
				+ notas + ", contadorNotas=" + contadorNotas + ", pattern=" + pattern + "]";
	}

	public void llenarNotas(ArrayList<Nota> notas) {
		for (Nota nota : notas) {
			this.notas.add(nota);
		}
	}

	public void setNote(int Id, String nombreNota, String octava, String figura, String alteracion) {
		Nota nota = new Nota(Id, nombreNota, octava, figura, alteracion);
		nota.setId(contadorNotas.getValor());
		notas.add(nota);
	}

	public PatternProducer getPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addNotas(ArrayList<Nota> notas2) {
		for (Nota nota : notas2) {
			this.notas.add(nota);
		}

	}

}
