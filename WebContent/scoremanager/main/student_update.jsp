<%-- 学生変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
<c:param name= "title">
		得点管理システム
</c:param>

<c:param name="scripts"></c:param>

<c:param name="content">
	<section class="my-2 me-4">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
		<form action="StudentUpdateExecute.action" method="get">
			<div class="my-3 me-4">
				<label class="form-label" for="student-entyear">入学年度</label><br>
				<input class="ps-3 border-0" id="student-entyear" value="${ent_year}" readonly/>
			</div>
			<div class="my-3 me-4">
				<label class="form-label" for="student-no">学生番号</label><br>
				<input class="ps-3 border-0" id="student-no" type="text" name="no" value="${no}"
					   maxlength="10" required readonly/>
			</div>
			<div class="my-3 me-4">
				<label class="form-label" for="student-name">氏名</label><br>
				<input class=" form-control" id="student-name" type="text" name="name" value="${name}"
					   maxlength="30" required/>
			</div>
			<div class="my-3 me-4">
				<label class="form-label" for="student-f2-select">クラス</label>
				<select class="form-select " id="student-f2-select" name="class_num">
					<c:forEach var="num" items="${num}">
						<%--現在のnumと選択されていたf2が一致していた場合selectedを追記--%>
						<option value="${num}" <c:if test="${num == class_num}">selected</c:if>>${num}</option>
					</c:forEach>
				</select>
			</div>
			<div class="my-3">
				<label for="student-check">在学中</label>
				<input class="form-check-input" type="checkbox" id="student-check" name="is_attend" checked/>
			</div>
			<div class="mb-2">
				<input class="mt-2 btn btn btn-primary" type="submit" value="変更"/>
			</div>
		</form>
		<a href="StudentList.action">戻る</a>
	</section>
</c:param>
</c:import>