package ie.tcd.scss.dsg.po;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


/**
 * tasks that asking participants to take pictures, videos
 * 
 * @author Lele
 * 
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ObjectiveTask extends Task {

	private byte taskingType;
	private byte[] result;

	private Long parentId;

	public byte getTaskingType() {
		return taskingType;
	}

	public void setTaskingType(byte taskingType) {
		this.taskingType = taskingType;
	}

	public byte[] getResult() {
		return result;
	}

	public void setResult(byte[] result) {
		this.result = result;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
