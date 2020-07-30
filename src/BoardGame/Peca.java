package boardGame;

public abstract class Peca {

	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
		
	public abstract boolean[][] possiveisMovimentos();
	
	public boolean possivelMovimentoPeca(Posicao posicao) {
		return possiveisMovimentos()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public boolean podeFazerMovimento() {
		boolean[][] matriz = possiveisMovimentos();
			for(int lin=0;lin<matriz.length;lin++) {
				for(int col=0;col<matriz.length;col++) {
					if(matriz[lin][col]) {
						return true;
					}
				}
			}
		return false;
	}
}
