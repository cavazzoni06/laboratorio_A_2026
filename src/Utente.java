/**
 * Rappresenta un utente generico del sistema di prenotazione cinema.
 * Le sottoclassi specificano il ruolo dell'utente, come cliente o proiezionista.
 */
public abstract class Utente{
	private String nome;
    private String cognome;
    private String username;
    private String password;
    private String dataNascita;
    private String domicilio;

    /**
     * Crea un nuovo utente con i dati personali specificati.
     *
     * @param username nome utente per l'accesso
     * @param password password associata all'account
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param dataNascita data di nascita dell'utente
     * @param domicilio domicilio dell'utente
     */
     public Utente(String username, String password,
                  String nome, String cognome, String dataNascita, String domicilio) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.domicilio = domicilio;
    }

    /**
     * Restituisce il nome utente.
     *
     * @return username dell'utente
     */
    public String getUsername() {
        return username;
    }

    /**
     * Restituisce la password dell'utente.
     *
     * @return password dell'utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return nome dell'utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return cognome dell'utente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Restituisce il domicilio dell'utente.
     *
     * @return domicilio dell'utente
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     * Restituisce la data di nascita dell'utente.
     *
     * @return data di nascita dell'utente
     */
    public String getDataNascita() {
        return dataNascita;
    }
}