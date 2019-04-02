package com.teneke.shophelper.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teneke.shophelper.entities.Item;
import com.teneke.shophelper.repository.ItemRepository;
import com.teneke.shophelper.util.Constants;

@Service
public class DataCollectorService {

	@Autowired
	ItemRepository itemRepository;
	
	/**
	 * Page
	 */
	public void getData() throws IOException {
		
		for (int i = 1; i < 15 ; i++) {
			String nonParsedHtml = getDataFromEpey(Integer.toString(i));
			
			parseDataForEpey(nonParsedHtml);	
			
		}
		
		for (int i = 1; i < 2 ; i++) {
			String nonParsedHtmlCimri = getDataFromCimri(Integer.toString(i));
			
			parseDataForCimri(nonParsedHtmlCimri);
			
		}
		
		
		
		
	
		for (int i = 1; i < 2 ; i++) {

			
			String nonParsedHtml = new String(Files.readAllBytes(Paths.get("html.txt")));
			
			// TODO Yeni bir Yöntem bulunacak

			
			parseDataForAkakce(nonParsedHtml);
			
			
		}
		
	}

	/**
	 * Epey Parse
	 * @param nonParsedHtml
	 */
	private void parseDataForEpey(String nonParsedHtml) {
		Document document = Jsoup.parse(nonParsedHtml);
		
		Elements elements = document.getElementsByClass(Constants.FIYAT);
		
		String rawHtml = elements.outerHtml();
		String[] rows = rawHtml.split(Constants.NEW_LINE);
		
		for (String row : rows) {
			if (row.contains(Constants.A_HREF)) {
				try {
					Document tmpDoc = Jsoup.parse(row);
					Elements tmpElements = tmpDoc.getElementsByClass(Constants.FIYAT);
					
					// Huawei Mate 20 Pro (LYA-L09) Cep Telefonu Fiyatı
					// Boşluklara bölünecek.Son 3 cümle silinecek.
					String rawTitle = tmpElements.first().childNode(1).attr(Constants.TITLE).replace(Constants.REMOVE_CHAR, Constants.EMPTY_STRING);
					
					String source = tmpElements.first().childNode(1).attr(Constants.HREF);
					
					// 6.913,90 TL
					// Nokta silinecek.Virgül noktaya çevrilecek.
					String rawPrice = tmpElements.first().childNode(1).childNode(0).toString();
					
					String brand = rawTitle.substring(0,rawTitle.indexOf(Constants.SPACE));
					String model = rawTitle.substring(rawTitle.indexOf(Constants.SPACE) + 1);
					String priceString = rawPrice.replace(Constants.DOT, Constants.EMPTY_STRING).replace(Constants.COMMA, Constants.DOT).replace(Constants.SPACE_TL, Constants.EMPTY_STRING);
					float price = Float.parseFloat(priceString);
					
					Item item = new Item();
					item.setBrand(brand.toLowerCase());
					item.setModel(model.toLowerCase());
					item.setPrice(price);
					item.setName(rawTitle.toLowerCase());
					item.setSource(source);
					
					itemRepository.save(item);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		
	}

	/**
	 * Akakce Parse
	 * @param nonParsedHtml
	 */
	private void parseDataForAkakce(String nonParsedHtml) {
		Document document = Jsoup.parse(nonParsedHtml);
		
		Elements elements = document.getElementsByClass(Constants.iC);
		
		Iterator<Element> elementIt = elements.iterator();
		
		while(elementIt.hasNext()) {
			try {
				Element element = elementIt.next();
				
				String source = Constants.AKAKCE_URL + element.attr(Constants.HREF);
				String name = element.child(0).attr(Constants.ALT).replace(Constants.REMOVE_CHAR2, Constants.EMPTY_STRING);
				String brand = name.substring(0,name.indexOf(Constants.SPACE));
				String model = name.substring(name.indexOf(Constants.SPACE) + 1);
				String priceString = element.childNode(3).childNode(2).childNode(0).childNode(0).toString().trim().replace(Constants.DOT, Constants.EMPTY_STRING);
				
				float price = Float.parseFloat(priceString);
				
				Item item = new Item();
				item.setBrand(brand.toLowerCase());
				item.setModel(model.toLowerCase());
				item.setPrice(price);
				item.setName(name.toLowerCase());
				item.setSource(source);
				
				itemRepository.save(item);
			} catch (Exception e) {
				// inndex out aticak
				System.out.println(e);
			}
		}
	}
	
	/**
	 * Cimri Parse
	 * @param nonParsedHtml
	 */
	private void parseDataForCimri(String nonParsedHtmlCimri) {
		Document document = Jsoup.parse(nonParsedHtmlCimri);
		
		Elements elements = document.getElementsByClass(Constants.CİMRİ);
		
		
		Iterator<Element> elementIt = elements.iterator();
		
		while(elementIt.hasNext()) {
			
			try {
				Element element = elementIt.next();
				
				String name = element.childNode(0).childNode(1).childNode(2).attr(Constants.TITLE);//.replace(Constants.REMOVE_CHAR3, Constants.EMPTY_STRING) eklenecek
				
				String brand = name.substring(0,name.indexOf(Constants.SPACE));
				String model = name.substring(name.indexOf(Constants.SPACE) + 1);
																				
				
				name = brand + Constants.SPACE + model;
				
				String fiyatAsString = element.childNode(0).childNode(2).childNode(0).childNode(1).toString();
				String source = Constants.CİMRİ_URL + element.childNode(0).childNode(3).attr(Constants.HREF);
				
				String Stringprice = fiyatAsString.replace(Constants.DOT, Constants.EMPTY_STRING).replace(Constants.COMMA, Constants.DOT).replace(Constants.SPACE_TL, Constants.EMPTY_STRING);
				float price = Float.parseFloat(Stringprice);
				Item item = new Item();
				
				item.setBrand(brand.toLowerCase());
				item.setModel(model.toLowerCase());//Ayarlanacak
				item.setPrice(price);
				item.setName(name.toLowerCase());//Ayarlanacak
				item.setSource(source);
				
				itemRepository.save(item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	

	/**
	 * Epey Url
	 * @param pageNum
	 * @return
	 */
	private String getDataFromEpey(String pageNum) {		
		URL url;
		String inputLine = "notOk";
		try {
			System.setProperty("http.agent", "Chrome");
			url = new URL("https://www.epey.com/akilli-telefonlar/" + pageNum + "/");
			URLConnection conn = url.openConnection();

			BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			inputLine = IOUtils.toString(br);

			return inputLine;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Akakce Url
	 * @param pageNum
	 * @return
	 */
	
	@SuppressWarnings("unused")
	private String getDataFromAkakce(String pageNum) {
		URL url;
		String inputLine = "notOk";
		try {
			System.setProperty("http.agent", "Chrome");
			url = new URL("https://www.akakce.com/cep-telefonu.html");
			URLConnection conn = url.openConnection();
			conn.addRequestProperty("User-Agent", "Mozilla/4.76"); 

			BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			inputLine = IOUtils.toString(br);
			
			return inputLine;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Cimri Url
	 * @param pageNum
	 * @return
	 */
	private String getDataFromCimri(String pageNum) {
		URL url;
		String inputLine = "notOk";
		try {
			System.setProperty("http.agent", "Chrome");
			url = new URL("https://www.cimri.com/cep-telefonlari" + "?page=" + pageNum);
			URLConnection conn = url.openConnection();

			BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			inputLine = IOUtils.toString(br);

			return inputLine;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
