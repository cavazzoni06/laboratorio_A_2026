package cinemax;
/**
 * Rappresenta il bigliettaio del cinema.
 */
public class Bigliettaio extends Utente {
    /**
     * Crea un nuovo bigliettaio con i dati personali specificati.
     *
     * @param username nome utente per l'accesso
     * @param password password associata all'account
     * @param nome nome del bigliettaio
     * @param cognome cognome del bigliettaio
     * @param dataNascita data di nascita del bigliettaio
     * @param domicilio domicilio del bigliettaio
     */
    public Bigliettaio(String username, String password,
                       String nome, String cognome, String dataNascita, String domicilio) {
        super(username, password, nome, cognome, dataNascita, domicilio);
    }
}