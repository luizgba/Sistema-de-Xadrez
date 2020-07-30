package pecasXadrez;

import boardGame.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
@Override
	public String toString() {
	return "R";
}

@Override
public boolean[][] possiveisMovimentos() {
	boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
	return matriz;
}

}
