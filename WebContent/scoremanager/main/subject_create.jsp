<%-- 科目情報登録JSP --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">

<c:param name="title">
    科目情報登録
</c:param>

<c:param name="content">
    <section class="me-4">
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
        <form action="SubjectCreateExecute.action" method="post">
            <div class="me-4">
                <label class="form-label" for="subject-code">科目コード</label><br>
                <input class="form-control" id="subject-code" type="text" name="code" placeholder="科目コードを入力してください" required>
            </div>
            <div class="me-4">
                <label class="form-label" for="subject-name">科目名</label><br>
                <input class="form-control" id="subject-name" type="text" name="name" placeholder="科目名を入力してください" required>
            </div>
            <div class="col-1 my-4 text-center">
                <button class="btn btn-secondary text-nowrap" name="end">登録</button>
            </div>
        </form>
        <a href="SubjectList.action">戻る</a>
    </section>
</c:param>

</c:import>