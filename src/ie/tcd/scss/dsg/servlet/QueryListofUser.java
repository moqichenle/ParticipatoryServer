package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.UserManagement;
import ie.tcd.scss.dsg.po.Query;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class QueryListofUser extends HttpServlet {

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
		String userId = req.getParameter("userId");
		UserManagement um = new UserManagement();
		List<Query> queryList = um.queryFromCertainUser(Long.valueOf(userId));
		Gson gson = new Gson();
		String json = gson.toJson(queryList);
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		resp.setStatus(200);
	}

	
}
