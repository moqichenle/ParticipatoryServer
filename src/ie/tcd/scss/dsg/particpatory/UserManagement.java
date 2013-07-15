package ie.tcd.scss.dsg.particpatory;

import ie.tcd.scss.dsg.po.Location;
import ie.tcd.scss.dsg.po.Query;
import ie.tcd.scss.dsg.po.User;

import java.util.List;

import com.google.appengine.api.search.GeoPoint;

/**
 * manage users. Register, Get latest user¡¯s context
 * 
 * @author Lele
 * 
 */
public class UserManagement {
	StorageManager sm = new StorageManager();

	/**
	 * user registration, need to be stored in database, return the userId.
	 * 
	 * @param user
	 * @return
	 */
	public Long registerUser(User user) {
		user = (User) sm.add(user);
		return user.getUserId();
	}

	/**
	 * update user's context information, need to be updated in the database
	 * 
	 * @param user
	 * @return
	 */
	public void updateContext(User user) {
		sm.updateUserContext(user);
	}

	/**
	 * get user's context according to the userId
	 * 
	 * @param userId
	 * @return
	 */
	public User getCertainUser(long userId) {
		User user = (User) sm.get(User.class, userId);
		return user;
	}

	/**
	 * according to user's location(calculation of current location according to
	 * the last update),has certain sensors, the range of searching to find out
	 * which users are suitable to finish a task
	 * 
	 * @param location
	 * @param sensorType
	 * @param searchrange
	 * @return
	 */
	public List<User> suitableUsers(double latitude, double longtitude,
			byte sensorType, double searchrange) {

		/*
		 * for every user,check it's location is inside the range. using
		 * ifUserInRange. select all the users whose current location is inside
		 * a certain range select all the users whose current location is
		 * outside the first range, but inside a bigger range.
		 * 
		 * ?? when to estimate the user's location?
		 */
		return null;
	}

	/**
	 * if the time of a latest update of user's context is inside of 5mins, then
	 * use current location information in the database. otherwise, calculate
	 * the estimated current location through speed, mode,and bearing.
	 * 
	 * @param user
	 * @return
	 */
	public GeoPoint estimateLocation(User user) {
		Location location = user.getLocation();
		double lat1 = location.getLatitude();
		double lon1 = location.getLongitude();
		float brng = location.getBearing();
		float speed = location.getSpeed();// per minute
		long timeMoved = (long) ((double) (System.currentTimeMillis() - user
				.getUpdatedTime()) / 60);// calculate into min.
		double distance = speed * timeMoved;

		double lat2, lon2;
		int R = 6371;// the radium of earth
		lat2 = lat1
				+ Math.asin(Math.sin(lat1) * Math.cos(distance / R)
						+ Math.cos(lat1) * Math.sin(distance / R)
						* Math.cos(brng));
		lon2 = lon1
				+ Math.atan2(
						Math.sin(brng) * Math.sin(distance / R)
								* Math.cos(lat1),
						Math.cos(distance / R) - Math.sin(lat1)
								* Math.sin(lat2));
		GeoPoint newLocation = new GeoPoint(lat2, lon2);
		return newLocation;
	}

	/**
	 * to find out if the location in inside certain range
	 * 
	 * @param taskLatitude
	 * @param taskLongtitude
	 * @param userLatitude
	 * @param userLongtitude
	 * @return
	 */
	public boolean ifUserInRange(double taskLatitude, double taskLongtitude,
			double userLatitude, double userLongtitude, double searchRange) {
		/*
		 * calculate the distance between two points.
		 */
		int R = 6371; // km
		double dLat = (userLatitude - taskLatitude) * Math.PI / 180;
		double dLon = (userLongtitude - taskLongtitude) * Math.PI / 180;
		double lat1 = taskLatitude * Math.PI / 180;
		double lat2 = userLatitude * Math.PI / 180;

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
				* Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;

		if (d <= searchRange) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * add new user query
	 * 
	 * @param query
	 */
	public void addQuery(Query query) {
		sm.add(query);
		//TODO when a new query comes, should check if there is any reply corresponding to the query.
		
	}

	/**
	 * get certain user's queries
	 * 
	 * @param userId
	 * @return
	 */
	public List<Query> queryFromCertainUser(long userId) {
		String query = "select q from Query q where q.userId=" + userId;
		@SuppressWarnings("unchecked")
		List<Query> list = (List<Query>) sm.getAll(query);
		return list;
	}

	/**
	 * get a detailed query
	 * 
	 * @param reportId
	 * @return
	 */
	public Query certainQuery(long queryId) {
		String query = "select q from Query q where q.queryId=" + queryId;
		@SuppressWarnings("unchecked")
		List<Query> list = (List<Query>) sm.getAll(query);
		return list.get(0);
	}
}
