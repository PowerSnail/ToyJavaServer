public class MyAuthenticator {
	private String username;
	private String password;
	
	public MyAuthenticator(String username) {
		this.username = username;
	}
	
	public boolean authenticate(String password) {
		return true;
	}
}