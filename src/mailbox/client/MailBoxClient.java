package mailbox.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import javax.naming.InitialContext;

import com.google.common.collect.Iterators;

import project.entity.Msg;
import project.entity.NewsGroup;
import mailbox.ejb.IMailBoxManager;

public class MailBoxClient {

	public static void main(String[] args) {

		IMailBoxManager intf;
		Scanner in = new Scanner(System.in);
		Collection <Msg> listofmsgs = new ArrayList<Msg> ();

		
		try {
			InitialContext ic = new InitialContext();
			intf = (IMailBoxManager) ic.lookup("mailbox.ejb.IMailBoxManager");
			intf.init();
			listofmsgs = intf.readAUserNewMessages("test");
			for (int i=0; i<listofmsgs.size();i++)
	        {
				System.out.println((Iterators.get(listofmsgs.iterator(), i)).getReceiverName()+"|"+(Iterators.get(listofmsgs.iterator(), i)).getBody());
	        }
			//intf.deleteAUserMessage(255);
			intf.deleteAUserReadMessages("test");
			Msg m1 = new Msg();
			Date date = new Date();
			m1.setSenderName("test2");
			m1.setReceiverName("test1");
			m1.setSubject("hello");
			m1.setSendingDate(date);
			m1.setBody("hello test2 from test1");
			intf.sendAMessageToABox(m1);
			
			NewsGroup ng1 = new NewsGroup ();
			Date date1 = new Date();
			ng1.setSenderName("haythem");
			ng1.setSubject("hello");
			ng1.setSendingDate(date);
			ng1.setBody("hello newsgroup from test2");
			System.out.println(intf.sendNews(ng1));
			
			NewsGroup ng2 = new NewsGroup ();
			ng2.setSenderName("test2");
			ng2.setSubject("hello");
			ng2.setSendingDate(date);
			ng2.setBody("hello newsgroup from test2");
			System.out.println(intf.sendNews(ng2));
			
			System.out.println(intf.deleteAUserNews(ng2));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
