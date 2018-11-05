package funciones_helper;

public class Contador {
	private int valor;

	public Contador() {
		valor = 1;
	}

	public void incrementar() {
		valor++;
	}

	public int getValor() {
		return valor;
	};
	
	public void actualizar() {
		this.valor = 1;
	}
}
