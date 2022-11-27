package com.mycompany.apisparkmovies;

/**
 *
 * @author mauro
 */

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import java.io.IOException;

import com.mycompany.apisparkmovies.model.Movie;
import com.mycompany.apispark.service.MoviesService;
import com.mycompany.apispark.transformer.JsonTransformer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MoviesApplication {
    
    private static final String ID_PARAMETER = "id";
        private static final String TITLE_PARAMETER = "name";
        
	private MoviesService moviesService;
        private String name = "batman";
	
	public MoviesApplication(final MoviesService moviesService) {
		this.moviesService = moviesService;
		initializeRoutes();
	}
	
	private void initializeRoutes() {
            
        HttpClient client = HttpClient.newHttpClient();
        
        port(8080); // TODO Auto-generated catch block
        
        get("/movie", (req, res) -> {
            return moviesService.getMovies();
        }, new JsonTransformer());
        
        get("/movie/:name", (req, res) -> {
            final String nombre = req.params(TITLE_PARAMETER);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.omdbapi.com/?t="+nombre+"&apikey=4f595cc2"))
                    .GET()
                    .build();
            HttpResponse<String> respuesta=client.send(request, HttpResponse.BodyHandlers.ofString());
            return respuesta.body();});
        
        get("/movies/:id", (req, res) -> {
            final String id = req.params(ID_PARAMETER);
            return moviesService.getMovie(Long.valueOf(id));
        }, new JsonTransformer());
        
        
        post("/movie", (req, res) -> {
            final Movie movie = readBody(req.body());
            return moviesService.addMovie(movie);
        }, new JsonTransformer());
        delete("/movie/:id", (req, res) -> {
            final String id = req.params(ID_PARAMETER);
            moviesService.deleteMovie(Long.valueOf(id));
            return "";
        });
        put("/movie/:id", (req, res) -> {
            final Movie movie = readBody(req.body());
            return moviesService.updateMovie(movie);
        }, new JsonTransformer());   
		
	}
	
	private Movie readBody(final String jsonData) throws JsonParseException, JsonMappingException, IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonData, Movie.class);
	}
    
    
}
