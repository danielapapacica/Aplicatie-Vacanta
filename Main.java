import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Main {

	private static Scanner scanner;
	
	
	public static String infoPlace(HashMap<String, Place> places) {
		
		// comanda pentru interogarea unei anumite locatii
		boolean validLocation = false;
		scanner = new Scanner(System.in);
		String place = null;
		
		// comanda pentru afisarea informatiilor despre o anumita locatie si 
		// verificarea existentei sale in baza de date
		while(!validLocation) {
			System.out.println("Introduceti locatia dorita: ");
			place =  scanner.nextLine();
			if(places.containsKey(place)) {
				validLocation = true;
				System.out.println("Va multumim! "
						+ "Informatiile au fost afisate in fisierul de output");
			}
			else {
				System.out.println("Locatie invalida. " + 
						"Va rugam introduceti alta locatie: ");
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
				transformedPlaces.put(p.getCity(), placesCity);
			}
		}
		return transformedPlaces;	
	}
	
	
	PriorityQueue<Place> Top5(HashMap<String, Place> places, HashMap<String, Country> countries){
		return null;
		
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
		
		//comenzi
		String place = infoPlace(places);
		
		// afisare informatii despre lcoatie
		Writer writer = new Writer(args[2]);
		writer.writePlaceInfo(places.get(place));
		writer.close();
	}

}
