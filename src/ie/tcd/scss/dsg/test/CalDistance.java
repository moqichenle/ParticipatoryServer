package ie.tcd.scss.dsg.test;


public class CalDistance {
	public static void main(String[] args) {
		int R = 6371; // km
		// double taskLatitude=40.7486;
		// double taskLongtitude=-73.9864;
		// double userLatitude = 40.84079432606736;
		// double userLongtitude = -73.9864;
		// double dLat = (userLatitude-taskLatitude)* Math.PI / 180;
		// double dLon = (userLongtitude-taskLongtitude)* Math.PI / 180;
		// double lat1 = taskLatitude* Math.PI / 180;
		// double lat2 = userLatitude* Math.PI / 180;
		//
		// double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		// Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) *
		// Math.cos(lat2);
		// double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		// double d = R * c;
		//
		// System.out.println("the distance between two points is:="+d);

		double lat1 = 40.7486;
		double lon1 = -73.9864;
		float brng = 180;
		double distance = 0.075;

		double lat2 = 0;
		double lon2 = 0;
		lat2 = lat1
				+ Math.asin(Math.sin(lat1) * Math.cos(distance / R)
						+ Math.cos(lat1) * Math.sin(distance / R)
						* Math.cos(brng));// ???do i need to plus lat1???
		lon2 = lon1
				+ Math.atan2(
						Math.sin(brng) * Math.sin(distance / R)
								* Math.cos(lat1),
						Math.cos(distance / R) - Math.sin(lat1)
								* Math.sin(lat2));

		System.out.println("the new location's latitude is :" + lat2);
		System.out.println("the new location's longtitude is :" + lon2);
	}
}
