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

	private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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
		setNome(nome);
		setEmail(email);
		setSenha(senha);
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
}
