import java.time.LocalDateTime;
public class Proiezione{
	private LocalDateTime dataOra;
	private String titolo;
	private String genre;
	private String regista;
	private int anno;
	private int durateMinuti;
	private int etaMinima;
    private double prezzoBiglietto;

	public Proiezione(LocalDateTime dataOra, String titolo, String genere; String rigista, int anno, int durateMinuti, int etaMinima, double prezzoBiglietto) {
		this.DataOra = dataOra;
		this.titolo = titolo;
		this.genere = genere;
		this.rigista = rigista;
		this.anno = anno;
		this.durateMinuti = durataMinuti;
		this.etaMinima = etaMinima;
		this.prezzoBiglietto = prezzoBiglietto;
	}
	public LocalDateTime getDataOra() {
        return dataOra;
    }

    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getRegista() {
        return regista;
    }

   public void setRegista(String regista) {
       this.regista = regista;
    }

   public int getAnno() {
       return anno;
    }

   public void setAnno(int anno) {
      this.anno = anno;
    }

   public int getDurataMinuti() {
      return durataMinuti;
    }

   public void setDurataMinuti(int durataMinuti) {
      this.durataMinuti = durataMinuti;
    }

   public int getEtaMinima() {
      return etaMinima;
    }

   public void setEtaMinima(int etaMinima) {
      this.etaMinima = etaMinima;
    }

   public double getPrezzoBiglietto() {
      return prezzoBiglietto;
    }

    public void setPrezzoBiglietto(double prezzoBiglietto) {
      this.prezzoBiglietto = prezzoBiglietto;
    }
 @Override
 publicb String toString(){
	 return "Proiezione{"+
		     "dataOra=" + dataOra +
            ", titolo='" + titolo + '\'' +
            ", genere='" + genere + '\'' +
            ", regista='" + regista + '\'' +
            ", anno=" + anno +
            ", durataMinuti=" + durataMinuti +
            ", etaMinima=" + etaMinima +
            ", prezzoBiglietto=" + prezzoBiglietto +
            '}';
}
}
	
