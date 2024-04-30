package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

// カスタムインタフェースまたはフレームワークのActionクラスを継承することが一般的です。
public class SubjectListAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション情報を取得
		Teacher teacher = (Teacher)session.getAttribute("user");

			SubjectDao subjectDao = new SubjectDao();

            // 学校に関連する科目リストを取得
            List<bean.Subject> subjects = subjectDao.filter(teacher.getSchool());

            // 科目リストをリクエスト属性にセット
            req.setAttribute("subjects", subjects);

            // 科目リストを表示するJSPにフォワード
            req.getRequestDispatcher("subject_list.jsp").forward(req, res);
        }
}