package com.teneke.shophelper.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestService {

	/**
	 * 
	 * @return
	 */
	public String testService() {
		
		RestTemplate restTemplate = new RestTemplate();
		String str = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", String.class);
		
		return str;
	}
	
	/**
	 * 
	 * @return
	 */
	public String testService2() {
		
		URL url;
		String inputLine = "notOk";

		try {
			System.setProperty("http.agent", "Chrome");

			// get URL content
			url = new URL("https://www.epey.com/akilli-telefonlar/");
			URLConnection conn = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream()));

			inputLine = IOUtils.toString(br);

			System.out.println(inputLine);
			
			System.out.println("Done");
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return inputLine;

	}
	
}
