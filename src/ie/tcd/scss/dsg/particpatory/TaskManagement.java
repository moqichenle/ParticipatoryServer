package ie.tcd.scss.dsg.particpatory;

import java.util.List;


import ie.tcd.scss.dsg.po.ObjectiveTask;
import ie.tcd.scss.dsg.po.SubjectiveTask;
import ie.tcd.scss.dsg.po.Task;

/**
 * Handle query. Look for data in DB for query; Create a main task for unsolved
 * query; define suitable people in DB to receive tasks; Divide the main task
 * into small subtasks; Gather information from feedback of users¡¯ tasks; Decide
 * if the task is done or not;
 * 
 * @author Lele
 * 
 */
public class TaskManagement {
	StorageManager sm = new StorageManager();

	/**
	 * when a user makes a query, search for the database to find out in the
	 * past withInSeconds if there are any subjective tasks finished in certain
	 * category.
	 * 
	 * @param categoryId
	 * @param withInSeconds
	 * @return
	 */
	public List<SubjectiveTask> answerQuerySTask(byte categoryId,
			int withInSeconds) {
		String query = "select o from ObjectiveTask o where o.categoryId ="
				+ categoryId + " and o.createdTime >"
				+ (System.currentTimeMillis() - withInSeconds)+" and o.createdTime<"+System.currentTimeMillis();
		@SuppressWarnings("unchecked")
		List<SubjectiveTask> list = (List<SubjectiveTask>) sm.getAll(query);
		return list;
	}

	/**
	 * when a user makes a query, search for the database to find out in the
	 * past withInSeconds if there are any objective tasks finished in certain
	 * category.
	 * 
	 * @param categoryId
	 * @param withInSeconds
	 * @return
	 */
	public List<ObjectiveTask> answerQueryOTask(byte categoryId,
			int withInSeconds) {
		String query = "select s from SubjectiveTask s where s.categoryId ="
				+ categoryId + " and s.createdTime >"
				+ (System.currentTimeMillis() - withInSeconds)+" and s.createdTime<"+System.currentTimeMillis();
		@SuppressWarnings("unchecked")
		List<ObjectiveTask> list = (List<ObjectiveTask>) sm.getAll(query);
		return list;
	}

	/**
	 * according to the category and the location, creating a new task, remember
	 * the query as well
	 * 
	 * @param queryId
	 * @param categoryId
	 * @param location
	 * @return
	 */
	public Long createMainTask(Long queryId, byte categoryId, double latitude,
			double longtitude) {
		Task t = new Task();
		/*
		 * for different category, create different task, has different
		 * sensorType.,different task description for example. categoryId=1,
		 * means traffic.
		 */
		switch (categoryId) {
		case 0:
			t.setDescription("how is the traffic situation in the street?");
			t.setSearchRange(30);
			t.setSensorType((byte) 48);
			break;
		}

		t.setCategoryId((byte) categoryId);
		t.setCreatedTime(System.currentTimeMillis());
		t.setExpirePeriod(300);
		t.setLatitude(latitude);
		t.setLongitude(longtitude);
		t.setQueryId(queryId);
		t.setStatus(false);
		t = (Task) sm.add(t);

		return t.getTaskId();
	}

	

	/**
	 * when the user said tasks(main tasks, or subtasks) are finished, the
	 * status will change to true;
	 * 
	 * @param taskId
	 * @return
	 */
	public void updateTaskStatus(long taskId, String taskKind) {
		sm.updateTaskStatus(taskId, taskKind);
	}

	/**
	 * create subtasks according to main tasks.(both subjective tasks and
	 * objective tasks)
	 * 
	 * @param parentTaskId
	 * @param categoryId
	 */
	public void createSubTasks(long parentTaskId, byte categoryId) {
		SubjectiveTask st = new SubjectiveTask();
		ObjectiveTask ot = new ObjectiveTask();
		st.setCreatedTime(System.currentTimeMillis());
		ot.setCreatedTime(System.currentTimeMillis());
		switch (categoryId) {
		case 0:
			break;
		}
		/*
		 * and more categories
		 */
	}
}
