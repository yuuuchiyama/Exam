<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <div class="container">
            <h2 class="bg-secondary bg-opacity-10 py-2">科目情報登録</h2>
            <p class="text-center" style="background-color: #66CC99;">登録が完了しました</p>
            <div class="text-start"> <%-- 左詰めに変更 --%>
                <a href="SubjectCreate.action" class="btn btn-primary m-2">戻る</a>
                <a href="SubjectList.action" class="btn btn-primary m-2">科目一覧</a>
            </div>
        </div>
    </c:param>
</c:import>
