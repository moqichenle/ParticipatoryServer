package ie.tcd.scss.dsg.servlet;

import ie.tcd.scss.dsg.particpatory.TaskManagement;
import ie.tcd.scss.dsg.po.TaskFromApp;
import ie.tcd.scss.dsg.po.TaskModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class TaskListofUser extends HttpServlet {

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
		TaskManagement tm = new TaskManagement();
		List<TaskModel> list = tm.getTasksforUser(Long.valueOf(userId));
		List<TaskFromApp> res=new ArrayList<TaskFromApp>(); 
		System.out.println("tasklist of user--->"+list.size());
		for(int i =0;i<list.size();i++){
			System.out.println("tasklist of user--->"+list.get(i)+"/"+list.get(i).getTaskId());
			TaskFromApp tfa = new TaskFromApp();
			tfa.setTaskId(list.get(i).getTaskId());
			tfa.setDescription(list.get(i).getDescription());
			tfa.setStatus(list.get(i).isStatus());
			res.add(tfa);
		}
		Gson gson = new Gson();
		String json = gson.toJson(res);
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		resp.setStatus(200);
	}

}
