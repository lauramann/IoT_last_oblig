package no.hvl.dat159.server;

import static spark.Spark.*;
import com.google.gson.*;

import no.hvl.dat159.Client;

public class Main {

    static Temperature temp = null;
    static Heater heater;
    static Client client;

    public static void main(String[] args) {
        temp = new Temperature();
        heater = new Heater();
        client = new Client();

        port(8080);
        

        after((req, res) -> res.type("application/json"));

        get("/tempsensor/current", (req, res) -> tempToJson());


        get("/heater/current", (req, res) -> heatToJson());

        put("/tempsensor/current", (req,res) -> {

            Gson gson = new Gson();

            System.out.println(req.body());

            temp = gson.fromJson(req.body(), Temperature.class);

            return tempToJson();

        });

        put("/heater/current", (req,res) -> {

            Gson gson = new Gson();

            System.out.println(req.body());

            heater = gson.fromJson(req.body(), Heater.class);

            return heatToJson();

        });
    }

    static String tempToJson () {

        Gson gson = new Gson();

        String jsonInString = gson.toJson(temp);

        return jsonInString;
    }

    static String heatToJson()
    {
        Gson gson = new Gson();

        String jsonInString = gson.toJson(heater);

        return jsonInString;
    }
}

