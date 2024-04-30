package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		Student subject = new Student();

		StudentDao sDao = new StudentDao();

		Map<String, String> errors = new HashMap<>();	// エラーメッセージ

		String url = "";	// 画面遷移で使うurl
		String cd = "";
		String name = "";

		cd = req.getParameter("cd");
		name = req.getParameter("name");

		if (cd.length() != 3) {
			errors.put("cd_error", "科目コードは3文字で入力してください");
			req.setAttribute("errors", errors);
		}else {
			if (sDao.get(cd) != null) {
				errors.put("no_error", "科目コードが重複しています");
				req.setAttribute("errors", errors);
			}else {
				// beenに値をセット
				subject.setName(name);
				subject.setNo(cd);


				//DBへデータ保存 5
				sDao.save(subject);	// sqlを実行

				url = "subject_create_done.jsp";
				req.getRequestDispatcher(url).forward(req, res);
			}
			url = "SubjectCreate.action";
			req.getRequestDispatcher(url).forward(req, res);
		}
	}

}
