

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestoreUtenti {
	private List<Utente> utenti;

    public boolean aggiungiUtente(Utente utente) {
		// Controlla se l'username esiste già
		if (usernameEsistente(utente.getUsername())) {
        	return false;
    	}

		String tipo;

    	if (utente instanceof Cliente) {
        	tipo = "CLIENTE";
    	} else if (utente instanceof Bigliettaio) {
        	tipo = "BIGLIETTAIO";
    	} else if (utente instanceof Proiezionista) {
        	tipo = "PROIEZIONISTA";
    	} else {
        	return false;
    	}

        try (BufferedWriter bw = new BufferedWriter(
            new FileWriter("utenti.csv", true))) {

        bw.write(
            utente.getUsername() + "," +
            utente.getPassword() + "," +
            utente.getNome() + "," +
			utente.getCognome() + "," +
			utente.getDataNascita() + "," +
			utente.getDomicilio() + "," +
			tipo
        );

        bw.newLine();
		utenti.add(utente); // Aggiungi l'utente alla lista in memoria
		return true;

    } catch (IOException e) {
        return false;
    }
    }

    public void leggiUtenti() {
        utenti = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("utenti.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 7) {
					String username = parts[0];
					String password = parts[1];
					String nome = parts[2];
					String cognome = parts[3];
					String dataNascita = parts[4];
					String domicilio = parts[5];
					String tipo = parts[6];
 
					Utente utente;
					switch (tipo) {
						case "CLIENTE":
							utente = new Cliente(username, password, nome, cognome, dataNascita, domicilio);
							break;
						case "BIGLIETTAIO":
							utente = new Bigliettaio(username, password, nome, cognome, dataNascita, domicilio);
							break;
						case "PROIEZIONISTA":
							utente = new Proiezionista(username, password, nome, cognome, dataNascita, domicilio);
							break;
						default:
							continue; // Skip unknown types
					}
					utenti.add(utente);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    public Utente login(String username, String password) {
        for (Utente u : utenti) {
			if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
				return u;
			}
		}
		return null;
    }

    public boolean usernameEsistente(String username) {

    for (Utente u : utenti) {
        if (u.getUsername().equals(username)) {
            return true;
        }
    }
    
	return false;

	}
}