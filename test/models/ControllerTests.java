package models;
import static org.junit.Assert.*;
import geral.AbstractTest;

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

import org.junit.Test;


public class ControllerTests extends AbstractTest{

	GenericDAO dao = new GenericDAOImpl();
	
	
	@Test
	public void deveCriarParticipante() throws PessoaInvalidaException{
		Participante novoParticipante = new Participante("Wesley Nunes", "Wesley@gmail.com", "1234");
		dao.persist(novoParticipante);
		assertTrue(dao.findAllByClassName("Participante").size() == 1);
		novoParticipante = new Participante("Luis Fernando", "Luis@gmail.com", "4312");
		dao.persist(novoParticipante);
		assertTrue(dao.findAllByClassName("Participante").size() > 1);
		
		
		
	}
	
	@Test
	public void deveCriarLocal(){
		Local local = new Local("SESI", "Marciel Pinheiro", 10);
		dao.persist(local);
		assertTrue(dao.findAllByClassName("Local").size() > 0);
		local = new Local("UEPB", "Rua João Pessoa", 10);
	    dao.persist(local);
		assertTrue(dao.findAllByClassName("Local").size() > 1);

	}
	
	@Test
	public void deveCadastrarEvento() throws EventoInvalidoException {
		List<Tema> temas = new ArrayList<Tema>();
		
		Local local = new Local("UFCG", "manoel do o junior", 10);
		dao.persist(local);
		
		temas.add(Tema.ARDUINO);
		temas.add(Tema.DESAFIOS);
		Evento event = new Evento("Teste", "Testando", new Date(), temas, "wesley@gmail.com", local);
		dao.persist(event);
		assertTrue(dao.findAllByClassName("Evento").size()!= 0);
	}


}
