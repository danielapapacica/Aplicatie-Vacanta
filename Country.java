import java.util.HashMap;

public class Country {
	
	private String name;
	private HashMap<String, District> districts;
	
	public Country(String name, HashMap<String, District> districts) {
		this.name = name;
		this.districts = districts;
	}
	
	public String getName() {
		return name;
	}
	
	public HashMap<String, District> getDistricts(){
		return districts;
	}

}
