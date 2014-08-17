package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Evento;
import models.EventoComparator;
import models.Local;
import models.Participante;
import models.Tema;
import models.exceptions.EventoInvalidoException;
import models.exceptions.PessoaInvalidaException;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.meusEventos;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventoController extends Controller {

	private final static Form<Evento> EVENTO_FORM = form(Evento.class);
	private final static Form<Participante> PARTICIPANTE_FORM = form(Participante.class);
	

	@Transactional
	public static Result eventosPorTema(int id) throws PessoaInvalidaException, EventoInvalidoException{
	
		List<Evento> todosEventos = Application.getDao().findAllByClassName("Evento");
		
		List<Evento> eventosRequeridos = new ArrayList<Evento>();
		
		for (Evento ev : todosEventos) {
			if (ev.getTemas().contains(Tema.values()[(int) id])){
				eventosRequeridos.add(ev);
			}
		}

		Collections.sort(eventosRequeridos, new EventoComparator());
		
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		
		try {
			json = mapper.writeValueAsString(eventosRequeridos);
		} catch (Exception e) {
			return badRequest();
		}
		
		return ok(json);
	}
	@Transactional
	public static Result listaInscritos(String email){
		Long idEvento = Long.parseLong(form().bindFromRequest().get("select-evento"));
		Evento evento = Application.getDao().findByEntityId(Evento.class, idEvento);
		return ok(meusEventos.render(Application.getSessionP(), evento));
	}
	
	@Transactional
	public static Result meusEventos(){
		if (session().get("email") == null) {
			return redirect(routes.LoginController.show());
		}
		Participante p = Application.getSessionP();
		return ok(meusEventos.render(p, (Evento)Application.getDao().findAllByClassName("Evento").get(0)));
	}
	@Transactional
	public static List<Evento> getEventosByOwnUser(){
		List<Evento> eventoAdmin = Application.getDao().findByAttributeName("Evento", "admin", Application.getSessionP().getEmail());
		return eventoAdmin;		
	}
	@Transactional
	public static Result novo() throws PessoaInvalidaException, EventoInvalidoException{
		Form<Evento> eventoFormRequest = EVENTO_FORM.bindFromRequest();
		if (EVENTO_FORM.hasErrors()) {
			return badRequest();
		} else {
			Evento novoEvento = eventoFormRequest.get();
			Long id = Long.parseLong(form().bindFromRequest().get("localOpt"));
			Local local = Application.getDao().findByEntityId(Local.class, id);
			novoEvento.setLocal(local);
			//System.out.println(novoEvento.getId()+" "+novoEvento.getAdmin());
			Application.getDao().persist(novoEvento);
			Application.getDao().flush();
			return redirect(controllers.routes.Application.index());
		}
	}
	
	@Transactional
	public static Result participar(long id) throws PessoaInvalidaException, EventoInvalidoException{
		
		if (PARTICIPANTE_FORM.bindFromRequest().hasErrors()) {
			return badRequest();
		} else {
			Evento evento = Application.getDao().findByEntityId(Evento.class, id);
			Participante novoParticipante = Application.getSessionP();
//			Participante novoParticipante = participanteFormRequest.get();
			if (!evento.addParticipante(novoParticipante)) {
				return badRequest();
			}
			Application.getDao().merge(evento);
			Application.getDao().flush();
			return redirect(controllers.routes.Application.index());
		}
	}
	
	@Transactional
	public static List<Local> getLocais() {
		return Application.getDao().findAllByClassName("Local");
	}
}
