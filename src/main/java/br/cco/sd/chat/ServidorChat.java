package br.cco.sd.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import br.cco.sd.servicos.Servidor;
import br.cco.sd.servicos.ServidorNomesConnector;

public class ServidorChat extends Servidor implements Runnable, Evento {
	private List<Socket> clientes;

	/**
	 * 
	 * @param connector
	 *            - O Connector para comunicação com SN <br/>
	 * @param porta
	 *            - A porta que será usada para o servidor de Chat
	 * */
	public ServidorChat(ServidorNomesConnector connector, int porta) {
		super.nome = "CHAT";
		super.porta = porta;

		super.cadastrar(connector);
		this.clientes = new ArrayList<>();
	}

	@Override
	public void run() {
		ServerSocket server = null;
		boolean loop = true;

		try {
			server = new ServerSocket(porta, 10);
			while (loop) {
				Socket socketCliente = server.accept();
				adicionarCliente(socketCliente);
				
				// Inicia uma thread para "escutar" as mensagens enviadas pelo cliente
				new Thread(new RecepcaoMensagemRunnable(socketCliente, this)).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fecharServerSocket(server);
		}
	}
	
	private void fecharServerSocket(ServerSocket server) {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void adicionarCliente(Socket socketCliente) {
		synchronized (clientes) {
			clientes.add(socketCliente);
		}
	}

	@Override
	public void quandoChegarMensagem(String mensagem) {
		synchronized (clientes) {
			for (Socket cliente : clientes) {
				new EnviarMensagemEvento(cliente).quandoChegarMensagem(mensagem);
			}
		}
	} 
}
