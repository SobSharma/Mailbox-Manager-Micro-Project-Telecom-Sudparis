package directory.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import project.entity.User;

@Remote
public interface IUserDirectory {
	
	public User addUser(String username, int userright);

	public String removeUser(User u);

	public List<User> lookupAllUsers();

	public int lookupAUserRights(String username);

	public void updateAUserRights(String username);

}
