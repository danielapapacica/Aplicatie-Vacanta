import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Reader {
    
    private BufferedReader bf;


    // deschidere fisier
    public Reader(String filename) {
        try {
            bf = new BufferedReader(new FileReader(new File(filename)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // citirea datelor din fisier despre lista locatiilor
    public HashMap<String, Place> readDataPlaces(){
    	
    	HashMap<String, Place> places = new HashMap<String, Place>();
    	String line = null;
    	String[] lineData;

        try {
        	
        	line = bf.readLine();
        	 do {
        		lineData = line.split(",");
        		String name = lineData[0];
        		String city = lineData[1];
        		double priceDay = Double.parseDouble(lineData[2]);
        		ArrayList<String> activities = new ArrayList<String>();
        		for(int i = 3; i < lineData.length-1; i++)
        				activities.add(lineData[i]);	
        		String[] dates = lineData[lineData.length-1].split("-");

        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        		LocalDate startDate = LocalDate.parse(dates[0], formatter);
        		LocalDate endDate = LocalDate.parse(dates[1], formatter);
        		
        		places.put(name,new Place(name, city, priceDay, startDate, endDate, activities));	// adauga locatie noua
        		
        		line = bf.readLine();	// citeste randul urmator
        	}while(line != null);

           
        } catch (IOException ex) {
        	Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
		return places;
        
    }

    // citirea informatiilor dintr-o harta (tari,judete orase)
    //places este un HashMap avand cheia un oras si valoarea o lista a locatiilor din orasul respectiv
    public HashMap<String, Country> readDataMap(HashMap<String, ArrayList<Place>> places) {
    	
    	HashMap<String, Country> countries = new HashMap<String, Country>();
    	String line = null;
    	String[] lineData, lineCities;
    	HashMap<String, City> cities = null;
    	HashMap<String, District> districts = null;

        try {  	
        	line = bf.readLine();
        	 do {
        		lineData = line.split(" ");
        		String countryName = lineData[0];
        		int numberDistricts = Integer.parseInt(lineData[1]);
        		districts = new HashMap<String, District>();
        		
        		for(int i=0; i < numberDistricts; i++) {
        			line = bf.readLine();
        			lineCities = line.split(",");
        			cities = new HashMap<String, City>();
        			for(int j = 1; j < lineCities.length; j++) {
        				ArrayList<String> placesCity = new  ArrayList<String>();
        				if(places.containsKey(lineCities[j])) {
        							for(Place p:places.get(lineCities[j])) {
        								placesCity.add(p.getName());
        							}	
        				}
        				City city = new City(lineCities[j], placesCity);
        				cities.put(lineCities[j], city);
        			}
        			District district = new District(lineCities[0], cities);	// prima linie e numele judetului de care apartin orasele
        			districts.put(lineCities[0], district);
        		}
        		Country country = new Country(countryName ,districts);
        		countries.put(countryName, country);
        		line = bf.readLine();	// citeste randul urmator
        	}while(line != null);

           
        } catch (IOException ex) {
        	Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
		return countries;
        
    }
    
    
    // inchidere fisier
    public void close() {
        try {
            bf.close();
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
