<%-- 学生追加JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
<c:param name= "title">
		得点管理システム
</c:param>

<c:param name="scripts"></c:param>

<c:param name="content">
	<section class="me-4">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>
		<form action="StudentCreateExecute.action" method="get">
			<div class="me-4">
				<label class="form-label" for="student-f1-select">入学年度</label>
				<select class="form-select" id="student-fl-select" name="ent_year">
					<option value="0">--------</option>
					<c:forEach var="year" items="${ent_year_set}">
						<%-- 現在のyearと選択されていだf1が一致していた場合selectedを追記--%>
						<option value="${year}" <c:if test="${year==ent_year}">selected</c:if>>${year}</option>-
					</c:forEach>
				</select>
				<div class="mt-2 text-warning">${errors.get("year_error")}</div>
			</div>
			<div class="me-4">
				<label class="form-label" for="student-no">学生番号</label><br>
				<input class="form-control" id="student-no" type="text" name="no" value="${no}"
					   maxlength="10" placeholder="学生番号を入力してください" required>
				<div class="mt-2 text-warning">${errors.get("no_error")}</div>
			</div>
			<div class="me-4">
				<label class="form-label" for="student-name">氏名</label><br>
				<input class="form-control" id="student-name" type="text" name="name" value="${name}"
					   maxlength="10" placeholder="氏名を入力してください" required>
			</div>
			<div class="me-4">
				<label class="form-label" for="student-f2-select">クラス</label>
				<select class="form-select " id="student-f2-select" name="class_num">
					<option value="0">--------</option>
					<c:forEach var="num" items="${class_num_set}">
						<%--現在のnumと選択されていたf2が一致していた場合selectedを追記--%>
						<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-1 my-4 text-center">
				<button class="btn btn-secondary text-nowrap" name="end">登録して終了</button>
			</div>
		</form>
		<a href="StudentList.action">戻る</a>
	</section>
</c:param>
</c:import>