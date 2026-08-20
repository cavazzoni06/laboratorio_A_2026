package cinemax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce le prenotazioni del cinema.
 *
 * Permette di creare, cercare, modificare ed eliminare prenotazioni,
 * controllare la disponibilità dei posti e gestire il salvataggio
 * e il caricamento delle prenotazioni da file.
 */
public class GestorePrenotazioni {

    private ArrayList<Prenotazione> prenotazioni;
    private GestoreProiezioni gestoreProiezioni;

    /**
     * Crea un nuovo gestore delle prenotazioni.
     *
     * @param gestoreProiezioni gestore contenente le proiezioni disponibili
     */
    public GestorePrenotazioni(
            GestoreProiezioni gestoreProiezioni) {

        this.prenotazioni = new ArrayList<>();
        this.gestoreProiezioni = gestoreProiezioni;
    }

    /**
     * Restituisce tutte le prenotazioni.
     *
     * @return copia della lista delle prenotazioni
     */
    public List<Prenotazione> getPrenotazioni() {
        return new ArrayList<>(prenotazioni);
    }

    /**
     * Cerca una prenotazione tramite il suo codice.
     *
     * @param codice codice della prenotazione
     * @return prenotazione trovata oppure null
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
     * Elimina una prenotazione.
     *
     * @param codice codice della prenotazione
     * @return true se eliminata, false altrimenti
     */
    public boolean eliminaPrenotazione(String codice) {

        Prenotazione prenotazione =
                cercaPerCodice(codice);

        if (prenotazione == null) {
            return false;
        }

        LocalDateTime adesso =
                LocalDateTime.now();

        /*
         * Non è possibile eliminare una prenotazione
         * se la proiezione è già iniziata.
         */
        if (!prenotazione
                .getProiezione()
                .getDataOra()
                .isAfter(adesso)) {

            return false;
        }

        prenotazioni.remove(prenotazione);

        return true;
    }

    /**
     * Cerca tutte le prenotazioni di un cliente.
     *
     * @param usernameCliente username del cliente
     * @return prenotazioni trovate
     */
    public List<Prenotazione> cercaPerCliente(
            String usernameCliente) {

        List<Prenotazione> risultato =
                new ArrayList<>();

        for (Prenotazione p : prenotazioni) {

            if (p.getUsernameCliente()
                    .equals(usernameCliente)) {

                risultato.add(p);
            }
        }

        return risultato;
    }

    /**
     * Cerca le prenotazioni in base al titolo.
     *
     * @param titolo titolo o parte del titolo
     * @return prenotazioni trovate
     */
    public List<Prenotazione> cercaPerTitolo(
            String titolo) {

        List<Prenotazione> risultato =
                new ArrayList<>();

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
     * Cerca prenotazioni comprese in un intervallo.
     *
     * @param inizio data iniziale
     * @param fine data finale
     * @return prenotazioni trovate
     */
    public List<Prenotazione> cercaPerIntervallo(
            LocalDateTime inizio,
            LocalDateTime fine) {

        List<Prenotazione> risultato =
                new ArrayList<>();

        for (Prenotazione p : prenotazioni) {

            LocalDateTime data =
                    p.getProiezione().getDataOra();

            if (!data.isBefore(inizio)
                    && !data.isAfter(fine)) {

                risultato.add(p);
            }
        }

        return risultato;
    }

    /**
     * Restituisce le prenotazioni relative alla data odierna.
     *
     * @return prenotazioni di oggi
     */
    public List<Prenotazione> prenotazioniDiOggi() {

        List<Prenotazione> risultato =
                new ArrayList<>();

        LocalDate oggi =
                LocalDate.now();

        for (Prenotazione p : prenotazioni) {

            if (p.getProiezione()
                    .getDataOra()
                    .toLocalDate()
                    .equals(oggi)) {

                risultato.add(p);
            }
        }

        return risultato;
    }

    /**
     * Calcola il numero totale di posti prenotati
     * per una determinata proiezione.
     *
     * @param proiezione proiezione da controllare
     * @return numero di posti prenotati
     */
    public int calcolaPostiPrenotati(
            Proiezione proiezione) {

        int totale = 0;

        for (Prenotazione p : prenotazioni) {

            if (p.getProiezione() == proiezione) {

                totale +=
                        p.getNumeroBiglietti();
            }
        }

        return totale;
    }

    /**
     * Genera un codice univoco.
     *
     * @return codice nel formato PREN-n
     */
    private String generaCodice() {

        int numero = 1;

        while (cercaPerCodice(
                "PREN-" + numero) != null) {

            numero++;
        }

        return "PREN-" + numero;
    }

    /**
     * Crea una nuova prenotazione.
     *
     * Controlla il numero di biglietti,
     * la data della proiezione e i posti disponibili.
     *
     * @param usernameCliente username del cliente
     * @param proiezione proiezione scelta
     * @param numeroBiglietti numero di biglietti
     * @return prenotazione creata
     */
    public Prenotazione creaPrenotazione(
            String usernameCliente,
            Proiezione proiezione,
            int numeroBiglietti) {

        if (numeroBiglietti <= 0) {

            throw new IllegalArgumentException(
                    "Il numero di biglietti deve essere maggiore di zero."
            );
        }

        if (proiezione == null) {

            throw new IllegalArgumentException(
                    "La proiezione non può essere null."
            );
        }

        /*
         * Non si può prenotare una proiezione
         * che è già iniziata.
         */
        if (!proiezione
                .getDataOra()
                .isAfter(LocalDateTime.now())) {

            throw new IllegalArgumentException(
                    "Non è possibile prenotare una proiezione già iniziata."
            );
        }

        int postiOccupati =
                calcolaPostiPrenotati(proiezione);

        int postiDisponibili =
                200 - postiOccupati;

        if (numeroBiglietti > postiDisponibili) {

            throw new IllegalArgumentException(
                    "Posti insufficienti. Disponibili: "
                            + postiDisponibili
            );
        }

        String codice =
                generaCodice();

        Prenotazione nuovaPrenotazione =
                new Prenotazione(
                        codice,
                        usernameCliente,
                        proiezione,
                        numeroBiglietti
                );

        /*
         * Non utilizziamo più aggiungiPrenotazione().
         * La prenotazione viene inserita direttamente.
         */
        prenotazioni.add(nuovaPrenotazione);

        return nuovaPrenotazione;
    }

    /**
     * Modifica la data di una prenotazione.
     *
     * Il gestore cerca automaticamente una proiezione
     * dello stesso film nella nuova data richiesta.
     *
     * @param codice codice della prenotazione
     * @param nuovaDataOra nuova data e ora
     * @return true se modificata, false altrimenti
     */
    public boolean modificaPrenotazione(
            String codice,
            LocalDateTime nuovaDataOra) {

        Prenotazione prenotazione =
                cercaPerCodice(codice);

        if (prenotazione == null) {
            return false;
        }

        LocalDateTime adesso =
                LocalDateTime.now();

        /*
         * La prenotazione originale
         * deve essere ancora futura.
         */
        if (!prenotazione
                .getProiezione()
                .getDataOra()
                .isAfter(adesso)) {

            return false;
        }

        /*
         * Anche la nuova data deve essere futura.
         */
        if (nuovaDataOra == null
                || !nuovaDataOra.isAfter(adesso)) {

            return false;
        }

        Proiezione nuovaProiezione = null;

        /*
         * Cerca lo stesso film nella nuova data.
         */
        for (Proiezione p :
                gestoreProiezioni.getProiezioni()) {

            if (p.getTitolo().equalsIgnoreCase(
                    prenotazione
                            .getProiezione()
                            .getTitolo())

                    && p.getDataOra()
                            .equals(nuovaDataOra)) {

                nuovaProiezione = p;
                break;
            }
        }

        /*
         * Nessuna proiezione trovata
         * nella data richiesta.
         */
        if (nuovaProiezione == null) {
            return false;
        }

        int postiOccupati =
                calcolaPostiPrenotati(
                        nuovaProiezione);

        int postiDisponibili =
                200 - postiOccupati;

        if (prenotazione
                .getNumeroBiglietti()
                > postiDisponibili) {

            return false;
        }

        prenotazione.setProiezione(
                nuovaProiezione);

        return true;
    }

    /**
     * Salva le prenotazioni su file.
     *
     * @param percorso percorso del file
     * @throws IOException in caso di errore
     */
    public void salvaPrenotazioniSuFile(
            String percorso)
            throws IOException {

        try (FileWriter writer =
                     new FileWriter(percorso)) {

            for (Prenotazione p : prenotazioni) {

                writer.write(
                        p.getCodice() + ";" +
                        p.getUsernameCliente() + ";" +
                        p.getProiezione()
                                .getDataOra() + ";" +
                        p.getProiezione()
                                .getTitolo() + ";" +
                        p.getNumeroBiglietti() +
                        System.lineSeparator()
                );
            }
        }
    }

    /**
     * Carica le prenotazioni da file.
     *
     * @param percorso percorso del file
     * @param proiezioniDisponibili proiezioni disponibili
     * @throws IOException in caso di errore
     */
    public void caricaPrenotazioniDaFile(
            String percorso,
            List<Proiezione> proiezioniDisponibili)
            throws IOException {

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(percorso))) {

            String riga;

            while ((riga = reader.readLine())
                    != null) {

                String[] dati =
                        riga.split(";");

                /*
                 * Una riga valida deve avere
                 * esattamente 5 campi.
                 */
                if (dati.length != 5) {
                    continue;
                }

                try {

                    String codice =
                            dati[0];

                    String usernameCliente =
                            dati[1];

                    LocalDateTime dataOra =
                            LocalDateTime.parse(
                                    dati[2]);

                    String titolo =
                            dati[3];

                    int numeroBiglietti =
                            Integer.parseInt(
                                    dati[4]);

                    Proiezione proiezioneTrovata =
                            null;

                    for (Proiezione p :
                            proiezioniDisponibili) {

                        if (p.getTitolo()
                                .equals(titolo)

                                && p.getDataOra()
                                        .equals(dataOra)) {

                            proiezioneTrovata = p;
                            break;
                        }
                    }

                    if (proiezioneTrovata != null
                            && cercaPerCodice(codice)
                            == null) {

                        Prenotazione prenotazione =
                                new Prenotazione(
                                        codice,
                                        usernameCliente,
                                        proiezioneTrovata,
                                        numeroBiglietti
                                );

                        prenotazioni.add(
                                prenotazione);
                    }

                } catch (Exception e) {

                    System.out.println(
                            "Riga prenotazione non valida: "
                                    + riga
                    );
                }
            }
        }
    }
}
