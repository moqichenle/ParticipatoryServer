package ie.tcd.scss.dsg.test;

import ie.tcd.scss.dsg.particpatory.StorageManager;
import ie.tcd.scss.dsg.po.Report;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestReport extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Report report = new Report();
		report.setUserId((long) 15);
		report.setCategoryId((byte) 1);
		report.setContend("traffic jam!!");
		report.setLatitude(23.4);
		report.setLongitude(48.5);
		report.setReportTime(System.currentTimeMillis());
		report.setAttachment(null);
		StorageManager sm = new StorageManager();
		report = (Report) sm.add(report);
		resp.getWriter().write(
				"A report! Can we get the report No." + report.getReportId()
						+ " from user NO." + report.getUserId());
	}

}
