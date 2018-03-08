package com.example.hemanth.FinalProject;
/**
 * Created by hemanth on 06/30/2017.
 */
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class GMail {

	final String emailPort = "587";// gmail's smtp port
	final String smtpAuth = "true";
	final String starttls = "true";
	final String emailHost = "smtp.gmail.com";
	String fromEmail;
	String fromPassword;
	String toEmail;
	String emailSubject;
	String emailBody;
	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;


	public GMail(String fromEmail, String fromPassword,String toemail,
			 String emailSubject, String emailBody) {


		this.fromEmail = fromEmail;
		this.fromPassword = fromPassword;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;
		this.toEmail = toemail; //assigns the value of the parameter a to the field
		emailProperties = System.getProperties();

		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", smtpAuth);
		emailProperties.put("mail.smtp.starttls.enable", starttls);
	}

	public MimeMessage createEmailMessage()  {
		try {
			Log.e("Hema_LOG:", "INSIDE SEND MAIL in GMAIL");
			mailSession = Session.getDefaultInstance(emailProperties, null);
			emailMessage = new MimeMessage(mailSession);
			MimeBodyPart part = new MimeBodyPart();
			File pictureFileDir = getDir();
			String photoFile = "Picture.jpg";

			String filename = pictureFileDir.getPath() + File.separator + photoFile;
			Log.e("Hema_LOG:", "INSIDE SEND MAIL in GMAIL Attaching pfile=" + filename);
			FileDataSource fds = new FileDataSource(filename);
			part.setDataHandler(new DataHandler(fds));
			part.setFileName(fds.getName());


			Multipart mp = new MimeMultipart();
			mp.addBodyPart(part);



			emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));

			emailMessage.addRecipient(Message.RecipientType.TO,
					new InternetAddress(toEmail));

			emailMessage.setSubject(emailSubject);
			emailMessage.setContent(mp);


			Log.e("Hema_LOG:", "MAIL SENT");

		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return emailMessage;
	}

	public void sendEmail() throws AddressException, MessagingException {

		Transport transport = mailSession.getTransport("smtps");
		transport.connect(emailHost, fromEmail, fromPassword);
        Log.e("Hema_LOG:", "Email HOST-=" + emailHost + "FROM EMAIL=" + fromEmail + " FROM PASSWORD" + fromPassword + "=ema" + emailMessage.getAllRecipients());
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();

	}

	public File getDir() {
		File sdDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		return new File(sdDir, "CameraAPIDemo");
	}

}
