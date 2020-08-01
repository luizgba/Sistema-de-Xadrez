package pecasXadrez;

import boardGame.Posicao;
import boardGame.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
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
			
			// En passant roxa
				if(posicao.getLinha() == 3) {
					Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
					if(getTabuleiro().existePosicao(esquerda) && temPecaOponenteAqui(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.enPassantVulneravel()) {
					matriz[esquerda.getLinha()-1][esquerda.getColuna()] = true;
				}
				
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				if(getTabuleiro().existePosicao(direita) && temPecaOponenteAqui(direita) && getTabuleiro().peca(direita) == partidaXadrez.enPassantVulneravel()) {
				matriz[direita.getLinha()-1][direita.getColuna()] = true;
			}
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
		
		// En passant amarelo
		if(posicao.getLinha() == 4) {
			Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
			if(getTabuleiro().existePosicao(esquerda) && temPecaOponenteAqui(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.enPassantVulneravel()) {
			matriz[esquerda.getLinha()+1][esquerda.getColuna()] = true;
		}
		
		Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
		if(getTabuleiro().existePosicao(direita) && temPecaOponenteAqui(direita) && getTabuleiro().peca(direita) == partidaXadrez.enPassantVulneravel()) {
		matriz[direita.getLinha()+1][direita.getColuna()] = true;

			}
		}
		return matriz;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
