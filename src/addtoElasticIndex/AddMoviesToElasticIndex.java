package addtoElasticIndex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import readMovieData.ReadMovieData;

public class AddMoviesToElasticIndex {

	public static void addMoviesToElasticIndex() throws IOException {

		String clusterName = "elasticsearch_dev";
		Client client;

		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build();
		client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

		HashMap<String, HashMap<String, String>> movieLists = ReadMovieData.readAllLists();

		Integer counter = 0;

		System.out.println("Running through " + movieLists.size() + " entries");

		for (Entry<String, HashMap<String, String>> movieEntries : movieLists.entrySet()) {

			String title = movieEntries.getKey();
			HashMap<String, String> variables = movieEntries.getValue();
			Map<String, Object> jsonMovie = new HashMap<String, Object>();
			jsonMovie.put("title", title);
			jsonMovie.put("genre", variables.get("genres"));
			jsonMovie.put("plot", variables.get("plot"));
			jsonMovie.put("keywords", variables.get("keywords"));

			String runningTime = variables.get("running-times");
			if (runningTime != null) {
				runningTime = runningTime.replaceAll("\\D+", "");
				try {

					int runTimeInt = Integer.parseInt(runningTime);
					jsonMovie.put("running-times", runTimeInt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			try {
				String json = ow.writeValueAsString(jsonMovie);
				IndexResponse response = client.prepareIndex("moviesindex", "movie", counter + "").setSource(json)
						.execute().actionGet();

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			counter++;

		}
		client.close();

	}

	public static void main(String[] args) throws IOException {

		addMoviesToElasticIndex();
	}

}
