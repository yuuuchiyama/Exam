<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報変更</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="bg-secondary bg-opacity-10 py-2">科目情報変更</h2>
            <p class="text-center" style="background-color: #66CC99;">変更が完了しました</p>
            <div class="text-start"> <!-- 左詰めにするための修正 -->
                <a href="SubjectList.action" class="btn btn-primary mt-3">科目一覧</a>
            </div>
        </section>
    </c:param>
</c:import>
