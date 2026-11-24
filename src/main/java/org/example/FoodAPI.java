package org.example;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

public class FoodAPI {

    public String searchFood(String query) throws IOException, InterruptedException{
        String apiKey = System.getenv("USDA_API_KEY").trim();
        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?query=" + query + "&api_key=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
