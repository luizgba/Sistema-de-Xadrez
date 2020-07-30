package xadrez;

import boardGame.Posicao;

public class PosicaoXadrez {
	
	private char coluna;
	private int linha;
	
	
	public PosicaoXadrez(char coluna, int linha) {
		if(coluna < 'a' || coluna > 'h' || linha <1 || linha > 8) {
			throw new ExcecaoXadrez("Erro na criação, valores validas são de 1A ate 8H");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}
	
	public int getLinha() {
		return linha;
	}

	protected Posicao posicionar() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	protected static PosicaoXadrez ParaPosicao (Posicao posicao) {
		return new PosicaoXadrez((char)('a' - posicao.getColuna()) , 8 - posicao.getLinha());
	}
@Override
	public String toString() {
		return "" + coluna + linha;
}
	
}
