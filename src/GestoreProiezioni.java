package cinemax;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestoreProiezioni {

    private List<Proiezione> proiezioni;
    private GestorePrenotazioni gestorePrenotazioni;

    public GestoreProiezioni() {
        proiezioni = new ArrayList<>();
    }

    /**
     * Permette di collegare il gestore delle prenotazioni
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

                LocalDateTime dataOra =
                        LocalDateTime.parse(
                                dati[0].replace("\"", ""),
                                formatter
                        );

                String titolo = dati[1].replace("\"", "");
                String genere = dati[2].replace("\"", "");
                String regista = dati[3].replace("\"", "");

                int anno = Integer.parseInt(dati[4]);
                int durataMinuti = Integer.parseInt(dati[5]);
                int etaMinima = Integer.parseInt(dati[6]);

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

    public void visualizzaProiezione(Proiezione p) {

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

            if (nuovaInizio.isBefore(fine)
                    && nuovaFine.isAfter(inizio)) {

                return false;
            }
        }

        proiezioni.add(nuovaProiezione);

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
                            .calcolaPostiPrenotati(proiezione);
        }

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
                            .calcolaPostiPrenotati(proiezione);
        }

        if (postiPrenotati > 0) {
            return false;
        }

        return proiezioni.remove(proiezione);
    }

    // =====================================================
    // GET GESTORE PRENOTAZIONI
    // =====================================================

    public GestorePrenotazioni getGestorePrenotazioni() {
        return gestorePrenotazioni;
    }
}
