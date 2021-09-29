import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HandicapScraper {
	
	
	private static String getURL(String link) {
		return "https://ncrdb.usga.org/courseTeeInfo.aspx?CourseID=" + link;
	}
	
	public static String fetchHTML(String link) {
		StringBuffer buffer = null;
		try {
			URL url = new URL(getURL(link));
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return buffer.toString(); 
	}
	
	public static void scrapeHTML(String html, String link) {
		Map<String, HashMap<String, Double>> myMap = new HashMap<String, HashMap<String, Double>>();
		while (html.contains("ratingsStyleItems")) {
			html = html.substring(html.indexOf("width=\"50%\"") + 12);
			String name = html.substring(0, html.indexOf(" GC"));
			myMap.put(name, new HashMap<String, Double>());
			html = html.substring(html.indexOf("BLUE") + 158);
			myMap.get(name).put("BLUE", Double.valueOf(html.substring(0, html.indexOf("<"))));
			break;
		}
		
		String printVal = "";
		for (String name : myMap.keySet()) {
			for (String color : myMap.get(name).keySet()) {
				printVal += name + ":\n" + color + " -> " + myMap.get(name).get(color) + "\n";
			}
			printVal += "\n";
		}
		System.out.println(printVal);
	}
	
	

}
