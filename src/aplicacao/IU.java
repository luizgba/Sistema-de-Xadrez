package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class IU {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	
	public static void clearScreen() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}
	
	
	public static PosicaoXadrez lerPosicaoXadrez(Scanner teclado) {
		try {
		String str = teclado.nextLine();
		char coluna = str.charAt(0);
		
		int linha = Integer.parseInt(str.substring(1));
		
		return new PosicaoXadrez(coluna, linha);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro na leitura das posições. Valores validos sao de a1 até h8");
		}
	}
	
	public static void mostrarPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturada) {
		mostrarTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		mostrarPecasCapturadas(capturada);
		System.out.println();
		System.out.println("Turno: " + partidaXadrez.getTurno());
		if(!partidaXadrez.getCheckMate()) {
		System.out.println("Esperando acao do jogador: " + partidaXadrez.getJogadorAtual());
		if(partidaXadrez.getCheck()) {
			System.out.println("Voce esta em xeque");
		}
	}
	 else {
		 System.out.println("Cheque-mate, parabens jogador: " + partidaXadrez.getJogadorAtual());
	 }
	}
	public static void mostrarTabuleiro(PecaXadrez[][] pecas) {
		
		for (int lin = 0; lin < pecas.length; lin++) {
			System.out.print((8 - lin) + "  ");
			for (int col = 0; col < pecas.length; col++) {
				mostrarPeca(pecas[lin][col], false);
			}
			System.out.println();
		}
		System.out.println("   a b c d e f g h");
	}
	
	private static void mostrarPeca(PecaXadrez peca, boolean backGround) {
		if(backGround) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if(peca.getCor() == Cor.ROXO) {
				System.out.print(ANSI_PURPLE + peca + ANSI_RESET );
			} else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET );
			}
}
		System.out.print(" ");
}
	
public static void mostrarTabuleiro(PecaXadrez[][] pecas, boolean[][]movimentosPossiveis) {
		
		for (int lin = 0; lin < pecas.length; lin++) {
			System.out.print((8 - lin) + "  ");
			for (int col = 0; col < pecas.length; col++) {
				mostrarPeca(pecas[lin][col], movimentosPossiveis[lin][col]);
			}
			System.out.println();
		}
		System.out.println("   a b c d e f g h");
	}	

	private static void mostrarPecasCapturadas(List<PecaXadrez> pecaCapturada) {
		List<PecaXadrez> branca = pecaCapturada.stream().filter(x -> x.getCor() == Cor.ROXO).collect(Collectors.toList());
		List<PecaXadrez> preta = pecaCapturada.stream().filter(x -> x.getCor() == Cor.AMARELO).collect(Collectors.toList());
		
		System.out.println("Pecas capturadas ");
		System.out.print("Roxas: ");
		System.out.print(ANSI_PURPLE);
		System.out.print(Arrays.toString(branca.toArray()));
		System.out.println(ANSI_RESET);
		System.out.print("Amarelas: ");
		System.out.print(ANSI_YELLOW);
		System.out.print(Arrays.toString(preta.toArray()));
		System.out.println(ANSI_RESET);
	}

}


	