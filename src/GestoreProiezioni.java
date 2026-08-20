
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestoreProiezioni {

    private List<Proiezione> proiezioni;
    private GestorePrenotazioni gestorePrenotazioni;

    private static final String PERCORSO_FILE =
            "data/proiezioni.csv";

    public GestoreProiezioni() {
        proiezioni = new ArrayList<>();
    }

    /**
     * Collega il gestore delle prenotazioni
     * al gestore delle proiezioni.
     *
     * @param gestorePrenotazioni gestore delle prenotazioni
     */
    public void setGestorePrenotazioni(
            GestorePrenotazioni gestorePrenotazioni) {

        this.gestorePrenotazioni = gestorePrenotazioni;
    }

    // =====================================================
    // CARICA PROIEZIONI DA FILE
    // =====================================================

    public void caricaProiezioniDaFile(String percorso)
            throws IOException {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(percorso))) {

            String riga;

            // Salta l'intestazione del CSV
            reader.readLine();

            while ((riga = reader.readLine()) != null) {

                String[] dati = riga.split(",");

                if (dati.length != 8) {
                    continue;
                }

                try {

                    LocalDateTime dataOra =
                            LocalDateTime.parse(
                                    dati[0].replace("\"", ""),
                                    formatter
                            );

                    String titolo =
                            dati[1].replace("\"", "");

                    String genere =
                            dati[2].replace("\"", "");

                    String regista =
                            dati[3].replace("\"", "");

                    int anno =
                            Integer.parseInt(dati[4]);

                    int durataMinuti =
                            Integer.parseInt(dati[5]);

                    int etaMinima =
                            Integer.parseInt(dati[6]);

                    double prezzoBiglietto =
                            Double.parseDouble(dati[7]);

                    Proiezione proiezione =
                            new Proiezione(
                                    dataOra,
                                    titolo,
                                    genere,
                                    regista,
                                    anno,
                                    durataMinuti,
                                    etaMinima,
                                    prezzoBiglietto
                            );

                    proiezioni.add(proiezione);

                } catch (Exception e) {

                    System.out.println(
                            "Riga non valida nel file proiezioni: "
                                    + riga
                    );
                }
            }
        }
    }

    // =====================================================
    // SALVA PROIEZIONI SU FILE
    // =====================================================

    public void salvaProiezioniSuFile(String percorso)
            throws IOException {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (FileWriter writer =
                     new FileWriter(percorso)) {

            // Intestazione CSV
            writer.write(
                    "data_ora_proiezione,titolo_film,genere,regista,anno,"
                            + "durata_minuti,eta_minima,prezzo_biglietto"
                            + System.lineSeparator()
            );

            for (Proiezione p : proiezioni) {

                writer.write(
                        "\"" + p.getDataOra().format(formatter) + "\"," +
                        "\"" + p.getTitolo() + "\"," +
                        "\"" + p.getGenere() + "\"," +
                        "\"" + p.getRegista() + "\"," +
                        p.getAnno() + "," +
                        p.getDurataMinuti() + "," +
                        p.getEtaMinima() + "," +
                        p.getPrezzoBiglietto() +
                        System.lineSeparator()
                );
            }
        }
    }

    // =====================================================
    // GET PROIEZIONI
    // =====================================================

    public List<Proiezione> getProiezioni() {
        return proiezioni;
    }

    // =====================================================
    // SCEGLI PROIEZIONE
    // =====================================================

    public Proiezione scegliProiezione() {

        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "Scegli la proiezione da modificare:"
        );

        for (int i = 0; i < proiezioni.size(); i++) {

            System.out.println(
                    (i + 1)
                            + " - "
                            + proiezioni.get(i).getTitolo()
                            + " - "
                            + proiezioni.get(i).getDataOra()
            );
        }

        int scelta = scanner.nextInt();

        if (scelta < 1 ||
                scelta > proiezioni.size()) {

            return null;
        }

        return proiezioni.get(scelta - 1);
    }

    // =====================================================
    // CERCA PROIEZIONE
    // =====================================================

    public List<Proiezione> cercaProiezione(
            String titolo,
            String genere,
            LocalDateTime dataInizio,
            LocalDateTime dataFine,
            Double costoMassimo) {

        List<Proiezione> risultati =
                new ArrayList<>();

        for (Proiezione p : proiezioni) {

            boolean trovato = true;

            if (titolo != null &&
                    !titolo.isEmpty()) {

                if (!p.getTitolo()
                        .toLowerCase()
                        .contains(
                                titolo.toLowerCase())) {

                    trovato = false;
                }
            }

            if (genere != null &&
                    !genere.isEmpty()) {

                if (!p.getGenere()
                        .equalsIgnoreCase(genere)) {

                    trovato = false;
                }
            }

            if (dataInizio != null) {

                if (p.getDataOra()
                        .isBefore(dataInizio)) {

                    trovato = false;
                }
            }

            if (dataFine != null) {

                if (p.getDataOra()
                        .isAfter(dataFine)) {

                    trovato = false;
                }
            }

            if (costoMassimo != null) {

                if (p.getPrezzoBiglietto()
                        > costoMassimo) {

                    trovato = false;
                }
            }

            if (trovato) {
                risultati.add(p);
            }
        }

        return risultati;
    }

    // =====================================================
    // VISUALIZZA PROIEZIONE
    // =====================================================

    public void visualizzaProiezione(
            Proiezione p) {

        int postiTotali = 200;
        int postiPrenotati = 0;

        if (gestorePrenotazioni != null) {

            postiPrenotati =
                    gestorePrenotazioni
                            .calcolaPostiPrenotati(p);
        }

        int postiLiberi =
                postiTotali - postiPrenotati;

        System.out.println(
                "Titolo: " + p.getTitolo());

        System.out.println(
                "Genere: " + p.getGenere());

        System.out.println(
                "Regista: " + p.getRegista());

        System.out.println(
                "Anno: " + p.getAnno());

        System.out.println(
                "Data/Ora: " + p.getDataOra());

        System.out.println(
                "Durata: "
                        + p.getDurataMinuti()
                        + " minuti");

        System.out.println(
                "Età minima: "
                        + p.getEtaMinima());

        System.out.println(
                "Costo: "
                        + p.getPrezzoBiglietto()
                        + " €");

        System.out.println(
                "Posti liberi: "
                        + postiLiberi);
    }

    // =====================================================
    // AGGIUNGI PROIEZIONE
    // =====================================================

    public boolean aggiungiProiezione(
            Proiezione nuovaProiezione) {

        LocalDateTime nuovaInizio =
                nuovaProiezione.getDataOra();

        LocalDateTime nuovaFine =
                nuovaInizio.plusMinutes(
                        nuovaProiezione
                                .getDurataMinuti()
                );

        for (Proiezione p : proiezioni) {

            LocalDateTime inizio =
                    p.getDataOra();

            LocalDateTime fine =
                    inizio.plusMinutes(
                            p.getDurataMinuti()
                    );

            // Controlla eventuali sovrapposizioni
            if (nuovaInizio.isBefore(fine)
                    && nuovaFine.isAfter(inizio)) {

                return false;
            }
        }

        proiezioni.add(nuovaProiezione);

        // Aggiorna automaticamente il CSV
        try {

            salvaProiezioniSuFile(PERCORSO_FILE);

        } catch (IOException e) {

            // Se il salvataggio fallisce,
            // annulla anche l'aggiunta nella lista
            proiezioni.remove(nuovaProiezione);

            System.out.println(
                    "Errore durante il salvataggio: "
                            + e.getMessage()
            );

            return false;
        }

        return true;
    }

    // =====================================================
    // MODIFICA PROIEZIONE
    // =====================================================

    public boolean modificaProiezione(
            Proiezione proiezione,
            Proiezione nuovaProiezione) {

        int postiPrenotati = 0;

        if (gestorePrenotazioni != null) {

            postiPrenotati =
                    gestorePrenotazioni
                            .calcolaPostiPrenotati(
                                    proiezione);
        }

        // Non si può modificare se ci sono prenotazioni
        if (postiPrenotati > 0) {
            return false;
        }

        LocalDateTime nuovoInizio =
                nuovaProiezione.getDataOra();

        LocalDateTime nuovaFine =
                nuovoInizio.plusMinutes(
                        nuovaProiezione
                                .getDurataMinuti()
                );

        // Controllo sovrapposizione
        for (Proiezione p : proiezioni) {

            if (p == proiezione) {
                continue;
            }

            LocalDateTime inizio =
                    p.getDataOra();

            LocalDateTime fine =
                    inizio.plusMinutes(
                            p.getDurataMinuti()
                    );

            if (nuovoInizio.isBefore(fine)
                    && nuovaFine.isAfter(inizio)) {

                return false;
            }
        }

        // Salviamo i vecchi dati nel caso
        // il salvataggio del file fallisca
        LocalDateTime vecchiaDataOra =
                proiezione.getDataOra();

        String vecchioTitolo =
                proiezione.getTitolo();

        String vecchioGenere =
                proiezione.getGenere();

        String vecchioRegista =
                proiezione.getRegista();

        int vecchioAnno =
                proiezione.getAnno();

        int vecchiaDurata =
                proiezione.getDurataMinuti();

        int vecchiaEtaMinima =
                proiezione.getEtaMinima();

        double vecchioPrezzo =
                proiezione.getPrezzoBiglietto();

        // Modifica l'oggetto già presente nella lista
        proiezione.setDataOra(
                nuovaProiezione.getDataOra());

        proiezione.setTitolo(
                nuovaProiezione.getTitolo());

        proiezione.setGenere(
                nuovaProiezione.getGenere());

        proiezione.setRegista(
                nuovaProiezione.getRegista());

        proiezione.setAnno(
                nuovaProiezione.getAnno());

        proiezione.setDurataMinuti(
                nuovaProiezione
                        .getDurataMinuti());

        proiezione.setEtaMinima(
                nuovaProiezione
                        .getEtaMinima());

        proiezione.setPrezzoBiglietto(
                nuovaProiezione
                        .getPrezzoBiglietto());

        // Aggiorna automaticamente il CSV
        try {

            salvaProiezioniSuFile(PERCORSO_FILE);

        } catch (IOException e) {

            // Ripristina i dati precedenti
            // se il salvataggio fallisce
            proiezione.setDataOra(vecchiaDataOra);
            proiezione.setTitolo(vecchioTitolo);
            proiezione.setGenere(vecchioGenere);
            proiezione.setRegista(vecchioRegista);
            proiezione.setAnno(vecchioAnno);
            proiezione.setDurataMinuti(vecchiaDurata);
            proiezione.setEtaMinima(vecchiaEtaMinima);
            proiezione.setPrezzoBiglietto(vecchioPrezzo);

            System.out.println(
                    "Errore durante il salvataggio: "
                            + e.getMessage()
            );

            return false;
        }

        return true;
    }

    // =====================================================
    // ELIMINA PROIEZIONE
    // =====================================================

    public boolean eliminaProiezione(
            Proiezione proiezione) {

        int postiPrenotati = 0;

        if (gestorePrenotazioni != null) {

            postiPrenotati =
                    gestorePrenotazioni
                            .calcolaPostiPrenotati(
                                    proiezione);
        }

        // Non si può eliminare se esistono prenotazioni
        if (postiPrenotati > 0) {
            return false;
        }

        int indice =
                proiezioni.indexOf(proiezione);

        if (indice == -1) {
            return false;
        }

        // Elimina dalla lista
        proiezioni.remove(indice);

        // Aggiorna automaticamente il CSV
        try {

            salvaProiezioniSuFile(PERCORSO_FILE);

        } catch (IOException e) {

            // Se il salvataggio fallisce,
            // rimette la proiezione nella lista
            proiezioni.add(indice, proiezione);

            System.out.println(
                    "Errore durante il salvataggio: "
                            + e.getMessage()
            );

            return false;
        }

        return true;
    }

    // =====================================================
    // GET GESTORE PRENOTAZIONI
    // =====================================================

    public GestorePrenotazioni
            getGestorePrenotazioni() {

        return gestorePrenotazioni;
    }
}
