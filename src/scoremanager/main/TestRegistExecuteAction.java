package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の宣言１
		HttpSession session = req.getSession();		// セッションを取得
		Teacher teacher = (Teacher)session.getAttribute("user");

		String class_num = "";		// クラス番号
		String subject_cd = "";		// 科目コード
		int test_no = 0;			// 回数コード
		int test_point = 0;			// 変更された点数

		String url = "";		// 遷移先のURL

		List<Test> testlist = new ArrayList<>() ;

		Test test = new Test();

		// DAOをインスタンス化
		StudentDao sDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();
		TestDao tDao = new TestDao();


		Map<String, String> errors = new HashMap<>();	// エラーメッセージ

		// リクエストパラメーターの取得２
		class_num = req.getParameter("f2");
		subject_cd = req.getParameter("f3");
		test_no = Integer.parseInt(req.getParameter("f4"));
		String[] student_no_all = req.getParameterValues("student_no_all");

		// DBからデータの取得３
		// なし

		// ビジネスロジック４
		for (String student_no:student_no_all) {
			test_point = Integer.parseInt(req.getParameter("point_" + student_no));

			if (0 <= test_point && test_point <= 100) {
				test.setStudent(sDao.get(student_no));
				test.setclassNum(class_num);
				test.setSubject(subDao.get(subject_cd, teacher.getSchool()));
				test.setSchool(teacher.getSchool());
				test.setNo(test_no);
				test.setPoint(test_point);

				testlist.add(test);
			} else {
				errors.put("point_error", "0～100の範囲で入力してください");
				req.setAttribute("errors", errors);
			}
		}

		// DBにデータの保存５
		boolean result = tDao.save(testlist);
		System.out.println(result);

		// レスポンス値をセット６

		// JSPへフォワード７
		url = "test_regist_done.jsp";
		req.getRequestDispatcher(url).forward(req, res);
	}

}
