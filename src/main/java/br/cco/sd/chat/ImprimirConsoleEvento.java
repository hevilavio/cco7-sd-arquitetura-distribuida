package br.cco.sd.chat;

public class ImprimirConsoleEvento implements Evento {

	@Override
	public void quandoChegarMensagem(String mensagem) {
		System.out.println(mensagem);
	} 
}
