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
		AppData appData = readerMap.readDataMap(places);
		
		readerMap.close();
	
		Writer writer = new Writer();
		
		// executia comenzilor
		CommandExecuter ex = new CommandExecuter(places, appData, scanner, writer);
		ex.executeCommands();

	}

}
