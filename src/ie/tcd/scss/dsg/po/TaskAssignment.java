package ie.tcd.scss.dsg.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * assign tasks to users.
 * 
 * @author Lele
 * 
 */
@Entity
public class TaskAssignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assignId;
	private Long taskId;
	private Long userId;
	public Long getAssignId() {
		return assignId;
	}
	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

}
