package br.cco.sd.chat;

import java.io.DataInputStream;
import java.net.Socket;

/**
 * Lê do teclado e envia para o Socket passado no Construtor
 * */
public class EnvioMensagemRunnable implements Runnable {
 
	private Evento mensagemEvento;
	
	
	public EnvioMensagemRunnable(Socket socket) { 
		this.mensagemEvento = new EnviarMensagemEvento(socket);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		DataInputStream dado = new DataInputStream(System.in);
		String mensagem = null;

		try {
			boolean loop = true;
			while (loop) { 
				mensagem = dado.readLine();
				
				mensagemEvento.quandoChegarMensagem(mensagem);
				//loop = mensagem == "/quit";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
