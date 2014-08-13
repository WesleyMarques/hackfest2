import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Evento;
import models.Participante;
import models.Tema;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.EventoInvalidoException;
import models.exceptions.PessoaInvalidaException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ParticipanteComSenhaTest {

	GenericDAO dao = new GenericDAOImpl();
	Participante user;
	private List<Tema> temas;
	
	@Before
	public void setUp(){
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void deveCadastrarNovoUsuariotest() {
		
			
	}
	
	

}
