package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.Required;


@Entity
public class Usuario {

	@Id
	@Required
	@NonEmpty
	private String	email;

	@Required
	private String	name;

	@Required
	private String	senha;

	/**
	 * @return o email do usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return nome do usuario
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return a senha do usuario
	 */
	public String getPassword() {
		return senha;
	}

	/** 
	 * @param senha
	 */
	public void setPassword(String senha) {
		this.senha = senha;
		this.senha = String.valueOf(this.hashCode());
	}

	@Override
	public String toString() {
		return String.format(name+" - "+ email);
	}
}
