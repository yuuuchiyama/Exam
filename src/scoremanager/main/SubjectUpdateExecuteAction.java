package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		HttpSession session = req.getSession();		// セッションを取得
		Teacher teacher = (Teacher)session.getAttribute("user");

		Subject subject = new Subject();

		SubjectDao sDao = new SubjectDao();

		Map<String, String> errors = new HashMap<>();	// エラーメッセージ

		String url = "";	// 画面遷移で使うurl
		String cd = "";
		String name = "";

		cd = req.getParameter("cd");
		name = req.getParameter("name");

		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());

		//DBへデータ保存 5
		sDao.save(subject);	// sqlを実行

		url = "subject_update_done.jsp";
		req.getRequestDispatcher(url).forward(req, res);
	}

}