package cinemax;
/**
 * Rappresenta una prenotazione effettuata da un cliente
 * per una determinata proiezione cinematografica.
 *
 * Una prenotazione è identificata da un codice univoco
 * e contiene il cliente, la proiezione scelta e il numero
 * di biglietti prenotati.
 *
 * @author Nome Cognome
 */
public class Prenotazione {

    private String codice;
    private String usernameCliente;
    private Proiezione proiezione;
    private int numeroBiglietti;
/**
 * Crea una nuova prenotazione.
 *
 * @param codice codice univoco della prenotazione
 * @param usernameCliente username del cliente che effettua la prenotazione
 * @param proiezione proiezione scelta dal cliente
 * @param numeroBiglietti numero di biglietti prenotati
 */
    public Prenotazione(String codice,String usernameCliente,Proiezione proiezione,int numeroBiglietti){
        if (numeroBiglietti <= 0) {
            throw new IllegalArgumentException("Il numero di biglietti deve essere maggiore di zero.");
        }
        this.codice = codice;
        this.usernameCliente = usernameCliente;
        this.proiezione = proiezione;
        this.numeroBiglietti = numeroBiglietti;
    }
/**
 * Restituisce il codice univoco della prenotazione.
 *
 * @return codice della prenotazione
 */
    public String getCodice() {
        return codice;
    }
/**
 * Restituisce lo username del cliente che ha effettuato la prenotazione.
 *
 * @return username del cliente
 */
    public String getUsernameCliente() {
        return usernameCliente;
    }
/**
 * Restituisce la proiezione associata alla prenotazione.
 *
 * @return proiezione prenotata
 */
    public Proiezione getProiezione() {
        return proiezione;
    }
/**
 * Restituisce il numero di biglietti prenotati.
 *
 * @return numero di biglietti
 */
    public int getNumeroBiglietti() {
        return numeroBiglietti;
    }
    public double getCostoTotale() {
        return numeroBiglietti * proiezione.getPrezzoBiglietto();
    }
    @Override
    public String toString() {
        return "Codice prenotazione: " + codice + "\nCliente: " + usernameCliente + "\nProiezione: " + proiezione + "\nNumero biglietti: " + numeroBiglietti + "\nCosto totale: " + getCostoTotale() + " €";
    }
}
