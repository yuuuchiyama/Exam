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

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言
		HttpSession session = req.getSession(); // セッション情報を取得
		Teacher teacher = (Teacher)session.getAttribute("user");

		StudentDao sDao = new StudentDao();
		ClassNumDao cDao = new ClassNumDao();

		String no = "";
		Student student = null;


		//リクエストパラメーターの取得
		no = req.getParameter("no");	// student_list.jspのaタグから受け取った値

		//DBからデータの取得
		student = sDao.get(no);										// 学生の詳細データを取得
		List<String> list = cDao.filter(teacher.getSchool());	//クラスの一覧を取得

		//ビジネスロジック


		//DBへの保存

		//レスポンス値をセット
		req.setAttribute("ent_year", student.getEntYear());
		req.setAttribute("no", student.getNo());
		req.setAttribute("name", student.getName());
		req.setAttribute("class_num", student.getClassNum());
		req.setAttribute("class_num_set", list);

		//フォワード
		req.getRequestDispatcher("student_update.jsp").forward(req, res); // 学生一覧まで画面遷移
	}

}
