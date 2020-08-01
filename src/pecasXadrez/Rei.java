package pecasXadrez;

import boardGame.Posicao;
import boardGame.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testeTorreRoque(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContMovimento() == 0;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// a baixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Diagonal Noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Diagonal Nordeste
		p.setValores(posicao.getLinha() -1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Diagonal Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Diagonal Sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		// Jogada Roque lado do Rei
		
		if(getContMovimento() == 0 && !partidaXadrez.getCheck()) {
			Posicao torre1 = new Posicao(posicao.getLinha(), posicao.getColuna() +3);
			if(testeTorreRoque(torre1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() +2);
					if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
						matriz[posicao.getLinha()][posicao.getColuna()+2] = true;
				}
			}
		}

		// Jogada Roque lado da Rainha

		
			Posicao torre2 = new Posicao(posicao.getLinha(), posicao.getColuna()-4);
			if(testeTorreRoque(torre2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() -2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() -3);
					if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
						matriz[posicao.getLinha()][posicao.getColuna()+2] = true;
				}
			}
		
		
		return matriz;
	}

}
