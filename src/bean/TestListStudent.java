package bean;

import java.io.Serializable;

public class TestListStudent implements Serializable {
	private String subjectName;
	private String subjectCd;
	private int num;
	private int point;
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String SubjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectCd() {
		return subjectCd;
	}
	public void setSubject(String SubjectCd) {
		this.subjectCd = subjectCd;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}

}
