import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeMap;
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


    // citirea datelor din fisier
    public TreeMap<String, Place> readData(){
    	
    	TreeMap<String, Place> places = new TreeMap<String, Place>();
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

    // inchidere fisier
    public void close() {
        try {
            bf.close();
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
