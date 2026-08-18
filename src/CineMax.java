import java.util.List;
import java.util.Scanner;
import java.io;

/**
 * Classe principale dell'applicazione CineMax.
 * Mostra un semplice menu iniziale all'avvio.
 */
public class CineMax{
	/**
	 * Punto di ingresso dell'applicazione CineMax.
	 */
	public static void main(String[]args){
		Scanner scanner = new Scanner(System.in);
		GestoreUtenti gestore = new GestoreUtenti();
		List<Utente> lista = gestore.leggiUtenti();
		int scelta;

		System.out.println("Benvenuto! Cosa vuoi fare?");

		System.out.println("1. Login");
		System.out.println("2. Registrazione");
		System.out.println("3. Procedi come guest");
		System.out.println("4. Esci");
		System.out.print("Inserisci la tua scelta: ");

		scelta = scanner.nextInt();

		switch(scelta){
			case 1:
				// Logica per il login
				System.out.print("Login \nUsername:");
				String username = scanner.next();
				System.out.print("Password:");
				String password = scanner.next();

				if (gestore.login(username, password)) {
					System.out.println("Login effettuato con successo!");
				} else {
					System.out.println("Credenziali non valide. Riprova.");
				}

				break;
			case 2:
				// Logica per la registrazione
				System.out.print("Registrazione \nUsername:");
				String username = scanner.next();
				System.out.print("Password:");
				String password = scanner.next();
				System.out.print("Nome:");
				String nome = scanner.next();
				System.out.print("Cognome:");
				String cognome = scanner.next();
				System.out.print("Data di nascita (YYYY-MM-DD):");
				String dataNascita = scanner.next();
				System.out.print("Domicilio:");
				String domicilio = scanner.next();
				System.out.print("Ruolo (1 per Cliente, 2 per Proiezionista, 3 per Bigliettaio):");
				int ruolo = scanner.nextInt();

				Utente utente = null;

				switch(ruolo){
					case 1:
						// Logica per la registrazione come Cliente
						utente = new Cliente(username, password, nome, cognome, dataNascita, domicilio);
						// Aggiungi il cliente al sistema (ad esempio, a una lista di utenti)
						break;
					case 2:
						// Logica per la registrazione come Proiezionista
						utente = new Proiezionista(username, password, nome, cognome, dataNascita, domicilio);
						// Aggiungi il proiezionista al sistema
						break;
					case 3:
						// Logica per la registrazione come Bigliettaio
						utente = new Bigliettaio(username, password, nome, cognome, dataNascita, domicilio);
						// Aggiungi il bigliettaio al sistema
						break;
					default:
						System.out.println("Ruolo non valido. Riprova.");
				}
				if(gestore.aggiungiUtente(utente)){
					System.out.println("Registrazione completata con successo!");
				}else{
					System.out.println("Errore durante la registrazione. Riprova.");
				}
				break;
			case 3:
				// Logica per procedere come guest
				System.out.println("Hai scelto di procedere come guest.");
				break;
			case 4:
				// Logica per uscire
				System.out.println("Uscita dall'applicazione. Arrivederci!");
				break;
			default:
				System.out.println("Scelta non valida. Riprova.");
		}
	}
}
