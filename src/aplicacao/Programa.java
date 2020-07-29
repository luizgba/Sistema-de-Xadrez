package aplicacao;

import Xadrez.PartidaXadrez;

public class Programa {

	public static void main(String[] args) {

		PartidaXadrez partidaXadrez = new PartidaXadrez();
		IU.mostrarTabuleiro(partidaXadrez.getPecas());
		
		
	}

}
