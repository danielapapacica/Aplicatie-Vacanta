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
	private AppData appData;
	private LocalDate startDate, endDate;

	public Task2(HashMap<String, Place> places, AppData appData) {
		this.places = places;
		this.appData = appData;
	}
	
	
	// location este zona introdusa de utilizator (oras/judet/tara)
	public void SetTask2Data(String location, LocalDate startDate, LocalDate endDate) {
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
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
	 * cauta cele mai bune locatii dintr-un oras
	 * @param top5Places cele mai bune locatii gasite pana acum
	 * @param cityName orasul in care le cautam
	 * @return
	 */
	public PriorityQueue<Place> searchInCity(PriorityQueue<Place> top5Places, String cityName){
		
		for(String locationName:appData.getCities().get(cityName)) {
			if(betterLocation(top5Places, places.get(locationName))) {
				if(top5Places.size() == 5)
					top5Places.poll();
				top5Places.add(places.get(locationName));
			}
		}
		return top5Places;
	}

	/**
	 * 
	 * @return cele mai ieftine 5 locuri de vacanta din tara/judetul/orasul X 
	 * X este campul location din aceasta clasa
	 */
	public ArrayList<String> top5(){
		
		PriorityQueue<Place> top5Places= new PriorityQueue<Place>(new PriceComparator());
		

		if(appData.getCities().containsKey(location)) {				//location e un oras
			top5Places = searchInCity(top5Places, location);
			
		}else if(appData.getDistricts().containsKey(location)) {	//location e un judet
			for(String cityName:appData.getDistricts().get(location)) {
				top5Places = searchInCity(top5Places, cityName);
			}
		}else if(appData.getCountries().containsKey(location)) {	// este o tara
			for(String districtName:appData.getCountries().get(location)) {
				for(String cityName:appData.getDistricts().get(districtName)) {
					top5Places = searchInCity(top5Places, cityName);
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
