package scoremanager.main;

import java.io.IOException;
import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.spi.orbutil.fsm.Action;

import bean.School;
import dao.SubjectDao;

// カスタムインタフェースまたはフレームワークのActionクラスを継承することが一般的です。
public abstract class SubjectListAction implements Action {

    private SubjectDao subjectDao;

    // コンストラクタでDAOのインスタンスを受け取る
    public SubjectListAction(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // パラメータから学校IDを取得
            String schoolId = request.getParameter("schoolId");
            if (schoolId == null) {
                // エラー処理：schoolIdが必要
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "School ID is required.");
                return;
            }

            School school = new School();
            school.setCd(schoolId); // 学校IDを設定

            // 学校に関連する科目リストを取得
            List<Subject> subjects = subjectDao.filter(school);

            // 科目リストをリクエスト属性にセット
            request.setAttribute("subjects", subjects);

            // 科目リストを表示するJSPにフォワード
            request.getRequestDispatcher("subject_list.jsp").forward(request, response);
        } catch (Exception e) {
            // 例外のロギングとエラーレスポンスの送信
            e.printStackTrace(); // 本番環境ではより適切なロギングが推奨されます。
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal error occurred.");
        }
    }
}
