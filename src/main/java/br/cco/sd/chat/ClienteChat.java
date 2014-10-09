package br.cco.sd.chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import br.cco.sd.modelo.Entrada;
import br.cco.sd.modelo.Servico;
import br.cco.sd.servicos.ServidorNomesConnector;

public class ClienteChat implements Cliente {

	private final ServidorNomesConnector connector;
	private Servico servicoChat;

	/**
	 * @param connector
	 *            Array com o SN primário e secundário
	 */
	public ClienteChat(ServidorNomesConnector connector) {
		this.connector = connector;
	}

	@Override
	public void preparar() {
		try {
			servicoChat = connector.requisitarServico("CHAT");
		} catch (IOException e) {
			System.err.println("Erro ao buscar serviço CHAT.");
			e.printStackTrace();
		}
	}

	@Override
	public void iniciar() {
		try {
			Socket socket = new Socket(servicoChat.getIp(),servicoChat.getPorta());

			DistribuidorRunnable recepcao = new DistribuidorRunnable(
					new Entrada(new DataInputStream(socket.getInputStream())),
					new ImprimirConsoleEvento());

			DistribuidorRunnable envio = new DistribuidorRunnable(
					new Entrada(new DataInputStream(System.in)), 
					new EnviarMensagemEvento(socket));

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
