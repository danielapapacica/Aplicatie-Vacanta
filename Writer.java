
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Writer {
    
    private PrintWriter pw;
    
    public Writer(String filename) {
        try {
            pw = new PrintWriter(new File(filename));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void println(String text) {
        pw.println(text);
    }
    
    public void print(String text) {
        pw.print(text);
    }
    
    public void close() {
        pw.close();
    }
    
    // afisarea informatiei pentru o locatie data
    public void writePlaceInfo(Place place) {
    	println("Nume locatie: " + place.getName());
    	println("Oras: " + place.getCity());
    	println("Pret: " + place.getMediumPricePerDay());
    	print("Activitati ce se pot desfasura in aceasta locatie: ");
    	ArrayList<String> activities = place.getActivities();
    	for(int i = 0; i < activities.size(); i++) {
    		if(i < activities.size()-1)
    			print(activities.get(i) +", ");
    		else
    			println(activities.get(i));
    	}
    	println("Perioada de sedere: " + place.getStartDate() + " - " + place.getEndDate());
    }
}