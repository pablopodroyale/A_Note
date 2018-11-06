package opciones;

import java.util.Scanner;

import org.jfugue.pattern.Pattern;

import entidades.AnoteManagger;

public interface IOpcion {

	void ejecutar(AnoteManagger manager, Scanner input, Pattern pattern);
}
