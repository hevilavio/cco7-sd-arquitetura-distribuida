package br.cco.sd.clientes;

public interface Cliente {
	
	/**
	 * Busca pelo serviço no qual o cliente irá se conectar
	 *	
	 * */
	void preparar();
	
	/**
	 * Fluxo da iteração com o server
	 * */
	void iniciar();
}
