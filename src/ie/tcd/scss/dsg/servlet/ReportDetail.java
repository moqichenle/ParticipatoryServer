package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.UserReport;
import ie.tcd.scss.dsg.po.Report;
import ie.tcd.scss.dsg.po.ReportFromApp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ReportDetail extends HttpServlet {

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
		String reportId = req.getParameter("reportId");
		UserReport ur = new UserReport();
		Report report = ur.certainReport(Long.valueOf(reportId));
		ReportFromApp toapp = new ReportFromApp();
		toapp.setCategoryId(report.getCategoryId());
		toapp.setContend(report.getContend());
		toapp.setReportId(report.getReportId());
		toapp.setReportTime(report.getReportTime());
		toapp.setStreetName(report.getStreetName());
		if(report.getAttachment()!=null){
			toapp.setAttachment(report.getAttachment().getBytes());
		}else{
			toapp.setAttachment(null);
		}
		toapp.setUser(null);
		toapp.setUserId(report.getUserId());
		Gson gson = new Gson();
		String json = gson.toJson(toapp);
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		resp.setStatus(200);
	}

}
