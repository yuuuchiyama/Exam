package scoremanager.main;

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

		String cd = "";
		String name = "";

		cd = req.getParameter("cd");
		name = req.getParameter("name");

		cd = Integer.parseInt(name);
		if (cd == 0) {
			errors.put("cd_error", "科目コードは3文字で入力してください");
			req.setAttribute("errors", errors);
		}else {
			if (sDao.get(no) != null) {
				errors.put("no_error", "科目コードが重複しています");
				req.setAttribute("errors", errors);
			}else {
				// beenに値をセット
				subject.setName(name);
				subject.setNo(cd);


				//DBへデータ保存 5
				sDao.save(student);	// sqlを実行

				url = "student_create_done.jsp";
				req.getRequestDispatcher(url).forward(req, res);
			}
			url = "StudentCreate.action";
			req.getRequestDispatcher(url).forward(req, res);
		}
	}

}
