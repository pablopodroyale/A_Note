package utils_textfiles;

public interface TextReader {
	boolean isReady();
	String readLine();
	void close();
}
