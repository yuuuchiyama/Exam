package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
	private String baseSql = "select subject.name, test.subject_cd, test.no, test.point from subject";

	/** フィルター後のリストへの格納処理をするメソッド */
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		List<TestListStudent> list = new ArrayList<>();		// リストを初期化

		try {
			while (rSet.next()) {
				TestListStudent tlStu = new TestListStudent();	// beenをインスタンス化

				// 学生別成績一覧インスタンスに検索結果をセット
				tlStu.setSubjectName(rSet.getString("subject.name"));
				tlStu.setSubject(rSet.getString("test.subject_cd"));
				tlStu.setNum(rSet.getInt("test.no"));
				tlStu.setPoint(rSet.getInt("test.point"));

				list.add(tlStu);	// リストに追加
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	/** 学生番号を指定して成績を取得するメソッド */
	public List<TestListStudent> filter(Student student) throws Exception {
		List<TestListStudent> list = new ArrayList<>();

		Connection connection = getConnection();		// データベースへのコネクションを確立

		PreparedStatement statement = null;				// プリペアードステートメントを初期化

		ResultSet rSet = null;							// リザルトセット

		String join = "join test on subject.cd = test.subject_cd";

		String where = "where test.student_no = ?";

		String order = "order by test.subject_cd asc";

		try {
			// 入力値：学生番号
			// 画面出力：科目名、科目コード、回数、点数

			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + join + where + order);

			// プリペアードステートメントに値をバインド
			statement.setString(1, student.getNo());

			rSet = statement.executeQuery();	// プリペアードステートメントを実行

			list = postFilter(rSet);

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}


		return list;
	}
}
