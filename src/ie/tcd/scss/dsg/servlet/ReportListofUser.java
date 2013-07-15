package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.UserReport;
import ie.tcd.scss.dsg.po.Report;
import ie.tcd.scss.dsg.po.ReportFromApp;

import java.io.IOException;
import java.util.ArrayList;
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
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userId = req.getParameter("userId");
		System.out.println("report list of user received userId=" + userId);
		UserReport ur = new UserReport();
		List<Report> reportList = ur
				.reportFromCertainUser(Long.valueOf(userId));
		List<ReportFromApp> results = new ArrayList<ReportFromApp>();
		if (reportList != null) {
			for (int i = 0; i < reportList.size(); i++) {
				ReportFromApp toapp = new ReportFromApp();

				toapp.setCategoryId(reportList.get(i).getCategoryId());
				toapp.setContend(reportList.get(i).getContend());
				toapp.setReportId(reportList.get(i).getReportId());
				toapp.setReportTime(reportList.get(i).getReportTime());
				toapp.setStreetName(reportList.get(i).getStreetName());
				byte[] attachment = reportList.get(i).getAttachment().getBytes();
				toapp.setAttachment(attachment);
				results.add(toapp);
			}
		}

		Gson gson = new Gson();
		String json = gson.toJson(results);
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		resp.setStatus(200);
	}

}
