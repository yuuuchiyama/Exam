<%-- 学生情報変更完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
			<h2 class = "bg-secondary bg-opacity-10 py-2">学生情報変更</h2>
			<p class = "text-center" style="background-color: #66CC99;">変更が完了しました</p><br>
			<a href="StudentList.action">学生一覧</a>
	</c:param>
</c:import>