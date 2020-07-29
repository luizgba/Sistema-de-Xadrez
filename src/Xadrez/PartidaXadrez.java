package Xadrez;

import BoardGame.Tabuleiro;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		
		for(int lin=0;lin<tabuleiro.getLinhas();lin++) {
			for(int col=0;col<tabuleiro.getColunas();col++) {
				matriz[lin][col] = (PecaXadrez) tabuleiro.peca(lin,col);
		}
		
	}
		return matriz;
}
}