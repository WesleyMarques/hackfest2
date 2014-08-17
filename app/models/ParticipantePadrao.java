/**
 * 
 */
package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Wesley
 *
 */
@Entity
public class ParticipantePadrao {
	
	
	@Id 
	@GeneratedValue
	private Long id;
	
	
	public ParticipantePadrao() {
	}
	
	/**
	 * Retorn os primeiros participantes da lista, sendo assim o critério é a ordem de cadastro
	 * @param participantes
	 * @param capacidade
	 * @return
	 */
	public List<Participante> getAceitos(List<Participante> participantes, Integer capacidade) {
		if (participantes.size() <= capacidade) {
			return participantes;
		} else {
			return participantes.subList(0, capacidade);
		}
	}
	
	public String getNome(){
		return "Ordem de inscrição";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ParticipantePadrao)) {
			return false;
		}
		ParticipantePadrao other = (ParticipantePadrao) obj;
		if (this.getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!(this.getId() == other.getId())) {
			return false;
		}
		return true;
	}

	public Long getId() {
		return this.id;
	}

}
