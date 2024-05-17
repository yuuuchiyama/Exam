package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		TestListSubjectDao TestListSubjectExecuteAction = new TestListSubjectDao();
		SubjectDao suDao = new SubjectDao();
		ClassNumDao cNumDao = new ClassNumDao();


		//ログインユーザーの学校コードをもとに、ユーザーが所属している学校の科目一覧用データを取得
		List<Subject> list = suDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list1 = cNumDao.filter(teacher.getSchool());

		List<TestListSubject> TestListSubjectExecuteActions = null;


		StudentDao sDao = new StudentDao();
		Map<String, String> errors = new HashMap<>();
		String entYearStr="";//入力された入学年度
		String classNum ="";//入力されたクラス番号
		String subName = "";//入力された科目
		String studentCd = "";
		int entYear = 0;// 入学年度

		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subName = req.getParameter("f3");
		studentCd = req.getParameter("f4");

		if (entYearStr !=null) {
			//数値に変換
			entYear = Integer.parseInt(entYearStr);
			}

		Subject subject = new Subject();
		subject.setName(subName);

		if(entYear == 0 || classNum == null || subName == null ){
			errors.put("f1", "入学年度とクラスと科目を選択してください");
			req.setAttribute("errors", errors);
		} else {

			TestListSubjectExecuteActions  = TestListSubjectExecuteAction.filter(entYear,classNum,subject,teacher.getSchool());
		}


		//リクエストにデータをセット
		//req.setAttribute("ent_year_set", );
		//req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject_list", list);
		req.setAttribute("class_num_set", list1);
		req.setAttribute("test_set", TestListSubjectExecuteActions);
		//JSPへフォワード
		req.getRequestDispatcher("test_list_Subject.jsp").forward(req, res);
	}

}