package ch09;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import oracle.net.aso.a;

/**
 * Servlet implementation class StudentController
 */
@WebServlet("/StudentControl")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StudentDAO dao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config); // 서블릿을 초기화 하는 역할을 함. 걍 냅둬야 함.
		dao = new StudentDAO(); // StudentDAO 객체가 딱 한번만 만들어진다.-> 공유를 해서 쓸 수가 있다.
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		//request: 뷰에서 넘어온 데이터들이 들어있음.
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action"); //앞단에서 보낸 데이터 받으려면 무조건 request.getParameter("") 쓰면 된다.
		String view = "";
		
		view = insert(request, response);
		//getRequestDispatcher(이동할 페이지): 이동할 페이지의 경로 지정
		//forward: 페이지를 이동시킨다. 내부에서 이동이 되므로 주소가 변하지 않는다.
		getServletContext().getRequestDispatcher("/ch09/" + view).forward(request, response);
			
		
	}

	//request 데이터 받아옴 ->DAO에 있는 insert 실행(DB에 insert가 됨) -> 페이지명(studentInfo.jsp) 리턴
	public String insert(HttpServletRequest request, HttpServletResponse response) {
		Student s = new Student();
		try {
			BeanUtils.populate(s, request.getParameterMap());
			
			
//			s.setUsername(request.getParameter("username"));
//			s.setEmail(request.getParameter("email"));
//			s.setUniv(request.getParameter("univ"));
//			s.setBirth(request.getParameter("birth"));
//			Date date = (Date)formatter.parse(request.getParameter("birth"));
//			s.setBirth(request.getParameter("birth");
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}

		dao.insert(s); // 컨트롤러는 DAO한테 있는 메소드를 사용한다. DAO한테 데이터베이스 관련 요청을 해야한다.
		
		return "StudentInfo.jsp";
	}
	
	public StudentController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
