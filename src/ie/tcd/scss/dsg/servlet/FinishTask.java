package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.TaskManagement;
import ie.tcd.scss.dsg.po.TaskFromApp;
import ie.tcd.scss.dsg.po.TaskModel;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.google.gson.Gson;

public class FinishTask extends HttpServlet {

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
		if (req.getContentType().equals("application/json")) {
			System.out.println("get json request");
			try {
				BufferedReader reader = req.getReader();
				Gson gson = new Gson();
				TaskFromApp task = gson.fromJson(reader, TaskFromApp.class);
				TaskModel model = new TaskModel();
				model.setTaskId(task.getTaskId());
				if(task.getComment()!=null){
					model.setComment(task.getComment());
				}else{
					model.setComment(null);
				}
				if(task.getPicture()!=null){
					model.setPicture(new Blob(task.getPicture()));
				}else{
					model.setPicture(null);
				}
				TaskManagement tm = new TaskManagement();
				tm.updateTask(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.setStatus(200);
		}
	}
}
