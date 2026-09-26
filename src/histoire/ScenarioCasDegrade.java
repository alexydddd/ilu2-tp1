package histoire;

import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;
import personnages.Gaulois;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal1 = new Etal();
		etal1.libererEtal();
		System.out.println("Fin du test 1");

		Etal etal2 = new Etal();
		Gaulois panoramix = new Gaulois("Panoramix", 20); 
		etal2.occuperEtal(panoramix, "fleurs", 10);
		etal2.acheterProduit(5, null);
		System.out.println("Fin du test 2");

		try {
			etal2.acheterProduit(-3, panoramix);
		} catch (IllegalArgumentException e) {
			System.err.println("Erreur : " + e.getMessage());
		}
		System.out.println("Fin du test 3");

		Etal etal3 = new Etal();
		try {
			etal3.acheterProduit(2, panoramix);
		} catch (IllegalStateException e) {
			System.err.println("Erreur : " + e.getMessage());
		}
		System.out.println("Fin du test 4");
			
		Village village = new Village("le village sans chef", 10, 4);
		try {
			System.out.println(village.afficherVillageois());
		} catch (VillageSansChefException e) {
			System.err.println("Erreur : " + e.getMessage());
		}
		System.out.println("Fin du test 5");
	}
}