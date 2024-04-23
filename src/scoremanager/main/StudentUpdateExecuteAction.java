package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言
		HttpSession session = req.getSession();		//セッションを取得
		Teacher teacher = (Teacher)session.getAttribute("user");

		String no = "";			// jspから引き渡された学生番号
		String name = "";		// 入力された氏名
		String classNum = "";	// 入力されたクラス番号
		String isAttendStr = "";	// 入力された在学フラグ(チェックが入っていると"on"が送信される)

		int entYear = 0;		// 入学年度
		boolean isAttend = false;	// 在学フラグ

		Student student = new Student();	// studentbeenをインスタンス化

		StudentDao sDao = new StudentDao();	// StudentDaoをインスタンス化

		String url = "";		// 画面遷移で使用するurl

		//リクエストパラメーターの取得
		no = req.getParameter("no");				// 学生番号を取得
		name = req.getParameter("name");			// 学生名を取得
		classNum = req.getParameter("class_num");	// クラスを取得
		isAttendStr = req.getParameter("is_attend");// 在学フラグを取得

		//DBからデータの取得
		Student getstu = sDao.get(no);
		entYear = getstu.getEntYear(); // 入学年度をDBから取得


		//ビジネスロジック
		// 在学フラグのチェックボックスにチェックがあればフラグを立てる
		if (isAttendStr != null) {
			isAttend = true;
		}

		// beenに値をセット
		student.setEntYear(entYear);
		student.setNo(no);
		student.setName(name);
		student.setClassNum(classNum);
		student.setIsAttend(isAttend);
		student.setSchool(teacher.getSchool());
		System.out.println(classNum);

		//DBへの保存
		sDao.save(student);

		//レスポンス値をセット

		//フォワード
		url = "student_update_done.jsp";
		req.getRequestDispatcher(url).forward(req, res);

	}

}
