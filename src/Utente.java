public abstract class Utente{
	private String nome;
    private String cognome;
    private String username;
    private String password;
    private String dataNascita;
    private String domicilio;

     public Utente(String username, String password,
                  String nome, String cognome, String dataNascita, String domicilio) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.domicilio = domicilio;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getDataNascita() {
        return dataNascita;
    }
}