package cinemax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Gestisce le prenotazioni del cinema.
 *
 * Permette di creare, cercare, modificare ed eliminare prenotazioni,
 * controllare la disponibilità dei posti e gestire il salvataggio
 * e il caricamento delle prenotazioni da file.
 * Sede di Varese (VA)
 766768 Cavazzoni Stella
 760763 Haddaji Aziz
 755773 Boubaker May Kamar
 763711 Marzouki Siwar
 */

public class GestorePrenotazioni {

    /** Elenco delle prenotazioni gestite. */
    private ArrayList<Prenotazione> prenotazioni;

    /**
     * Crea un nuovo gestore delle prenotazioni.
     */
    public GestorePrenotazioni() {
        prenotazioni = new ArrayList<>();
    }

    /**
     * Restituisce tutte le prenotazioni.
     *
     * @return lista delle prenotazioni
     */
    public List<Prenotazione> getPrenotazioni() {
        return new ArrayList<>(prenotazioni);
    }

    /**
     * Cerca una prenotazione tramite il suo codice.
     *
     * @param codice codice della prenotazione da cercare
     * @return la prenotazione trovata, oppure null se non esiste
     */
    public Prenotazione cercaPerCodice(String codice) {
        for (Prenotazione p : prenotazioni) {
            if (p.getCodice().equals(codice)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Aggiunge una nuova prenotazione.
     *
     * @param prenotazione prenotazione da aggiungere
     * @throws IllegalArgumentException se esiste già una prenotazione con lo stesso codice
     */
    public void aggiungiPrenotazione(Prenotazione prenotazione) {
        if (cercaPerCodice(prenotazione.getCodice()) != null) {
            throw new IllegalArgumentException("Esiste già una prenotazione con questo codice.");
        }
        prenotazioni.add(prenotazione);
    }

    /**
     * Elimina una prenotazione tramite il suo codice.
     *
     * @param codice codice della prenotazione da eliminare
     * @return true se la prenotazione è stata eliminata,
     *         false se non esiste o se la data della proiezione
     *         non consente l'eliminazione
     */
    public boolean eliminaPrenotazione(String codice) {
        Prenotazione prenotazione = cercaPerCodice(codice);

        if (prenotazione == null) {
            return false;
        }

        LocalDate oggi = LocalDate.now();
        if (!prenotazione.getProiezione().getDataOra().toLocalDate().isBefore(oggi)) {
            return false;
        }

        prenotazioni.remove(prenotazione);
        return true;
    }

    /**
     * Cerca tutte le prenotazioni effettuate da un cliente.
     *
     * @param usernameCliente username del cliente
     * @return lista delle prenotazioni del cliente
     */
    public List<Prenotazione> cercaPerCliente(String usernameCliente) {
        List<Prenotazione> risultato = new ArrayList<>();
        for (Prenotazione p : prenotazioni) {
            if (p.getUsernameCliente().equals(usernameCliente)) {
                risultato.add(p);
            }
        }
        return risultato;
    }
 
    /**
     * Cerca le prenotazioni in base al titolo del film.
     * La ricerca accetta anche una parte del titolo.
     *
     * @param titolo titolo o parte del titolo del film
     * @return lista delle prenotazioni trovate
     */
    public List<Prenotazione> cercaPerTitolo(String titolo) {
        List<Prenotazione> risultato = new ArrayList<>();

        for (Prenotazione p : prenotazioni) {
            if (p.getProiezione()
                    .getTitolo()
                    .toLowerCase()
                    .contains(titolo.toLowerCase())) {
                risultato.add(p);
            }
        }
        return risultato;
    }

    /**
     * Cerca le prenotazioni con proiezione compresa
     * in un determinato intervallo di date.
     *
     * @param inizio data e ora iniziale
     * @param fine data e ora finale
     * @return lista delle prenotazioni trovate
     */
    public List<Prenotazione> cercaPerIntervallo(LocalDateTime inizio, LocalDateTime fine) {
        List<Prenotazione> risultato = new ArrayList<>();
        for (Prenotazione p : prenotazioni) {
            LocalDateTime data = p.getProiezione().getDataOra();
            if (!data.isBefore(inizio) && !data.isAfter(fine)) {
                risultato.add(p);
            }
        }
        return risultato;
    }

    /**
     * Restituisce le prenotazioni relative alla data odierna.
     *
     * @return lista delle prenotazioni di oggi
     */
    public List<Prenotazione> prenotazioniDiOggi() {
        List<Prenotazione> risultato = new ArrayList<>();
        LocalDate oggi = LocalDate.now();

        for (Prenotazione p : prenotazioni) {
            if (p.getProiezione().getDataOra()
                    .toLocalDate().equals(oggi)) {
                risultato.add(p);
            }
        }

        return risultato;
    }

    /**
     * Calcola il numero totale di posti già prenotati
     * per una determinata proiezione.
     *
     * @param proiezione proiezione di cui calcolare i posti prenotati
     * @return numero totale di posti già prenotati
     */
    public int calcolaPostiPrenotati(Proiezione proiezione) {
        int totale = 0;
        for (Prenotazione p : prenotazioni) {
            if (p.getProiezione().getTitolo().equals(proiezione.getTitolo())
                    && p.getProiezione().getDataOra().equals(proiezione.getDataOra())) {
                totale += p.getNumeroBiglietti();
            }
        }
        return totale;
    }

    /**
     * Genera automaticamente un codice univoco per una nuova prenotazione.
     * Il codice ha il formato PREN-n, dove n è un numero progressivo.
     *
     * @return codice univoco della nuova prenotazione
     */
    private String generaCodice() {
        int numero = 1;

        while (cercaPerCodice("PREN-" + numero) != null) {
            numero++;
        }

        return "PREN-" + numero;
    }

    /**
     * Crea una nuova prenotazione per una determinata proiezione.
     * Il codice della prenotazione viene generato automaticamente.
     * La prenotazione viene creata solo se sono disponibili
     * abbastanza posti.
     *
     * @param usernameCliente username del cliente
     * @param proiezione proiezione da prenotare
     * @param numeroBiglietti numero di biglietti richiesti
     * @return la nuova prenotazione creata
     * @throws IllegalArgumentException se il numero di biglietti
     *         non è valido o se i posti disponibili non sono sufficienti
     */
    public Prenotazione creaPrenotazione(String usernameCliente, Proiezione proiezione,
            int numeroBiglietti) {
        String codice = generaCodice();
        if (numeroBiglietti <= 0) {
            throw new IllegalArgumentException("Il numero di biglietti deve essere maggiore di zero.");
        }

        LocalDateTime adesso = LocalDateTime.now();
        if (!proiezione.getDataOra().isAfter(adesso)) {
            throw new IllegalArgumentException(
                    "Non è possibile prenotare una proiezione già iniziata.");
        }

        int postiOccupati = calcolaPostiPrenotati(proiezione);
        int postiDisponibili = 200 - postiOccupati;
        if (numeroBiglietti > postiDisponibili) {
            throw new IllegalArgumentException(
                    "Posti insufficienti. Disponibili: " + postiDisponibili);
        }

        Prenotazione nuovaPrenotazione = new Prenotazione(
                codice, usernameCliente, proiezione, numeroBiglietti);

        aggiungiPrenotazione(nuovaPrenotazione);
        return nuovaPrenotazione;
    }

    /**
     * Modifica la proiezione associata a una prenotazione.
     * La modifica è consentita solo se la prenotazione esiste,
     * se la vecchia e la nuova proiezione sono future
     * e se nella nuova proiezione ci sono posti sufficienti.
     *
     * @param codice codice della prenotazione da modificare
     * @param nuovaProiezione nuova proiezione scelta
     * @return true se la modifica è stata effettuata,
     *         false altrimenti
     */
    public boolean modificaPrenotazione(String codice, Proiezione nuovaProiezione) {
        Prenotazione prenotazione = cercaPerCodice(codice);

        if (prenotazione == null) {
            return false;
        }
        if (!prenotazione.getProiezione().getTitolo()
                .equalsIgnoreCase(nuovaProiezione.getTitolo())) {
            return false;
        }

        LocalDate oggi = LocalDate.now();
        if (!prenotazione.getProiezione().getDataOra()
                .toLocalDate().isAfter(oggi)) {
            return false;
        }
        if (!nuovaProiezione.getDataOra().toLocalDate().isAfter(oggi)) {
            return false;
        }

        int postiOccupati = calcolaPostiPrenotati(nuovaProiezione);
        int postiDisponibili = 200 - postiOccupati;
        if (prenotazione.getNumeroBiglietti() > postiDisponibili) {
            return false;
        }

        prenotazione.setProiezione(nuovaProiezione);
        return true;
    }

    /**
     * Salva tutte le prenotazioni su un file di testo.
     *
     * @param percorso percorso del file su cui salvare le prenotazioni
     * @throws IOException se si verifica un errore durante la scrittura
     */
    public void salvaPrenotazioniSuFile(String percorso) throws IOException {
        FileWriter writer = new FileWriter(percorso);

        for (Prenotazione p : prenotazioni) {
            writer.write(
                    p.getCodice() + ";"
                            + p.getUsernameCliente() + ";"
                            + p.getProiezione().getDataOra() + ";"
                            + p.getProiezione().getTitolo() + ";"
                            + p.getNumeroBiglietti()
                            + System.lineSeparator());
        }

        writer.close();
    }

    /**
     * Carica le prenotazioni da un file di testo.
     *
     * Per ogni prenotazione letta cerca la proiezione corrispondente
     * nella lista delle proiezioni disponibili, confrontando titolo e data.
     * Le prenotazioni con codice già presente non vengono aggiunte nuovamente.
     *
     * @param percorso percorso del file da cui caricare le prenotazioni
     * @param proiezioniDisponibili lista delle proiezioni disponibili
     * @throws IOException se si verifica un errore durante la lettura del file
     */
    public void caricaPrenotazioniDaFile(String percorso,
            List<Proiezione> proiezioniDisponibili) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(percorso));
        String riga;
        while ((riga = reader.readLine()) != null) {
            String[] dati = riga.split(";");

            if (dati.length != 5) {
                continue;
            }

            try {
                String codice = dati[0];
                String usernameCliente = dati[1];
                LocalDateTime dataOra = LocalDateTime.parse(dati[2]);
                String titolo = dati[3];
                int numeroBiglietti = Integer.parseInt(dati[4]);

                Proiezione proiezioneTrovata = null;
                for (Proiezione p : proiezioniDisponibili) {
                    if (p.getTitolo().equals(titolo)
                            && p.getDataOra().equals(dataOra)) {
                        proiezioneTrovata = p;
                        break;
                    }
                }

                if (proiezioneTrovata != null
                        && cercaPerCodice(codice) == null) {
                    Prenotazione prenotazione = new Prenotazione(
                            codice,
                            usernameCliente,
                            proiezioneTrovata,
                            numeroBiglietti);

                    prenotazioni.add(prenotazione);
                }
            } catch (Exception e) {
                System.out.println(
                        "Riga non valida nel file prenotazioni: " + riga);
            }
        }
        reader.close();
    }
}