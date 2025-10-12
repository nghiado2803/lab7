<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Employee Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleEmployee.css">
</head>
<body>
<h2>EMPLOYEE MANAGEMENT</h2>

<c:url var="path" value="/employee" />

<!-- Thông báo -->
<c:if test="${not empty message}">
    <div class="message">${message}</div>
</c:if>

<!-- Form nhân viên -->
<form method="post" enctype="multipart/form-data" class="card">
    <!-- ID -->
    <div class="form-row">
        <label for="id">Id:</label>
        <input id="id" type="text" name="id" value="${item.id}" required />
    </div>

    <!-- Password -->
    <div class="form-row">
        <label for="password">Password:</label>
        <input id="password" type="password" name="password" value="${item.password}" required />
    </div>

    <!-- Fullname -->
    <div class="form-row">
        <label for="fullname">Fullname:</label>
        <input id="fullname" type="text" name="fullname" value="${item.fullname}" required />
    </div>

    <!-- Birthday -->
    <div class="form-row">
        <label for="birthday">Birthday:</label>
        <input id="birthday" type="date" name="birthday"
               value="<fmt:formatDate value='${item.birthday}' pattern='yyyy-MM-dd'/>" required />
    </div>

    <!-- Gender -->
    <div class="form-row">
        <label>Gender:</label>
        <label>
            <input type="radio" name="gender" value="true"
                   <c:if test="${item.gender == true}">checked</c:if> /> Male
        </label>
        <label>
            <input type="radio" name="gender" value="false"
                   <c:if test="${item.gender == false}">checked</c:if> /> Female
        </label>
    </div>

    <!-- Salary -->
    <div class="form-row">
        <label for="salary">Salary:</label>
        <input id="salary" type="number" name="salary" step="0.01" value="${item.salary}" required />
    </div>

    <!-- Department -->
    <div class="form-row">
        <label for="departmentId">Department:</label>
        <select id="departmentId" name="departmentId" required>
            <option value="">-- Select Department --</option>
            <c:forEach var="d" items="${departments}">
                <option value="${d.id}" 
                        <c:if test="${item.department != null && item.department.id == d.id}">selected</c:if>>
                    ${d.name}
                </option>
            </c:forEach>
        </select>
    </div>

    <!-- Photo -->
    <div class="form-row">
        <label for="photoFile">Photo:</label>
        <input id="photoFile" type="file" name="photoFile" accept="image/*" />
        <input type="hidden" name="photo" value="${item.photo}" />
        <c:if test="${not empty item.photo}">
            <div class="thumb">
                <img src="${pageContext.request.contextPath}/uploads/${item.photo}" alt="photo" />
            </div>
        </c:if>
    </div>

    <!-- Actions -->
    <div class="form-actions">
        <button formaction="${path}/create" type="submit">Create</button>
        <button formaction="${path}/update" type="submit">Update</button>
        <button formaction="${path}/delete" type="submit"
                onclick="return confirm('Bạn có chắc muốn xóa nhân viên này không?');">Delete</button>
        <button formaction="${path}/reset" type="submit">Reset</button>
    </div>
</form>

<!-- Danh sách nhân viên -->
<table class="data-table">
    <thead>
        <tr>
            <th>No.</th>
            <th>Id</th>
            <th>Fullname</th>
            <th>Birthday</th>
            <th>Gender</th>
            <th>Salary</th>
            <th>Department</th>
            <th>Photo</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="e" items="${list}" varStatus="vs">
            <tr>
                <td>${vs.count}</td>
                <td>${e.id}</td>
                <td>${e.fullname}</td>
                <td><fmt:formatDate value="${e.birthday}" pattern="yyyy-MM-dd" /></td>
                <td>
                    <c:choose>
                        <c:when test="${e.gender}">Male</c:when>
                        <c:otherwise>Female</c:otherwise>
                    </c:choose>
                </td>
                <td><fmt:formatNumber value="${e.salary}" type="number" pattern="#,##0.##" /></td>
                <td>${e.department.name}</td>
                <td>
                    <c:if test="${not empty e.photo}">
                        <img class="tbl-thumb" src="${pageContext.request.contextPath}/uploads/${e.photo}" alt="photo" />
                    </c:if>
                </td>
                <td><a class="edit-link" href="${path}/edit/${e.id}">Edit</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>
