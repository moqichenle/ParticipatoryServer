package ie.tcd.scss.dsg.particpatory;

public class TaskDelivery {
	

	public static void assignTask(String device,String message) {
		System.out.println(GCMUtilities.sendMessage(device, message));
	}
}
