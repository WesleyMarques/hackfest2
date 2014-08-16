package controllers;

import java.util.List;

import models.Participante;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.PessoaInvalidaException;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class RegistroController extends Controller {
	private static Form<Participante> registroForm = Form
			.form(Participante.class);
	private static GenericDAO dao = new GenericDAOImpl();

	@Transactional
	public static Result show() {
		return ok(registro.render(registroForm));
	}

	@Transactional
	public static Result registrar() throws PessoaInvalidaException{

		Form<Participante> registroPessoa = registroForm.bindFromRequest();
		Participante u;

		if (registroForm.hasErrors()) {
			flash("fail", "Erro na captura dos dados");
			return badRequest(registro.render(registroPessoa));
		} else {
			u = criaParticipante(registroPessoa);
			if (!validate(u.getEmail())) {
				flash("fail", "Email já está em uso");
				return badRequest(registro.render(registroPessoa));
			} else {
				dao.persist(u);
				dao.flush();
				return redirect(routes.LoginController.show());
			}

		}

	}

	private static Participante criaParticipante(Form<Participante> registroPessoa) throws PessoaInvalidaException {
		Participante p = new Participante();
		p.setEmail(registroPessoa.get().getEmail());
		p.setNome(registroPessoa.get().getNome());
		p.setSenha(registroPessoa.get().getSenha());
		return p;
	}

	private static boolean validate(String email) {
		List<Participante> u = dao.findByAttributeName("Participante", "email",
				email);
		if (u != null && u.isEmpty()) {
			return true;
		}
		return false;
	}

}
