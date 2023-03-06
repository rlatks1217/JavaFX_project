package vo;

public class User {

	private String userName;
	private String UserPhoneNum;
	private int point;
	private String id;
	private String pw;
	
	public User (){
		
	}
	
	public User(String userName, String userPhoneNum, int point) {
		super();
		this.userName = userName;
		this.UserPhoneNum = userPhoneNum;
		this.point = point;
	}
	
	public User(String userName, String id, String pw, String userPhoneNum, int point) {
		super();
		this.userName = userName;
		UserPhoneNum = userPhoneNum;
		this.point = point;
		this.id = id;
		this.pw = pw;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhoneNum() {
		return UserPhoneNum;
	}
	public void setUserPhoneNum(String userPhoneNum) {
		UserPhoneNum = userPhoneNum;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
