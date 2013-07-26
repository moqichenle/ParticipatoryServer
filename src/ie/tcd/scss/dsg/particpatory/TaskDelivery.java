package ie.tcd.scss.dsg.particpatory;

import java.util.List;

import ie.tcd.scss.dsg.po.TaskAssignment;
import ie.tcd.scss.dsg.po.TaskModel;
import ie.tcd.scss.dsg.po.User;

public class TaskDelivery {

	public static void assignTask(Long taskId, double latitude,
			double longtitude, String streetName) {
		UserManagement um = new UserManagement();
		TaskManagement tm = new TaskManagement();
		StorageManager sm = new StorageManager();
		TaskModel task = (TaskModel) sm.get(TaskModel.class, taskId);
		System.out.println("Get a task from database"+task);
		System.out.println("TaskDelivery-->receive a task "+taskId+"/"+latitude+"/"+longtitude+"/"+
				task.getSensorType()+"/"+task.getSearchRange());
		List<User> suitableUsers = um.suitableUsers(latitude, longtitude,
				task.getSensorType(), task.getSearchRange());
		System.out.println("TaskDelivery-->find suitableUsers how many:"+suitableUsers.size());
		String device;
		String message;
		if (suitableUsers.size() != 0) {
			// have suitable Users, assign tasks to them.
			for (int i = 0; i < suitableUsers.size(); i++) {
				User user = um.getCertainUser(suitableUsers.get(i).getUserId());
				TaskAssignment ta = new TaskAssignment();
				Long assignId ;
				if (i != 0) {
					// create a new task to user.
					Long newTaskId = tm.createMainTask(task.getQueryId(), task.getCategoryId(),
							latitude, longtitude, streetName);
					ta.setTaskId(newTaskId);
					ta.setUserId(user.getUserId());
					//ta = (TaskAssignment) sm.add(ta);
					//assignId = ta.getAssignId();
				}else{
					ta.setTaskId(task.getTaskId());
					ta.setUserId(user.getUserId());
					//ta = (TaskAssignment) sm.add(ta);
					//assignId = ta.getAssignId();
				}

				device = user.getRegisterId();
				message = task.getDescription() + "_" + task.getExpirePeriod()
						+ "_" + task.getTaskId() + "_"+86;
				System.out.println("MESSAGE="+message);
				// send message out, return infor if sending successful.
				System.out.println(GCMUtilities.sendMessage(device, message));
			}
		}else{
			System.out.println("TaskDelivery-->No suitable users.");
		}
	}
}
