package ie.tcd.scss.dsg.po;

//import javax.persistence.Entity;

/**
 * different categories provided. e.g, traffic, event impression, waiting queue
 * 
 * @author Ivy
 *
 */

public class Category {

	
	private byte categoryId;
	private String categoryName;

	public byte getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(byte categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
