
/**
 * Rappresenta una prenotazione effettuata da un cliente
 * per una determinata proiezione cinematografica.
 *
 * Una prenotazione è identificata da un codice univoco
 * e contiene il cliente, la proiezione scelta e il numero
 * di biglietti prenotati.
 *
 * Sede di Varese (VA)
 766768 Cavazzoni Stella
 760763 Haddaji Aziz
 755773 Boubaker May Kamar
 763711 Marzouki Siwar
 */
public class Prenotazione {

    /** Codice univoco della prenotazione. */
    private String codice;
    /** Username del cliente che ha effettuato la prenotazione. */
    private String usernameCliente;
    /** Proiezione associata alla prenotazione. */
    private Proiezione proiezione;
    /** Numero di biglietti prenotati. */
    private int numeroBiglietti;
/**
 * Crea una nuova prenotazione.
 *
 * @param codice codice univoco della prenotazione
 * @param usernameCliente username del cliente che effettua la prenotazione
 * @param proiezione proiezione scelta dal cliente
 * @param numeroBiglietti numero di biglietti prenotati
 * @throws IllegalArgumentException se il numero di biglietti
 *         è minore o uguale a zero
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
/**
 * Calcola il costo totale della prenotazione.
 *
 * @return costo totale della prenotazione
 */
public double getCostoTotale() {
    return numeroBiglietti * proiezione.getPrezzoBiglietto();
}

/**
 * Restituisce una rappresentazione testuale della prenotazione.
 *
 * @return stringa contenente i dati della prenotazione
 */
@Override
public String toString() {
    return "Codice prenotazione: " + codice
            + "\nCliente: " + usernameCliente
            + "\nProiezione: " + proiezione
            + "\nNumero biglietti: " + numeroBiglietti
            + "\nCosto totale: " + getCostoTotale() + " €";
}

/**
 * Modifica la proiezione associata alla prenotazione.
 *
 * @param proiezione nuova proiezione
 */
public void setProiezione(Proiezione proiezione) {
    this.proiezione = proiezione;
}
