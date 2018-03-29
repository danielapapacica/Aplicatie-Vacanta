import java.util.Scanner;
import java.util.HashMap;

public class Main {

	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args){	

		// citire lista de locatii
		Reader readerPlaces = new Reader(args[0]);
		HashMap<String, Place> places = readerPlaces.readDataPlaces();
		readerPlaces.close();
			
		// accesarea fisierului ce contine o "harta", adica un fisier cu ierarhia tari,judete,orase
		Reader readerMap = new Reader(args[1]);
		
		// ma folosesc de o functie din cadrul taskului 2 pentru a crea ierarhia de tari, judete, orase
		HashMap<String, Country> countries = readerMap.readDataMap(Task2.transformPlacesMap(places));
		
		readerMap.close();
	
		Writer writer = new Writer();
		
		// executia comenzilor
		CommandExecuter ex = new CommandExecuter(places, countries, scanner, writer);
		ex.executeCommands();

	}

}
