import java.util.ArrayList;

public class Writer {
    
    // afisarea informatiei pentru o locatie data
    public void writePlaceInfo(Place place) {
    	System.out.println("Nume locatie: " + place.getName());
    	System.out.println("Oras: " + place.getCity());
    	System.out.println("Pret: " + place.getMediumPricePerDay() + " lei / zi");
    	System.out.print("Activitati ce se pot desfasura in aceasta locatie: ");
    	ArrayList<String> activities = place.getActivities();
    	for(int i = 0; i < activities.size(); i++) {
    		if(i < activities.size()-1)
    			System.out.print(activities.get(i) +", ");
    		else
    			System.out.println(activities.get(i));
    	}
    	System.out.println("Perioada de sedere: " + place.getStartDate() + " -> " + place.getEndDate());
    }
}