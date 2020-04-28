import java.util.Scanner;

public class PlateauDeReversi extends Plateau<Pion>{

	final static int TAILLE = 8;
	Humain humain;
	Ordinateur ordinateur;
	static int[][] valAllOfPlaces;
	static int[][] valOfPlaces = {
			{40,3 ,15,10},
			{3 ,0 ,9 ,12},
			{15,9 ,11,15},
			{10,12,15,18}};

	private Scanner sc;
	
	public static void main(String[] args) {
		PlateauDeReversi plateau = new PlateauDeReversi();
		plateau.jouer();		
	}
	
	public PlateauDeReversi() {
		super(TAILLE, TAILLE, Pion.LIBRE);
		
		sc = new Scanner(System.in);
		System.out.println("Quel est ton nom ?");
		String nomJoueur = sc.nextLine();
		this.humain = new Humain(nomJoueur, Pion.NOIR);
		this.ordinateur = new Ordinateur("Mr l'ordinateur", Pion.BLANC);
		
		PlateauDeReversi.valAllOfPlaces = generatePlateauVal();
		
		for(int i = 0; i < TAILLE; i++)
			for(int j = 0; j < TAILLE; j++)
				this.setPion(i, j, Pion.LIBRE);

		// On place le pions de départ
		poser(Pion.BLANC, (TAILLE/2)-1, (TAILLE/2)-1);
		poser(Pion.NOIR, (TAILLE/2)-1, TAILLE/2);
		poser(Pion.NOIR, TAILLE/2, (TAILLE/2)-1);
		poser(Pion.BLANC, TAILLE/2, TAILLE/2);
		
		humain.getPion().gagne(2);
		ordinateur.getPion().gagne(2);
	}
	
	static int[][] rotateArray(int mat[][]) 
    { 
        for (int x = 0; x < TAILLE/2 / 2; x++) { 
            for (int y = x; y < TAILLE/2 - x - 1; y++) { 
            	int temp = mat[x][y]; 
            	mat[x][y] = mat[y][TAILLE/2 - 1 - x]; 
            	mat[y][TAILLE/2 - 1 - x] = mat[TAILLE/2 - 1 - x][TAILLE/2 - 1 - y]; 
            	mat[TAILLE/2 - 1 - x][TAILLE/2 - 1 - y] = mat[TAILLE/2 - 1 - y][x]; 
            	mat[TAILLE/2 - 1 - y][x] = temp; 
            } 
        } 
        return mat;
    } 
  
    static int[][] generatePlateauVal() 
    { 
    	int[][] valOfAllPlaces = new int[TAILLE][TAILLE];
    	int x;
    	int y = 0;
    	
    	boolean firstPart = false;
        for (int i = 0; i < TAILLE/2; i++) { 
        	x = 0;
            for (int j = 0; j < (TAILLE/2); j++) {
            	valOfAllPlaces[x][y] = valOfPlaces[i][j];
                x++;
            }
            for (int j = (TAILLE/2)-1; j >= 0; j--) {
            	valOfAllPlaces[x][y] = valOfPlaces[i][j];
                x++;
            }
            if(i == (TAILLE/2)-1 && !firstPart) {
            	rotateArray(valOfPlaces);
            	firstPart = true;
            	i=-1;
            }
            y++;
        } 
        return valOfAllPlaces;
    } 
	public void jouer() {
		Joueur joueurTemp = humain;
		int nbTourPasse = 0;
				
		
		// Tant on a passé deux fois le tour
		while(nbTourPasse < 2) {
			boolean hasPlayed = false;
			System.out.println("Au tour de " + joueurTemp.getNom() + " avec le pion " + joueurTemp.getPion().getSymbole());

			afficher();
			
			// Tant on a pas encore joué
			do{
				if(peutJouer(joueurTemp.getPion())){
					// Au tour du joueur
					int[] placement = joueurTemp.jouer(this, joueurTemp.getPion());
					// On test si il est possible de jouer (avec des pions autour de la position)
					int nbPionsTest = tester(joueurTemp.getPion(), placement[0], placement[1]);
					//Si on a trouvé des solutions autour
					if(nbPionsTest > 0) {
						poser(joueurTemp.getPion(), placement[0], placement[1]);
						joueurTemp.getPion().gagne(nbPionsTest);
						nbTourPasse = 0;
						hasPlayed = true;
					}else if(joueurTemp != ordinateur) {
						System.out.println("Pas possible de placer le pion ici :/");
					}						
				}else {
					System.out.println("Pas possible de jouer pour " + joueurTemp.getNom() + ", passes ton tour");
					nbTourPasse++;
					hasPlayed = true;
				}
			}while(!hasPlayed);	

			System.out.println("_____________________________________________");
			
			// On change de joueur
			joueurTemp = joueurTemp == humain ? ordinateur : humain;
		}
			

		if(humain.getPion().getNbPions() > ordinateur.getPion().getNbPions()) {
			System.out.println("Bien joué " + humain.getNom() + " tu as marqué " + humain.getPion().getNbPions() + " points !!! Alors que " + ordinateur.getNom()+ " a marqué " + ordinateur.getPion().getNbPions() + " points !! HAHAHAHA LE NUL MONTREZ LE DU DOIGT");
		}else if(humain.getPion().getNbPions() < ordinateur.getPion().getNbPions()) {
			System.out.println("Bien joué " + humain.getNom() + " tu as marqué " + humain.getPion().getNbPions() + " points ... mais " + ordinateur.getNom() + " a marqué " + ordinateur.getPion().getNbPions() + " points ...");
			System.out.println("Je le savais depuis le début que la machine dépasserait l'homme");
		}else {
			System.out.println("Incroyable égalité, " + humain.getPion().getNbPions() + " partout ! Revanche ?");
		}
		
		System.out.println("Merci d'avoir joué");
	}
		
	public void poser(Pion pion, int x, int y) {
		//On place le pion
		this.setPion(x, y, pion);
		int nbPions;
		
		// On va regarder tout autour du point
		// . . .
		// o x o
		// x . .
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++) {
				nbPions = 0;
				// Si on est sur le point éa sert a rien
				if (i != 0 || j != 0) {
					nbPions += checkIfPossible(pion, x, y, i, j);
					// Une fois qu'on a notre nombre de point, on va parcourir ces points ex : x= 1, i=-1, nbPions = 10 on parcours sur la ligne x dans la direction de i * le nombre de pions
					for (int k = 1; k <= nbPions; k++)
						this.setPion(x + i * k, y + j * k, pion);
				}
			}
	}
	
	public int tester(Pion pion, int x, int y) {
		int nbPionsPrisMax = 0;
		if(this.getPion(x, y) == Pion.LIBRE)
			// On va regarder tout atour du point
			// . . .
			// o x o
			// x . .
			for(int i = -1; i <= 1; i++) 
				for(int j = -1; j <= 1; j++) 
					// Si on est sur le point Ã§a sert a rien
					if(i != 0 || j != 0) 
						nbPionsPrisMax += checkIfPossible(pion, x, y, i, j);

		return nbPionsPrisMax;
	}
	
	public int checkIfPossible(Pion pion, int x, int y, int i, int j) {
		int nbPionspris = 0;
		int currentX = x + i;
		int currentY = y + j;
		// On va regarder si il y a un bien d'autres autre pion
		while(currentX >= 0 && currentX < TAILLE && currentY >= 0 && currentY < TAILLE && this.getPion(currentX, currentY) == pion.autrePion() ) {
			currentX += i;
			currentY += j;
			nbPionspris++;
		}
		
		// Et si on  notre pion a la fin c'est ok sinon 0 car fin du tableau
		if (currentX < 0 || currentX >= TAILLE || currentY < 0 || currentY >= TAILLE || this.getPion(currentX, currentY) != pion)
			nbPionspris = 0;
		
		return nbPionspris;
	}

	public boolean peutJouer(Pion pion) {
		boolean res = false;
		int i = 0, j;
		
		// On va tester si on peut jouer
		while(i < TAILLE && !res){
			j = 0;
			while(j < TAILLE && !res) {
				if(this.getPion(i, j) == Pion.LIBRE) {
					res = tester(pion, i, j) > 0;
				}
				j++;
			}
			i++;
		}
		return res;
	}
	
	public void afficher() {
		// On affiche les scores
		System.out.println(Pion.BLANC.getNbPions() + " " + Pion.BLANC.getSymbole());
		System.out.println(Pion.NOIR.getNbPions() + " " + Pion.NOIR.getSymbole());
		System.out.print("  ");

		// On affiche les colonnes
		for(int i = 1; i <= TAILLE; i++ )
			System.out.print(i + " ");
		System.out.println();

		// On affiche les lignes et les symboles a chaque case
		for(int i = 0; i < TAILLE; i++) {
			System.out.printf(i+1 + " ");
			for(int j = 0; j < TAILLE; j++) {
				System.out.printf(this.getPion(i, j).getSymbole()+" ");
			}
			System.out.println();
		}
	}

}
