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
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の宣言１
		HttpSession session = req.getSession();		// セッションを取得
		Teacher teacher = (Teacher)session.getAttribute("user");

		String classNum = "";		// 画面で選択されたクラス番号
		String subjectCd = "";		// 画面で選択された科目コード

		LocalDate todaysDate = LocalDate.now();	// LocalDateインスタンスを取得
		int year = todaysDate.getYear();	// 現在の年を取得

		String entYearStr = "";
		String numStr = "";
		int entYear = 0;			// int型の入学年度
		int  num = 0;				// int型の回数
		String subjectName = "";
		Subject getSubject = new Subject();

		ClassNumDao cNumDao = new ClassNumDao();	// クラス番号Daoをインスタンス化
		SubjectDao subDao = new SubjectDao();		// 科目Daoをインスタンス化
		TestDao tDao = new TestDao();				// 成績Daoをインスタンス化

		Map<String, String> errors = new HashMap<>();	// エラーメッセージ

		// リクエストパラメーターの取得２
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");
		numStr = req.getParameter("f4");
		System.out.println(entYearStr);
		System.out.println(classNum);
		System.out.println(subjectCd);
		System.out.println(numStr);


		// DBからデータの取得３
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> classlist = cNumDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに学校の科目コードと科目名の一覧を取得
		List<Subject> sublist = subDao.filter(teacher.getSchool());

		// ビジネスロジック４
		List<Integer> entYearSet = new ArrayList<>();	// リストを初期化
		for (int i = year - 10; i < year + 1; i++) {	// 10年前から1年後まで年をリストに追加
			entYearSet.add(i);
		}
		List<Integer> tesNumSet = new ArrayList<>();	// リストを初期化
		for (int i = 1; i < 3; i++) {					// 1～2回のテストの回数をリストに追加
			tesNumSet.add(i);
		}

		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
			num = Integer.parseInt(numStr);

			// 条件で内容が分岐
			// 検索条件が選択されていない場合
			if (entYear == 0 || classNum == null || subjectCd == null || num == 0) {
				errors.put("input_error", "入学年度とクラスと科目と回数を選択してください");
				req.setAttribute("errors", errors);

			// 検索条件が選択されている場合
			} else {
				getSubject = subDao.get(subjectCd, teacher.getSchool());
				subjectName = getSubject.getName();

				// 画面で選択された値をもとにテストデータの全件を取得
				List<Test> testlist = tDao.filter(entYear, classNum, getSubject, num, teacher.getSchool());

				// ビジネスロジック４
				// なし

				// DBへのデータ保存５
				// なし

				// レスポンス値をセット６
				// 遷移先での初期値をセット
				req.setAttribute("f1", entYear);
				req.setAttribute("f2", classNum);
				req.setAttribute("f3", subjectCd);
				req.setAttribute("f4", num);
				req.setAttribute("get_subject_name", subjectName);

				req.setAttribute("test_set", testlist);			// リクエストに絞り込みをした成績データリストをセット
			}
		}

		// レスポンス値をセット６
		// 遷移先で使用するリストのセット
		req.setAttribute("ent_year_set", entYearSet);	// リクエストに入学年度をセット
		req.setAttribute("class_num_set", classlist);	// リクエストにクラス番号一覧のリストをセット
		req.setAttribute("subject_list", sublist);		// リクエストに科目コードと科目名の一覧リストをセット
		req.setAttribute("tes_num_set", tesNumSet);		// リクエストにテストの回数をセット

		// JSPへフォワード７
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}

}
