package ie.tcd.scss.dsg.particpatory;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * Class to obtain information and configure about the Google Cloud Messaging
 * module.
 * 
 * @author Rene
 * 
 */
public class GCMUtilities {

	private static final int TRIES = 5;
	private static Sender SENDER = new Sender(Utils.API_KEY);
	
	public static String sendMessage(String device, String message) {
		try {
			System.out.println("prepare for sending  a message to phone.");
			Builder messagebuilder = new Message.Builder().addData("message", message);
			Message gcmMessage = messagebuilder.build();
			Result result = SENDER.send(gcmMessage, device, TRIES);
			if(result.getErrorCodeName() != null) {
				return "Error while sending message to GCM: " + result.getErrorCodeName();
			} else {
				return "Sending message to GCM succesfull: GCM-MessageId:" + result.getMessageId();
			}
		} catch (IOException e) {
			return e.getMessage();
		}
	}
}
