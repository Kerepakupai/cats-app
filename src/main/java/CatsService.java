import com.google.gson.Gson;
import okhttp3.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatsService {
    public static void showCats() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search")
                .method("GET", null)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String jsonResponse = response.body().string();

            jsonResponse = jsonResponse.substring(1, jsonResponse.length());
            jsonResponse = jsonResponse.substring(0, jsonResponse.length() - 1);

            Gson gson = new Gson();
            Cats cats = gson.fromJson(jsonResponse, Cats.class);

            URL url = new URL(cats.getUrl());
            Image image = ImageIO.read(url);

            ImageIcon icon = new ImageIcon(image);

            Image catIcon = icon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            icon = new ImageIcon(catIcon);

            String menu = "Options: \n"
                    + " 1. Watch new image \n"
                    + " 2. Favourite \n"
                    + " 3. Back \n";

            String[] buttons = {
                    "Watch new image", "Favourite", "Back"
            };

            String idCat = cats.getId();
            String option = (String) JOptionPane.showInputDialog(null, menu, idCat,
                    JOptionPane.INFORMATION_MESSAGE, icon, buttons, buttons[0]);

            int selection = -1;

            for (int i = 0; i < buttons.length; i++) {
                if(option.equals(buttons[i])) {
                    selection = i;
                }
            }

            switch (selection) {
                case 0:
                    showCats();
                    break;
                case 1:
                    favouriteCat(cats);
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void favouriteCat(Cats cat) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create("{\"image_id\": \"" + cat.getId() + "\"}", mediaType);
            Request request = new Request.Builder().url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", cat.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void showFavourites(String apiKey) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder().url("https://api.thecatapi.com/v1/favourites")
                    .method("GET", null)
                    .addHeader("x-api-key", apiKey)
                    .build();
            Response response = client.newCall(request).execute();

            String jsonResponse = response.body().string();

            Gson gson = new Gson();
            FavouritesCats[] favouritesCats = gson.fromJson(jsonResponse, FavouritesCats[].class);

            if (favouritesCats.length > 0) {
                int min = 1;
                int max = favouritesCats.length;
                int rnd = (int) (Math.random() * ((max - min) + 1)) + min;
                int index = rnd - 1;

                FavouritesCats favouriteCat = favouritesCats[index];

                URL url = new URL(favouriteCat.image.getUrl());
                Image image = ImageIO.read(url);

                ImageIcon icon = new ImageIcon(image);

                Image catIcon = icon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                icon = new ImageIcon(catIcon);

                String menu = "Options: \n"
                        + " 1. Watch new image \n"
                        + " 2. Remove favourite \n"
                        + " 3. Back \n";

                String[] buttons = {
                        "Watch new image", "Remove favourite", "Back"
                };

                String idCat = favouriteCat.getId();
                String option = (String) JOptionPane.showInputDialog(null, menu, idCat,
                        JOptionPane.INFORMATION_MESSAGE, icon, buttons, buttons[0]);

                int selection = -1;

                for (int i = 0; i < buttons.length; i++) {
                    if(option.equals(buttons[i])) {
                        selection = i;
                    }
                }

                switch (selection) {
                    case 0:
                        showFavourites(apiKey);
                        break;
                    case 1:
                        removeFavourite(favouriteCat);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void removeFavourite(FavouritesCats favouriteCat) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create("", mediaType);
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/" + favouriteCat.image.getId() + "")
                    .method("DELETE", body)
                    .addHeader("x-api-key", favouriteCat.getApiKey())
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            System.out.println(response);
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
