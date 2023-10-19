package unip.com.model;

import java.time.LocalDateTime;

public class FilterCo2 {
	
	private String estado;
	private String cidade;
	private String bairro;
	private LocalDateTime dataInicial;
	private LocalDateTime dataFinal;
	
	
	
	public FilterCo2(String estado, String cidade, String bairro, LocalDateTime dataInicial, LocalDateTime dataFinal) {
		super();
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public LocalDateTime getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(LocalDateTime dataInicial) {
		this.dataInicial = dataInicial;
	}
	public LocalDateTime getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(LocalDateTime dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	

}
