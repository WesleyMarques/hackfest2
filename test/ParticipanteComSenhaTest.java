import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Evento;
import models.Local;
import models.Participante;
import models.Tema;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.EventoInvalidoException;
import models.exceptions.PessoaInvalidaException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ParticipanteComSenhaTest extends AbstractTest{

	GenericDAO dao = new GenericDAOImpl();
	Participante user;
	private List<Tema> temas;
	
	@Test
	public void deveCadastrarEvento() throws EventoInvalidoException {
		Participante part = new Participante(nome, email, senha)
		dao.persist(part);
		Local local = new Local("UFCG", "manoel do o junior", 10);
		dao.persist(local);
		assertEquals(((Local)(dao.findAllByClassName("Local").get(0))).getNome(), "UFCG");
		List<Tema> temas = new ArrayList<Tema>();
		temas.add(Tema.ARDUINO);
		temas.add(Tema.DESAFIOS);
		Evento event = new Evento("Teste", "Testando", new Date(), temas, "wesley@gmail.com", local);
		dao.persist(event);
		assertTrue(dao.findAllByClassName("Evento").size()!= 0);
			
	}
	
	

}
