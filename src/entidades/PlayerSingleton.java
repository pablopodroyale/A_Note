package entidades;

import org.jfugue.player.Player;

public class PlayerSingleton extends Player {
	private static PlayerSingleton instance;

	private PlayerSingleton() {

	}
	
	public static PlayerSingleton getInstance() {
		if (instance == null) {
			instance = new PlayerSingleton();
		}
		return instance;
	}

}
