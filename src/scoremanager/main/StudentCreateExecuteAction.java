package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();		// セッションを取得
		Teacher teacher = (Teacher)session.getAttribute("user");

		String entYearStr = "";	// 入力された入学年度
		String no = "";			// 入力された学生番号
		String name = "";		// 入力された氏名
		String classNum = "";	// 入力されたクラス番号

		int entYear = 0;		// 入学年度
		boolean isAttend = true;	// 在学フラグ

		String url = "";	// 画面遷移で使うurl

		// beenをインスタンス化
		Student student = new Student();

		// Daoをインスタンス化
		StudentDao sDao = new StudentDao();

		Map<String, String> errors = new HashMap<>();	// エラーメッセージ

		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("ent_year");
		no = req.getParameter("no");
		name = req.getParameter("name");
		classNum = req.getParameter("class_num");

		//DBからデータ取得 3

		//フォワード 7
		//条件で手順4~7の内容が分岐
		entYear = Integer.parseInt(entYearStr);
		if (entYear == 0) {
			errors.put("year_error", "入学年度を選択してください");
			req.setAttribute("errors", errors);
		}else {
			if (sDao.get(no) != null) {
				errors.put("no_error", "学生番号が重複しています");
				req.setAttribute("errors", errors);
			}else {
				// beenに値をセット
				student.setEntYear(entYear);
				student.setNo(no);
				student.setName(name);
				student.setClassNum(classNum);
				student.setIsAttend(isAttend);
				student.setSchool(teacher.getSchool());

				//DBへデータ保存 5
				sDao.save(student);	// sqlを実行

				url = "student_create_done.jsp";
				req.getRequestDispatcher(url).forward(req, res);
			}
			url = "StudentCreate.action";
			req.getRequestDispatcher(url).forward(req, res);
		}
		//レスポンス値をセット 6
		req.setAttribute("ent_year", entYear);
		req.setAttribute("no", no);
		req.setAttribute("name", name);
		req.setAttribute("class_num", classNum);

		url = "StudentCreate.action";
		req.getRequestDispatcher(url).forward(req, res);
	}

}
