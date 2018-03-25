import java.time.LocalDate;
import java.util.ArrayList;

public class Place {
	
	private String name;
	private String city;
	private double priceDay;
	private LocalDate startDate;
	private LocalDate endDate;
	private ArrayList<String> activities;
	
	public Place(String name, String city, double priceDay, 
			LocalDate startDate, LocalDate endDate, ArrayList<String> activities) {
		
		this.name = name;
		this.city = city;
		this.priceDay = priceDay;
		this.startDate = startDate;
		this.endDate = endDate;
		this.activities = activities;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCity() {
		return city;
	}
	
	public double getMediumPricePerDay() {
		return priceDay;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public ArrayList<String> getActivities(){
		return activities;
	}
}
