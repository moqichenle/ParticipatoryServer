package ie.tcd.scss.dsg.particpatory;

import java.util.List;

import ie.tcd.scss.dsg.po.Report;

/**
 * manage users' report
 * 
 * Get users¡¯ report Get users¡¯ latest context which will be sent to the User
 * Manager part
 * 
 * @author Lele
 * 
 */
public class UserReport {
	StorageManager sm = new StorageManager();

	/**
	 * store the report from user. at the same time, update the user's context
	 * information
	 * 
	 * @param r
	 * @return
	 */
	public Long saveReport(Report r) {
		r = (Report) sm.add(r);
		return r.getReportId();
	}

	/**
	 * give reports to users who query sth under the category within some time.
	 * time differs from category to category.
	 * 
	 * @param categoryId
	 * @param withInSeconds
	 * @return
	 */
	public List<Report> answerQueryReports(byte categoryId, int withInSeconds) {

		String query = "select r from Report r where r.categoryId ="
				+ categoryId + " and r.reportTime <"
				+ (System.currentTimeMillis() - withInSeconds);
		@SuppressWarnings("unchecked")
		List<Report> list = (List<Report>) sm.getAll(query);
		return list;
	}
	
	/**
	 * get certain user's reports
	 * @param userId
	 * @return
	 */
	public List<Report> reportFromCertainUser(long userId){
		String query = "select r from Report r where r.userId="+userId;
		@SuppressWarnings("unchecked")
		List<Report> list = (List<Report>) sm.getAll(query);
		return list;
	}
}
