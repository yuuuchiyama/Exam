package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の宣言
		HttpSession session = req.getSession(); // セッション情報を取得
		Teacher teacher = (Teacher)session.getAttribute("user");

		String entYearStr = "";	// 入力された入学年度
		String classNum = "";	// 入力されたクラス番号
		String isAttendStr = "";// 入力された在学フラグ
		int entYear = 0;		// 入学年度
		boolean isAttend = false;	// 在学フラグ
		List<Student> students = null;	// 学生リスト
		LocalDate todaysDate = LocalDate.now();	// LocalDateインスタンスを取得
		int year = todaysDate.getYear();	// 現在の年を取得
		StudentDao sDao = new StudentDao();	// 学生Daoをインスタンス化
		ClassNumDao cNumDao = new ClassNumDao();	// クラス番号Daoをインスタンス化
		Map<String, String> errors = new HashMap<>();	// エラーメッセージ

		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		isAttendStr = req.getParameter("f3");

		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);		// 数値に変換
		}
		List<Integer> entYearSet = new ArrayList<>();	// リストを初期化
		for (int i = year - 10; i < year + 1; i++) {	// 10年前から1年後まで年をリストに追加
			entYearSet.add(i);
		}

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());

		if (entYear != 0 && !classNum.equals("0")) {
			// 入学年度とクラス番号を指定
			students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
		} else if (entYear != 0 && classNum.equals("0")) {
			// 入学年度のみ指定
			students = sDao.filter(teacher.getSchool(), entYear, isAttend);
		} else if (entYear == 0 && classNum == null || entYear == 0 && classNum.equals("0")) {
			// 指定なしの場合
			// 全学生情報を取得
			students = sDao.filter(teacher.getSchool(), isAttend);
		} else {
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			req.setAttribute("errors", errors);
			// 全学生情報を取得
			students = sDao.filter(teacher.getSchool(), isAttend);
		}

		//ビジネスロジック 4

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6

		req.setAttribute("students", students);		// リクエストに学生リストをセット

		req.setAttribute("class_num_set", list);	// リクエストにデータをセット
		req.setAttribute("ent_year_set", entYearSet);//↓↓↓  同じく  ↓↓↓

		//JSPへフォワード 7
		req.getRequestDispatcher("student_list.jsp").forward(req, res); // 学生一覧まで画面遷移
	}

}
