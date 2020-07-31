package xadrez;

import boardGame.Peca;
import boardGame.Posicao;
import boardGame.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Cor cor;
	private int contMovimento;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int contMovimento() {
		return contMovimento;
	}
	
	public void incrementarContMovimento() {
		contMovimento++;
	}
	
	public void DecrementarContMovimento() {
		contMovimento--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.ParaPosicao(posicao);
	}
	
	protected boolean temPecaOponenteAqui(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null & p.getCor() != cor;
		
	}
	
}
