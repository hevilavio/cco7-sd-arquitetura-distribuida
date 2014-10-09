package br.cco.sd.chat;

import br.cco.sd.modelo.Entrada;


public class DistribuidorRunnable implements Runnable {
 
	private Evento mensagemEvento;
	private Entrada entrada;
	
	/**
	 * Lê de uma Entrada e responde chamando um Evento <br/>
	 * O loop para quando chegar uma mensagem igual a <b>/quit</b>
	 * 
	 * */	
	public DistribuidorRunnable(Entrada entrada, Evento evento) { 
		this.mensagemEvento = evento;
		this.entrada = entrada;
	}

	@Override
	public void run() {
		
		String mensagem = null;

		try {
			boolean loop = true;
			while (loop) { 
				mensagem = entrada.readUTF();
				
				mensagemEvento.quandoChegarMensagem(mensagem);

				loop = (mensagem != "/quit");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
