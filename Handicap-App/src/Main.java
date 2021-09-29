import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Map<String, HashMap<String, Double>> myMap = new HashMap<String, HashMap<String, Double>>();
		for (int i = 2500; i < 3000; i++) {
			try {
				String html = fetchHTML(String.valueOf(i));
				scrapeHTML(html, myMap);
			} catch (Exception e) {
				
			}
		}
		
		String printVal = "";
		for (String name : myMap.keySet()) {
			printVal += name + ":\n";
			for (String color : myMap.get(name).keySet()) {
				printVal += color + " -> " + myMap.get(name).get(color) + "\n";
			}
			printVal += "\n";
		}
		System.out.println(printVal);
//		createCSV(myMap);
		
	}
	
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
	
	public static void scrapeHTML(String html, Map<String, HashMap<String, Double>> myMap) {
		//TODO: make switch to check if the colors exist
		// make work for more than just blue and white
		
		while (html.contains("ratingsStyleItems")) {
			html = html.substring(html.indexOf("width=\"50%\"") + 12);
			String name = html.substring(0, html.indexOf(" GC"));
			
			if (html.contains("Blue")) {
				myMap.put(name, new HashMap<String, Double>());
				html = html.substring(html.indexOf("Blue<"));
				html = html.substring(html.indexOf("M") + 101);
				myMap.get(name).put("Blue", Double.valueOf(html.substring(0, html.indexOf("<"))));
			}
			if (html.contains("White")) {
				html = html.substring(html.indexOf("White<"));
				html = html.substring(html.indexOf("M") + 101);
				
				myMap.get(name).put("White", Double.valueOf(html.substring(0, html.indexOf("<"))));
			}
			if (html.contains("Gold")) {
				html = html.substring(html.indexOf("Gold<"));
				html = html.substring(html.indexOf("M") + 101);
				myMap.get(name).put("Gold", Double.valueOf(html.substring(0, html.indexOf("<"))));
			}
			if (html.contains("Red")) {
				html = html.substring(html.indexOf("Red<"));
				html = html.substring(html.indexOf("M") + 101);
				myMap.get(name).put("Red", Double.valueOf(html.substring(0, html.indexOf("<"))));
			}	
			if (html.contains("Blue/White")) {
				html = html.substring(html.indexOf("Blue/White<"));
				html = html.substring(html.indexOf("M") + 101);
				myMap.get(name).put("Blue/White", Double.valueOf(html.substring(0, html.indexOf("<"))));
			}	
			if (html.contains("Red/Red")) {
				html = html.substring(html.indexOf("Red/Red<"));
				html = html.substring(html.indexOf("M") + 101);
				myMap.get(name).put("Red/Red", Double.valueOf(html.substring(0, html.indexOf("<"))));
			}	
			if (html.contains("Competition")) {
				html = html.substring(html.indexOf("Competition<"));
				html = html.substring(html.indexOf("M") + 101);
				myMap.get(name).put("Competition", Double.valueOf(html.substring(0, html.indexOf("<"))));
			}	
			if (html.contains("White/Black")) {
				html = html.substring(html.indexOf("White/Black<"));
				html = html.substring(html.indexOf("M") + 101);
				myMap.get(name).put("White/Black", Double.valueOf(html.substring(0, html.indexOf("<"))));
			}	
		break;
		}
	}
	
	public static void createCSV(Map<String, HashMap<String, Double>> myMap) {
		
		try {
			FileWriter csvFile = new FileWriter("test.csv");
			for (String name : myMap.keySet()) {
				csvFile.append(name);
				csvFile.append(",");
				for (String color : myMap.get(name).keySet()) {
					csvFile.append(",");
					csvFile.append(color);
					csvFile.append(",");
					csvFile.append(String.valueOf(myMap.get(name).get(color)));
				}
				csvFile.append("\n");
			}
			csvFile.flush();
			csvFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
