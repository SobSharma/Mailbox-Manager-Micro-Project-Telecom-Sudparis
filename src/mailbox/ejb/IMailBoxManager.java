package mailbox.ejb;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Remote;

import project.entity.Msg;
import project.entity.NewsGroup;
import project.entity.User;

@Remote
public interface IMailBoxManager {

	public void init () ;
	
	public Collection<Msg> readAUserNewMessages(String username);

	public Collection <Msg> readAUserAllMessages(String username);

	public void deleteAUserMessage(int idMsg);

	public void deleteAUserReadMessages(String username);

	public void sendAMessageToABox(Msg message);

	public String sendNews(NewsGroup ng);
	
	public String deleteAUserNews (NewsGroup ng);

}
