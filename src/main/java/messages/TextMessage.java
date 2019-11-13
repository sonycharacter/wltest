package messages;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileOutputStream;

public class TextMessage {
	
	public static MimeMessage generate(String fasong, String jieshou, String biaoti, String content) throws Exception {
		// 创建Session实例对象
		Session session = Session.getDefaultInstance(new Properties());
		// 创建MimeMessage实例对象
		MimeMessage message = new MimeMessage(session);
		// 设置发件人
		message.setFrom(new InternetAddress(fasong));
		// 设置收件人
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(jieshou));
		// 设置发送日期
		message.setSentDate(new Date());
		// 设置邮件主题
		message.setSubject(biaoti);
		// 设置纯文本内容的邮件正文
		message.setText(content);
		// 保存并生成最终的邮件内容
		message.saveChanges();
		return message;
	}
}
