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
	
}	
