<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報変更</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
            <form action="SubjectUpdateExecute.action" method="post">
                <div class="me-4">
                    <label class="form-label" for="subject-code">科目コード</label><br>
                    <input class="form-control" id="subject-code" type="text" name="cd" value="${subject.cd}" maxlength="10" required/>
                </div>
                <div class="me-4">
                    <label class="form-label" for="subject-name">科目名</label><br>
                    <input class="form-control" id="subject-name" type="text" name="name" value="${subject.name}" maxlength="30" required/>
                </div>
                <div class="mt-4">
                    <input class="btn btn-lg btn-primary" type="submit" value="変更"/>
                </div>
            </form>
            <a href="SubjectList.action" class="btn btn-secondary mt-3">戻る</a>
        </section>
    </c:param>
</c:import>
