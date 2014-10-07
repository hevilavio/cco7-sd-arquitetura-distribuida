package br.cco.sd.modelo;

public interface Cliente {
	
	/**
	 * Busca pelo servi�o no qual o cliente ir� se conectar
	 *	
	 * */
	void preparar();
	
	/**
	 * Fluxo da itera��o com o server
	 * */
	void iniciar();
}
