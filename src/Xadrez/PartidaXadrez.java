package xadrez;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardGame.Peca;
import boardGame.Posicao;
import boardGame.Tabuleiro;
import pecasXadrez.Bispo;
import pecasXadrez.Cavalo;
import pecasXadrez.Peao;
import pecasXadrez.Queen;
import pecasXadrez.Rei;
import pecasXadrez.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez enPassantVulneravel;
	private PecaXadrez promocao;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.ROXO;

		setupInicial();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}
	
	public PecaXadrez enPassantVulneravel() {
		return enPassantVulneravel;
	}
	
	public PecaXadrez getPromocao() {
		return promocao;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];

		for (int lin = 0; lin < tabuleiro.getLinhas(); lin++) {
			for (int col = 0; col < tabuleiro.getColunas(); col++) {
				matriz[lin][col] = (PecaXadrez) tabuleiro.peca(lin, col);
			}

		}
		return matriz;
	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.posicionar();
		validaPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}

	public PecaXadrez executaMovimento(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem = origemPosicao.posicionar();
		Posicao destino = destinoPosicao.posicionar();
		validaPosicaoOrigem(origem);
		validaPosicaoAlvo(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		if (testeCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ExcecaoXadrez("Voce nao pode se colocar em xeque");
		}
		
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino);
		
		//Movimento promocao
		promocao = null;
		
		if(pecaMovida instanceof Peao) {
			if((pecaMovida.getCor() == Cor.ROXO && destino.getLinha() == 0) || (pecaMovida.getCor() == Cor.AMARELO && destino.getLinha() == 7)) {
				promocao = (PecaXadrez)tabuleiro.peca(destino);
				promocao = colocarPecaPromovida("Q");
		}
		}
		
			

		check = (testeCheck(oponente(jogadorAtual))) ? true : false;

		if (testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}
		
		//Movimento En passant
			if(pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha()+2 || destino.getLinha() == origem.getLinha()-2))
				enPassantVulneravel = pecaMovida;
			else
				enPassantVulneravel = null;
		
		return (PecaXadrez) pecaCapturada;
	}
	
	public PecaXadrez colocarPecaPromovida(String tipo) {
		if (promocao == null) {
			throw new IllegalStateException("Não tem peça para voce trocar");
		}
		if(!tipo.equals("T") && !tipo.equals("B") && !tipo.equals("C") && !tipo.equals("Q")) {
			return promocao;
	}
		
		Posicao posi = promocao.getPosicaoXadrez().posicionar();
		Peca p = tabuleiro.removePeca(posi);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(tipo, promocao.getCor());
		tabuleiro.colocarPeca(novaPeca, posi);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
		
	}
	
	private PecaXadrez novaPeca(String tipo, Cor cor) {
		if(tipo.equals("B")) return new Bispo (tabuleiro, cor);
		if(tipo.equals("Q")) return new Queen (tabuleiro, cor);
		if(tipo.equals("T")) return new Torre (tabuleiro, cor);
		return new Cavalo (tabuleiro, cor);
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(origem);
		p.incrementarContMovimento();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, destino);
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// Movimento de roque para direita
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.incrementarContMovimento();
		}

		// Movimento de roque para esquerda
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() -4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() -1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.incrementarContMovimento();
		}
		
		//En passant captura
			if (p instanceof Peao ) {
				if(origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
					Posicao posicaoPeao;
					if(p.getCor() == Cor.ROXO) {
						posicaoPeao = new Posicao(destino.getLinha() +1, destino.getColuna());
					} else {
						posicaoPeao = new Posicao(destino.getLinha() -1, destino.getColuna());
					}
					pecaCapturada = tabuleiro.removePeca(posicaoPeao);
					pecasCapturadas.add(pecaCapturada);
					pecasNoTabuleiro.remove(pecaCapturada);
				}
			}
			

		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, origem);
		p.decrementarContMovimento();
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	
		// Movimento de roque para direita
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.decrementarContMovimento();
		}

		// Movimento de roque para esquerda
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() -4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() -1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.decrementarContMovimento();
		}
		
		//En passant captura
		if (p instanceof Peao ) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulneravel) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removePeca(destino);
				Posicao posicaoPeao;
				if(p.getCor() == Cor.ROXO) {
					posicaoPeao = new Posicao(3 , destino.getColuna());
				} else {
					posicaoPeao = new Posicao(4 , destino.getColuna());
				}
			tabuleiro.colocarPeca(peao, posicaoPeao);
			}
		}
	}

	private void validaPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temPecaPosicao(posicao)) {
			throw new ExcecaoXadrez("Nao existe uma peca na posicao de origem");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecaoXadrez("Peca escolhida e do adversario");
		}
		if (!tabuleiro.peca(posicao).podeFazerMovimento()) {
			throw new ExcecaoXadrez("Nao existe movimentos possiveis para a peca");
		}
	}

	private void validaPosicaoAlvo(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimentoPeca(destino)) {
			throw new ExcecaoXadrez("A peca escolhida nao pode se mover para a posicao de destino");
		}
	}

	private Cor oponente(Cor cor) {
		if (cor == Cor.ROXO) {
			return Cor.AMARELO;
		} else {
			return Cor.ROXO;
		}
	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Nao existe o rei" + cor + " no tabuleiro");
	}

	private boolean testeCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().posicionar();
		List<Peca> oponentePecas = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : oponentePecas) {
			boolean[][] matriz = p.possiveisMovimentos();
			if (matriz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Cor cor) {
		if (!testeCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] matriz = p.possiveisMovimentos();
			for (int lin = 0; lin < tabuleiro.getLinhas(); lin++) {
				for (int col = 0; col < tabuleiro.getColunas(); col++) {
					if (matriz[lin][col]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().posicionar();
						Posicao destino = new Posicao(lin, col);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testeCheck = testeCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).posicionar());
		pecasNoTabuleiro.add(peca);
	}

	private void proximoTurno() {
		turno++;
		if (jogadorAtual == Cor.ROXO) {
			jogadorAtual = Cor.AMARELO;
		} else {
			jogadorAtual = Cor.ROXO;
		}
	}

	private void setupInicial() {

		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.ROXO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.ROXO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.ROXO, this));
		colocarNovaPeca('d', 1, new Queen(tabuleiro, Cor.ROXO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.ROXO));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.ROXO));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.ROXO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.ROXO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.ROXO, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.ROXO, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.ROXO, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.ROXO, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.ROXO, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.ROXO, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.ROXO, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.ROXO, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.AMARELO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.AMARELO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.AMARELO, this));
		colocarNovaPeca('d', 8, new Queen(tabuleiro, Cor.AMARELO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.AMARELO));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.AMARELO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.AMARELO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.AMARELO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.AMARELO, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.AMARELO, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.AMARELO, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.AMARELO, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.AMARELO, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.AMARELO, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.AMARELO, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.AMARELO, this));

	}

}
