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
			<div class="my-2 text-end px-4"></div>
			<form action="TestListSubjectExecute.action" method="get">
				<div class="row border mx-3 py-2 px-3 align-items-center rounded" id="filter">
					<div class="col-2">科目情報</div>
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
					<div class="col-2 text-center">
						<button class="btn btn-secondary">検索</button>
					</div>

					<div class="row border-bottom mx-0 mb-3 pb-2 align-items-center"></div>

					<div class="col-2">
						<p><label>学生情報</label></p>
					</div>
					<div class="col-4">
						<label class="form-label" for="test-f4-input">学生番号</label>
						<input class="form-select" id="test-f4-input" type="text" name="f4" value="${f4}" placeholder="学生番号を入力してください"/>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary">検索</button>
					</div>
				</div>
				<div>科目:${get_subject_name} (${test_set.size()})件</div>
				<table class="table table-hover">
					<tr>
						<th>入学年度</th>
						<th>クラス</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>1回</th>
						<th>2回</th>
					</tr>
					<c:forEach var="test" items="${test_set}">
						<tr>
							<td>${test.entYear}</td>
							<td>${test.classNum}</td>
							<td>${test.studentNo}</td>
							<td>${test.studentName}</td>
							<td>${test.getPoint(1)}</td>
							<td>${test.getPoint(2)}</td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</section>
	</c:param>
</c:import>
