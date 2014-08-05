package controllers;

import java.util.List;

import models.Participante;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.registro;

public class Registro extends Controller{
	public static Form<Participante> registroForm;
	private static GenericDAO dao = new GenericDAOImpl();
	
	@Transactional
    public static Result show() {
        return ok(registro.render(registroForm));
    }

	@Transactional
	public static Result registrar() {

		Participante u = registroForm.bindFromRequest().get();
    	
		if (registroForm.hasErrors() || validate(u.getEmail())) {
			flash("fail", "Email já está em uso");
            return badRequest(registro.render(registroForm));
        } else {
        	dao.persist(u);
            return redirect(routes.Login.show());
        }
    }

	private static boolean validate(String email) {
		List<Participante> u = dao.findByAttributeName("Participante", "email", email);
		if (u == null || u.isEmpty()) {
			return true;
		}
		return false;
	}

}
