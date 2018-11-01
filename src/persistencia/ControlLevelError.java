package persistencia;

public enum ControlLevelError {
	IGNORE_ERRORS("ControlLevelError"), LOAD_UNTIL_ERROR("ControlLevelError"), NO_LOAD_WITH_ERRORS("ControlLevelError");
	private String levelError;

	ControlLevelError(String level) {
		this.levelError = level;
	}

	public String getLevelError() {
		return levelError;
	}

}
