package ie.tcd.scss.dsg.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Lele
 * 
 *         store user reports
 */
@Entity
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportId;

	private Long userId;
	private byte categoryId;
	private String contend;
	private long reportTime;
	private double latitude;
	private double longitude;
	private byte[] attachment;

	

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public byte getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(byte categoryId) {
		this.categoryId = categoryId;
	}

	public String getContend() {
		return contend;
	}

	public void setContend(String contend) {
		this.contend = contend;
	}

	public long getReportTime() {
		return reportTime;
	}

	public void setReportTime(long reportTime) {
		this.reportTime = reportTime;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

}
