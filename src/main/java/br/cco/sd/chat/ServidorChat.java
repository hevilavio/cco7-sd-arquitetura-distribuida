package br.cco.sd.chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import br.cco.sd.modelo.Entrada;
import br.cco.sd.servicos.Servidor;
import br.cco.sd.servicos.ServidorNomesConnector;

public class ServidorChat extends Servidor implements Runnable, Evento {
	private List<Socket> clientes;
	private final Logger LOGGER = Logger.getLogger("ServidorChat");

	/**
	 * 
	 * @param connector
	 *            - O Connector para comunica��o com SN <br/>
	 * @param porta
	 *            - A porta que ser� usada para o servidor de Chat
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
			LOGGER.info("SChat iniciado. IP=localhost|PORTA=" + porta);
			
			while (loop) {
				Socket socketCliente = server.accept();
				LOGGER.info("Novo Cliente aceito.");
				
				adicionarCliente(socketCliente);

				// Inicia uma thread para "escutar" as mensagens enviadas pelocliente
				new Thread(new DistribuidorRunnable(
						new Entrada(new DataInputStream(socketCliente.getInputStream())),
						this)).start();
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
				new EnviarMensagemEvento(cliente)
						.quandoChegarMensagem(mensagem);
			}
		}
	}
}
