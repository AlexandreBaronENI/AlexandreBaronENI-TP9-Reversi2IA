import java.util.ArrayList;
import java.util.List;

public class Plateau<T> implements Affichable{

	private int largeur;
	private int hauteur;
	private List<T> pions;
	
	public Plateau() {

	}

	public Plateau(int largeur, int hauteur, T valInitiale) {
		
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.pions = new ArrayList<T>(hauteur*largeur);
		for(int i = 0; i < hauteur*largeur; i++)
			this.pions.add(valInitiale);
	}

	@Override
	public char getSymbole() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void afficher() {
		for(int i = 0; i < largeur; i++) {
			for(int j = 0; j < hauteur; j++) {
				if(i%largeur==0) {
					System.out.println();		
				}
				System.out.print(getPion(i, j)+" ");
			}
		}
	}
	
	protected T getPion(int i, int j) {
		return pions.get(i*hauteur+j);
	}
	
	protected void setPion(int x,int y, T t) {
		pions.set(x*hauteur+y, t);
	}
	
}
