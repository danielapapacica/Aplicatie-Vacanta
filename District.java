import java.util.HashMap;

public class District {
	
	private String name;
	private HashMap<String, City> cities;
	
	public District(String name, HashMap<String, City> cities) {
		this.name = name;
		this.cities = cities;
	}
	
	public String getName() {
		return name;
	}
	
	public HashMap<String, City> getPlaces(){
		return cities;
	}
}