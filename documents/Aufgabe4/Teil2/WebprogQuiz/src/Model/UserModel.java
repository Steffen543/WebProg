package Model;

public class UserModel {

	private String username;
	private double points;
	
	public UserModel(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public double getPoints(){
		return this.points;
	}
	
}
