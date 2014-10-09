package br.cco.sd.chat;

import java.io.IOException;
import java.util.List;

import br.cco.sd.modelo.Servico;
import br.cco.sd.servicos.ServidorNomes;

public class SimuladorServidorDeNomes {

	private Thread sNomesThread;
	private ServidorNomes sNomes;
	
	
	public void iniciar(int porta) {
		sNomes = new ServidorNomes(porta);
		sNomesThread = new Thread(sNomes);
		sNomesThread.start();
	}

	public void finalizar() {
		try {
			sNomes.parar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Servico> getServicos() {
		return sNomes.getServicos();
	}

}
