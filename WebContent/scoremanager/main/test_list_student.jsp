<%-- 学生一覧JSP --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">得点管理システム</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<section class="mp-4">

			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（学生）</h2>

			<div class="my-2 text-end px-4">

			</div>

			<form method="get">

				<div class="row border mx-3 py-2 align-items-center rounded" id="filter">

					<div class="col-4">

						<label class="form-label" for="student-f1-select">入学年度</label>

						<select class="form-select" id="student-f1-select" name="f1">

							<option value="0">--------</option>

							<c:forEach var="year" items="${ent_year_set}">

								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>

							</c:forEach>

						</select>

					</div>

					<div class="col-4">

						<label class="form-label" for="student-f2-select">クラス</label>

						<select class="form-select" id="student-f2-select" name="f2">

							<option value="0">--------</option>

							<c:forEach var="num" items="${class_num_set}">

								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>

							</c:forEach>

						</select>

					</div>

					<div class="col-4">

						<label class="form-label" for="student-f3-select">科目</label>

						<select class="form-select" id="student-f3-select" name="f3">

							<option value="0">--------</option>

							<c:forEach var="subject" items="${subject_list}">

								<option value="${subject.id}" <c:if test="${subject.id==f3}">selected</c:if>>${subject.name}</option>

							</c:forEach>

						</select>

											<div class="col-12 text-end mt-2">

						<button class="btn btn-secondary" id="filter-button">検索</button>

					</div>

					</div>

						<label class="form-label">学生番号</label>

					<input type="text" class="from-text" name="no" size="85" required="requier" maxlength="10" placeholder="学生番号を入力してください"><br>

							<c:forEach var="year" items="${stu_num_set}">

								<option value="${num}" <c:if test="${num==f4}">selected</c:if>>${num}</option>

							</c:forEach>

						</select>

					</div>

					<div class="col-12 text-end mt-2">

						<button class="btn btn-secondary" id="filter-button">検索</button>

					</div>

						</select>

					</div>

				</div>

			</form>

			<c:choose>

				<c:when test="${students.size()>0}">

					<div>検索結果:${students.size()}件</div>

					<table class="table table-hover">

						<tr>

							<th>入学年度</th>

							<th>学生番号</th>

							<th>氏名</th>

							<th>クラス</th>

							<th class="text-center">在学中</th>

							<th></th>

							<th></th>

						</tr>

						<c:forEach var="student" items="${students}">

							<tr>

								<td>${student.entYear}</td>

								<td>${student.no}</td>

								<td>${student.name}</td>

								<td>${student.classNum}</td>

								<td class="text-center">

									<c:choose>

										<c:when test="${student.isAttend()}">〇</c:when>

										<c:otherwise>☓</c:otherwise>

									</c:choose>

								</td>

								<td><a href="StudentUpdate.action?no=${student.no}">変更</a></td>

								<td><a href="StudentDelete.action?no=${student.no}">削除</a></td>

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
