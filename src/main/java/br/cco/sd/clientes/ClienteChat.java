package br.cco.sd.clientes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import br.cco.sd.modelo.Servico;
import br.cco.sd.servicos.ServidorNomesConnector;

public class ClienteChat implements Cliente {
	
	private final ServidorNomesConnector connector;
	private Servico servicoChat;	
	
	/**
	 * @param connector Array com o SN primário e secundário
	 */
	public ClienteChat(ServidorNomesConnector connector) {
		this.connector = connector;
	}
	
	@Override
	public void preparar() {
		try {
			servicoChat = connector.requisitarServico("CHAT");
		} catch (IOException e) {
			System.err.println("Erro ao buscar serviço.");
			e.printStackTrace();
		}
	}

	@Override
	public void iniciar() {
		try {
			Socket socket = new Socket(servicoChat.getIp(), servicoChat.getPorta());
			
			RecepcaoMensagem envio = new RecepcaoMensagem(socket);
			EnvioMensagem recepcao = new EnvioMensagem(socket);

			Thread threadEnvio = new Thread(envio);
			Thread threadRecepcao = new Thread(recepcao);
			
			threadEnvio.start();
			threadRecepcao.start();
			
			threadEnvio.join();
			threadRecepcao.join();
			
			socket.close();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class EnvioMensagem implements Runnable {

	private Socket socket;

	public EnvioMensagem(Socket socket) {
		this.socket = socket;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		DataInputStream dado = new DataInputStream(System.in);
		DataOutputStream output = null;
		String mensagem = null;

		try {
			boolean loop = true;
			while (loop) { 
				mensagem = dado.readLine();
				output = new DataOutputStream(socket.getOutputStream());
				output.writeUTF(mensagem);
				loop = mensagem == "/quit";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class RecepcaoMensagem implements Runnable {

	private Socket socket;
	
	public RecepcaoMensagem(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		String mensagem = null;
		DataInputStream stream;
		
		try {
			while(true){
				stream = new DataInputStream(socket.getInputStream());
				while((mensagem = stream.readUTF()) != "/quit"){
					System.out.println("R> " + mensagem);
				}
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
