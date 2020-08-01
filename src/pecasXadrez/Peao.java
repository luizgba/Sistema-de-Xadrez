package pecasXadrez;

import boardGame.Posicao;
import boardGame.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.ROXO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPecaPosicao(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPecaPosicao(p) && getTabuleiro().existePosicao(p2)&& !getTabuleiro().temPecaPosicao(p2) && getContMovimento() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && temPecaOponenteAqui(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && temPecaOponenteAqui(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		} else {
			
		p.setValores(posicao.getLinha() +1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPecaPosicao(p))
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posicao.getLinha() +2, posicao.getColuna());
		Posicao p2 = new Posicao(posicao.getLinha() +1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPecaPosicao(p) && getTabuleiro().existePosicao(p2)&& !getTabuleiro().temPecaPosicao(p2) && getContMovimento() == 0) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posicao.getLinha() +1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && temPecaOponenteAqui(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posicao.getLinha() +1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && temPecaOponenteAqui(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		return matriz;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
