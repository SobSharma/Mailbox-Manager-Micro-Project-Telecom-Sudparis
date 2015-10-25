package mailbox.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.common.collect.Iterators;

import directory.ejb.IUserDirectory;
import project.entity.Msg;
import project.entity.NewsGroup;
import project.entity.User;

@Stateful(name = "mailbox.ejb/IMailBoxManager")
public class MailBoxManager implements IMailBoxManager {

	@PersistenceContext(unitName = "mp1")
	private EntityManager em;

	@Override
	public Collection<Msg> readAUserNewMessages(String username) {

		Query q = em
				.createQuery("select m from Msg m where m.receiverName = :username and m.alreadyRead = 0");
		q.setParameter("username", username);
		Collection<Msg> m = new ArrayList<Msg>(q.getResultList());
		updateMsg(username);
		return m;
	}

	public void updateMsg(String username) {
		Query q = em
				.createQuery("UPDATE Msg m SET m.alreadyRead = 1 WHERE m.receiverName = :username");
		q.setParameter("username", username);
		int rslt = q.executeUpdate();
	}

	@Override
	public Collection<Msg> readAUserAllMessages(String username) {
		Query q = em
				.createQuery("select m from Msg m where m.receiverName = :username");
		q.setParameter("username", username);
		Collection<Msg> m = new ArrayList<Msg>(q.getResultList());
		return m;
	}

	@Override
	public void deleteAUserMessage(int idMsg) {
		Query q = em.createQuery("Delete FROM Msg WHERE IDMSG= :id");
		q.setParameter("id", idMsg);
		int x = q.executeUpdate();
	}

	@Override
	public void deleteAUserReadMessages(String username) {
		Query q = em
				.createQuery("Delete FROM Msg m WHERE m.alreadyRead = 1 and m.receiverName = :username");
		q.setParameter("username", username);
		int x = q.executeUpdate();

	}

	@Override
	public void sendAMessageToABox(Msg message) {
		// TODO Auto-generated method stub
		/*
		 * Query q = em.createNativeQuery(
		 * "Insert into MSG (senderName,receiverName,subject,sendingDate,body,alreadyRead) values ('"
		 * +
		 * message.getSenderName()+"','"+message.getReceiverName()+"','"+message
		 * .
		 * getSubject()+"','"+message.getSendingDate()+"','"+message.getBody()+"',"
		 * +0+")");
		 * 
		 * int y = q.executeUpdate();
		 */

		em.persist(message);
	}

	@Override
	public String sendNews(NewsGroup ng) {
		// TODO Auto-generated method stub

		try {
			InitialContext ic = new InitialContext();
			IUserDirectory intf = (IUserDirectory) ic
					.lookup("directory.ejb.IUserDirectory");
			int x = intf.lookupAUserRights(ng.getSenderName());
			if (x == 2) {
				em.persist(ng);
				return "Your message is successfuly posted in the newsBox";
			} else if (x == 1)
				return "You only have the right to read the newsBox ";
			else if (x == 0)
				return "You don't have the read/write access to the newsBox ";
			else
				return "You are not a registred user ";

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	@Override
	public String deleteAUserNews(NewsGroup ng) {

		try {
			InitialContext ic = new InitialContext();
			IUserDirectory intf = (IUserDirectory) ic
					.lookup("directory.ejb.IUserDirectory");
			int x = intf.lookupAUserRights(ng.getSenderName());
			if (x == 2) {
				Query q = em.createQuery("Delete FROM NewsGroup n where n.senderName = :username");
				q.setParameter("username", ng.getSenderName());
				int y = q.executeUpdate();
				return "Your message is successfuly deleted from the newsBox";
			} else 
				return "Your message can not be deleted ";
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		Date date = new Date();
		Msg m = new Msg();
		m.setSenderName("test1");
		m.setReceiverName("test");
		m.setSubject("hello");
		m.setSendingDate(date);
		m.setBody("hello test from test1");
		m.setAlreadyRead(0);
		em.persist(m);
		User u = new User();
		u.setUsername("test");
		u.setUserright(0);
		em.persist(u);
	}

}
