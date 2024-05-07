package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

	/** 科目を取得するメソッド */
	public Subject get(String cd, School school) throws Exception {
		Subject subject = new Subject();			// 科目インスタンスを初期化

		Connection connection = getConnection();	// データベースへのコネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where school_cd = ? and cd = ?");

			// プリペアードステートメントに値をバインド
			statement.setString(1, school.getCd());
			statement.setString(2, cd);

			ResultSet rSet = statement.executeQuery();	// プリペアードステートメントを実行

			SchoolDao schoolDao = new SchoolDao();		// 学校Daoを初期化

			// リザルトセットが存在する場合
			if (rSet.next()) {
				// 科目インスタンスに検索結果をセット
				subject.setSchool(schoolDao.get(rSet.getString("school_cd")));	// 学校コード
				subject.setCd(rSet.getString("cd"));							// 科目コード
				subject.setName(rSet.getString("name"));						// 科目名

			// リザルトセットが存在しない場合
			} else {
				subject = null;		// 学生インスタンスにnullをセット
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

		return subject;
	}

	/** ユーザーの学校の科目コードと科目名を全て取得するメソッド */
	public List<Subject> filter(School school) throws Exception {
		// リストを初期化
		List<Subject> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection
					.prepareStatement("select cd, name from subject where school_cd=? order by cd");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// リザルトセットを全件走査
			while (rSet.next()) {
				Subject subject  = new Subject();

				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));

				// リストに科目を追加
				list.add(subject);
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

		return list;
	}

	/** 科目を登録、変更するメソッド（実行されると戻り値でtrueを返す） */
	public boolean save(Subject subject) throws Exception {
		Connection connection = getConnection();	// コネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		int count = 0;								// 実行件数

		try {
			Subject old = get(subject.getCd(),subject.getSchool());// データベースから科目を取得

			// 科目が存在しなかった場合
			if (old == null) {
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement(
						"insert into subject(school_cd, cd, name) values (?, ?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1, subject.getSchool().getCd());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getName());

			// 科目情報が存在した場合
			} else {
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement(
						"update subject set name = ? where school_cd = ? and cd = ? ");
				// プリペアードステートメントに値をバインド
				statement.setString(1, subject.getName());
				statement.setString(2, subject.getSchool().getCd());
				statement.setString(3, subject.getCd());
			}

			count = statement.executeUpdate();	// プリペアードステートメントを実行

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

		// 実行件数が1件以上ある場合
		if (count > 0) {
			return true;

		// 実行件数が0件の場合
		} else {
			return false;
		}
	}

	/** ユーザーの学校の科目を削除するメソッド（実行されると戻り値でtrueを返す） */
	public boolean delete(Subject subject) throws Exception {
		Connection connection = getConnection();	// コネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		int count = 0;								// 実行件数

		try {
			// 科目の削除
			statement = connection.prepareStatement(
					"delete from subject where school_cd = ? and cd = ?");
			// プリペアードステートメントに値をバインド
			statement.setString(1, subject.getSchool().getCd());
			statement.setString(2, subject.getCd());

			count = statement.executeUpdate();	// プリペアードステートメントを実行

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

		// 実行件数が1件以上ある場合
		if (count > 0) {
			return true;

		// 実行件数が0件の場合
		} else {
			return false;
		}
	}
}
