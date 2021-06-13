package core;

import java.net.URI;
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
		      .uri(URI.create("http://192.168.0.115" + 
		      		"/"+tag))
		      .build();
				client.sendAsync(request, BodyHandlers.ofString())
		      .thenApply(HttpResponse::body)
		      .thenAccept(System.out::println)
		      .join();
		
		
	}
	
	public static void get(String tag) throws Exception {
		System.out.println("Sending get Request");
	    HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	          .uri(URI.create(chipIP+tag))
	          .build();

	    HttpResponse<String> response =
	          client.send(request, BodyHandlers.ofString());

	    System.out.println(response.body());
	}
}
