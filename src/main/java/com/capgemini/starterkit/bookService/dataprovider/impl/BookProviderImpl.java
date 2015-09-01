package com.capgemini.starterkit.bookService.dataprovider.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.capgemini.starterkit.bookService.dataprovider.BookProvider;
import com.capgemini.starterkit.bookService.dataprovider.data.BookVO;

public class BookProviderImpl implements BookProvider {

	private static final Logger LOG = Logger.getLogger(BookProviderImpl.class);

	/*
	 * REV: wynik wyszukiwania nie powinien byc przechowywany jako pole w klasie
	 */
	private List<BookVO> books = new ArrayList<>();

	/*
	 * REV: to pole nie powinno byc statyczne
	 */
	static ObjectMapper objectMapper = new ObjectMapper();

	public BookProviderImpl() {
	}

	public Collection<BookVO> findBooks(String title) {
		LOG.debug("Entering findBooks()");

		try {
			books = callRestAndGetBooksWithTitle(title);
		} catch (IOException e) {
			/*
			 * REV: lepiej byloby rzucic wyjatek i wyswietlic komunikat na GUI
			 */
			LOG.debug("Problem with findBooks!");
		}

		LOG.debug("Leaving findBooks()");

		return books;
	}

	public Void saveBook(BookVO book) {
		LOG.debug("Entering saveBook()");

		String json;
		try {
			json = mapToJson(book);
			makePostRequest(json);
		} catch (IOException e) {
			/*
			 * REV: j.w.
			 */
			LOG.debug("Problem with saveBook!");
		}

		LOG.debug("Leaving saveBook()");
		return null;
	}

	/*
	 * REV: ta metoda nie powinna byc statyczna
	 */
	private static List<BookVO> callRestAndGetBooksWithTitle(String title) throws IOException {
		/*
		 * REV: adres serwera powinien byc wczytany z pliku konfiguracyjnego
		 */
		URL url = new URL("http://localhost:9721/workshop/rest/books/books-by-title?titlePrefix=" + title);
		List<BookVO> books = Arrays.asList(objectMapper.readValue(url, BookVO[].class));

		return books;
	}

	private String mapToJson(BookVO book) throws IOException {
		String json = objectMapper.writeValueAsString(book);

		return json;
	}

	private void makePostRequest(String json) throws IOException {
		/*
		 * REV: adres serwera powinien byc wczytany z pliku konfiguracyjnego
		 */
		String url = "http://localhost:9721/workshop/rest/books/book";
		/*
		 * REV: tworzenie obiektu Httpclient jest kosztowne.
		 * Powinien on byc zapisany jako pole w tej klasie, a nie tworzony przy kazdym wywolaniu serwera.
		 */
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		StringEntity jsonEntity = new StringEntity(json, "UTF-8");
		jsonEntity.setContentType("application/json");
		request.setEntity(jsonEntity);

		client.execute(request);
	}
}
