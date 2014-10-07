package br.cco.sd.servicos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import br.cco.sd.modelo.Servico;

public class ServidorNomesConnector {
	private Servico snPrimario;
	private Servico snSecundario;

	/**
	 * @param snPrimario
	 *            O Servidor primário <br/>
	 * @param snSecundario
	 *            O Servidor secundário <br/>
	 */
	public ServidorNomesConnector(Servico snPrimario, Servico snSecundario) {
		this.snPrimario = snPrimario;
		this.snSecundario = snSecundario;
	}

	/**
	 * Busca pelo ip e porta baseado no nome do serviço <br/>
	 * 
	 * @param nome
	 *            do serviço
	 * */
	public Servico requisitarServico(String nome) throws IOException {

		Socket socket = getSocket();
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		DataInputStream input = new DataInputStream(socket.getInputStream());

		output.writeUTF("1_" + nome);// protocolo: 1 + nome do serviço
		String resposta = input.readUTF();// formato: 192.168.1.110_8081
		socket.close();

		String[] info = resposta.split("_");
		if ("NAO_ENCONTRADO".equals(info[0])) {
			throw new IllegalArgumentException("Serviço não encontrado");
		}

		Servico servico = new Servico(nome, info[0], Integer.parseInt(info[1]));
		return servico;
	}

	private Socket getSocket() throws IOException {
		Socket socket;
		try {
			socket = new Socket(snPrimario.getIp(), snPrimario.getPorta());
		} catch (Exception e) {
			System.err.println("Erro sn primario: " + e.getMessage());
			socket = new Socket(snSecundario.getIp(), snSecundario.getPorta());
		}
		return socket;
	}

	/**
	 * Cadastra o serviço no SN <br/>
	 * 
	 * @param servico : o nome do serviço
	 * 
	 * */
	public String cadastrarServico(Servico servico) {
		String resposta = null;
		try {
			Socket socket = getSocket();
			DataOutputStream output = new DataOutputStream(
					socket.getOutputStream());
			DataInputStream input = new DataInputStream(socket.getInputStream());

			String utf = String.format("0_%s_%s_%s", servico.getNome(),
					servico.getIp(), servico.getPorta());

			output.writeUTF(utf);// 0 + nome do serviço
			resposta = input.readUTF();// espera o servidor mandar um OK
			socket.close();
		} catch (IOException e) {
			System.err.println("Erro ao cadastrar servico. " + e.getMessage());
		}
		return resposta;
	}
}
