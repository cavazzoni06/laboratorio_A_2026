package cinemax;
/**
 * Rappresenta un proiezionista del cinema.
 */
public class Proiezionista extends Utente{
	/**
	 * Crea un nuovo proiezionista con i dati personali specificati.
	 *
	 * @param username nome utente per l'accesso
	 * @param password password associata all'account
	 * @param nome nome del proiezionista
	 * @param cognome cognome del proiezionista
	 * @param dataNascita data di nascita del proiezionista
	 * @param domicilio domicilio del proiezionista
	 */
	public Proiezionista(String username, String password,
                   String nome, String cognome, String dataNascita, String domicilio) {
        super(username, password, nome, cognome, dataNascita, domicilio);
    }
}