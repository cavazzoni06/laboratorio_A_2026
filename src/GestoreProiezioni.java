pachkage cinemax;


import java.util.ArrayLits;
import java.util.Lits;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.dateTimeFormatter


public class GestoreProiezioni  {
  private Lits<Proiezione> proiezioni;

  public GestoreProiezione() {
    proiezioni = new ArrayLits();
  }
  public void caricaProiezioniDaFile(String percorso) Throws IOException {
     DateTimeFormatter formatter =
             DateTimeFormatter.ofPattern("yyy-MM-dd HH:ss");
     try (BufferedReader reader = new BufferredReader(new FileReader(percorso))){
         String riga;
         // Salta La prima riga del file csv
       reader.readLine();
       while ((riga = reader.readerLine() != null){
             String[] dati = riga.split(",");
              LocalDateTime dataOra =
                       LocaleDateTime.parse(dati[0].replace("\"",""), formatter);
              String titolo = dati[1].replace("\"","");
              String genere = dati[2].replace("\"","");
              String regista = dati[3].replace("\"","");

              int anno = Integer.parseInt(dati[4]);
              int durataMinuti = Integer.parseInt(dati[4]);
              int etaMinima = Integer.parseInt(dati[6]);
              double prezzoBiglietto = double.parseDouble(dati[7]);
         
              Proiezione proiezione = new proiezione(
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
  public Lista<Proiezione> getProiezioni(){
       return proiezione;
  }
  public List<proiezione> cercaProiezione(String titolo) {
     Lista<Proiezione> risulatti = new ArrayList<>();
    for (Proiezione p : proiezione) {
        if (p.getTitolo().toLowerCase().contains(titolo.toLowerCase())){
            risultati.add(p);
        }
    }
    return risultati;
  }
  
}
