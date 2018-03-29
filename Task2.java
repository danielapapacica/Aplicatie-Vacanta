import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Top 5 locuri de vacanta din destinatia X, ordonate dupa pret
 * @author Dana
 *
 */
public class Task2 {
	
	private String location;
	private HashMap<String, Place> places;
	private HashMap<String, Country> countries;
	private LocalDate startDate, endDate;

	public Task2(HashMap<String, Place> places, HashMap<String, Country> countries) {
		this.places = places;
		this.countries = countries;
	}
	
	
	// location este zona introdusa de utilizator (oras/judet/tara)
	public void SetTask2Data(String location, LocalDate startDate, LocalDate endDate) {
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	/** 
 	 * 
	 * @param places hashmap-ul ce contine locatiile
	 * @return un hashMap structurat astfel incat locatiile dintr-un oras sa fie usor accesate avand numele orasului
	 */
	public static HashMap<String, ArrayList<Place>> transformPlacesMap(HashMap<String, Place> places){
	
		HashMap<String, ArrayList<Place>> transformedPlaces = new HashMap<String, ArrayList<Place>>();
		for(Place p:places.values()) {
			if(transformedPlaces.containsKey(p.getCity())) {
				transformedPlaces.get(p.getCity()).add(p);
			}
			else {
				ArrayList<Place> placesCity = new ArrayList<Place>();
				placesCity.add(p);
				transformedPlaces.put(p.getCity(), placesCity);
			}
		}
		return transformedPlaces;	
	}
	
	/**
	 * 
	 * @param top5Places lista cu cele mai bune(ieftine) 5 locatii gasite pana a cum
	 * @param place o noua locatie de verificat
	 * @return
	 */
	private boolean betterLocation(PriorityQueue<Place> top5Places, Place place) {
			// daca perioada disponibila de sedere este una convenabila
			if(place.getStartDate().isAfter(this.startDate) || place.getEndDate().isBefore(this.endDate)) 
				return false;
			if(top5Places.size() < 5)
				return true;
			else if(top5Places.peek().getMediumPricePerDay() > place.getMediumPricePerDay())
				return true;
			return false;
	}
	
	

	/**
	 * 
	 * @return cele mai ieftine 5 locuri de vacanta din tara/judetul/orasul X 
	 * X este campul location din aceasta clasa
	 */
	public ArrayList<String> top5(){
		
		PriorityQueue<Place> top5Places= new PriorityQueue<Place>(new PriceComparator());
			
		//verificam daca zona introdusa de utilizator este o tara/oras/judet
		// si retinem cele mai bune locuri de vacanta din zona respectiva
		if(countries.containsKey(location)) {	//daca e o tara
			Country country = countries.get(location);
			for(String districtKey : country.getDistricts().keySet()) {
				District district = country.getDistricts().get(districtKey);
				for(String cityKey : district.getPlaces().keySet()) {
					City city = district.getPlaces().get(cityKey);
					for(String locationKey : city.getPlaces()){
						if(betterLocation(top5Places, places.get(locationKey))) {
							if(top5Places.size() == 5)
								top5Places.poll();
							top5Places.add(places.get(locationKey));
						}	
					}
				}
			}
		}else {	// daca gasim un oras cu denumirea locatiei cautate
			
			HashMap<String, ArrayList<Place>> transformedPlacesMap = transformPlacesMap(places);
			if(transformedPlacesMap.containsKey(location)){		// daca e un oras
				for(Place p: transformedPlacesMap.get(location)) {
					if(betterLocation(top5Places, p)) {
						if(top5Places.size() == 5)
							top5Places.poll();
						top5Places.add(p);
					}
				}
			}else { // daca gasim un judet cu denumirea locatiei introduse
				for(String countryKey: countries.keySet()) {
					Country country = countries.get(countryKey);
					for(String districtKey : country.getDistricts().keySet()) {
						if(districtKey.equals(location)) {			// s-a gasit un judetul
							District district = country.getDistricts().get(districtKey);
							for(String cityKey : district.getPlaces().keySet()) {
								City city = district.getPlaces().get(cityKey);
								for(String locationKey : city.getPlaces()){
									if(betterLocation(top5Places, places.get(locationKey))) {
										if(top5Places.size() == 5)
											top5Places.poll();
									top5Places.add(places.get(locationKey));
									}					
								}
							}
						}
					}
				}
			}
		}
			
		if(top5Places.isEmpty())	return null;	// nu s-a gasit nicio locatie in acest oras/judet/tara
		ArrayList<String> placesList = new ArrayList<String>();
		for(Place p:top5Places) {
			placesList.add(p.getName());
		}
		return placesList;
	}
}
