package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.ibatis.session.SqlSession;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vo.Book;

public class BookDAO {

	private SqlSession session;

	public BookDAO() {

	}

	public BookDAO(SqlSession session) {
		this.session = session;
	}

	public List<Book> offlineSelectBook(String keyword) {

		List<Book> list = null;

		String btitle = "%"+keyword+"%";
			if (keyword.equals("")) {
				list = session.selectList("library.book.offlineSelectBook");
			} else {
				list = session.selectList("library.book.offlineSelectBookLike", btitle);
			}

		return list;
	}

	public List<Book> onlineSelectBook(String keyword, String id) {
		List<Book> list = null;

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("keyword", "%"+keyword+"%");
		if (keyword.equals("")) {

			list = session.selectList("library.book.selectAllBook", map);
		} else {
			list = session.selectList("library.book.selectByTitle", map);
		}

		for (Book book : list) {
			if (!book.getRentalDate().equals("")) {
				LocalDate rentDate = LocalDate.parse(book.getRentalDate());
				LocalDate now = LocalDate.now();
				if (rentDate.getDayOfMonth() + 1 < now.getDayOfMonth()) {
					book.setRentalDate(rentDate + " 대여중(연체)");
				} else {
					book.setRentalDate(rentDate + " 대여중");
				}
			}
		}
		return list;
	}

	public Book selectBookDetail(String bookTitle) {
		
		Book bookDetail = session.selectOne("library.book.selectBookDetail", bookTitle);

		if (!bookDetail.getRentalDate().equals("")) {
			LocalDate now = LocalDate.now();
			LocalDate rentDate = LocalDate.parse(bookDetail.getRentalDate());
			int rentDay = rentDate.getDayOfMonth();
			int sysDate = now.getDayOfMonth();

			if (rentDay + 1 < sysDate) {
				bookDetail.setRentalDate(bookDetail.getRentalDate() + "(연체)");
			} else {
				bookDetail.setRentalDate(bookDetail.getRentalDate());
			}
			bookDetail.setbBase64(bookDetail.getbImgbase64().split(",")[1]);
		}

		return bookDetail;
	}

	public int rentBook(String fieldId, Book book) {

		int count = 0;

		LocalDate now = LocalDate.now();
		HashMap<String, String> map = new HashMap<>();
		
		map.put("rentalDate", now.toString());
		map.put("id", fieldId);
		map.put("bisbn", book.getBisbn());
		map.put("btitle", book.getBtitle());
		
		count = session.insert("library.book.rentBook", map);

		return count;
	}

	public int checkRent(String fieldId, Book book) {

		int result = 0;
		
		HashMap<String, String> map = new HashMap<>();
		map.put("id", fieldId);
		map.put("bisbn", book.getBisbn());
		String bisbn = session.selectOne("library.book.checkRent", map);
		
		if (bisbn != null) {
			result = 1;
		}

		return result;
	}

	public int returnBook(String fieldId, Book book) {

		int result = 0;
		
		String bisbn = book.getBisbn();
		result = session.delete("library.book.returnBookDelete", bisbn);
		
		if (result == 1) {
			
			StringTokenizer st = new StringTokenizer(book.getRentalDate(),"(");
			String dateStr = st.nextToken();
			LocalDate rentDate = LocalDate.parse(dateStr);
			int rentDay = rentDate.getDayOfMonth();
			
			LocalDate now = LocalDate.now();
			int sysDate = now.getDayOfMonth();
			
			int point = session.selectOne("library.user.returnBookSelectPoint", fieldId);
			
			if (rentDay + 1 > sysDate) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("id", fieldId);
				map.put("point", point + 5);
				result = session.update("library.user.updatePoint", map);
			} else {
				HashMap<String, Object> map = new HashMap<>();
				map.put("id", fieldId);
				map.put("point", point - 3);
				result = session.update("library.user.updatePoint", map);
			}
		}

		return result;
	}

	public List<Book> getUserRentBook(String fieldId) {

		List<Book> list = session.selectList("library.book.getUserRentBook", fieldId);

		return list;
	}

	public int deleteBook(String bisbn) {

		int result = 0;

			String rs = session.selectOne("library.book.deleteBookSelect", bisbn);

			if (rs != null) {
				result = session.delete("library.book.deleteBookrental", bisbn);
				result = session.delete("library.book.deleteBook", bisbn);
			} else {
				result = session.delete("library.book.deleteBook", bisbn);
			}

		return result;
	}

	public int registerBook(Book book) {
	 
		int result = 0;
		String bisbn = book.getBisbn();

		String rs = session.selectOne("library.book.registerBookSelect", bisbn);
		if (rs!= null) {
			result = 0;
		} else {
			result = session.insert("library.book.registerBookInsert", book);
		}

		return result;
	}

	public ArrayList<ObservableList<Book>> getUserRentBookAll() {

		ArrayList<ObservableList<Book>> list = new ArrayList<ObservableList<Book>>();
		;
		ObservableList<Book> list1 = null;
		ObservableList<Book> list2 = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.id, b.rentalDate, a.bisbn, a.btitle, a.bprice, a.bauthor, ");
		sql.append("a.bpage, a.bpublisher, a.btranslator ");
		sql.append("FROM book a ");
		sql.append("INNER JOIN ");
		sql.append("rental b ");
		sql.append("ON a.bisbn = b.bisbn ");

		List<Book> list_ = session.selectList("library.book.getUserRentBookAll");

		list1 = FXCollections.observableArrayList();
		list2 = FXCollections.observableArrayList();

		for (Book book : list_) {
			
			LocalDate rentDate = LocalDate.parse(book.getRentalDate());
			LocalDate deadline = rentDate.plusDays(7);
			LocalDate now = LocalDate.now();
			if (deadline.getDayOfMonth() > now.getDayOfMonth()) {
				book.setReturnDate(deadline.toString());
				book.setStatus("연체중");

				list2.add(book);
			} else {
				book.setReturnDate(deadline.toString());
				book.setStatus(" ");
				book.setId(book.getId());

				list1.add(book);
			}

		}
			
		list.add(list1);
		list.add(list2);

		return list;
	}

}
