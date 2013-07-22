package ie.tcd.scss.dsg.particpatory;

import ie.tcd.scss.dsg.po.ObjectiveTask;
import ie.tcd.scss.dsg.po.SubjectiveTask;
import ie.tcd.scss.dsg.po.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class StorageManager {

	EntityManager em;

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

	/**
	 * add a new object into database
	 * 
	 * @param o
	 * @return
	 */
	public Object add(Object o) {
		em = getEntityManager();
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
		em.close();
		return o;
	}

	/**
	 * search for single object in the database, according to the id
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object get(Class<?> clazz, long id) {
		em = getEntityManager();
		em.getTransaction().begin();
		Object o = em.find(clazz, id);
		em.getTransaction().commit();
		em.close();
		return o;
	}

	/**
	 * delete an object
	 * 
	 * @param o
	 */
	public void delete(Object o) {
		em = getEntityManager();
		em.getTransaction().begin();
		em.remove(o);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * according to the query to return all the results
	 * 
	 * @param query
	 * @return
	 */
	public List<?> getAll(String query) {
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery(query);
		List<?> results = q.getResultList();
		em.getTransaction().commit();
		em.close();
		return results;
	}

	/**
	 * update user's context information.
	 * 
	 * @param newUser
	 */
	public void updateUserContext(User newUser) {
		em = getEntityManager();
		em.getTransaction().begin();
		User user = em.find(User.class, newUser.getUserId());
		user.setAcceptPercent(newUser.getAcceptPercent());
		user.setAverCycleSpeed(newUser.getAverCycleSpeed());
		user.setAverDriveSpeed(newUser.getAverDriveSpeed());
		user.setAverWalkSpeed(newUser.getAverWalkSpeed());
		user.setMode(newUser.getMode());
		user.setLocation(newUser.getLocation());
		user.setStreetName(newUser.getStreetName());
		user.setUpdatedTime(System.currentTimeMillis());
		em.getTransaction().commit();
		em.close();
	}

	public void updateTaskStatus(long taskId, String clazz) {
		em = getEntityManager();
		em.getTransaction().begin();
		if (clazz.equals("ObjectiveTask")) {
			ObjectiveTask task = em.find(ObjectiveTask.class, taskId);
			task.setStatus(true);
		} else if (clazz.equals("SubjectiveTask")) {
			SubjectiveTask task = em.find(SubjectiveTask.class, taskId);
			task.setStatus(true);
		}
		em.getTransaction().commit();
		em.close();
	}
}
