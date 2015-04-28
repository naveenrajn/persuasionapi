<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- Include common css -->
<%@include file="includes/css_common.jsp"%>

<!-- Include page specific css -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap-editable.css"
	rel="stylesheet" />
	
<title>Configure User Badges</title>
</head>
<body role="document">

	<!-- Include navigation bar -->
	<%@include file="includes/jsp/navbar.jsp"%>
	<div class="container theme-showcase" role="main">
		<div class="page-header">
			<h1>User Badges</h1>
		</div>

		<!-- Display error message, if any -->
		<c:if test="${not empty errorCode or not empty errorMessage}">
			<div class="alert alert-danger" role="alert">
				<strong>Oh snap!</strong> Something went wrong.<br /> Error
				Message: ${errorMessage }
			</div>
		</c:if>
		
		<!-- Display the list of badges; add options for adding, editing and deleting badges -->
		<c:if test="${not empty badges }">
			<c:forEach var="entry" items="${badges}">
				<div id="panel-${entry.key}" class="panel panel-default">
					<div class="panel-heading">
						<div class="panel-title">
							Class: <span id="class-${entry.key}" data-pk="${entry.key}"
								data-toggle="manual" data-original-title="Enter New Class Name">${entry.key}</span>
							<a href="javascript://" id="pencil-${entry.key}"
								onclick=editAndPostBadgeClass(event,"${entry.key}");
								data-toggle="tooltip" data-placement="top" title="Edit class name"><span
								class="glyphicon glyphicon-edit"></span></a> 
							<a href="javascript://"
								id="plus-${entry.key}" style="float: right"
								onclick=toggleNewBadgeModal(event,"${entry.key}"); 
								data-toggle="tooltip" data-placement="top" title="Add a new badge"><span
								class="glyphicon glyphicon-plus-sign"></span></a>
						</div>
					</div>
					<div class="panel-body">
						<table id='badge-table-${entry.key}' class="table table-striped" style="margin-bottom:0px">
							<thead>
								<tr>
									<th>Badge Level</th>
									<th>Badge Name</th>
									<th>Badge Description</th>
									<th>Email Subject</th>
									<th>Email Body</th>
									<th>Social Update</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="badge" items="${entry.value }">
									<tr id="badge-row-${badge.badgeId}">
										<td class="badgeLevel">${badge.badgeLevel }</td>
										<td class="badgeName">${badge.badgeName }</td>
										<td class="badgeDesc">${badge.badgeDesc }</td>
										<td class="emailSubject">${badge.emailSubject }</td>
										<td class="emailMsg">${badge.emailMsg }</td>
										<td class="publicRecognition">${badge.publicRecognition }</td>
										<td><a href="javascript://" onclick=toggleEditBadgeModal("${entry.key}","${badge.badgeId}");
										data-toggle="tooltip" data-placement="top" title="Edit Badge">
										<span class="glyphicon glyphicon-pencil"></span></a>
										&nbsp;<a href="javascript://" class="deletePopoverAria" data-toggle="popover" data-pk="${badge.badgeId}">
										<span class="glyphicon glyphicon-remove"></span></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:forEach>
		</c:if>
		
		<!-- Trigger for creating a new badge class -->
		<a href="javascript://" id="createNewBadgeClassTrigger"
			onclick=toggleNewBadgeModal(event,"");><span
			class="glyphicon glyphicon-plus-sign"></span>&nbsp;New badge class</a>

		<!-- Include additional pluggable modals -->
		<%@include file="includes/jsp/createOrEditBadgeModal.jsp"%>
		<%@include file="includes/jsp/errorMessageModal.jsp"%>
		
		<!-- Include common scripts -->
		<%@include file="includes/scripts_common.jsp"%>
		
		<!-- Include page specific scripts -->
		<script
			src="${pageContext.request.contextPath}/resources/js/bootstrap-editable.min.js"></script>
			
		<script type="text/javascript">
			var webContextRoot = '${pageContext.request.contextPath}';
			highlightNavBarLink('configure-api-dropdown');
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/badges.js"></script>
	</div>
</body>
</html>