package utils.files;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Section {
	private static final String COMENT = ";";
	private String name;
	private HashMap<String, String> items;
	private String line;

	public Section(String name) {
		setName(name);
		this.items = new LinkedHashMap<>();
	}

	private void setName(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public String getValue(String value) {
		return items.get(value);

	}

	public void setItem(String item) {
		this.items.put(COMENT, item);
	}

	public void setItem(String key, String value) {
		items.put(key, value);
	}

	public void list() {
		items.forEach((key, value) -> System.out.println(String.format("%s : %s ", key, value)));
	}

	public void save(TextWriter writer, String fileDestiny) {
		
		items.forEach((k, v) -> {
			line = (k + "=" + v.trim());
			writer.writeLine(line);
			writer.newLine();
			line = "";
		});
	}

	public boolean remove(String value) {
		return items.keySet().remove(value);
	}

	public void updateValue(String attribute, String value) {
		items.replace(attribute, value);
		
	}

}
