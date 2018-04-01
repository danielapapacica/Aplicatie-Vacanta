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


    /**
     * @return HashMap cu toate locatiile de vizitat
     */
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

    
    
	/** 
 	 * 
	 * @param places hashmap-ul ce contine locatiile
	 * @return un hashMap structurat astfel incat locatiile dintr-un oras sa fie usor accesate avand numele orasului
	 */
	public static HashMap<String, ArrayList<String>> transformPlacesMap(HashMap<String, Place> places){
	
		HashMap<String, ArrayList<String>> transformedPlaces = new HashMap<String, ArrayList<String>>();
		for(Place p:places.values()) {
			if(transformedPlaces.containsKey(p.getCity())) {
				transformedPlaces.get(p.getCity()).add(p.getName());
			}
			else {
				ArrayList<String> placesCity = new ArrayList<String>();
				placesCity.add(p.getName());
				transformedPlaces.put(p.getCity(), placesCity);
			}
		}
		return transformedPlaces;	
	}
	 

    /**
     * citirea informatiilor dintr-o harta (tari,judete orase)
     * @param HashMap cu locatiile
     * @return un obiect de tip AppData avand HashMap pt tari/orase/judete accesate usor prin nume
     */
    public AppData readDataMap(HashMap<String, Place> places) {
    	
    	HashMap<String, ArrayList<String>> countries = new HashMap<String, ArrayList<String>>();
    	String line = null;
    	String[] lineData, lineCities;
    	HashMap<String, ArrayList<String>> districts = new HashMap<String, ArrayList<String>>();
    	HashMap<String, ArrayList<String>> cities = transformPlacesMap(places);

        try {  	
        	line = bf.readLine();
        	 do {
        		lineData = line.split(" ");
        		String countryName = lineData[0];
        		int numberDistricts = Integer.parseInt(lineData[1]);
        		ArrayList<String> districtList = new ArrayList<String>();
        		countries.put(countryName, districtList);	
        		for(int i=0; i < numberDistricts; i++) {
        			line = bf.readLine();
        			lineCities = line.split(",");
        			String districtName = lineCities[0];
        			countries.get(countryName).add(districtName);
        			ArrayList<String> citiesList= new ArrayList<String>();
        			districts.put(districtName, citiesList);
        			for(int j = 1; j < lineCities.length; j++) {
        				String cityName = lineCities[j];
        				districts.get(districtName).add(cityName);
        			}
        		}
        		
        		line = bf.readLine();
        	}while(line != null);
        	
        } catch (IOException ex) {
        	Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        AppData appData = new AppData(cities, districts, countries);
		return appData;    
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
