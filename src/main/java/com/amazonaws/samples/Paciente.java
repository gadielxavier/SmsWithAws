package com.amazonaws.samples;

public class Paciente {
	
	private String nome;
	private String email;
	private String telefone;
	private String data_nascimento;
	
	
	public Paciente(String nome, String email, String telefone, String data_nascimento) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.data_nascimento = data_nascimento;
	}
	
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getData_nascimento() {
		return data_nascimento;
	}
	
	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
	}
}
