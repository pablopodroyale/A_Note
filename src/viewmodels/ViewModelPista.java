package viewmodels;

import java.util.ArrayList;

import org.jfugue.pattern.PatternProducer;

import entidades.Instrumentos;
import entidades.MyPattern;
import entidades.PlayerSingleton;
import funciones_helper.Funcion_Helper;
import interfaces.ISonable;

public class ViewModelPista {
	private static final int TEMPO_MAXIMO = Integer.MAX_VALUE;
	private static final int TEMPO_MINIMO = 1;
	private static final int INSTRUMENTO_MIN = 0;
	private static final int INSTRUMENTO_MAX = 127;
	private static final String ERROR_MELODIA_VACIA = null;
	private int pistaID;
	private int idCancion;
	private String nombreCancion;
	private String nombrePista;
	private String tempo;
	private String instrumento;
	private ArrayList<ViewModelNota> notas;
	private MyPattern pattern;

	public ViewModelPista() {
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

	public void setNotas(ArrayList<ViewModelNota> notas) {
		this.notas = notas;
	}

	public void setPattern(MyPattern pattern) {
		this.pattern = pattern;
	}

	public String getNombre() {
		return nombrePista;
	}

	public void setNombre(String nombre) {
		this.nombrePista = nombre;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public String getInstrument() {
		return instrumento;
	}

	public void setInstrument(String instrument) {
		try {
			Funcion_Helper.validarInstrumento(instrument);
			// Funcion_Helper.validarRango(INSTRUMENTO_MIN, INSTRUMENTO_MAX,
			// Instrumentos.valueOf(instrument).getValue());
			this.instrumento = instrument.toUpperCase();
		} catch (RuntimeException re) {
			throw new RuntimeException(re.getMessage());
		}
	}

	@Override
	public String toString() {
		return "ViewModelPista [pistaID=" + pistaID + ", idCancion=" + idCancion + ", nombreCancion=" + nombreCancion
				+ ", nombrePista=" + nombrePista + ", tempo=" + tempo + ", instrumento=" + instrumento + ", notas="
				+ notas + ", pattern=" + pattern + "]";
	}

	public void play(PlayerSingleton player, ArrayList<ViewModelNota> notas) {
		if (notas.isEmpty()) {
			throw new IllegalArgumentException(ERROR_MELODIA_VACIA);
		} else {
			if (!this.instrumento.isEmpty()) {
				this.pattern.add(Funcion_Helper.toJFugueInstrument(instrumento));
			}
			if (!this.tempo.isEmpty()) {
				this.pattern.add(Funcion_Helper.toJfugueTempo(tempo));
			}
			notas.forEach(x -> {
				pattern.add(" " + x.getNombre() + x.getOctava() + x.getFigura() + x.getAlteracion() + " ");
			});
			try {
				player.play(pattern);
				pattern.clear();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public boolean hasNotes() {
		return notas.size() > 0;
	}

	public void listarNotas() {
		notas.forEach(x -> {
			System.out.println(x.toString());
		});

	}

	public int getSize() {
		return notas.size();
	}

	public MyPattern getPattern() {
		this.pattern.clear();
		for (ViewModelNota viewModelNota : notas) {
			pattern.add(viewModelNota.toString());
		}
		return null;
	}

	public void addNota(ViewModelNota modelNota) {
		notas.add(modelNota);

	}

	public ViewModelNota getNotaById(int idNota) {
		int indice = 0;
		ViewModelNota aux;
		ViewModelNota notaVM = null;
		while (indice < notas.size() && notaVM == null) {
			aux = notas.get(indice);
			if (aux.getId() == idNota) {
				notaVM = aux;
			} else {
				indice++;
			}
		}
		return notaVM;
	}

	public void removeNotaById(int idNota) {
		notas.remove(getNotaById(idNota));
	}

	public ArrayList<ViewModelNota> getNotas() {
		return notas;
	}
}
