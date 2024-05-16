package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		String no = req.getParameter("no");

		StudentDao sDao = new StudentDao();
		Student student = sDao.get(no);

		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		List<String> list = cNumDao.filter(teacher.getSchool());

		// リクエストにデータをセット
		req.setAttribute("ent_year", student.getEntYear());
		req.setAttribute("no", student.getNo());
		req.setAttribute("name", student.getName());
		req.setAttribute("num", list);

		//JSPへフォワード 7
		req.getRequestDispatcher("student_update.jsp").forward(req, res);

	}


}