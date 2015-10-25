package directory.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import project.entity.User;



@Stateful(name="directory.ejb/IUserDirectory")

public class DirectoryManager implements IUserDirectory {

	@PersistenceContext(unitName="mp1")
    private EntityManager em;

	@Override
	public User addUser(String username, int userright) {
		User u = new User();
		u.setUsername(username);
		u.setUserright(userright);
		em.persist(u);
		return u;
	}

	@Override
	public String removeUser(User u) {
		
		User u0 = em.merge(u);
        // Delete all records.
        em.remove(u0);
        return "User Account is deleted ";
		
	}

	@Override
	public List<User> lookupAllUsers() {
		
		Query q = em.createQuery("SELECT u FROM User u");
		//ArrayList<User> listofusers = (ArrayList) q.getResultList();
		List<User> listofusers = q.getResultList();
		return listofusers;
	}

	@Override
	public int lookupAUserRights(String username) {
		
		Query q = em.createQuery("select u from User u where u.username = :username");
        q.setParameter("username", username);
        User u;
        try {
        u = (User) q.getSingleResult();
        }
	    catch(Exception e) {e.printStackTrace(); return -1 ;}
        return u.getUserright();
	}

	@Override
	public void updateAUserRights(String username) {
		
		int oldright = lookupAUserRights(username);
		Query q = em.createQuery("UPDATE User u SET u.userright = :userright WHERE u.username = :username");
		q.setParameter("userright", Math.abs(oldright-1));
		q.setParameter("username", username);
		int rslt = q.executeUpdate();
	
	}
	
}
