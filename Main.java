import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Main {

	private static Scanner scanner = new Scanner(System.in);
	
	
	public static String infoPlace(HashMap<String, Place> places) {
		
		// comanda pentru interogarea unei anumite locatii
		boolean validLocation = false;
		String place = null;
		
		// comanda pentru afisarea informatiilor despre o anumita locatie si 
		// verificarea existentei sale in baza de date
		while(!validLocation) {
			System.out.println("Introduceti locatia dorita (sau pentru a iesi Quit): ");
			place =  scanner.nextLine();
			if(places.containsKey(place)) {
				validLocation = true;
			}
			else if(place.equals("Quit") || place.equals("quit")) {
				place = "quit";
				System.out.println("Va multumim!");
				validLocation = true;
			}
			else{
				System.out.println("Locatie invalida. ");
			}
		}
		return place;
	}

	
	
	
	// folosesc aceasta functie pentru a transforma hashmap-ul ce contine locatiile
	// intr-unul structurat astfel incat locatiile dintr-un oras sa fie usor accesate avand numele orasului
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
	
	
	
	
	private static class PriceComparator implements Comparator<Place>{
		@Override
		public int compare(Place p1, Place p2) {			
			if(p2.getMediumPricePerDay() - p1.getMediumPricePerDay() < 0)
				return -1;
			return 1;
		}	
	}
	
	
	public static boolean betterLocation(PriorityQueue<Place> top5Places, 
		Place place, LocalDate startDate, LocalDate endDate) {
		if(place.getStartDate().isAfter(startDate) || place.getEndDate().isBefore(endDate))
			return false;
		if(top5Places.size() < 5)
			return true;
		else if(top5Places.peek().getMediumPricePerDay() > place.getMediumPricePerDay())
			return true;
		return false;
	}
	
	
	// location este zona introdusa de utilizator (oras/judet/tara)
	public static ArrayList<String> top5(String location, HashMap<String, Place> places,
			HashMap<String, Country> countries, LocalDate startDate, LocalDate endDate){
		
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
						if(betterLocation(top5Places, places.get(locationKey), startDate, endDate)) {
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
					if(betterLocation(top5Places, p, startDate, endDate)) {
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
									if(betterLocation(top5Places, places.get(locationKey), startDate, endDate)) {
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
	
	
	public static Place findCheapest(HashMap<String, Place> places) {
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
	
	
	
	
	// 
	public static void executeCommands(HashMap<String, Place> places, HashMap<String, Country> countries, Writer writer) {
		boolean DaNu = true;	
		while(DaNu) {
			System.out.println("Ce va intereseaza?"
				+ "\n1) Informatii despre o anumita locatie"
				+ "\n2) Top 5 locatii de vizitat in perioada dorita de dumneavoastra"
				+ "\n3) Mega Oferta 10 zile"
				+ "\nCe vreti sa aflati mai intai(1/2/3)?");
			String command = scanner.nextLine();
			while(!command.equals("1") && !command.equals("2") && !command.equals("3")) {
				System.out.println("Optiune invalida. Reincercati: ");
				command = scanner.nextLine();
			}	
			String place = null;
			if(command.equals("1")) {
				place = infoPlace(places);
				if(place != null && !place.equals("quit"))
						writer.writePlaceInfo(places.get(place));
			}
			else if(command.equals("2")) {	
				System.out.println("Introduceti destinatia dorita:");
				String location = scanner.nextLine();
				
				System.out.println("Introduceti data de inceput a sederii (dd/MM/yyyy):");
				String date = null;
				LocalDate endDate = null, startDate = null;
				
				do {
					date = scanner.nextLine();
					try {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						startDate = LocalDate.parse(date, formatter);
					}catch (Exception ex) {
						System.out.println("Format Invalid. Reintroduceti:");
					}
				}while(date == null);	
				System.out.println("Introduceti data de sfartsit a sederii (dd/MM/yyyy):");
				date = null;
				do {
					date = scanner.nextLine();
					try {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						endDate = LocalDate.parse(date, formatter);
					}catch (Exception ex) {
						System.out.println("Format Invalid. Reintroduceti:");
					}
				}while(date == null);	
				ArrayList<String> top5 = top5(location, places, countries, startDate, endDate);
				if(top5 == null)
					System.out.println("Nu s-a gasit niciun rezultat pentru locatia introdusa.");
				else {
					for(String p: top5) {
						System.out.println();
						writer.writePlaceInfo(places.get(p));
					}
				}
			}
			else{
				writer.writePlaceInfo(findCheapest(places));
			}	
			System.out.println("Doriti sa cautati si altceva? (Da/Nu)");	
			String answer = scanner.nextLine();
			while(!answer.equals("Da") && !answer.equals("Nu") && !answer.equals("da") && !answer.equals("nu")) {
				System.out.println("Comanda invalida. Intrudceti da/nu: ");
				answer = scanner.nextLine();
			}
			if(answer.equals("Nu") || answer.equals("nu"))
				DaNu = false;
		}
		System.out.println("Va multumim. La revedere!");
	}
	
	
	
	
	public static void main(String[] args){	

		// citire lista de locatii
		Reader readerPlaces = new Reader(args[0]);
		HashMap<String, Place> places = readerPlaces.readDataPlaces();
		readerPlaces.close();
			
		// citire informatii din harta
		// crearea unei structuri pe niveluri de ierarhie
		Reader readerMap = new Reader(args[1]);
		HashMap<String, Country> countries = readerMap.readDataMap(transformPlacesMap(places));
		readerMap.close();
	
		Writer writer = new Writer();
		executeCommands(places, countries, writer);

	}

}
