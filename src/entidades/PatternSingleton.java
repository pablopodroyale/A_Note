package entidades;

import java.io.File;
import java.io.IOException;

import org.jfugue.pattern.Pattern;

public class PatternSingleton extends Pattern {
	private static PatternSingleton instance;

	private PatternSingleton() {

	}

	public static PatternSingleton getInstance() {
		if (instance == null) {
			instance = new PatternSingleton();
		}
		return instance;
	}

	

}
