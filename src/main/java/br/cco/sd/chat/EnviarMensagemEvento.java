package br.cco.sd.chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EnviarMensagemEvento implements Evento {

	private Socket socket;

	public EnviarMensagemEvento(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void quandoChegarMensagem(String mensagem) {
		try {
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF(mensagem);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
