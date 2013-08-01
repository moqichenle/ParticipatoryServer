package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.TaskDelivery;
import ie.tcd.scss.dsg.particpatory.TaskManagement;
import ie.tcd.scss.dsg.particpatory.UserManagement;
import ie.tcd.scss.dsg.particpatory.UserReport;
import ie.tcd.scss.dsg.particpatory.Utils;
import ie.tcd.scss.dsg.po.Query;
import ie.tcd.scss.dsg.po.Report;
import ie.tcd.scss.dsg.po.ResultsToQuery;
import ie.tcd.scss.dsg.po.TaskModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class GetQueryResults extends HttpServlet {

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
		String queryId = req.getParameter("queryId");
		UserManagement um = new UserManagement();
		Query q = um.certainQuery(Long.valueOf(queryId));

		UserReport ur = new UserReport();
		int withInSeconds = 30 * 60 * 1000;// 30 mins
		List<Report> answerQueryReports = ur.answerQueryReports(
				q.getCategoryId(), withInSeconds);
		List<ResultsToQuery> results = new ArrayList<ResultsToQuery>();

		for (int i = 0; i < answerQueryReports.size(); i++) {
			ResultsToQuery rtq = new ResultsToQuery();
			Report r = answerQueryReports.get(i);
			String content = Utils.getCategoryName(r.getCategoryId()) + ":"
					+ r.getContend();
			rtq.setContent(content);
			rtq.setLatitude(r.getLatitude());
			rtq.setLongitude(r.getLongitude());
			rtq.setReportId(r.getReportId());
			rtq.setTaskId(null);
			rtq.setTaskComment(null);
			if (r.getAttachment() != null) {
				rtq.setResultImage(r.getAttachment().getBytes());
			} else {
				rtq.setResultImage(null);
			}
			rtq.setCategoryId(r.getCategoryId());
			results.add(rtq);
		}
		TaskManagement tm = new TaskManagement();
		List<TaskModel> answerQueryTasks = tm.answerQueryTask(
				q.getCategoryId(), withInSeconds);

		for (int j = 0; j < answerQueryTasks.size(); j++) {
			ResultsToQuery rtq = new ResultsToQuery();
			TaskModel t = answerQueryTasks.get(j);
			rtq.setTaskId(t.getTaskId());
			rtq.setLatitude(t.getLatitude());
			rtq.setLongitude(t.getLongitude());
			if (t.getPicture() != null) {
				rtq.setResultImage(t.getPicture().getBytes());
			} else {
				rtq.setResultImage(null);
			}
			rtq.setContent(null);
			rtq.setReportId(null);
			if (t.getComment() != null) {
				rtq.setTaskComment(t.getComment());
			} else {
				rtq.setTaskComment(null);
			}
			rtq.setCategoryId(t.getCategoryId());
			System.out.println("has a task which is finished" + t.getComment()
					+ "/" + t.getPicture());
			results.add(rtq);
		}

		Gson gson = new Gson();
		String json = gson.toJson(results);
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		if (results.size() == 0) {
			System.out.println("if no results towards a query:");
			// assign tasks.
			// TODO and at the same time , add the assigning into database.
			byte categoryId = q.getCategoryId();
			String streetName = q.getStreetName();
			double lat = q.getLatitude();
			double lont = q.getLongitude();
			Long taskId = tm.createMainTask(q.getQueryId(), categoryId, lat,
					lont, streetName);
			TaskDelivery.assignTask(Long.valueOf(taskId), lat, lont, streetName);
		}
		resp.setStatus(200);

	}
}
