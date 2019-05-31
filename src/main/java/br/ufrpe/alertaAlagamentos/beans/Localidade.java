package br.ufrpe.alertaAlagamentos.beans;

public class Localidade {
	
	public String nome;
	public int pluviometria;
	public int mareh;
	// public int riscoAlagamento; // risco atribuído a cada localidade, varia de 1 a 3 (1 - Baixo, 2 - Médio, 3 - Alto).
	public int risco; // risco calculado pelo Naive Bayes(inicia com '0'). e pode assumir os valores como os de riscoAlag.
	
	public Localidade(String nome, int precip, int nivelMareh, int riscoAlagamento) {
		this.nome = nome;
		this.pluviometria = precip;
		this.mareh = nivelMareh;
		// this.riscoAlagamento = riscoAlagamento;
		this.risco = 0;
	}

	public Localidade() {
		// TODO Auto-generated constructor stub
	}

}
