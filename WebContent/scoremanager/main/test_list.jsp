<%-- 学生一覧JSP --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">得点管理システム</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="mp-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			<div class="my-2 text-end px-4">
			</div>
			<form action="TestListStudentExecute.action" method="get">
				<div class="row border mx-3 py-2 align-items-center rounded" id="filter">
					<div class="col-2">
						<label class="form-label" for="test-f1-select">入学年度</label>
						<select class="form-select" id="test-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="test-f2-select">クラス</label>
						<select class="form-select" id="test-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="test-f3-select">科目</label>
						<select class="form-select" id="test-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_list}">
								<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="test-f4-select">回数</label>
						<select class="form-select" id="test-f4-select" name="f4">
							<option value="0">--------</option>
							<c:forEach var="num" items="${tes_num_set}">
								<option value="${num}" <c:if test="${num==f4}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
				</div>
			</form>
			<c:choose>
				<c:when test="${students.size()>0}">
					<div>検索結果:${students.size()}件</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>点数</th>
						</tr>
						<c:forEach var="student" items="${students}">
							<tr>
								<td>${student.entYear}</td>
								<td>${student.no}</td>
								<td>${student.name}</td>
								<td>${student.classNum}</td>
								<td></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>
