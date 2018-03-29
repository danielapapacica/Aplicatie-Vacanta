import java.time.Period;
import java.util.HashMap;

/**
 * Unde este cel mai ieftin sa practic 10 zile o activitate
 * @author Dana
 *
 */
public class Task3 {

	private HashMap<String, Place> places;

	public Task3(HashMap<String, Place> places) {
		this.places = places;
	}
	
	public Place findCheapest() {
		double cheapestPrice = Double.MAX_VALUE;
		Place cheapestPlace = null;
		for(String placeKey : places.keySet()) {
			Period period = Period.between(places.get(placeKey).getStartDate(), places.get(placeKey).getEndDate());
			if(places.get(placeKey).getMediumPricePerDay() < cheapestPrice && period.getDays() >10) {
				cheapestPrice = places.get(placeKey).getMediumPricePerDay();
				cheapestPlace = places.get(placeKey);
			}
		}
		return cheapestPlace;
	}
}
