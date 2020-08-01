package pecasXadrez;

import boardGame.Posicao;
import boardGame.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);
		// Noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() -1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPecaPosicao(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha()-1, p.getColuna()-1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaOponenteAqui(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;

		}

		// Nordeste
		p.setValores(posicao.getLinha() -1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPecaPosicao(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha()-1, p.getColuna()+1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaOponenteAqui(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;

		}

		// Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPecaPosicao(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha()+1, p.getColuna() +1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaOponenteAqui(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;

		}

		// Sudoeste
		p.setValores(posicao.getLinha()+1, posicao.getColuna()-1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPecaPosicao(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() +1, p.getColuna()-1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaOponenteAqui(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;

		}

		return matriz;
	}

}