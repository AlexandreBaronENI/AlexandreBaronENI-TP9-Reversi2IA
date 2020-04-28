import java.util.Scanner;

public class Humain implements Joueur{

	private String nom;
	private Pion pion;
	Scanner sc = new Scanner(System.in);

	public Humain(String nom, Pion pion){
		this.nom = nom;
		this.pion = pion;
	}

	@Override
	public int[] jouer(PlateauDeReversi plateau, Pion pion) {
		boolean ok = false;
		int[] res = new int[2];
		int y = 0, x = 0;
		
		do {
			try {
				// On demandeà l'utilisateur
				System.out.print("Ligne :");
				x = sc.nextInt();
				sc.nextLine();
				System.out.print("Colonne :");
				y = sc.nextInt();
				sc.nextLine();
				
			}catch(Exception e) {
				System.out.println("Merci de rentrer un nombre entre 1 et " + PlateauDeReversi.TAILLE);
			}
			// On vient enlever 1 pour correspondre aux tableaux car 1 -> [0]
			x--;
			y--;
			
			// On vérifie si l'utilisateur a bien rentré un nombre correct inférieur a la taille du plateau
			if(x >= 0 && x < PlateauDeReversi.TAILLE && y >= 0 && y < PlateauDeReversi.TAILLE) {
				if(plateau.getPion(x, y) == Pion.LIBRE) {
					ok = true;
				}else {
					System.out.println("Merci de placer le pion a un emplacement libre et différent de votre pion");
				}
			}else {
				System.out.println("Merci de rentrer un nombre entre 1 et " + PlateauDeReversi.TAILLE);
			}
			
		}while(!ok);

		
		res[0] = x;
		res[1] = y;
		
		return res;
	}

	@Override
	public Pion getPion() {
		return this.pion;
	}
	
	@Override
	public String getNom() {
		return this.nom;
	}

}
