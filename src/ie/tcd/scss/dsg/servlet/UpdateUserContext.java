package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.UserManagement;
import ie.tcd.scss.dsg.po.User;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class UpdateUserContext extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getContentType().equals("application/json")) {
			System.out.println("get updating user's context request");
			BufferedReader reader = req.getReader();
			Gson gson = new Gson();
			User user = new User();
			user = gson.fromJson(reader, User.class);
			UserManagement um = new UserManagement();
			um.updateContext(user);
			resp.setStatus(200);
		}
	}
}
