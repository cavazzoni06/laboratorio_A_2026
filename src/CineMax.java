
import java.util.List;
import java.util.Scanner;

import java.io;
import java.time.LocalDateTime;

/**
 * Classe principale dell'applicazione CineMax.
 * Mostra un semplice menu iniziale all'avvio.
 */
public class CineMax {
	/**
	 * Punto di ingresso dell'applicazione CineMax.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		GestoreUtenti utenti = new GestoreUtenti();
		GestoreProiezioni proiezioni = new GestoreProiezioni();
		GestorePrenotazioni prenotazioni = new GestorePrenotazioni(proiezioni);
		proiezioni.setGestorePrenotazioni(prenotazioni);
		Utente utente=null;
		
		int scelta, s;
		String ricercaTitolo = "", ricercaGenere = "", username = "", password = "";
		LocalDateTime ricercaDataInizio = null, ricercaDataFine = null;
		double ricercaCostoMassimo = 0.0;

		utenti.leggiUtenti();

		System.out.println("Benvenuto!");

		do {
			System.out.println("Cosa vuoi fare?");
			System.out.println("1. Login");
			System.out.println("2. Registrazione");
			System.out.println("3. Procedi come guest");
			System.out.println("4. Esci");

			System.out.print("Inserisci la tua scelta: ");
			scelta = scanner.nextInt();

			switch (scelta) {
				case 1:
					// Logica per il login
					System.out.print("Login \nUsername:");
					username = scanner.next();
					System.out.print("Password:");
					password = scanner.next();

					utente = utenti.login(username, password);
					if (utente instanceof Cliente) {
						menuCliente((Cliente) utente, proiezioni, prenotazioni);
					} else if (utente instanceof Proiezionista) {
						menuProiezionista((Proiezionista) utente, proiezioni, prenotazioni);
					} else if (utente instanceof Bigliettaio) {
						menuBigliettaio((Bigliettaio) utente, proiezioni, prenotazioni);
					} else {
						System.out.println("Username o password errati. Riprova.");
					}
					break;
				case 2:
					// Logica per la registrazione
					System.out.print("Registrazione \nUsername:");
					username = scanner.next();
					if (utenti.usernameEsistente(username)) {
						System.out.println("Username già esistente. Riprova.");
						break;
					}
					System.out.print("Password:");
					password = scanner.next();
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

					switch (ruolo) {
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
					if (utenti.aggiungiUtente(utente)) {
						System.out.println("Registrazione completata con successo!");

						if (utente instanceof Cliente) {
							menuCliente((Cliente) utente, proiezioni, prenotazioni);
						} else if (utente instanceof Proiezionista) {
							menuProiezionista((Proiezionista) utente, proiezioni, prenotazioni);
						} else if (utente instanceof Bigliettaio) {
							menuBigliettaio((Bigliettaio) utente, proiezioni, prenotazioni);
						}
					} else {
						System.out.println("Errore durante la registrazione. Riprova.");
					}
					
					break;
				case 3:
					// Logica per procedere come guest
					System.out.println("Hai scelto di procedere come guest.");
					do {
						System.out.println("Cosa ti piacerebbe fare? \n1. Cercare una proiezione \n2. Indietro");
						System.out.print("Inserisci la tua scelta: ");
						s = scanner.nextInt();
						if (s != 1 && s != 2) {
							System.out.println("Scelta non valida. Riprova.");
						}
					} while (s != 1 && s != 2);

					if (s == 1) {
						System.out.println("Inserire dati ricerca: ");
						System.out.print("Inserire titolo: ");
						ricercaTitolo = scanner.next();
						System.out.print("Inserire intervallo di date (YYYY-MM-DDTHH:MM): ");
						ricercaDataInizio = LocalDateTime.parse(scanner.next());
						ricercaDataFine = LocalDateTime.parse(scanner.next());
						System.out.print("Inserire genere: ");
						ricercaGenere = scanner.next();
						System.out.print("Inserire costo massimo: ");
						ricercaCostoMassimo = scanner.nextDouble();
						System.out.println("Risultati della ricerca:");
						for (Proiezione p : proiezioni.cercaProiezione(ricercaTitolo, ricercaGenere, ricercaDataInizio, ricercaDataFine, ricercaCostoMassimo)) {
							proiezioni.visualizzaProiezione(p);
						}
					}

					break;
				case 4:
					// Logica per uscire
					System.out.println("Uscita dall'applicazione. Arrivederci!");
					break;
				default:
					System.out.println("Scelta non valida. Riprova.");
			}
		} while (scelta != 4);
	}

	private static void menuCliente(Cliente cliente, GestoreProiezioni proiezioni, GestorePrenotazioni prenotazioni) {
		// Logica del menu per il cliente
		System.out.println("Benvenuto, " + cliente.getUsername() + "!");
		do{
		System.out.println("Cosa vuoi fare?");
		System.out.println("1. Cercare una proiezione");
		System.out.println("2. Visualizzare le mie prenotazioni");
		System.out.println("3. Logout");
		System.out.print("Inserisci la tua scelta: ");
		Scanner scanner = new Scanner(System.in);
		int scelta = scanner.nextInt();
		
		switch(scelta) {
			case 1:

				System.out.println("Inserire dati ricerca: ");
				System.out.print("Inserire titolo: ");
				ricercaTitolo = scanner.next();
				System.out.print("Inserire intervallo di date (YYYY-MM-DDTHH:MM): ");
				ricercaDataInizio = LocalDateTime.parse(scanner.next());
				ricercaDataFine = LocalDateTime.parse(scanner.next());
				System.out.print("Inserire genere: ");
				ricercaGenere = scanner.next();
				System.out.print("Inserire costo massimo: ");
				ricercaCostoMassimo = scanner.nextDouble();
				System.out.println("Risultati della ricerca");
				int i=0;
				for (Proiezione p : proiezioni.cercaProiezione(ricercaTitolo, ricercaGenere, ricercaDataInizio, ricercaDataFine, ricercaCostoMassimo)) {
					System.out.println("Proiezione " + (++i) + ":");
					proiezioni.visualizzaProiezione(p);
				}

				if(i>0){
					do{
					System.out.print("Vuoi fare una prenotazione? Inserisci il numero della proiezione o 0 per tornare indietro: ");
					int sceltaPrenotazione = scanner.nextInt();
					if(sceltaPrenotazione > 0 && sceltaPrenotazione <= i){
						int conta=0;
						for (Proiezione p : proiezioni.cercaProiezioni(ricerca)){
							conta++;
							if(conta==sceltaPrenotazione){
								System.out.print("Inserire il numero di biglietti da prenotare: ");
								int numeroBiglietti = scanner.nextInt();
								if(numeroBiglietti > 0){
									prenotazioni.creaPrenotazione(cliente.getUsername(), p, numeroBiglietti);
									System.out.println("Prenotazione effettuata con successo!");
								} else {
									System.out.println("Numero di biglietti non valido. Riprova.");
								}
							}
						}
					} else if (sceltaPrenotazione != 0) {
						System.out.println("Scelta non valida. Riprova.");
					}
				}while(sceltaPrenotazione != 0);
				}
				else{
					System.out.println("Nessuna proiezione trovata per la ricerca effettuata.");
				}

				break;
			case 2:
				System.out.println("Le tue prenotazioni:");
				for (Prenotazione p : prenotazioni.cercaPerCliente(cliente.getUsername())) {
					System.out.println(p.toString());
				}

				do{
					System.out.println("Cosa desideri fare? \n1. Modificare la data di una prenotazione \n2. Cancellare una prenotazione \n3. Indietro");
					System.out.print("Inserisci la tua scelta: ");
					int sceltaModifica = scanner.nextInt();
					switch(sceltaModifica) {
						case 1:
							System.out.print("Inserire il codice della prenotazione da modificare: ");
							String codicePrenotazione = scanner.next();
							System.out.println(prenotazioni.cercaPerCodice(codicePrenotazione).toString());
							System.out.print("Inserire la nuova data: ");
							String nuovaData = scanner.next();
							if(modificaPrenotazione(codicePrenotazione, nuovaData)) {
							System.out.println("Prenotazione modificata con successo!");
						} else {
							System.out.println("Errore durante la modifica della prenotazione. Riprova.");
						}
						break;
						case 2:
							System.out.print("Inserire il codice della prenotazione da cancellare: ");
							String codicePrenotazione = scanner.next();
							if(prenotazioni.eliminaPrenotazione(codicePrenotazione)) {
								System.out.println("Prenotazione cancellata con successo!");
							} else {
								System.out.println("Errore durante la cancellazione della prenotazione. Riprova.");
							}
						break;
						case 3:
						break;
						default:
							System.out.println("Scelta non valida. Riprova.");
					}
				}while(sceltaModifica != 3);
				break;
			case 3:
				System.out.println("Logout effettuato. Arrivederci, " + cliente.getUsername() + "!");
				break;
			default:
				System.out.println("Scelta non valida. Riprova.");
		}
	}while (scelta != 3);
	}
	private static void menuProiezionista(Proiezionista proiezionista, GestoreProiezioni proiezioni, GestorePrenotazioni prenotazioni) {
		// Logica del menu per il proiezionista
		System.out.println("Benvenuto, " + proiezionista.getUsername() + "!");
		do{
			System.out.println("Cosa vuoi fare?");
			System.out.println("1. Visualizzare le proiezioni");
			System.out.println("2. Creare una proiezione");
			System.out.println("3. Modificare una proiezione");
			System.out.println("4. Cancellare una proiezione");
			System.out.println("5. Logout");
			System.out.print("Inserisci la tua scelta: ");
			Scanner scanner = new Scanner(System.in);
			int scelta = scanner.nextInt();
			switch(scelta) {
				case 1:
					for(Proiezione p : proiezioni.getProiezioni()) {
						p.visualizzaProiezione();
					}
					break;
				case 2:
					System.out.print("Inserire data e ora (YYYY-MM-DDTHH:MM): ");
					LocalDateTime dataOra = LocalDateTime.parse(scanner.next());
					System.out.print("Inserire titolo: ");
					String titolo = scanner.next();
					System.out.print("Inserire genere: ");
					String genere = scanner.next();
					System.out.print("Inserire regista: ");
					String regista = scanner.next();
					System.out.print("Inserire anno: ");
					int anno = scanner.nextInt();
					System.out.print("Inserire durata in minuti: ");
					int durataMinuti = scanner.nextInt();
					System.out.print("Inserire età minima: ");
					int etaMinima = scanner.nextInt();
					System.out.print("Inserire prezzo del biglietto: ");
					double prezzoBiglietto = scanner.nextDouble();
					Proiezione nuova=new Proiezione(dataOra, titolo, genere, regista, anno, durataMinuti, etaMinima, prezzoBiglietto);
					if(proiezioni.aggiungiProiezione(nuova)) {
						System.out.println("Proiezione creata con successo!");
					} else {
						System.out.println("Errore durante la creazione della proiezione. Riprova.");
					}
					break;
				case 3:
					Proiezione modificata = proiezioni.scegliProiezione();
					System.out.print("Inserire titolo: ");
					String titolo = scanner.next();
					System.out.print("Inserire data e ora (YYYY-MM-DDTHH:MM): ");
					LocalDateTime dataOra = LocalDateTime.parse(scanner.next());
					System.out.print("Inserire genere: ");
					String genere = scanner.next();
					System.out.print("Inserire regista: ");
					String regista = scanner.next();
					System.out.print("Inserire anno: ");
					int anno = scanner.nextInt();
					System.out.print("Inserire durata in minuti: ");
					int durataMinuti = scanner.nextInt();
					System.out.print("Inserire età minima: ");
					int etaMinima = scanner.nextInt();
					System.out.print("Inserire prezzo del biglietto: ");
					double prezzoBiglietto = scanner.nextDouble();
					Proiezione nuova=new Proiezione(dataOra, titolo, genere, regista, anno, durataMinuti, etaMinima, prezzoBiglietto);
					if(proiezioni.modificaProiezione(modificata, nuova)) {
						System.out.println("Proiezione modificata con successo!");
					} else {
						System.out.println("Errore durante la modifica della proiezione. Riprova.");
					}
					break;
				case 4:
					if(proiezioni.eliminaProiezione(proiezioni.scegliProiezione())) {
						System.out.println("Proiezione cancellata con successo!");
					} else {
						System.out.println("Errore durante la cancellazione della proiezione. Riprova.");
					}
					break;
				case 5:
					System.out.println("Logout effettuato. Arrivederci, " + proiezionista.getUsername() + "!");
					break;
				default: 
					System.out.println("Scelta non valida. Riprova.");
			}
		}
		while(scelta!=3);
	}
	private static void menuBigliettaio(Bigliettaio bigliettaio, GestoreProiezioni proiezioni, GestorePrenotazioni prenotazioni) {
		// Logica del menu per il bigliettaio
		System.out.println("Benvenuto, " + bigliettaio.getUsername() + "!");
		do{
			System.out.println("Cosa vuoi fare?");
			System.out.println("1. Visualizzare le prenotazioni di oggi");
			System.out.println("2. Cercare una prenotazione");
			System.out.println("3. Logout");
			System.out.print("Inserisci la tua scelta: ");
			Scanner scanner = new Scanner(System.in);
			int scelta = scanner.nextInt();
			switch(scelta) {
				case 1:
					for(Prenotazione p : prenotazioni.prenotazioniDiOggi()) {
						System.out.println(p.toString());
					}
					break;
				case 2:
					System.out.println("Si vuole effettuare la ricerca per:\n1. Codice prenotazine\n2. Username cliente \n3. Titolo \n4. Intervallo");
					System.out.print("Inserisci la tua scelta: ");
					int sceltaRicerca = scanner.nextInt();
					switch(sceltaRicerca) {
						case 1:
							System.out.print("Inserire il codice della prenotazione da cercare: ");
							String codicePrenotazione = scanner.next();
							System.out.println(prenotazioni.cercaPerCodice(codicePrenotazione).toString());
							break;
						case 2:
							System.out.print("Inserire lo username del cliente da cercare: ");
							String usernameCliente = scanner.next();
							for(Prenotazione p : prenotazioni.cercaPerCliente(usernameCliente)) {
								System.out.println(p.toString());
							}
							break;
						case 3:
							System.out.print("Inserire il titolo della proiezione da cercare: ");
							String titoloProiezione = scanner.next();
							for(Prenotazione p : prenotazioni.cercaPerTitolo(titoloProiezione)) {
								System.out.println(p.toString());
							}
							break;
						case 4:
							System.out.print("Inserire la data di inizio (YYYY-MM-DD): ");
							LocalDateTime dataInizio = scanner.next();
							System.out.print("Inserire la data di fine (YYYY-MM-DD): ");
							LocalDateTime dataFine = scanner.next();

							for(Prenotazione p : prenotazioni.cercaPerIntervallo(dataInizio, dataFine)) {
								System.out.println(p.toString());
							}
							break;
						default:
							System.out.println("Scelta non valida. Riprova.");
					}
					break;
				case 3:
					System.out.println("Logout effettuato. Arrivederci, " + bigliettaio.getUsername() + "!");
					break;
				default: 
					System.out.println("Scelta non valida. Riprova.");
			}
		}
		while(scelta!=3);
	}

}
