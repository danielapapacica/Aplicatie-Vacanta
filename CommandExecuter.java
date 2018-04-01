import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Clasa foloseste metoda executeCommands care interactioneaza cu utilizatorul si executa orice task acesta ar cere
 * @author Dana
 *
 */
public class CommandExecuter {
	
	private HashMap<String, Place> places;
	private AppData appData;
	private Scanner scanner;
	private Writer writer;
	private Task1 task1Solver;
	private Task2 task2Solver;
	private Task3 task3Solver;
	
	public CommandExecuter(HashMap<String, Place> places, AppData appData, Scanner scanner, Writer writer) {
		this.places = places;
		this.appData = appData;
		this.scanner = scanner;
		this.writer = writer;
		task1Solver = new Task1(places, scanner);
		task2Solver = new Task2(places, appData);
		task3Solver = new Task3(places);
	}

	
	public void executeCommands() {
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
				place = task1Solver.solveTask1();
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
				
				task2Solver.SetTask2Data(location, startDate, endDate);
				ArrayList<String> top5 = task2Solver.top5();
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
				writer.writePlaceInfo(task3Solver.findCheapest());
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
}
