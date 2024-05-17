package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の宣言１
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		LocalDate todaysDate = LocalDate.now();	// LocalDateインスタンスを取得
		int year = todaysDate.getYear();	// 現在の年を取得

		// DAOをインスタンス化
		ClassNumDao cNumDao = new ClassNumDao();
		SubjectDao subDao = new SubjectDao();

		// リクエストパラメーターの取得２
		// なし

		// DBからデータの取得
		// クラス番号の一覧をリストに取得
		List<String> class_list = cNumDao.filter(teacher.getSchool());
		// 科目コードと科目名をリストに取得
		List<Subject> subject_list = subDao.filter(teacher.getSchool());

		//ビジネスロジック 4
		List<Integer> entYearSet = new ArrayList<>();	// リストを初期化
		for (int i = year - 10; i < year + 1; i++) {	// 10年前から1年後まで年をリストに追加
			entYearSet.add(i);
		}
		List<Integer> tesNumSet = new ArrayList<>();	// リストを初期化
		for (int i = 1; i < 3; i++) {					// 1～2回のテストの回数をリストに追加
			tesNumSet.add(i);
		}


		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("ent_year_set", entYearSet);		// リクエストに入学年度をセット
		req.setAttribute("class_num_set", class_list);		// リクエストにクラス番号をセット
		req.setAttribute("subject_list", subject_list);		// リクエストに科目情報をセット
		req.setAttribute("tes_num_set", tesNumSet);				// リクエストにテストの回数をセット

		//JSPへフォワード 7
		req.getRequestDispatcher("test_list.jsp").forward(req, res); // 学生一覧まで画面遷移
	}
}
