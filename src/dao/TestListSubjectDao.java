package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
	private String baseSql = "select student.ent_year, test.class_num, test.student_no, student.name, test.no, test.point from test";

	/** フィルター後のリストへの格納処理を行うメソッド */
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		List<TestListSubject> list = new ArrayList<>();		// リストを初期化
		int i = 0;

		try {
			while (rSet.next()) {	// リザルトセットを全権走査
				if (rSet.getInt("test.no") == 2) {
					System.out.println(rSet.getInt("test.no"));
					System.out.println(rSet.getInt("test.point"));
					System.out.println(list.get(i - 1).getPoints());
					list.get(i - 1).putPoint(rSet.getInt("test.no"), rSet.getInt("test.point"));
					System.out.println(list.get(i - 1).getPoints());
				} else {
					TestListSubject listSubject = new TestListSubject();	//科目別成績インスタンスを初期化

					// 科目別成績インスタンスに結果をセット
					listSubject.setEntYear(rSet.getInt("student.ent_year"));
					listSubject.setClassNum(rSet.getString("test.class_num"));
					listSubject.setStudentNo(rSet.getString("test.student_no"));
					listSubject.setStudentName(rSet.getString("student.name"));
					listSubject.putPoint(rSet.getInt("test.no"), rSet.getInt("test.point"));

					// リストに追加
					// [entYear, studentNo, studentName, classNum, points]
					list.add(listSubject);
					System.out.println(list.size());
				}

				i++;
			}

		} catch (Exception e) {
			throw e;
		}

		return list;
	}

	/** 入学年度、クラス番号、科目名、学校コードを指定して科目別の成績を取得するメソッド */
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		List<TestListSubject> list = new ArrayList<>();		// リストを初期化

		Connection connection = getConnection();	//コネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		ResultSet rSet = null;						// リザルトセット

		String join = " join student on student.no = test.student_no";		// SQLのjoin

		String where = " where student.ent_year = ? and student.class_num = ? and  test.subject_cd = (select cd from subject where school_cd = ? and name = ?) and test.school_cd = ?";		// SQLの条件式

		String order = " order by student.no asc, test.no";		// SQLのorder文

		try {
			// 画面出力：入学年度(引数)、クラス、学生番号、氏名、回数別の点数(testテーブル)

			statement = connection.prepareStatement(baseSql + join + where + order);

			// プリペアードステートメントに値をセット
			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, school.getCd());
			statement.setString(4, subject.getName());
			statement.setString(5, school.getCd());

			rSet = statement.executeQuery();	// プリペアードステートメントを実行

			list = postFilter(rSet);			// リストへの格納処理を実行

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
