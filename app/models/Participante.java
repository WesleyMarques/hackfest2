package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import models.exceptions.PessoaInvalidaException;

import org.hibernate.validator.constraints.Email;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

@Entity
public class Participante {

	private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	@MaxLength(value = 70)
	private String nome;
	
	@Required
	@NotNull
	private String senha;

	@Email
	@NotNull
	@MaxLength(value = 70)
	private String email;

	

	public Participante() {
	}

	/**
	 * 
	 * @param nome
	 * @param email
	 * @throws PessoaInvalidaException
	 */
	public Participante(String nome, String email, String senha)
			throws PessoaInvalidaException {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public Long getId(){
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	/**
	 * 
	 * @param nome
	 * @throws PessoaInvalidaException
	 */
	public void setNome(String nome) throws PessoaInvalidaException {
		if (nome == null)
			throw new PessoaInvalidaException("Parametro nulo");
		if (nome.length() > 70)
			throw new PessoaInvalidaException("Nome longo");
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 * @throws PessoaInvalidaException
	 */
	public void setEmail(String email) throws PessoaInvalidaException {
		if (email == null)
			throw new PessoaInvalidaException("Parametro nulo");
		if (!email.matches(EMAIL_PATTERN))
			throw new PessoaInvalidaException("Email inválido");
		if (email.length() > 70)
			throw new PessoaInvalidaException("Email longo");
		this.email = email;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Participante)) {
			return false;
		}
		Participante other = (Participante) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equalsIgnoreCase(other.email)) {
			return false;
		}
		return true;
	}
}
