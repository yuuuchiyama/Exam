package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.javafx.collections.MappingChange.Map;

import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class TestListAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		String studentNoStr = "";
		String subjectCdStr = "";
		String schoolCdStr  = "";
		String Str= "";
		int studentNo = 0;
		List<Test> tests = null;
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();
		Map<String,String> errors = new HasMap<>();

		studentNoStr = req.getParameter("f1");
		subjectCdStr = req.getParameter("f2");
		schoolCdStr = req.getParameter("f3");

		if (studentNoStr != null) {
			studentNo = Integer.parseInt(studentNoStr);		// 数値に変換
		}
		List<Integer> studentNoSet = new ArrayList<>();	// リストを初期化
		for (int i = year - 10; i < year + 1; i++) {	// 10年前から1年後まで年をリストに追加
			studentNoSet.add(i);
		}
		List<String> list = cNumDao.filter(teacher.getSchool());

	}
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
