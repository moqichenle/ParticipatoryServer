package ie.tcd.scss.dsg.test;

import ie.tcd.scss.dsg.particpatory.TaskManagement;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddTask extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		TaskManagement tmgr= new TaskManagement();
		Long id=tmgr.createMainTask((long)16, (byte)1, 45.5, 23.4);
		resp.getWriter().write("the latest task's id is : "+id);
	}

}
