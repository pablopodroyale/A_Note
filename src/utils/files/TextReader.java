package utils.files;

public interface TextReader {
	boolean isReady();
	String readLine();
	void close();
}
