package org.example;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FoodAPI {

    public ArrayList<Food> searchFood(String query) throws IOException, InterruptedException{
        String apiKey = System.getenv("USDA_API_KEY").trim();
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?query=" + encodedQuery + "&api_key=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        JsonNode foods = root.get("foods");
        System.out.println(foods);

        ArrayList<Food> results = new ArrayList<>();

        for (JsonNode food: foods) {
            String name = food.get("description").asText();
            System.out.println(name);
            JsonNode nutrients = food.get("foodNutrients");

            double calories = 0.0;
            double protein = 0.0;
            double carbs = 0.0;
            double fat = 0.0;

            for(JsonNode nutrient: nutrients) {
                String nutrientName = nutrient.get("nutrientName").asText();
                double value = nutrient.get("value").asDouble();
                String unit = nutrient.get("unitName").asText();
                if(nutrientName.equals("Protein")) {
                    protein = value;
                }
                else if(nutrientName.equals("Energy") && unit.equalsIgnoreCase("KCAL")) {
                    calories = value;
                }
                else if(nutrientName.equals("Carbohydrate, by difference")) {
                    carbs = value;
                }
                else if(nutrientName.equals("Total lipid (fat)")) {
                    fat = value;
                }

            }

            Food result = new Food(name, calories, protein, carbs, fat);
            results.add(result);

            System.out.println("Calories: " + calories);
            System.out.println("Protein: " + protein);
            System.out.println("Carbs: " + carbs);
            System.out.println("Fat: " + fat);

        }


        return results;
    }
}
