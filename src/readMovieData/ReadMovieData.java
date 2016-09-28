package readMovieData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class ReadMovieData {

	public static HashMap<String, String> readList(String filepath) throws IOException {

		HashMap<String, String> listMapping = new HashMap<String, String>();
		File inputfile = new File(filepath);

		FileReader reader = new FileReader(inputfile);
		BufferedReader bufReader = new BufferedReader(reader);
		String line = bufReader.readLine();
		while (line != null) {
			String[] lineParts = line.split("\\t+");

			if (listMapping.get(lineParts[0]) == null) {
				try {
					listMapping.put(lineParts[0], lineParts[1]);
				} catch (Exception e) {
					System.out.println("ERROR: " + line);
				}

			} else {
				String addText = listMapping.get(lineParts[0]) + ";" + lineParts[1];
				listMapping.put(lineParts[0], addText);
			}

			line = bufReader.readLine();
		}
		bufReader.close();

		return listMapping;
	}

	public static HashMap<String, String> readPlot(String filepath) throws IOException {
		HashMap<String, String> listMapping = new HashMap<String, String>();
		File inputfile = new File(filepath);

		FileReader reader = new FileReader(inputfile);
		BufferedReader bufReader = new BufferedReader(reader);
		String line = bufReader.readLine();
		String name = null;
		String plot = null;
		while (line != null) {
			if (line.startsWith("MV:")) {
				name = line.substring(line.indexOf("MV:")).trim();
			} else if (line.startsWith("PL:")) {
				plot += line.substring(line.indexOf("PL:")).trim();
			} else if (line.startsWith("---------------------")) {
				if (name != null && plot != null) {
					listMapping.put(name, plot);
					name = null;
					plot = null;
				}
			}
			line = bufReader.readLine();
		}
		bufReader.close();

		return listMapping;
	}

	public static HashMap<String, HashMap<String, String>> readAllLists() throws IOException {

		String dirPath = "data/";
		HashMap<String, HashMap<String, String>> fullDataLists = new HashMap<String, HashMap<String, String>>();
		String[] filenamesSimple = { "genres.list", "running-times.list", "release-dates.list", "keywords.list" };
		String plotfileName = "plot.list";

		for (String filename : filenamesSimple) {
			System.out.println("Processing: " + filename);
			HashMap<String, String> mappings = readList(dirPath + filename);
			String categorytype = filename.substring(0, filename.indexOf(".list"));

			for (Entry<String, String> entry : mappings.entrySet()) {
				HashMap<String, String> item = fullDataLists.get(entry.getKey());
				if (item == null) {
					item = new HashMap<String, String>();
				}
				item.put(categorytype, entry.getValue());
				fullDataLists.put(entry.getKey(), item);
			}
		}

		HashMap<String, String> plotMappings = readPlot(dirPath + plotfileName);

		for (Entry<String, String> entry : plotMappings.entrySet()) {
			HashMap<String, String> item = fullDataLists.get(entry.getKey());
			if (item == null) {
				item = new HashMap<String, String>();
			}
			item.put("plot", entry.getValue());
			fullDataLists.put(entry.getKey(), item);
		}

		return fullDataLists;
	}

}
