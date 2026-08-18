package cinemax;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestoreProiezioni {

    private List<Proiezione> proiezioni;

    public GestoreProiezioni() {
        proiezioni = new ArrayList<>();
    }

    public void caricaProiezioniDaFile(String percorso) throws IOException {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(percorso))) {

            String riga;

            // Salta la prima riga del file CSV
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

                Proiezione proiezione = new Proiezione(
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

    public List<Proiezione> getProiezioni() {
        return proiezioni;
    }

    public List<Proiezione> cercaProiezione(String titolo) {

        List<Proiezione> risultati = new ArrayList<>();

        for (Proiezione p : proiezioni) {

            if (p.getTitolo().toLowerCase()
                    .contains(titolo.toLowerCase())) {

                risultati.add(p);
            }
        }

        return risultati;
    }

    public void visualizzaProiezione(Proiezione p) {

        System.out.println("Titolo: " + p.getTitolo());
        System.out.println("Data/Ora: " + p.getDataOra());
        System.out.println("Costo: " + p.getPrezzoBiglietto() + " €");
        System.out.println("Posti liberi: 200");
    }

    public boolean aggiungiProiezione(
            Proiezione nuovaProiezione) {

        LocalDateTime nuovaInizio =
                nuovaProiezione.getDataOra();

        LocalDateTime nuovaFine =
                nuovaInizio.plusMinutes(
                        nuovaProiezione.getDurataMinuti()
                );

        for (Proiezione p : proiezioni) {

            LocalDateTime inizio = p.getDataOra();

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

    public boolean modificaProiezione(
            Proiezione proiezione,
            Proiezione nuovaProiezione) {

        proiezione.setDataOra(
                nuovaProiezione.getDataOra()
        );

        proiezione.setTitolo(
                nuovaProiezione.getTitolo()
        );

        proiezione.setGenere(
                nuovaProiezione.getGenere()
        );

        proiezione.setRegista(
                nuovaProiezione.getRegista()
        );

        proiezione.setAnno(
                nuovaProiezione.getAnno()
        );

        proiezione.setDurataMinuti(
                nuovaProiezione.getDurataMinuti()
        );

        proiezione.setEtaMinima(
                nuovaProiezione.getEtaMinima()
        );

        proiezione.setPrezzoBiglietto(
                nuovaProiezione.getPrezzoBiglietto()
        );

        return true;
    }

    public boolean eliminaProiezione(
            Proiezione proiezione) {

        return proiezioni.remove(proiezione);
    }
}
