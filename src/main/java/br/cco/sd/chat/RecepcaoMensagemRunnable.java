package br.cco.sd.chat;

import java.io.DataInputStream;
import java.net.Socket;

/**
 * Lê do socket e aciona o evento passado no Construtor
 * */
public class RecepcaoMensagemRunnable implements Runnable {

	private Socket socket;
	private Evento mensagemEvento;
	
	/**
	 * @param socket o Socket da conexão <br/>
	 * @param mensagemEvento Evento a ser executado quando sobre a chegada/saída de uma mensagem
	 * */
	public RecepcaoMensagemRunnable(Socket socket, Evento mensagemEvento) {
		this.socket = socket;
		this.mensagemEvento = mensagemEvento;
	}

	@Override
	public void run() {
		String mensagem = null;
		DataInputStream stream;
		
		try {
			while(true){
				stream = new DataInputStream(socket.getInputStream());
				while((mensagem = stream.readUTF()) != "/quit"){
					mensagemEvento.quandoChegarMensagem(mensagem);
				}
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}