package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


import bookView.Shared;
import dao.BookDAO;
import dao.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mybatis.MyBatisConnectionFactory;
import vo.Book;
import vo.User;

public class Service {
	
	private SqlSessionFactory factory;
	
	public Service () {
		this.factory = MyBatisConnectionFactory.getSqlSessionFactory();
	}

	//로그인 체크
	public boolean checkIdPw(String id, String pw) {
		
		SqlSession session = factory.openSession();
		boolean result = false;
		UserDAO user = new UserDAO(session);
		result = user.checkIdPw(id, pw);
		
		session.close();
		
		return result;
	}

	public boolean checkDuplication(String checkId) {
		
		SqlSession session = factory.openSession();
		
		boolean result = false;
		UserDAO user = new UserDAO(session);
		result = user.checkDuplication(checkId);
		session.close();
		
		return result;
	}

	public int signUp(String mName, String mId, String mPw, String mPhone) {
		
		SqlSession session = factory.openSession();
		
		int count = 0;
		
		UserDAO user = new UserDAO(session);
		count = user.signUp(mName, mId, mPw, mPhone);
		
		if (count == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		
		return count;
	}

	public ObservableList<Book> selectBook(String keyword) {

		SqlSession session = factory.openSession();
		
		BookDAO dao = new BookDAO(session);
		List<Book> list = dao.offlineSelectBook(keyword);
		
		session.close();
		ObservableList<Book> list_ = FXCollections.observableArrayList();
		for (Book book : list) {
			list_.add(book);
		}
		return list_;
	}
	
	public ObservableList<Book> onlineSelectBook(String keyword) {
		
		SqlSession session = factory.openSession();
		
		BookDAO dao = new BookDAO(session);
		List<Book> list_ = dao.onlineSelectBook(keyword, Shared.getFieldId());
		session.close();
		
		ObservableList<Book> list = FXCollections.observableArrayList();
		for (Book book : list_) {
			list.add(book);
		}
		return list;
	}

	public Book selectBookDetail(String bookTitle) {
		
		SqlSession session = factory.openSession();
		
		BookDAO dao = new BookDAO(session);
		Book bookDetail = dao.selectBookDetail(bookTitle);
		
		session.close();
		
		return bookDetail;
	}

	public int rent(String fieldId, Book book) {

		SqlSession session = factory.openSession();
		
		int count = 0;
		BookDAO dao = new BookDAO(session);
		count = dao.rentBook(fieldId, book);
		
		if (count == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		return count;
	}

	public int checkRent(String fieldId, Book book) {
		SqlSession session = factory.openSession();
		
		int result = 0;
		
		BookDAO dao = new BookDAO(session);
		result = dao.checkRent(fieldId, book);
		
		return result;
	}

	public int returnBook(String fieldId, Book book) {
		SqlSession session = factory.openSession();
		
		int result = 0;
		BookDAO dao = new BookDAO(session);
		result = dao.returnBook(fieldId, book);
		
		if (result == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		
		return result;
	}

	public User getProfile(String fieldId) {
		SqlSession session = factory.openSession();
		UserDAO dao = new UserDAO(session);
		User user = dao.getProfile(fieldId);
	
		session.close();
		return user;
	}

	public ObservableList<Book> getUserRentBook(String fieldId) {
		SqlSession session = factory.openSession();
		
			BookDAO dao = new BookDAO(session);
			List<Book> list= dao.getUserRentBook(fieldId);
			
			ObservableList<Book> list_ = FXCollections.observableArrayList();
			for (Book book : list) {
				
				LocalDate rentDate = LocalDate.parse(book.getRentalDate());
				LocalDate deadline = rentDate.plusDays(7);
				book.setReturnDate(deadline.toString());
				
				LocalDate now = LocalDate.now();
				
				if (now.getDayOfMonth() > deadline.getDayOfMonth()) {
					book.setStatus("연체중");
				} else {
					book.setStatus("");
				}
				
				list_.add(book);
			}
			
			session.close();
		
		return list_;
	}

	public User updateInfo(String fieldId, String name, String phone) {
			
		SqlSession session = factory.openSession();
		UserDAO dao = new UserDAO(session);
		int result = dao.updateInfo(fieldId, name, phone);
		User user = null;
		if (result == 1) {
			user = dao.getProfile(fieldId);
			session.commit();
		} else {
			session.rollback();
		}
		
		return user;
	}

	public int changePw(String password) {
		SqlSession session = factory.openSession();
		int result = 0;
		
		UserDAO dao = new UserDAO(session);
		result = dao.changePw2(password, Shared.getFieldId());
		
		if (result == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		
		return result;
	}

	public boolean checkPw(String password) {
		SqlSession session = factory.openSession();
		boolean result = false;
		UserDAO dao = new UserDAO(session);
		result = dao.checkPw(password);
		
		return result;
	}

	public int deleteMember(String fieldId) {
		
		SqlSession session = factory.openSession();
		
		int result = 0;
		
		UserDAO dao = new UserDAO(session);
		result = dao.deleteMember(fieldId);
		
		if (result == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		
		return result;
	}

	public int deleteBook(String bisbn) {
		
		SqlSession session = factory.openSession();
		int result = 0;
		BookDAO dao = new BookDAO(session);
		result = dao.deleteBook(bisbn);
		
		if (result == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		
		return result;
	}

	public int registerBook(Book book) {
		SqlSession session = factory.openSession();
		int result = 0;
		
		BookDAO dao = new BookDAO(session);
		result = dao.registerBook(book);
		
		if (result == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		
		return result;
	}

	public ArrayList<ObservableList<Book>> getUserRentBookAll() {
		ArrayList<ObservableList<Book>> list = null;
		SqlSession session = factory.openSession();
		
		BookDAO dao = new BookDAO(session);
		list= dao.getUserRentBookAll();
		session.close();
	
		return list;
	}

	public ObservableList<User> getUSerInfo() {
		
		SqlSession session = factory.openSession();
		ObservableList<User> list_ = FXCollections.observableArrayList();
		
		UserDAO dao = new UserDAO(session);
		List<User> list= dao.getUserInfo();
			
		for (User user : list) {
			list_.add(user);
		}
		
		return list_;
	}

	public ObservableList<User> getUSerselect(String selectname) {
		
		SqlSession session = factory.openSession();
		ObservableList<User> list_ = FXCollections.observableArrayList();
		
		UserDAO dao = new UserDAO(session);
		List<User> list= dao.getUSerselect(selectname);
		
		for (User user : list) {
			list_.add(user);
		}
		
		return list_;
	}

	public int modifycomplete(String name, String id, String pw, String telephone) {
		
		SqlSession session = factory.openSession();
		int result = 0;
		
		UserDAO dao = new UserDAO(session);
		result= dao.modifycomplete(name, id, pw, telephone);
		
		if (result == 1) {
			session.commit();
		} else {
			session.rollback();
		}
			
		return result;
	}
	
	

}
