package com.prueba.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	ApplicationContext context = new ClassPathXmlApplicationContext("com/prueba/app/Beans.xml");
	AlbumJDBCTemplate aTemplate = (AlbumJDBCTemplate) context.getBean("albumJDBCTemplate");
	
	
	static class Album{
		int userId;
		int id;
		String title;
	}
	
	static class ListAlbums{
		List<Album> albums;
		
	}
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	public static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	
	public String sha1(String s){
		String digest = DigestUtils.sha1Hex(s.getBytes());
		return digest;
	}
	
	
	@RequestMapping(value="/json/", method = RequestMethod.GET)
	public String getJson(Model model) throws Exception{
		logger.info("Working retrieveng from json");
		
		String jsontext = new String();
		jsontext = readUrl("http://jsonplaceholder.typicode.com/albums");
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray jArray =  parser.parse(jsontext).getAsJsonArray();
		ArrayList<Album> ls_a = new ArrayList<Album>();
		int i = 0;
		for(JsonElement obj : jArray){
			Album a = gson.fromJson(obj, Album.class);
			System.out.println("sha1: " + sha1(a.title));
			aTemplate.create(a.id, a.userId, a.title, sha1(a.title));
			i++;
		}
		
		model.addAttribute("field", i);
		return "json";
	}
	
	
	
}
