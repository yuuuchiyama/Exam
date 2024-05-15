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

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		HttpSession session = req.getSession();		// セッションを取得
		Teacher teacher = (Teacher)session.getAttribute("user");

		Subject test_name = new Subject();

		Subject subject = new Subject();

		SubjectDao sDao = new SubjectDao();

		Map<String, String> errors = new HashMap<>();	// エラーメッセージ

		String url = "";	// 画面遷移で使うurl
		String cd = "";
		String name = "";

		cd = req.getParameter("code");
		name = req.getParameter("name");

		test_name = sDao.get(cd, teacher.getSchool());

		if (cd.length() != 3) {
			errors.put("cd_error", "科目コードは3文字で入力してください");
			req.setAttribute("errors", errors);
		}else {
			if (test_name != null) {
				errors.put("no_error", "科目コードが重複しています");
				req.setAttribute("errors", errors);
			}else {
				// beenに値をセット
				subject.setSchool(teacher.getSchool());
				subject.setName(name);
				subject.setCd(cd);


				//DBへデータ保存 5
				sDao.save(subject);	// sqlを実行

				url = "subject_create_done.jsp";
				req.getRequestDispatcher(url).forward(req, res);
			}
			url = "subject_create.jsp";
			req.getRequestDispatcher(url).forward(req, res);
		}
		url = "subject_create.jsp";
		req.getRequestDispatcher(url).forward(req, res);
	}

}
