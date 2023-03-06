package dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.User;

public class UserDAO {

	private SqlSession session;
	
	public UserDAO(SqlSession session) {
		this.session = session;
	}


	// 아이디 비번 체크(로그인)
	public boolean checkIdPw(String id, String pw) {

		boolean result = false;
		
		HashMap<String, String> map = new HashMap<String, String>(); 
		map.put("id", id);
		map.put("pw", pw);
		User user = session.selectOne("library.user.checkIdPw", map);

		if (user != null) {
			result = true;
		}

		return result;
	}

	public boolean checkDuplication(String checkId) {

		boolean result = false;

		User user = session.selectOne("library.user.checkDuplication", checkId);

		if (user != null) {
			result = false;
		} else {
			result = true; // 검색된 아이디가 없으면 사용 가능이므로 true
		}
	
		return result;
	}

	public int signUp(String mName, String mId, String mPw, String mPhone) {

		User user = new User();
		user.setUserName(mName);
		user.setId(mId);
		user.setPw(mPw);
		user.setUserPhoneNum(mPhone);
		user.setPoint(0);
		
		int result = session.insert("library.user.signUp", user);
		
		return result;
	}

	public int deleteMember(String fieldId) {

		int result = 0;

		result = session.delete("library.user.deleteMember", fieldId);
		
		return result;
	}

	public boolean checkPw(String password) {

		boolean result = false;

		String pw = session.selectOne("library.user.checkPw", password);
		
		if (pw != null) {
			result = true;
		}

		return result;
	}

	public int changePw2(String password, String fieldId) {

		int result = 0;
		
		User user = new User();
		user.setId(fieldId);
		user.setPw(password);
		
		result = session.update("library.user.changePw2", user);
		
		return result;
	}

	public int updateInfo(String fieldId, String name, String phone) {

		int result = 0;
		
		User user = new User();
		user.setId(fieldId);
		user.setUserName(name);
		user.setUserPhoneNum(phone);
		
		result = session.update("library.user.updateInfo", user);
		
		return result;
	}

	public User getProfile(String fieldId) {

		User user = session.selectOne("library.user.getProfile", fieldId);
		
		return user;
	}

	public List<User> getUserInfo() {
		List<User> list = session.selectList("library.user.getUserInfo");
		return list;
	}

	public List<User> getUSerselect(String selectname) {
		
		String name = "%" + selectname + "%";
		List<User> list = session.selectList("getUSerselect", name);
		
		return list;
	}

	public int modifycomplete(String name, String id, String pw, String telephone) {

		int result = 0;

		HashMap<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("id", id);
		map.put("pw", pw);
		map.put("telephone", telephone);

		result = session.update("library.user.modifycomplete", map);
		
		return result;
	}

}
