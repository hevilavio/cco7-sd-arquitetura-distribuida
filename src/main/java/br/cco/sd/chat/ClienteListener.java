package br.cco.sd.chat;

import java.io.DataInputStream;
import java.net.Socket;

public class ClienteListener implements Runnable {

	private ServidorChat servidorChat;
	private Socket socketCliente;

	public ClienteListener(ServidorChat servidorChat, Socket socketCliente) {
		this.servidorChat = servidorChat;
		this.socketCliente = socketCliente;
	}

	@Override
	public void run() {
		boolean loop = true;
		DataInputStream stream;
		String mensagem;

		try {
			while (loop) {
				stream = new DataInputStream(socketCliente.getInputStream());
				while ((mensagem = stream.readUTF()) != "/quit") {
					servidorChat.distribuirMensagem(mensagem);
				}
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
