package bean;

import java.io.Serializable;

public class Student implements Serializable {
	private String no;

	private String name;

	private int entYear;

	private String classnum;

	private boolean isAttend;

	private School school;


	// セッタ
	public void setNo(String no) {
		this.no = no;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}

	public void setClassNum(String classnum) {
		this.classnum = classnum;
	}

	public void setIsAttend(boolean isAttend) {
		this.isAttend = isAttend;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	// ゲッタ
	public String getNo() {
		return no;
	}

	public String getName() {
		return name;
	}

	public int getEntYear() {
		return entYear;
	}

	public String getClassNum() {
		return classnum;
	}

	public boolean isAttend() {
		return isAttend;
	}

	public School getSchool() {
		return school;
	}

	public int getSchoolYear() {
		return 0;
	}
}
