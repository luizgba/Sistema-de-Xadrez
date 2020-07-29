package aplicacao;

import Xadrez.PecaXadrez;

public class IU {
	
	public static void mostrarTabuleiro(PecaXadrez[][] pecas) {
		
		for(int lin=0;lin<pecas.length;lin++) {
			System.out.print((8 - lin) + "  ");
			for(int col=0;col<pecas.length;col++) {
				mostrarPeca(pecas[lin][col]);
		}
		System.out.println();
	}	
		System.out.println("   a b c d e f g h");
	}
	private static void mostrarPeca(PecaXadrez peca) {
		if(peca == null) {
			System.out.print("-");
		} else {
			System.out.print(peca);
		}
			System.out.print(" ");
	}
}

