import java.util.ArrayList;
import java.util.Arrays;

public class Ordinateur implements Joueur{

	private String nom;
	private Pion pion;

	public Ordinateur(String nom, Pion pion){
		this.nom = nom;
		this.pion = pion;
	}

	@Override
	public int[] jouer(PlateauDeReversi plateau, Pion pion) {
		int[] res = {0,0};
		int  maxVal = 0;
		
		ArrayList<ArrayList<Integer>> allCoupsPossibles = getAllCoupsPossibles(plateau);
		
		//Pour chaque emplacement on regarde le meilleur score par case
		for(ArrayList<Integer> coup : allCoupsPossibles) {
			if( maxVal < PlateauDeReversi.valAllOfPlaces[coup.get(0)][coup.get(1)]) {
				maxVal = PlateauDeReversi.valAllOfPlaces[coup.get(0)][coup.get(1)];
				res[0] = coup.get(0);
				res[1] = coup.get(1);
			}
		}
		
		return res;
	}

	public ArrayList<ArrayList<Integer>> getAllCoupsPossibles(PlateauDeReversi plateau) {
		boolean res;
		ArrayList<ArrayList<Integer>> emplacementsValides = new ArrayList<ArrayList<Integer>>();
		
		// On va chercher tous les emplacements possible
		for(int i = 0; i < PlateauDeReversi.TAILLE; i++){
			for(int j =0; j < PlateauDeReversi.TAILLE; j++) {
				if(plateau.getPion(i, j) == Pion.LIBRE) {
					res = plateau.tester(pion, i, j) > 0;
					if(res)
						emplacementsValides.add(new ArrayList<Integer>(Arrays.asList(i, j)));
				}
			}
		}
		return emplacementsValides;
	}
	
	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public Pion getPion() {
		return this.pion;
	}

}
