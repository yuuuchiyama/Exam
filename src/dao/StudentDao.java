package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

/*
<用語>
 プリペアードステートメント：プログラム上で動的にSQL文を生成する必要があるとき、可変部分を変数のように
 							 したSQL文をあらかじめ作成しておき、値の挿入は処理系に行わせる方式。
 */

public class StudentDao extends Dao {
	// フィールド変数
	private String baseSql = "select * from student where school_cd = ?"; // 各メソッドで使用するSQLを用意

	// 1つのデータの取得は下のフィルターメソッドと違いbennの値をリストに格納する必要はない
	/** 学生番号を指定して学生インスタンスを1件取得するメソッド */
	public Student get(String no) throws Exception {
		Student student = new Student();			// 学生インスタンスを初期化

		Connection connection = getConnection();	// データベースへのコネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from student where no = ?");
			statement.setString(1, no);		// プリペアードステートメントに学生番号をバインド

			ResultSet rSet = statement.executeQuery();	// プリペアードステートメントを実行

			SchoolDao schoolDao = new SchoolDao();		// 学校Daoを初期化

			// リザルトセットが存在する場合
			if (rSet.next()) {
				// 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setIsAttend(rSet.getBoolean("is_attend"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				student.setSchool(schoolDao.get(rSet.getString("school_cd")));

			// リザルトセットが存在しない場合
			} else {
				student = null;		// 学生インスタンスにnullをセット
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

		return student;
	}

	// 複数のデータを取得する場合はStudentsインスタンスを作製してbeenに値を格納してリストに追加する(88行目)
	/** フィルター後のリストへの格納処理をするメソッド */
	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
		List<Student> list = new ArrayList<>();	// リストを初期化

		try {
			while (rSet.next()) {	// リザルトセットを全権走査
				Student student = new Student();	// 学生インスタンスを初期化

				// 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setIsAttend(rSet.getBoolean("is_attend"));
				student.setSchool(school);

				list.add(student);	// リストに追加
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	/* フィルターメソッド */
	/** 学校、入学年度、クラス番号、在学フラグを指定して学生の一覧を取得するメソッド */
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();		// リストを初期化

		Connection connection = getConnection();	// コネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		ResultSet rSet = null;						// リザルトセット

		String condition = "and ent_year = ? and class_num = ?";	// SQL文の条件

		String order = "order by no asc";							// SQL文のソート

		String conditionIsAttend = "";	// SQL文の在学フラグ条件
		if (isAttend) {					// 在学フラグがtrueの場合
			conditionIsAttend = "and is_attend = true";
		}

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);

			statement.setString(1, school.getCd());	// プリペアードステートメントに学校コードをバインド
			statement.setInt(2, entYear);			// プリペアードステートメントに入学年度をバインド
			statement.setString(3, classNum);		// プリペアードステートメントにクラス番号をバインド

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

	/** 学校、入学年度、在学フラグを指定して学生の一覧を取得するメソッド */
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();

		Connection connection = getConnection();	// コネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		ResultSet rSet = null;						// リザルトセット

		String condition = "and ent_year = ?";		// SQL文の条件

		String order = "order by no asc";			// SQL文のソート

		String conditionIsAttend = "";	// SQL文の在学フラグ条件
		if (isAttend) {					// 在学フラグがtrueの場合
			conditionIsAttend = "and is_attend = true";
		}

		try {
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			statement.setString(1, school.getCd());	// プリペアードステートメントに学校コードをバインド
			statement.setInt(2, entYear);			// プリペアードステートメントに入学年度をバインド

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

	/** 学校、在学フラグを指定して学生の一覧を取得するメソッド */
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();

		Connection connection = getConnection();	// コネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		ResultSet rSet = null;						// リザルトセット

		String order = "order by no asc";			// SQL文のソート

		String conditionIsAttend = "";	// SQL文の在学フラグ条件
		if (isAttend) {					// 在学フラグがtrueの場合
			conditionIsAttend = "and is_attend = true";
		}

		try {
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
			statement.setString(1, school.getCd());	// プリペアードステートメントに学校コードをバインド

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

	/** 学生インスタンスをデータベースに保存するメソッド */
	public boolean save(Student student) throws Exception {
		Connection connection = getConnection();	// コネクションを確立

		PreparedStatement statement = null;			// プリペアードステートメント

		int count = 0;								// 実行件数

		try {
			Student old = get(student.getNo());		// データベースから学生を取得

			// 学生が存在しなかった場合
			if (old == null) {
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement(
						"insert into student(no, name, ent_year, class_num, is_attend, school_cd) values (?, ?, ?, ?, ?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.isAttend());
				statement.setString(6, student.getSchool().getCd());

			// 学生情報が存在した場合
			} else {
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement(
						"update student set name = ?, ent_year = ?, class_num = ?, is_attend = ? where no = ?");
				// プリペアードステートメントに値をバインド
				statement.setString(1, student.getName());
				statement.setInt(2, student.getEntYear());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.isAttend());
				statement.setString(5, student.getNo());
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
}
