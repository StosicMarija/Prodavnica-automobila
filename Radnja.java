package prodavnica;

import java.sql.*;
import java.util.Scanner;

public class Radnja {
	String connectionString;
	Connection con;
	
	static String poslednjiKorisnik;
	
	public Radnja(String conStr) {
		connectionString = conStr;
	}

	public void connect() {
		try {
			con = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pozdravnaPoruka() {
		System.out.println("Dodbrodosli na nas sajt!");
	}

	public void logovanjeKorisnika() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Uneti username: ");
		String user = sc.next();
		System.out.println("Sifra: ");
		String pass = sc.next();

		String upit = "select COUNT(*) " + "from Korisnik k " + "where k.Username = \"" + user
				+ "\" and k.Password = \"" + pass + "\"";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(upit);

			rs.next();
			int i = rs.getInt(1);
			if (i == 0) {
				System.out.println("Informacije su pogresne");
				logovanjeKorisnika();
			} else {
				System.out.println("Uspesno ste se registrovali");

			}
			
		    poslednjiKorisnik = user;
            stm.close();
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void registracijaKorisnika() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Unesite username: ");
		String user = sc.next();

		String upit = "" + "SELECT COUNT(*)" + " FROM Korisnik k" + " WHERE k.Username = \"" + user + "\"";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(upit);

			rs.next();
			int i = rs.getInt(1);

			if (i == 1) {
				System.out.println("Korisnik vec postoji, unesite ponovo!");
				registracijaKorisnika();
			} else {
				System.out.println("Unesite lozinku: ");
				String pass = sc.next();
				System.out.println("Ponovite lozinku: ");
				String pass1 = sc.next();

				String upit1 = " INSERT INTO Korisnik (Username, Password) " + " VALUES (\"" + user + "\",  \"" + pass
						+ "\")";

				Statement st = con.createStatement();
				st.executeUpdate(upit1);

				if (pass.equals(pass1) && pass.length() >= 5 && pass.charAt(i)==' ') {
					System.out.println("Registracija je uspesna!");

				} else {
					System.out.println("Registracija nije uspesna!");
					registracijaKorisnika();
				}
				st.close();
			}
			stm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ispisiDostupneAutomobile() {
		System.out.println("Nazivi dostupnih automobili su: ");
		String upit = "SELECT a.IdAuto, a.IdModel, m.Naziv, a.Cena, a.Status "
				+ "FROM Model m, Automobil a "
				+ "WHERE a.IdModel = m.IdModel AND a.Status = 'n'";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(upit);

			while (rs.next()) {
			int idAuto = rs.getInt(1);
			int idModela= rs.getInt(2);
			String naziv=rs.getString(3);
			int cena=rs.getInt(4);
			String status=rs.getString(5);
			
			System.out.println(idAuto + " " + idModela + " " + naziv + " " + cena + " " + status);

			}
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void kupovinaAutomobila () {
		
		Scanner sc=new Scanner (System.in);
		System.out.println("Unesite Id automobila koji zelite: ");
		int id=sc.nextInt();
		System.out.println("Unesite Username: ");
		String user=sc.next();
		java.sql.Date datum=new java.sql.Date (System.currentTimeMillis());
		
		String upit="INSERT INTO Prodaja (IdAuto, Username, Datum) "
				+ " VALUES(\"" + id + "\",  \"" + user + "\",  \"" + datum + "\")";
		
		try {
			Statement stm = con.createStatement();
			stm.executeUpdate(upit);
			stm.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}
		public void ispisiKupljeneAutomobile () {
			Scanner sc=new Scanner (System.in) ; 
			System.out.println("Unesite korisnicko ime: ");
			String user=sc.next();
			
			String upit = "SELECT m.Naziv, a.idAuto "  
					+ "FROM Automobil a, Model m, Prodaja p " 
					+ "WHERE a.idAuto = p.idAuto and a.idModel = m.idModel and p.Username = \"" + user + "\"";
			
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(upit);
			
			while (rs.next()) {
				String naziv=rs.getString(1);
				int idA=rs.getInt(2);
				System.out.println(naziv + " " + idA);
			}
			stm.close();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block  
				e.printStackTrace();
			}
		}
		
	public void uplatiNovacZaKupljeniAutomobil() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Unesite Id modela koji zelite da kupite");
		int id = sc.nextInt();
		
		String upit = " SELECT m.Naziv, a.Cena, a.idAuto"
				+ " FROM Automobil a, Model m "
				+ " WHERE a.idModel = m.idModel and a.IdAuto = \"" + id + "\"";
	
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(upit);
			
			rs.next();
			int cena = rs.getInt(2);
			System.out.println("Upisite iznos koji zelite da uplatite: ");
			int placanje = sc.nextInt();
			
			while (placanje > cena) {
				System.out.println("Uneli ste vise novca, pokusajte ponovo: ");
				placanje = sc.nextInt();
			
	}
			stm.close();
			
			java.sql.Date datum = new java.sql.Date(System.currentTimeMillis());
			System.out.println("Unesite Id uplate: ");
			int idU = sc.nextInt();
			
			String upit1 = " INSERT INTO Uplata (IdAuto, Iznos, Datum, IdUplata)"
					+ " VALUES (\"" + id + "\", \"" + placanje + "\", \"" + datum + "\", \"" + idU + "\")";
			
			Statement st = con.createStatement();
			st.executeUpdate(upit1);
			
			System.out.println("Uplata je uspesno izvrsena");
			
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	public void opcija() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Izaberite opciju: ");
		System.out.println("1. Da pogledate dostupne automobile ");
		System.out.println("2. Da uplatite novac");

		int i = sc.nextInt();
		
		 for (int j = 0; j < 6 ; j++) {
		
		if (i == 1) {
			System.out.println("Automobili na stanju: ");
			ispisiDostupneAutomobile();
			kupovinaAutomobila ();
			break;
			
		} 
		if (i == 2) {
			ispisiKupljeneAutomobile();
			uplatiNovacZaKupljeniAutomobil();
			break;
			
		} 
		else if (i!=1 || i!=2) {
			System.out.println("Pogresan unos, pokusajte ponovo");
			System.out.println("Ponudjene opcije: ");
			i = sc.nextInt();
		}
	}
	}
}
