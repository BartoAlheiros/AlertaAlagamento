package br.ufrpe.alertaAlagamentos.beans;

public class Localidade {
	
	private String nome;
	private float precip;
	private int mareh;
	private int riscoAlag; // risco atribuído a cada localidade, varia de 1 a 3 (1 - Baixo, 2 - Médio, 3 - Alto).
	private int riscoEminente; // risco calculado pelo Naive Bayes(inicia com '0'). e pode assumir os valores como os de riscoAlag.
	
	public Localidade(String nome, float precip, int mareh, int riscoAlag) {
		this.nome = nome;
		this.precip = precip;
		this.mareh = mareh;
		this.riscoAlag = riscoAlag;
		this.riscoEminente = 0;
	}

	public Localidade() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getPrecip() {
		return precip;
	}

	public void setPrecip(float precip) {
		this.precip = precip;
	}

	public int getMareh() {
		return mareh;
	}

	public void setMareh(int mareh) {
		this.mareh = mareh;
	}

	public int getRiscoAlagamento() {
		return riscoAlag;
	}

	public void setRiscoAlagamento(int risco) {
		this.riscoAlag = risco;
	}

	public int getRiscoEminente() {
		return riscoEminente;
	}

	public void setRiscoEminente(int riscoEminente) {
		this.riscoEminente = riscoEminente;
	}
	
	
}
