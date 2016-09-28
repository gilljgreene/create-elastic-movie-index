package readMovieData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class ReadMovieData {

	public static HashMap<String, String> readList(String filepath) {

		HashMap<String, String> listMapping = new HashMap<String, String>();
		File inputfile = new File(filepath);
		try {
			FileReader reader = new FileReader(inputfile);
			BufferedReader bufReader = new BufferedReader(reader);
			String line = bufReader.readLine();
			while (line != null) {
				String[] lineParts = line.split("\\t+");

				if (listMapping.get(lineParts[0]) == null) {
					listMapping.put(lineParts[0], lineParts[1]);
				} else {
					String addText = listMapping.get(lineParts[0]) + ";" + lineParts[1];
					listMapping.put(lineParts[0], addText);
				}

				line = bufReader.readLine();
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMapping;
	}

	public static HashMap<String, String> readPlot(String filepath) {
		HashMap<String, String> listMapping = new HashMap<String, String>();
		File inputfile = new File(filepath);
		try {
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMapping;
	}

	public static HashMap<String, HashMap<String, String>> readAllLists() {

		HashMap<String, HashMap<String, String>> fullDataLists = new HashMap<String, HashMap<String, String>>();
		String[] filenamesSimple = { "genres.list, running-times.list, realease-dates.list", "writers.list",
				"keywords.list" };

		for (String filename : filenamesSimple) {
			HashMap<String, String> mappings = readList(filename);
			String categorytype = filename.substring(0, filename.indexOf(".list"));

			for (Entry<String, String> entry : mappings.entrySet()) {
				HashMap<String, String> item = fullDataLists.get(entry.getKey());
				if (item == null) {
					item = new HashMap<String, String>();
				}
				item.put(categorytype, entry.getValue());
			}

		}

		return fullDataLists;
	}

}
