package com.weatherwebapp.dao;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Repository
public class WeatherDAOImpl implements WeatherDAO{
	                             
	private static final String RAPID_API_KEY = "9bc7b004a9msh175d8cde3024b5ep112861jsnb05402840a08";
	
	//Get current weather for any given city.
	@Override
	public String getWeatherDataCity(String city, String country) throws IOException {
      
		return connectAPICity(city, country);
		
	}
	
	//Get a five day forecast in 3 hour increments for any given city.
	@Override
	public String getHourlyWeatherData(String city, String country) throws IOException {
		
		return connectFiveDayForecast(city, country);
		
	}
	
	//Gets weather data for current time
	private String connectAPICity(String city, String country) throws IOException {
		
		OkHttpClient client = new OkHttpClient();
		Request request;
		
		
		if(country.isEmpty()) {
		
			request = new Request.Builder()
				.url("https://open-weather13.p.rapidapi.com/city/" + city+"/%7Blang%7D")
				.get()
				.addHeader("x-rapidapi-key", RAPID_API_KEY)
				.addHeader("x-rapidapi-host", "open-weather13.p.rapidapi.com")
				.build();
		}else {
			
			request = new Request.Builder()
				.url("https://open-weather13.p.rapidapi.com/city/" + city + "/" + country)
				.get()
				.addHeader("x-rapidapi-key", RAPID_API_KEY)
				.addHeader("x-rapidapi-host", "open-weather13.p.rapidapi.com")
				.build();
		}

		return getResponse(client, request);
		
	}
	
	private String connectFiveDayForecast(String city, String country) throws IOException {
		
		String jsonResponse=this.connectAPICity(city, country);
		JSONObject obj = new JSONObject(jsonResponse);
		double lons=obj.getJSONObject("coord").getDouble("lon");
		double lats=obj.getJSONObject("coord").getDouble("lat");
		String lon=String.valueOf(lons);
		String lat=String.valueOf(lats);
		
		OkHttpClient client = new OkHttpClient(); 
		Request request = new Request.Builder()
			.url("https://open-weather13.p.rapidapi.com/city/fivedaysforcast/"+lat+"/"+lon)
			.get()
			.addHeader("x-rapidapi-key", RAPID_API_KEY)
			.addHeader("x-rapidapi-host", "open-weather13.p.rapidapi.com")
			.build();

		return getFiveDayResponse(client, request);
		
	}
	
	private String getFiveDayResponse(OkHttpClient client, Request request) throws IOException {
		
		Response response = client.newCall(request).execute();
		
		String getResponseBody = response.body().string();
		
		return getResponseBody;
		
	}
	
	private String getResponse(OkHttpClient client, Request request) throws IOException {
		
		Response response = client.newCall(request).execute();
		
		String getResponseBody = response.body().string();
		
		return getResponseBody;
		
	}

}
