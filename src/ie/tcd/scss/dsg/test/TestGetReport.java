package ie.tcd.scss.dsg.test;

import ie.tcd.scss.dsg.particpatory.UserReport;
import ie.tcd.scss.dsg.po.Report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestGetReport extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserReport ur = new UserReport();
		List<Report> list = new ArrayList<Report>();
		list = ur.answerQueryReports((byte) 1, 0);
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println("report content" + list.get(i).getReportId()
						+ ":" + list.get(i).getContend() + "from user:"
						+ list.get(i).getUserId());
			}
		}
		resp.getWriter().write("A report list");
	}

}
