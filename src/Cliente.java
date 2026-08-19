package cinemax;
/**
 * Rappresenta un cliente nel sistema di prenotazione cinema.
 */
public class Cliente extends Utente{
	/**
	 * Crea un nuovo cliente con i dati personali specificati.
	 *
	 * @param username nome utente per l'accesso
	 * @param password password associata all'account
	 * @param nome nome del cliente
	 * @param cognome cognome del cliente
	 * @param dataNascita data di nascita del cliente
	 * @param domicilio domicilio del cliente
	 */
	public Cliente(String username, String password,
                   String nome, String cognome, String dataNascita, String domicilio) {
        super(username, password, nome, cognome, dataNascita, domicilio);
    }
}