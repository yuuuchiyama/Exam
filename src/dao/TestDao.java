package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
	private String baseSql = "select test.student_no, test.subject_cd, test.school_cd, test.no, test.point, test.class_num from test";

	/** テストデータを一件取得するメソッド */
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		Test test = new Test();						// テストインスタンスを初期化

		Connection connection = getConnection();	// データベースのコネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		String where = "where student_no = ? and subject_cd = ? and school_cd = ? and no = ?";

		String  order = "order by student_no asc";

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + where + order);

			// プリペアードステートメントに値をバインド
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);

			ResultSet rSet = statement.executeQuery();	// プリペアードステートメントを実行

			// リザルトセットが存在する場合
			if (rSet.next()) {
				// 検索結果をセット
				test.setStudent(student);
				test.setSubject(subject);
				test.setSchool(school);
				test.setNo(no);
				test.setPoint(rSet.getInt("point"));
				test.setclassNum(rSet.getString("test.class_num"));

			// リザルトセットが存在しない場合
			} else {
				test = null;
			}

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

		return test;
	}

	/** フィルター後のリストへの格納処理をするメソッド */
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		List<Test> list = new ArrayList<>();	// リストを初期化
		StudentDao sDao = new StudentDao();		// studentDaoをインスタンス化
		SubjectDao subDao = new SubjectDao();

		try {
			while (rSet.next()) {	// リザルトセットを全権走査
				Test test = new Test();		// Testインスタンスを初期化

				// テストインスタンスに検索結果をセット
				test.setStudent(sDao.get(rSet.getString("student.no")));
				test.setclassNum(rSet.getString("student.classNum"));
				test.setSubject(subDao.get(rSet.getString("test.subject_cd"), school));
				test.setSchool(school);
				test.setNo(rSet.getInt("test.no"));
				test.setPoint(rSet.getInt("test.point"));

				list.add(test);	// リストに追加
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	/** テストデータの全件取得メソッド */
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();			// リストを初期化

		Connection connection = getConnection();	// コネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		ResultSet rSet = null;						// リザルトセット

		String join = "join student on student.no = test.student_no";

		String where = "where student.ent_year = ? and student.class_num = ? and  test.subject_cd = (select cd from subject where school_cd = ?, name = ?) and test.no = ? and test.school_cd = ?";

		String  order = "order by student_no asc";

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + join + where + order);

			statement.setInt(1, entYear);	// プリペアードステートメントに入学年度をバインド
			statement.setString(2, classNum);			// プリペアードステートメントにクラス番をバインド
			statement.setString(3, school.getCd());		// プリペアードステートメントに学校コードをバインド
			statement.setString(4, subject.getName());	// プリペアードステートメントに学生名をバインド
			statement.setInt(5, num);					// プリペアードステートメントに回数をバインド
			statement.setString(6, school.getCd());		// プリペアードステートメントに学校コードをバインド

			rSet = statement.executeQuery();		// プリペアードステートメントを実行

			list = postFilter(rSet, school);		// リストへの格納処理を実行

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

	/**  */
	public boolean save(List<Test> list) throws Exception {
		Connection connection = getConnection();		// コネクションを確立

		int countMeth = 0;	// メソッドが呼び出された回数をカウント

		try {
			for (Test test:list) {
				boolean saveSet = save(test, connection);

				// メソッドの実行件数(セーブした学生の数)をインクリメント
				if (saveSet == true) {
					countMeth++;
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
		}catch (Exception e) {
			throw e;
		}

		// 実行件数が1件以上ある場合
		if (countMeth > 0) {
			return true;
		// 実行件数が0件の場合
		}else {
			return false;
		}
	}

	/**  */
	private boolean save(Test test, Connection connection) throws Exception {
		PreparedStatement statement = null;

		int count = 0;

		try {
			Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());

			// 学生のテストデータがない場合
			if (old == null) {
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement(
						"insert into test values (?, ?, ?, ?, ?, ?)");

				// プリペアードステートメントに値をバインド
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());

			// 学生のテストデータが存在する場合
			} else {
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement(
						"update test set point = ? where student_no = ? and subject_cd = ? and school_cd = ? and no = ?");

				// プリペアードステートメントに値をバインド
				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getStudent().getNo());
				statement.setString(3, test.getSubject().getCd());
				statement.setString(4, test.getSchool().getCd());
				statement.setInt(5, test.getNo());
			}

			count = statement.executeUpdate();	// プリペアードステートメントを実行

		}catch (Exception e) {
			throw e;
		}finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		// 実行件数が1件以上ある場合
		if (count > 0) {
			return true;
		// 実行件数が0件の場合
		}else {
			return false;
		}
	}

	/**  */
	public boolean delete(List<Test> list) throws Exception {
		Connection connection = getConnection();		// コネクションを確立

		int countMeth = 0;	// メソッドが呼び出された回数をカウント

		try {
			for (Test test:list) {
				boolean deleteSet = delete(test, connection);

				// メソッドの実行件数(削除した学生の数)をインクリメント
				if (deleteSet == true) {
					countMeth++;
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
		}catch (Exception e) {
			throw e;
		}

		// 実行件数が1件以上ある場合
		if (countMeth > 0) {
			return true;
		// 実行件数が0件の場合
		}else {
			return false;
		}
	}

	/**  */
	private boolean delete(Test test, Connection connection) throws Exception {
		PreparedStatement statement = null;

		int count = 0;

		try {
			// テストデータの削除
			statement = connection.prepareStatement(
					"delete from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ?");

			// プリペアードステートメントに値をバインド
			statement.setString(1, test.getStudent().getNo());
			statement.setString(2, test.getSubject().getCd());
			statement.setString(3, test.getSchool().getCd());
			statement.setInt(4, test.getNo());

			count = statement.executeUpdate();	// プリペアードステートメントを実行

		} catch (Exception e) {
			throw e;
		} finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		// 実行件数が1件以上ある場合
		if (count > 0) {
			return true;
		// 実行件数が0件の場合
		}else {
			return false;
		}
	}
}
