package ch09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {
	Connection conn = null; // 데이터베이스랑 연결을 해준다.
	PreparedStatement pstmt; // 쿼리문의 실행을 담당한다.

	// jdbc: 자바와 db를 연결해주는 api -> ojbcd6.jar
	final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";

	// DB연결 메소드
	public void open() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "test", "test1234");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			// *pstmt, conn은 리소스이므로 사용 후 반드시 닫아줘야 한다.
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 학생 정보를 다 불러온다
	public ArrayList<Student> getAll() {
		open(); // db 오픈
		ArrayList<Student> students = new ArrayList<>(); // student객체를 담을 리스트 준비.
		try {
			pstmt = conn.prepareStatement("select * from student"); // 쿼리문을 입력한다.
			ResultSet rs = pstmt.executeQuery(); // 쿼리문 실행(select문 사용시 사용) // ResultSet : 데이터를 받는 역할을 한다.

			while (rs.next()) { // 한 행씩 값이 있는지 없는지 판단한다.
				Student s = new Student();
				s.setId(rs.getInt("id"));
				s.setUsername(rs.getString("username")); // 이름 : 김길동 들어옴
				s.setUniv(rs.getString("univ"));
				s.setBirth(rs.getDate("birth"));
				s.setEmail(rs.getString("email"));

				students.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			// 중요 = 리소스 닫아줘야 함 finally 무조건적으로 실행되어야 하는 구문!
		} finally {
			close();
		}

		return students;
	}

	// 학생 정보를 입력
	public void insert(Student s) {
		open();
		String sql = "insert into student values(id_seq.nextval,?,?,?,?)";


		try {
			pstmt = conn.prepareStatement(sql); // 실행할 쿼리문을 입력
			pstmt.setString(1, s.getUsername()); // pstmt.setString(값을 넣어줄 위치, 넣어줄 데이터)
			pstmt.setString(2, s.getUniv());
			pstmt.setDate(3, s.getBirth());
			pstmt.setString(4, s.getEmail());
			
			pstmt.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
	//insert, delete, update 실행 시
	}
}
