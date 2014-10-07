package br.cco.sd.servicos;

import java.net.InetAddress;
import java.net.UnknownHostException;

import br.cco.sd.modelo.Servico;

public class Servidor {
	protected String nome;
	protected int porta;
	
	/**
	 * Registra o serviço no SN
	 * */
	protected void cadastrar(ServidorNomesConnector connector){
		String ip = null;
		
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		connector.cadastrarServico(new Servico(nome, ip, porta));
	}
	
}
