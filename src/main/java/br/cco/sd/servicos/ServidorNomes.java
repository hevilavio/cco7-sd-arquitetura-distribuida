package br.cco.sd.servicos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import br.cco.sd.modelo.Servico;
import br.cco.sd.util.ServicoUtil;

public class ServidorNomes implements Runnable{
	
	private List<Servico> servicos = new ArrayList<>();
	private ServerSocket server;
	private int porta;
	
	private final Logger LOGGER = Logger.getLogger("ServidorNomes");
    
	public ServidorNomes(int porta) {
		this.porta = porta;
	}
	
	// apenas para uso nos Testes
	public List<Servico> getServicos(){
		return servicos;
	}
	
	public void parar() throws IOException{
		if(server != null && !server.isClosed()){
			server.close();
		}
	}

	@Override
	public void run() {
		
		try {
			server = new ServerSocket(porta, 10);
			LOGGER.info("SN iniciado. IP=localhost|PORTA=" + porta);
				
			String recepcao;
			String[] parametros;

			while(true){
				Socket cliente = server.accept();
				
				String ipRemoto = cliente.getRemoteSocketAddress().toString();
				LOGGER.info("Cliente aceito. IP=" + ipRemoto);
				
				DataOutputStream resposta = new DataOutputStream(cliente.getOutputStream());
				recepcao = new DataInputStream(cliente.getInputStream()).readUTF();
				parametros = recepcao.split("_");

				String acao = parametros[0];
				String nome = parametros[1];
				String ip, porta;
				
				/*
				 * Formato de entrada
				 * 
				 * cadastrar: 0_SOMA_192.168.1.10_8881
				 * recuperar: 1_SOMA
				 * */ 
				if(acao.equals("0")){ 
					ip = parametros[2];
					porta = parametros[3];
					
					Servico servico = new Servico(nome, ip, Integer.parseInt(porta));
					servicos.add(servico);

					//TODO: atualizar SN secundário
					//TODO: persistir no DB
					resposta.writeUTF("OK");
					LOGGER.info("Servico Cadastrado. Servico=" + servico.toString());

				}else if(acao.equals("1")){ 
					ServicoUtil util = new ServicoUtil();
					ip = porta = "NAO_ENCONTRADO";
					
					List<Servico> especificos = util.agruparPorNome(servicos, nome);
					Servico melhorEscolha = util.getServicoComMenorUso(especificos);
					
					ip = melhorEscolha.getIp();
					porta = melhorEscolha.getPortaString();
					
					// formato: 192.168.1.10_8881
					resposta.writeUTF(String.format("%s_%s", ip, porta));
					LOGGER.info("Servico Recuperado. Servico=" + melhorEscolha.toString());
				}
			}  
		} catch (SocketException se) {
			// provavelmente paramos o servidor, nada a ser feito
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}