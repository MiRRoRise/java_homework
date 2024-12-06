package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Inet {
    // Тестовые API
    private static final String URL_USERS = "https://fake-json-api.mock.beeceptor.com/users";
    private static final String URL_POSTS = "https://dummy-json.mock.beeceptor.com/posts";

    public static void main(String[] args) {
        try {
            // Получаем и обрабатываем информацию о пользователях
            System.out.println("Users:");
            String users = get_info(URL_USERS);
            display_users(users);

            System.out.println("--------------------------------------\n");

            // Получаем и обрабатываем информацию о постах
            System.out.println("Posts:");
            String posts = get_info(URL_POSTS);
            display_posts(posts);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static String get_info(String url) {
        StringBuilder result = new StringBuilder();
        try {
            // Открываем соединение с URL и готовимся получать данные
            URL url_api = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) url_api.openConnection();
            huc.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String str;

            // Считываем все данные
            while ((str = reader.readLine()) != null) {
                result.append(str);
            }
            reader.close();
            huc.disconnect();
        } catch (MalformedURLException e) {
            System.out.println("Некорректный URL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при чтении данных: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return result.toString();
    }

    private static void display_users(String users_json) {
        // Выводим информацию о пользователях
        try {
            JSONArray users = new JSONArray(users_json);
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                System.out.println("ID: " + user.getInt("id"));
                System.out.println("Name: " + user.getString("name"));
                System.out.println("Company: " + user.getString("company"));
                System.out.println("Username: " + user.getString("username"));
                System.out.println("Email: " + user.getString("email"));
                System.out.println("Address: " + user.getString("address"));
                System.out.println("ZIP: " + user.getString("zip"));
                System.out.println("State: " + user.getString("state"));
                System.out.println("Country: " + user.getString("country"));
                System.out.println("Phone: " + user.getString("phone"));
                System.out.println("Photo: " + user.getString("photo"));
                System.out.println();
            }
        } catch (JSONException e) {
            System.out.println("Ошибка при обработке JSON: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void display_posts(String posts_json) {
        // Выводим информацию о постах
        try {
            JSONArray posts = new JSONArray(posts_json);
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.getJSONObject(i);
                System.out.println("User ID: " + post.getInt("userId"));
                System.out.println("ID: " + post.getInt("id"));
                System.out.println("Title: " + post.getString("title"));
                System.out.println("Body: " + post.getString("body"));
                System.out.println("Link: " + post.getString("link"));
                System.out.println("Comment count: " + post.getInt("comment_count"));
                System.out.println();
            }
        } catch (JSONException e) {
            System.out.println("Ошибка при обработке JSON: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
