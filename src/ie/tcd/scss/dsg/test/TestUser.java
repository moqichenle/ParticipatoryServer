package ie.tcd.scss.dsg.test;

import ie.tcd.scss.dsg.particpatory.StorageManager;
import ie.tcd.scss.dsg.po.Location;
import ie.tcd.scss.dsg.po.User;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class TestUser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Location location = new Location();
		location.setAccuracy(20.0f);
		location.setBearing(33.3f);
		location.setLatitude(20.3);
		location.setLongitude(20.3);
		location.setSpeed(5.4f);
		User user = new User();

		user.setAcceptPercent(0);
		user.setAverCycleSpeed(0);
		user.setAverDriveSpeed(0);
		user.setHasSensor((byte) 48);
		user.setLocation(location);
		user.setMode("walking");
		user.setRegisterId(null);
		user.setStreetName("Pearse Street");
		user.setUpdatedTime(System.currentTimeMillis());

		StorageManager sm = new StorageManager();
		user = (User) sm.add(user);
		resp.getWriter().write(user.getUserId().toString());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getContentType().equals("application/json")) {
			System.out.println("get json request");
			try {
				BufferedReader reader = req.getReader();
				Gson gson = new Gson();
				User user = gson.fromJson(reader, User.class);
				System.out.println("get user ^-^");
				StorageManager sm = new StorageManager();
				user = (User) sm.add(user);
				System.out.println("userId is:"+user.getUserId());
				resp.getWriter().write(user.getUserId().toString());
			} catch (Exception e) {
				// crash and burn
				throw new IOException("Error parsing JSON request string");
			}
		}else{
			resp.setStatus(500);
		}
		
	}

}
