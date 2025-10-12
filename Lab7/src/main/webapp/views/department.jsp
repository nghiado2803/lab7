<%@ page pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Department Management</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h2>DEPARTMENT MANAGEMENT</h2>

<c:url var="path" value="/department" />

<!-- ✅ Hiển thị thông báo -->
<c:if test="${not empty message}">
    <div class="message">${message}</div>
</c:if>

<!-- 🔹 FORM -->
<form method="post">
    <label>Id:</label><br>
    <input type="text" name="id" value="${item.id}" required><br>

    <label>Name:</label><br>
    <input type="text" name="name" value="${item.name}" required><br>

    <label>Description:</label><br>
    <textarea name="description" rows="3">${item.description}</textarea>
    <hr>

    <button formaction="${path}/create">Create</button>
    <button formaction="${path}/update">Update</button>
    <button formaction="${path}/delete">Delete</button>
    <button formaction="${path}/reset">Reset</button>
</form>

<!-- 🔹 TABLE -->
<table>
    <thead>
        <tr>
            <th>No.</th>
            <th>Id</th>
            <th>Name</th>
            <th>Description</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="d" items="${list}" varStatus="vs">
            <tr>
                <td>${vs.count}</td>
                <td>${d.id}</td>
                <td>${d.name}</td>
                <td>${d.description}</td>
                <td><a href="${path}/edit/${d.id}">Edit</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
