package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExcecaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);

		PartidaXadrez partidaXadrez = new PartidaXadrez();

		while (true) {
			try {
				IU.clearScreen();
				IU.mostrarTabuleiro(partidaXadrez.getPecas());
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = IU.lerPosicaoXadrez(teclado);
				System.out.print("Destino: ");
				PosicaoXadrez destino = IU.lerPosicaoXadrez(teclado);
	
				PecaXadrez pecaCapturada = partidaXadrez.executaMovimento(origem, destino);
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
	}
}
