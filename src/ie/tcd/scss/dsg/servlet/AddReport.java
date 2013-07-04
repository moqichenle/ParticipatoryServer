package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.UserManagement;
import ie.tcd.scss.dsg.particpatory.UserReport;
import ie.tcd.scss.dsg.po.Location;
import ie.tcd.scss.dsg.po.Report;
import ie.tcd.scss.dsg.po.ReportFromApp;
import ie.tcd.scss.dsg.po.User;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class AddReport extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getContentType().equals("application/json")) {
			System.out.println("get json request");
			try {
				BufferedReader reader = req.getReader();
				Gson gson = new Gson();
				ReportFromApp reportFromApp = gson.fromJson(reader, ReportFromApp.class);
				Long userId = reportFromApp.getUserId();
				Report report= new Report();

				Location location = new Location();
				location = reportFromApp.getUser().getLocation();
				
				report.setCategoryId(reportFromApp.getCategoryId());
				report.setContend(reportFromApp.getContend());
				report.setLatitude(location.getLatitude());
				report.setLongitude(location.getLongitude());
				report.setReportTime(System.currentTimeMillis());
				report.setUserId(userId);
				
				System.out.println("new report from user " + userId + " ^-^");
				UserReport ur = new UserReport();
				ur.saveReport(report);
				
				
				//update user's context
				User newUser = new User();
				newUser = reportFromApp.getUser();
				System.out.println("get user updates!");
				UserManagement um = new UserManagement();
				um.updateContext(newUser);
				
				resp.setStatus(200);
			} catch (Exception e) {
				// crash and burn
				e.printStackTrace();
			}
		} else {
			resp.setStatus(500);
		}
	}
}
