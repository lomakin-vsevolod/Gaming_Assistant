package com.by.lomakin.gaming_assistant.api;

import android.util.Log;

import com.by.lomakin.gaming_assistant.bo.GameResponse;
import com.by.lomakin.gaming_assistant.bo.GamesResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nuclear on 18.12.2016.
 */

public class GiantBombApi {
    private static Gson gson = new Gson();

    private GiantBombApi() {

    }

    private static String doApiRequest(String request) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(request.replace(' ','\u00A0'));
            httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder("");
            String line = "";
            String lineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + lineSeparator);
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static GamesResponse searchGames(String searchString) {
        try {
            String response = doApiRequest(GiantBombApiConstants.GAMES_URL + "name:" + searchString);
            GamesResponse gamesResponse = gson.fromJson(response,GamesResponse.class);
            return gamesResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GameResponse getGameById(String id) {
        try {
            String response = doApiRequest(GiantBombApiConstants.API_GAME + id + GiantBombApiConstants.GAME_URL);
            GameResponse gameResponse = gson.fromJson(response,GameResponse.class);
            return gameResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
