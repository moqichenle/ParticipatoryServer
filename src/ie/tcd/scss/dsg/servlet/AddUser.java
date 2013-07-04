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

public class AddUser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getContentType().equals("application/json")) {
			System.out.println("get json request");
			try {
				BufferedReader reader = req.getReader();
				Gson gson = new Gson();
				User user = gson.fromJson(reader, User.class);
				System.out.println("get user ^-^");
				UserManagement um = new UserManagement();
				Long userId =  um.registerUser(user);
				System.out.println("userId is:"+userId);
				resp.getWriter().write(userId.toString());
			} catch (Exception e) {
				// crash and burn
				throw new IOException("Error parsing JSON request string");
			}
		}else{
			resp.setStatus(500);
		}
	}

}
