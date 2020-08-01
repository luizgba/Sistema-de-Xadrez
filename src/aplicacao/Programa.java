package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);

		PartidaXadrez partidaXadrez = new PartidaXadrez();

		List<PecaXadrez> capturada = new ArrayList<>();
		
		while (!partidaXadrez.getCheckMate()) {
			try {
				IU.clearScreen();
				IU.mostrarPartida(partidaXadrez, capturada);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = IU.lerPosicaoXadrez(teclado);
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				IU.clearScreen();
				IU.mostrarTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);
				System.out.print("Destino: ");
				PosicaoXadrez destino = IU.lerPosicaoXadrez(teclado);
	
				PecaXadrez pecaCapturada = partidaXadrez.executaMovimento(origem, destino);
				
				if(pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
				
				if(partidaXadrez.getPromocao() != null) {
					System.out.println("Qual peca deseja promover? (B/Q/C/T)");
					String tipo = teclado.nextLine().toUpperCase();
					while (!tipo.equals("T") && !tipo.equals("B") && !tipo.equals("C") && !tipo.equals("Q")) {
						System.out.println("Peca invalida! Qual peca deseja promover? (B/Q/C/T)");
						tipo = teclado.nextLine().toUpperCase();
				}
					partidaXadrez.colocarPecaPromovida(tipo);

			}
			}
			catch(ExcecaoXadrez e) {
				System.out.println(e.getMessage());
			teclado.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
			teclado.nextLine();
			}
		}
		IU.clearScreen();
		IU.mostrarPartida(partidaXadrez, capturada);
	}
}
