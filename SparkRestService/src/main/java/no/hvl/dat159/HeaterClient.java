package no.hvl.dat159;

import com.google.gson.Gson;
//import com.squareup.okhttp.*;

import no.hvl.dat159.server.Temperature;
import okhttp3.*;

import java.io.IOException;

public class HeaterClient {
    static OkHttpClient client;
    static Temperature temperature;

    public static void main(String[] args) {
        client = new OkHttpClient();
        temperature = new Temperature();

        while (true) {
            Request tempRequest = new Request.Builder()
                    .url("http://localhost:8080/tempsensor/current")
                    .get()
                    .build();
            try {
                Response response = client.newCall(tempRequest).execute();

                Gson gson = new Gson();
                temperature = gson.fromJson(response.body().string(), Temperature.class);
            } catch (IOException e) {

            }

            MediaType mediaType = MediaType.parse("application/octet-stream");
            RequestBody body;

            if (temperature.getTemperature() <= 15) {

                body = RequestBody.create(mediaType, "{\n    \"heat\": " + true + "\n}");

            }
            else
            {
                body = RequestBody.create(mediaType, "{\n    \"heat\": " + false + "\n}");
            }

            Request heatRequest = new Request.Builder()
                    .url("http://localhost:8080/heater/current")
                    .put(body)
                    .build();

            try {
                client.newCall(heatRequest).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

