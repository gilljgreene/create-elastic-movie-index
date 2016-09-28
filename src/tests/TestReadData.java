package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;

import readMovieData.ReadMovieData;

public class TestReadData {

	@Test
	public void testReadRunningTimes() throws IOException {

		HashMap<String, String> runningTimes = ReadMovieData.readList("src/tests/fixtures/testreadrunningtimes.list");
		//fail();
		Integer counter = 0;
		for (Entry<String, String> entry : runningTimes.entrySet()) {
			if (counter == 0) {
				assertTrue(entry.getValue().equals("43"));
				assertTrue(entry.getKey().equals("\"12 Monkeys\" (2015) {One Hundred Years (#2.3)}"));
			}
			counter++;
		}

	}

	@Test
	public void testReadGenres() throws IOException {

		HashMap<String, String> genres = ReadMovieData.readList("src/tests/fixtures/testreadgenres.list");
		Integer counter = 0;
		for (Entry<String, String> entry : genres.entrySet()) {
			if (counter == 0) {
				assertTrue(entry.getValue().equals("Reality-TV"));
				assertTrue(entry.getKey().equals("\"#Boss Moms\" (2016)"));
			}
			counter++;
		}

	}

	@Test
	public void testReadPlots() throws IOException {

		HashMap<String, String> plots = ReadMovieData.readPlot("src/tests/fixtures/testreadplots.list");
		Integer counter = 0;
		for (Entry<String, String> entry : plots.entrySet()) {
			if (counter == 0) {
				assertTrue(entry.getValue().equals(
						"This week, the five women work on getting what they want in a relationship by being more open and learning to communicate properly. Cynthia, after dealing with a heartbreaking separation, goes on her first date in several years, with disastrous results. Jahmil meets a cute and confident girl and they decide to go on a date, but when the conversation turns to her past, is she pushed too far out of her comfort zone? Tennesha tries to take a step back from her control issues and lets Errol take the lead on a date, while Laree is challenged to break down some of her walls and commit herself to Karl."));
				assertTrue(entry.getKey().equals("\"#BlackLove\" (2015) {Bringing Sexy Back (#1.3)}"));
			}
			counter++;
		}

	}

}
