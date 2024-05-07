<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">科目管理</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <style>
            .table th,
            .table td {
                border: none; /* 周りの罫線を削除 */
            }
            .table-bordered td,
            .table-bordered th {
                border-bottom: 1px solid #dee2e6; /* 横線を追加 */
            }
        </style>

        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
            <div class="my-2 text-end px-4">
                <a href="SubjectCreate.action">新規登録</a>
            </div>
            <c:choose>
			<c:when test="${subjects.size()>0}">
				<div>検索結果:${subjects.size()}件</div>
				<table class="table table-bordered table-hover">
					<tr>
						<th>科目コード</th>
						<th>科目名</th>
					</tr>
					<c:forEach var="subjects" items="${subjects}">
						<tr>
							<td>${subjects.cd}</td>
							<td>${subjects.name}</td>
							<td><a href="SubjectUpdate.action?cd=${subjects.cd}">変更</a></td>
							<td><a href="SubjectDelete.action?cd=${subjects.cd}">削除</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div>科目情報が存在しませんでした</div>
			</c:otherwise>
			</c:choose>
        </section>
    </c:param>
</c:import>
