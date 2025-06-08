package com.examen.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/pokemon")
public class PokeApiServlet extends HttpServlet{
    //URL del API
    private static final String API_URL = "https://pokeapi.co/api/v2/pokemon/";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServerException, IOException, ServletException{
            
        //Variable de entrada
        String pokemon = request.getParameter("pokemon");

        //Validacion de datos
        if (pokemon != null && !pokemon.isEmpty()){
            //Obtener datos
            JSONObject pokeData = obtenerPokemonDesdeApi(pokemon);

            //Procesar la informacion
            Map<String, Object> pokeDataMap = new HashMap<>();
           
            pokeDataMap.put("name",pokeData.getString("name"));
            pokeDataMap.put("height",pokeData.getInt("height"));
            pokeDataMap.put("weight",pokeData.getInt("weight"));

            //Obtener la imagen
            JSONObject sprites = pokeData.getJSONObject("sprites");
            Map<String, Object> spritesMap = new HashMap<>();
            spritesMap.put("front_default", sprites.getString("front_default"));
            pokeDataMap.put("sprites",spritesMap);

            //Pasar el Map al JSP
            request.setAttribute("pokeData", pokeDataMap);
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        }

    }

    private JSONObject obtenerPokemonDesdeApi(String pokemon) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder responseContent = new StringBuilder();

         try {
                // Construir la URL
                @SuppressWarnings("deprecation")
                URL url = new URL(API_URL + pokemon.toLowerCase());

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                // Leer la respuesta
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
            }

            // Convertir en JSON
            return new JSONObject(responseContent.toString());
            
        }
        catch (IOException e) {
                e.printStackTrace();
                return null;
        }
        finally{
            try{
                if(reader != null) reader.close();
                if(connection != null) connection.disconnect();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
