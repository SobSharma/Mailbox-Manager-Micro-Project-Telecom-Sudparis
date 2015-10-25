package directory.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.InitialContext;

import project.entity.User;
import directory.ejb.IUserDirectory;


public class DirectoryJavaClient {

	public static void main(String[] args) {
		IUserDirectory intf;
		User u1,u2;
		String username;
		List<User> listofusers;
		int userright;
		Scanner in = new Scanner(System.in);
		
		try {
			InitialContext ic = new InitialContext();
			intf = (IUserDirectory) ic.lookup("directory.ejb.IUserDirectory");
			
			///////
			
			/*System.out.println("UserName =");
			username=in.nextLine();
			System.out.println("UserRight (1/0) =");
			userright = in.nextInt();
			u1 = intf.addUser(username, userright);
			String ch=in.nextLine();
			System.out.println("UserName =");
			username=in.nextLine();
			System.out.println("UserRight (1/0) =");
			userright = in.nextInt();
			u2 = intf.addUser(username, userright);
			
			///////
			
			System.out.println(intf.removeUser(u1));
			
			///////*/
			
			listofusers = intf.lookupAllUsers();
			
			for (int i=0; i<listofusers.size();i++)
			{
				User u0 = listofusers.get(i);
				System.out.println(u0.getUsername()+"-"+u0.getUserright());
			}
					
			/////////
			
			String ch1=in.nextLine();
			System.out.println("UserName =");
			username=in.nextLine();
			if (intf.lookupAUserRights(username)>=0)
			System.out.println("UserRight= "+intf.lookupAUserRights(username));
			else System.out.println("UserName does not exist !");
			
			//////
			
			//String ch2=in.nextLine();
			System.out.println("UserName =");
			username=in.nextLine();
			intf.updateAUserRights(username);
			System.out.println("New UserRight= "+intf.lookupAUserRights(username));


		} catch(Exception e) {e.printStackTrace();}


	}

}
