package prodavnica;

public class TestRadnja {

	public static void main(String[] args) {

		
		Radnja db= new Radnja ("jdbc:sqlite:C:\\Users\\Korisnik\\Desktop\\IT Bootcamp\\06.9.2019\\Prodavnica Automobila\\Prodavnica.db") ;
		
		db.connect();
		
		db.pozdravnaPoruka();
		
		db.logovanjeKorisnika();
		//db.registracijaKorisnika ();
		db.opcija();
		db.ispisiDostupneAutomobile();
		
		db.kupovinaAutomobila ();
		db.ispisiKupljeneAutomobile();
		db.uplatiNovacZaKupljeniAutomobil();
		db.disconnect ();
		
		

	}

	}



