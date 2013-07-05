package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.UserReport;
import ie.tcd.scss.dsg.po.Report;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ReportListofUser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp); 
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userId = req.getParameter("userId");
		System.out.println("report list of user received userId="+userId);
		UserReport ur = new UserReport();
		List<Report> reportList = ur.reportFromCertainUser(Long.valueOf(userId));
		Gson gson = new Gson();
		String json = gson.toJson(reportList);
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		resp.setStatus(200);
	}

}
