package entidades;

public enum Figuras_Ritmicas {
	REDONDA("w",4),BLANCA("h",2),NEGRA("q",1),CORCHEA("i",0.5),SEMICORCHEA("s",0.25),FUSA("t",0.125),SEMIFUSA("x",0.0625);
	
	private String nombreJFuge;
	private double duracion;
	
	Figuras_Ritmicas(String nombre, double duracion) {
		this.nombreJFuge = nombre;
		this.duracion = duracion;
	}

	public String getNombreJFuge() {
		return nombreJFuge;
	}

	public double getDuracion() {
		return duracion;
	}
}
