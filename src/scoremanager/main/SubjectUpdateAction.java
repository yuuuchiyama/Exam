package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言
		HttpSession session = req.getSession(); // セッション情報を取得
		Teacher teacher = (Teacher)session.getAttribute("user");

		Subject subject = new Subject();

		SubjectDao sDao = new SubjectDao();

		String cd = "";

		cd = req.getParameter("cd");
		//DBからデータの取得
		subject = sDao.get(cd, teacher.getSchool());					// 科目の詳細データを取得

		// リクエストにデータをセット
		req.setAttribute("subject", subject);

		//フォワード
		req.getRequestDispatcher("subject_update.jsp").forward(req, res); // 学生一覧まで画面遷移
	}

}