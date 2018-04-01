import java.util.ArrayList;
import java.util.HashMap;

public class AppData {
	
	private HashMap<String, ArrayList<String>> cities;
	private HashMap<String, ArrayList<String>> districts;
	private HashMap<String, ArrayList<String>> countries;
	
	public AppData(HashMap<String, ArrayList<String>> cities,
			HashMap<String, ArrayList<String>> districts, HashMap<String, ArrayList<String>> countries) {
		this.cities = cities;
		this.districts = districts;
		this.countries = countries;
	}
	
	
	public HashMap<String, ArrayList<String>> getCities(){
		return cities;
	}
	
	public HashMap<String, ArrayList<String>> getDistricts(){
		return districts;
	}
	
	public HashMap<String, ArrayList<String>> getCountries(){
		return countries;
	}
}
