package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	

	public Village(String nom, int nbVillageoisMaximum, int nbEtalMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalMaximum);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException(
					"Le village \"" + nom + "\" n'a pas de chef.");
		}

		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	class Marche{
		private Etal[] etals;
		
		public Marche(int nbEtals) {
	        etals = new Etal[nbEtals];
	        for (int i = 0; i < nbEtals; i++) {
	            etals[i] = new Etal();
	        }
	    }
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			 if ( indiceEtal >=0 && indiceEtal < etals.length) {
				 etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			 }
		 }
		
		public int trouverEtalLibre() {
	        for (int i = 0; i < etals.length; i++) {
	            if (!etals[i].isEtalOccupe()) {
	                return i;
	            }
	        }
	        return -1;
	    }
		 
		public Etal[] trouverEtals(String produit) {
	        int nbEtalVendeur = 0;
	        for (int i = 0; i < etals.length; i++) {
	            if (etals[i].contientProduit(produit)) {
	                nbEtalVendeur++;
	            }
	        }

	        Etal[] etalVendeur = new Etal[nbEtalVendeur];
	        int index = 0;
	        for (int i = 0; i < etals.length; i++) {
	            if (etals[i].contientProduit(produit)) {
	                etalVendeur[index] = etals[i];
	                index++;
	            }
	        }
	        return etalVendeur;
	    }
		
		public Etal trouverVendeur(Gaulois gaulois) {
	        for (int i = 0; i < etals.length; i++) {
	            if (gaulois.equals(etals[i].getVendeur())) {
	                return etals[i];
	            }
	        }
	        return null;
	    }

		 
		public String afficherMarche() {
	        StringBuilder chaine = new StringBuilder();
	        int nbEtalVide = 0;

	        for (int i = 0; i < etals.length; i++) {
	            if (etals[i].isEtalOccupe()) {
	                chaine.append(etals[i].afficherEtal());
	            } else {
	                nbEtalVide++;
	            }
	        }
	        chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
	        return chaine.toString();
	    }
		 
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre "
				+ nbProduit + " " + produit + ".\n");

		int indiceLibre = marche.trouverEtalLibre();
		if (indiceLibre != -1) {
			marche.utiliserEtal(indiceLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des "
					+ produit + " à l'étal n°" + (indiceLibre + 1) + ".\n");
		} else {
			chaine.append("Il n'y a plus d'étal disponible au marché.\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etalsTrouves = marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();

		if (etalsTrouves.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des "
					+ produit + " au marché.\n");
		} else if (etalsTrouves.length == 1) {
			chaine.append("Seul le vendeur " + etalsTrouves[0].getVendeur().getNom()
					+ " propose des " + produit + " au marché.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (Etal etal : etalsTrouves) {
				chaine.append("- " + etal.getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		if (etal != null) {
			return etal.libererEtal();
		}
		return "";
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + nom + "\" possède plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
}