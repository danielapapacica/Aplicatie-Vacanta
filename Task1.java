import java.util.HashMap;
import java.util.Scanner;


/**
 * Clasa aceasta nu face si afisarea informatiilor, doar returneaza numele locatiei
 * si comunica cu utilizatorul pentur introducerea unei locatii valide
 * @author Dana
 *
 */
public class Task1 {

	private HashMap<String, Place> places;
	private Scanner scanner;

	public Task1(HashMap<String, Place> places, Scanner scanner) {
		this.places = places;
		this.scanner = scanner;
	}
	
	public String solveTask1() {
		
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
}
