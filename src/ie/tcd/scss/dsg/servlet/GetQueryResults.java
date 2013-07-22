package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.TaskDelivery;
import ie.tcd.scss.dsg.particpatory.TaskManagement;
import ie.tcd.scss.dsg.particpatory.UserManagement;
import ie.tcd.scss.dsg.particpatory.UserReport;
import ie.tcd.scss.dsg.particpatory.Utils;
import ie.tcd.scss.dsg.po.ObjectiveTask;
import ie.tcd.scss.dsg.po.Query;
import ie.tcd.scss.dsg.po.Report;
import ie.tcd.scss.dsg.po.ResultsToQuery;
import ie.tcd.scss.dsg.po.SubjectiveTask;

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
		int withInSeconds = 5 * 60 * 1000;// 5 mins
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
			rtq.setResultImage(null);
			rtq.setCategoryId(r.getCategoryId());
			results.add(rtq);
		}
		TaskManagement tm = new TaskManagement();
		List<ObjectiveTask> answerQueryOTasks = tm.answerQueryOTask(
				q.getCategoryId(), withInSeconds);
		List<SubjectiveTask> answerQuerySTasks = tm.answerQuerySTask(
				q.getCategoryId(), withInSeconds);

		for (int j = 0; j < answerQueryOTasks.size(); j++) {
			ResultsToQuery rtq = new ResultsToQuery();
			ObjectiveTask t = answerQueryOTasks.get(j);
			rtq.setTaskId(t.getTaskId());
			rtq.setLatitude(t.getLatitude());
			rtq.setLongitude(t.getLongitude());
			rtq.setResultImage(t.getResult());
			rtq.setContent(null);
			rtq.setReportId(null);
			rtq.setTaskComment(null);
			rtq.setCategoryId(t.getCategoryId());
			results.add(rtq);
		}
		for (int k = 0; k < answerQuerySTasks.size(); k++) {
			ResultsToQuery rtq = new ResultsToQuery();
			SubjectiveTask t = answerQuerySTasks.get(k);
			rtq.setTaskId(t.getTaskId());
			rtq.setLatitude(t.getLatitude());
			rtq.setLongitude(t.getLongitude());
			rtq.setTaskComment(t.getResult());
			rtq.setContent(null);
			rtq.setReportId(null);
			rtq.setResultImage(null);
			rtq.setCategoryId(t.getCategoryId());
			results.add(rtq);
		}
		Gson gson = new Gson();
		String json = gson.toJson(results);
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		if (results.size()==0) {
			System.out.println("if no results towards a query:");
			//assign tasks. TODO  and at the same time , add the assigning into database.
			String device = "APA91bEJa5xiYJcfJt3mRNnmX-vvA_0vkHhbnXOr_Eh-ebf5tM3WzAAJJncnyr7so4nvshtprR2VPp0R9G2Y524hkrzc6cgIydMJ_mVLKJ_2oktVLhx3j9XAeuZ0W88-lJOYuNeJI_pONsTIZUMgyUar7N6ybYSM3A";
			String message = "You have a task!";
			TaskDelivery.assignTask(device,message);
		}
		resp.setStatus(200);
		

	}
}
