public enum Pion {
	LIBRE('.'),
	BLANC('o'),
	NOIR('●');
	
	private int nbPions;
	private char name;
	
	private Pion(char name) {
		this.name = name;
	}
	
	public char getSymbole() {
		return this.name;
	}
	
	public int getNbPions() {
		return this.nbPions;
	}
	
	
	public Pion autrePion() {
		// On renvoi l'autre pion
		if(this.getSymbole() == BLANC.getSymbole()) {
			return NOIR;
		}else {
			return BLANC;
		}
	}
	
	public void gagne(int nbPions) {
		// On ajoute les pions gagné + celui placé
		this.nbPions += nbPions + 1;
		// On enlève les pions de l'autre couleur qui ont été remplacés
		this.autrePion().nbPions -= nbPions;
	}	
	
}
