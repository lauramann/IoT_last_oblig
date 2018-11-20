package no.hvl.dat159;

import com.google.gson.Gson;

import no.hvl.dat159.server.Heater;
import okhttp3.*;
import okhttp3.Request;
import okhttp3.Response;
import spark.*;

import java.io.IOException;

public class TempClient {
    static OkHttpClient client;
    static Heater heater;

    public static void main(String[] args) {
        client = new OkHttpClient();
        heater = new Heater();
        int temp = 23;

        while (true) {
            Request heatRequest = new Request.Builder()
                    .url("http://localhost:8080/heater/current")
                    .get()
                    .build();
            try {
                Response response = client.newCall(heatRequest).execute();
                Gson gson = new Gson();
                heater = gson.fromJson(response.body().string(), Heater.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            MediaType mediaType = MediaType.parse("application/octet-stream");
            RequestBody body;
            if(!heater.isHeat())
            {
                body = RequestBody.create(mediaType, "{\n    \"temperature\": " + temp-- + "\n}");
            }
            else
            {
                temp = 23;
                body = RequestBody.create(mediaType, "{\n    \"temperature\": " + temp + "\n}");
            }

            Request tempRequest = new Request.Builder()
                    .url("http://localhost:8080/tempsensor/current")
                    .put(body)
                    .build();
            try {
                client.newCall(tempRequest).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

