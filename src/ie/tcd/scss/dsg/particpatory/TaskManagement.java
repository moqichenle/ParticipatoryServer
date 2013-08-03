package ie.tcd.scss.dsg.particpatory;

import ie.tcd.scss.dsg.po.TaskAssignment;
import ie.tcd.scss.dsg.po.TaskModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Handle query. Look for data in DB for query; Create a main task for unsolved
 * query; define suitable people in DB to receive tasks; Divide the main task
 * into small subtasks; Gather information from feedback of users�� tasks; Decide
 * if the task is done or not;
 * 
 * @author Lele
 * 
 */
public class TaskManagement {
	StorageManager sm = new StorageManager();

	/**
	 * when a user makes a query, search for the database to find out in the
	 * past withInSeconds if there are any tasks finished in certain category.
	 * 
	 * @param categoryId
	 * @param withInSeconds
	 * @return
	 */
	public List<TaskModel> answerQueryTask(byte categoryId, int withInSeconds) {
		String query = "select t from TaskModel t where t.categoryId ="
				+ categoryId + " and t.createdTime >"
				+ (System.currentTimeMillis() - withInSeconds)
				+ " and t.createdTime<" + System.currentTimeMillis()
				+ " and t.status = true";
		@SuppressWarnings("unchecked")
		List<TaskModel> list = (List<TaskModel>) sm.getAll(query);
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
			double longtitude, String location) {
		System.out.println("TaskManagement-->a new Task");
		TaskModel t = new TaskModel();
		/*
		 * for different category, create different task, has different
		 * sensorType.,different task description for example. categoryId=1,
		 * means traffic.
		 */
		switch (categoryId) {
		case 0:
			t.setDescription("how is the traffic situation at " + location
					+ " ?");
			t.setSearchRange(1000);
			break;
		case 1:
			t.setDescription("how is the event at " + location + " ?");
			t.setSearchRange(200);

			break;
		case 2:
			t.setDescription("how is the queue at " + location + " ?");
			t.setSearchRange(200);
			break;
		}
		t.setSensorType((byte) 48);
		t.setCategoryId((byte) categoryId);
		t.setCreatedTime(System.currentTimeMillis());
		t.setExpirePeriod(5 * 60 * 1000);// 5 mins
		t.setLatitude(latitude);
		t.setLongitude(longtitude);
		t.setQueryId(queryId);
		t.setStatus(false);
		t.setComment(null);
		t.setPicture(null);
		t = (TaskModel) sm.add(t);
		return t.getTaskId();
	}

	/**
	 * when the user said tasks(main tasks, or subtasks) are finished, update
	 * the task information. add user's comment or uploaded picture;
	 * 
	 * @param task
	 */
	public void updateTask(TaskModel task) {
		sm.updateTaskStatus(task);
	}

	/**
	 * get all the tasks a user received
	 * 
	 * @param userId
	 * @return
	 */
	public List<TaskModel> getTasksforUser(Long userId) {
		String query = "select a from TaskAssignment a where a.userId="
				+ userId +" ORDER BY a.taskId descending";
		List<TaskModel> list = new ArrayList<TaskModel>();
		@SuppressWarnings("unchecked")
		List<TaskAssignment> tasks = (List<TaskAssignment>) sm.getAll(query);
		for (int i = 0; i < tasks.size(); i++) {
			Long taskId = tasks.get(i).getTaskId();
			TaskModel model = new TaskModel();
			model = (TaskModel) sm.get(TaskModel.class, taskId);
			list.add(model);
		}
		return list;
	}
	
	public TaskModel certainTask(Long taskId){
		TaskModel t = (TaskModel) sm.get(TaskModel.class, taskId);
		return t;
	}
}
