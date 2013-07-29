package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.TaskManagement;
import ie.tcd.scss.dsg.po.TaskFromApp;
import ie.tcd.scss.dsg.po.TaskModel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class TaskDetail extends HttpServlet {

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
		String taskId = req.getParameter("taskId");
		TaskManagement tm = new TaskManagement();
		TaskModel task = tm.certainTask(Long.valueOf(taskId));
		TaskFromApp tfa = new TaskFromApp();
		tfa.setDescription(task.getDescription());
		tfa.setComment(task.getComment());
		if(task.getPicture()!=null){
			tfa.setPicture(task.getPicture().getBytes());
		}else{
			tfa.setPicture(null);
		}
		tfa.setTaskId(task.getTaskId());
		Gson gson = new Gson();
		String json = gson.toJson(tfa);
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		resp.setStatus(200);
	}

}
