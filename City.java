import java.util.ArrayList;

public class City {
	private String name;
	private ArrayList<String> places;
	
	public City(String name, ArrayList<String> places) {
		this.name = name;
		this.places = places;
	}
	public String getName() {
		return name;
	}
	
	public ArrayList<String> getPlaces(){
		return places;
	}
}
