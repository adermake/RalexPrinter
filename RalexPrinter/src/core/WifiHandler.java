package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class WifiHandler {

	static String chipIP = "http://192.168.0.115/";

	public static void doStuff() {
		String tag = "test";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://192.168.0.115" + "/" + tag)).build();
		client.sendAsync(request, BodyHandlers.ofString())
				.thenApply(HttpResponse::body).thenAccept(System.out::println)
				.join();

	}

	public static void get(String tag) throws Exception {
		System.out.println("Sending get Request");
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(chipIP + tag)).build();

		HttpResponse<String> response = client.send(request,
				BodyHandlers.ofString());

		System.out.println(response.body());
	}

	public static String post() {
		String url = chipIP+"inputdata";
		try {

			

			URL UrlObj = new URL(url);

			HttpURLConnection connection = (HttpURLConnection) UrlObj
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setDoOutput(true);

			DataOutputStream outputStream = new DataOutputStream(
					connection.getOutputStream());

			String urlPostParameters = "x=1&y=2.5";
			outputStream.writeBytes(urlPostParameters);

			outputStream.flush();
			outputStream.close();

			System.out.println("Send 'HTTP POST' request to : " + url);

			int responseCode = connection.getResponseCode();
			System.out.println("Response Code : " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader inputReader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = inputReader.readLine()) != null) {
					response.append(inputLine);
				}
				inputReader.close();

				return response.toString();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
