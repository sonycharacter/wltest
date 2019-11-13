package mooctest;

import java.sql.*;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import messages.TextMessage;

public class SelectTest {
	public static class MailClientSend {
		  private Session session;
		  private Transport transport;
		  private String username = "731390924@qq.com";
		  private String password = "***************";//因为要提交慕课，所以把密码这一段给换了，防止被盗号
		  private String smtpServer = "smtp.qq.com";//QQ邮箱的接收服务器
		  public void init()throws Exception{
			//设置属性
		    Properties  props = new Properties();
		    props.put("mail.transport.protocol", "smtp");
		    props.put("mail.smtp.class", "com.sun.mail.smtp.SMTPTransport");
		    props.put("mail.smtp.host", smtpServer); //设置发送邮件服务器
		    props.put("mail.smtp.port", "25");
		    props.put("mail.smtp.auth", "true"); //SMTP服务器需要身份验证
		    // 创建Session对象
		    session = Session.getInstance(props,new Authenticator(){   //验账账户 
		        public PasswordAuthentication getPasswordAuthentication() { 
		          return new PasswordAuthentication(username, password); 
		        }            
		 });
		    session.setDebug(true); 
		    // 创建Transport对象
		    transport = session.getTransport();
		  }
		  
		  public void sendMessage()throws Exception{
			  Connection conn = null;
		        try {
		        	String fasong = null; // 发件人地址
		    		String jieshou = null; // 收件人地址
		    		String biaoti = null;
		    		String content = null;
		            //从Druid获取
		            conn = DruidFactory1.getConnection();
		            //构建数据库执行者
		            Statement stmt = conn.createStatement(); 
		            System.out.println("创建Statement成功！");      
		            //执行SQL语句并返回结果到ResultSet
		            ResultSet rs = stmt.executeQuery("select fasong,jieshou,biaoti,content from t_mail where id=1");
		            //开始遍历ResultSet数据
		            if(rs.next()){
		            	fasong = rs.getString(1);
		            	jieshou = rs.getString(2);
		            	biaoti = rs.getString(3);
		            	content = rs.getString(4);
		            	Message msg = TextMessage.generate(fasong,jieshou,biaoti,content);
		            	//发送邮件 
		            	transport.connect();
		            	transport.sendMessage(msg, msg.getAllRecipients());
		            	//打印结果
		            	System.out.println("邮件已经成功发送");
		            }
		            rs.close();
		            stmt.close();
		        }catch (Exception e){
		            e.printStackTrace();
		        }finally {
		        	try	{
		        		if(null != conn) {
		            		conn.close();
		            	}
		        	} catch (SQLException e){
		                e.printStackTrace();
		        	}        	
		        }
		  }
		  public void close()throws Exception
		  {
			transport.close();
		  }
	}
    public static void main(String[] args) throws Exception{    	
            MailClientSend client=new MailClientSend();
            //初始化
            client.init();
            //发送邮件
            client.sendMessage();
            //关闭连接
            client.close();
    }
}