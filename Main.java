import java.util.Scanner;
import java.util.TreeMap;

public class Main {

	private static Scanner scanner;

	public static void main(String[] args){	

		// citire lista de locatii
		Reader reader = new Reader(args[0]);
		TreeMap<String, Place> places = reader.readData();
		reader.close();
		
		
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
		
		// afisare informatii despre lcoatie
		Writer writer = new Writer(args[1]);
		writer.writePlaceInfo(places.get(place));
		writer.close();
	}

}
