package controllers;

import java.util.List;

import net.sf.ehcache.writer.writebehind.operations.DeleteAllOperation;
import models.Participante;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Login extends Controller {
	
	public static Form<Participante> loginForm = new Form<Participante>(Participante.class);
	private static GenericDAO dao = new GenericDAOImpl();
	
	public static Result show(){
		if (session().get("user") != null) {
			return redirect(routes.Application.index());			
		}
		return ok(login.render(loginForm));
	}

	
	@Transactional
	public static Result authenticate() {

		Participante userA = loginForm.bindFromRequest().get();

		String email = userA.getEmail();
		String senha = userA.getSenha();

        if (loginForm.hasErrors() || !validate(email, senha)) {
        	flash("fail", "Email ou Senha Inválidos");
        	return badRequest(login.render(loginForm));
        } else {
        	Participante user = (Participante) dao.findByAttributeName(
        			"Participante", "email", userA.getEmail()).get(0);
            session().clear();
            session("user", user.getNome());
            return redirect(routes.Application.index());
        }
    }
	
	private static boolean validate(String email, String senha) {
		List<Participante> u = dao.findByAttributeName("Participante", "email", email);
		if (u == null || u.isEmpty()) {
			return false;
		}
		if (!u.get(0).getSenha().equals(senha)) {
			return false;
		}
		return true;
	}
}
