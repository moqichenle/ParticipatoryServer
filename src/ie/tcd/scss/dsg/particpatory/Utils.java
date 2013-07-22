package ie.tcd.scss.dsg.particpatory;

public class Utils {
	public static String API_KEY = "AIzaSyCkdZtUn3Y3kcR2Qnv51qI1f9ysWB7faP8";

	public static String getCategoryName(byte id) {
		if (id == (byte) 0) {
			return "Traffic";
		} else if (id == (byte) 1) {
			return "Impression";
		} else if (id == (byte) 2) {
			return "Queue";
		} else {
			return "no category";
		}
	}
}
