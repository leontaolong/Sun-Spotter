import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
* A helper class for working with JSON Strings
* @author joelross
*/
public class JsonHelper {

    /**
    * Parses a JSON-format String (from OMDB search results) into a list of "Movie" objects
    * @param json A JSON formatted String, such as 
        <code>
        {"Search":[{"Title":"Star Wars: A New Hope","Year":"1977","imdbID":"tt0076759","Poster":"http://..."},
                  {"Title":"Star Wars: The Empire Strikes Back","Year":"1980","imdbID":"tt0080684","Poster":"http://..."}
                 ]
        }
        </code>
     * @return An ArrayList of Movie objects with title, year, imdbId, and posterUrl as specified in the input
     */
    public ArrayList<Movie> parseMovieJSONData(String json) {
        ArrayList<Movie> movies = new ArrayList<Movie>(); //empty list to return

        try {
            JSONArray moviesJsonArray = new JSONObject(json).getJSONArray("Search"); //get array object from "Search" key
            for(int i=0; i<moviesJsonArray.length(); i++) //iterate through array object
            {
                JSONObject movieJsonObject = moviesJsonArray.getJSONObject(i); //get ith object from array
                Movie movie = new Movie(); //make a "blank" movie
                movie.title = movieJsonObject.getString("Title"); //get title from object and assign
                movie.year = Integer.parseInt(movieJsonObject.getString("Year")); //get year from object
                movie.imdbId = movieJsonObject.getString("imdbID"); //get imdb from object
                movie.posterUrl = movieJsonObject.getString("Poster"); //get poster from object

                movies.add(movie);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing json", e); //Android log the error
            return null;
        }

        return movies;
    }
}
