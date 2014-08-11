package controllers;

import java.util.List;

import net.sf.ehcache.writer.writebehind.operations.DeleteAllOperation;
import models.Participante;
import models.Usuario;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Login extends Controller {
	
	public static Form<Usuario> loginForm = Form.form(Usuario.class);
	private static GenericDAO dao = new GenericDAOImpl();
	
	public static Result show(){
		if (session().get("email") != null) {
			return redirect(routes.Application.index());			
		}
		return ok(login.render(loginForm));
	}

	
	@Transactional
	public static Result authenticate() {

		Form<Usuario> userForm = loginForm.bindFromRequest();
		Usuario userA;

		if (userForm.hasErrors()) {
			flash("fail", "Erro na captura dos dados");
        	return badRequest(login.render(loginForm));						
		}else{
			userA = userForm.get();
			String email = userA.getEmail();
			String senha = userA.getSenha();

	        if (!validate(email, senha)) {
	        	flash("fail", "Email ou Senha Inválidos");
	        	return badRequest(login.render(loginForm));
	        } else {
	        	Participante user = (Participante) dao.findByAttributeName(
	        			"Participante", "email", userA.getEmail()).get(0);
	            session().clear();
	            session("email", user.getEmail());
	            System.err.println("cheguei");
	            return redirect(routes.Application.index());
	        }
		}
		
    }
	
	@Transactional
	public static Result logout() {
		session().clear();
		flash("success", "Você saiu do sistema!");
		return ok(login.render(Form.form(Usuario.class)));
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
