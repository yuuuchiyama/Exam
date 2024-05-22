package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
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
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の宣言１
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		String entYearStr="";		//入力された入学年度
		String classNum ="";		//入力されたクラス番号
		String subjectCd = "";		//入力された科目

		int entYear = 0;			// 入学年度
		String subjectName = "";	// 科目名

		LocalDate todaysDate = LocalDate.now();	// LocalDateインスタンスを取得
		int year = todaysDate.getYear();	// 現在の年を取得

		List<TestListSubject> test_list_sub = new ArrayList<TestListSubject>();

		// DAO
		TestListSubjectDao testListSubDao = new TestListSubjectDao();
		SubjectDao subDao = new SubjectDao();
		ClassNumDao cNumDao = new ClassNumDao();

		// been
		Subject  getSubject = new Subject();


		//ログインユーザーの学校コードをもとにユーザーが所属している学校の科目一覧用データを取得
		List<Subject> subList = subDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> classNumList = cNumDao.filter(teacher.getSchool());

		// エラーメッセージ
		Map<String, String> errors = new HashMap<>();



		// リクエストパラメーターの取得２
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");


		// DBからデータの取得３
		// なし


		// ビジネスロジック４
		if (entYearStr !=null) {
			//数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

		List<Integer> entYearSet = new ArrayList<>();	// リストを初期化
		for (int i = year - 10; i < year + 1; i++) {	// 10年前から1年後まで年をリストに追加
			entYearSet.add(i);
		}
		List<Integer> tesNumSet = new ArrayList<>();	// リストを初期化
		for (int i = 1; i < 3; i++) {					// 1～2回のテストの回数をリストに追加
			tesNumSet.add(i);
		}

		// ビジネスロジック４
		// 入力内容のチェック
		if(entYear == 0 || classNum == null || subjectCd == null ){
			errors.put("filter_error", "入学年度とクラスと科目を選択してください");
			req.setAttribute("errors", errors);

			// リクエストにデータをセット６
			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("subject_list", subList);
			req.setAttribute("class_num_set", classNumList);

			//JSPへフォワード７
			req.getRequestDispatcher("test_list.jsp").forward(req, res);
		} else {

			getSubject = subDao.get(subjectCd, teacher.getSchool());
			subjectName = getSubject.getName();

			test_list_sub  = testListSubDao.filter(entYear, classNum, getSubject, teacher.getSchool());

			// リクエストにデータをセット６
			req.setAttribute("f1", entYear);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectCd);

			req.setAttribute("get_subject_name", subjectName);

			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("subject_list", subList);
			req.setAttribute("class_num_set", classNumList);
			req.setAttribute("test_set", test_list_sub);

			// JSPへフォワード７
			req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
		}
	}

}