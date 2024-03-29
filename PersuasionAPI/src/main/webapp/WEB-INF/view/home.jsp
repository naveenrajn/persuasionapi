<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- Include common css -->
<%@include file="includes/css_common.jsp"%>

<title>Persuasion API</title>
</head>
<body role="document">
	<!-- Include navigation bar -->
	<%@include file="includes/jsp/navbar.jsp"%>

	<div class="container" role="main">
		<div class="jumbotron">
        	<h1>Persuasion API</h1>
        	<p>Create and manage configurations. Try the API to learn or test your configurations</p>
      	</div>
		
		<div class="homepage-content">
			<div class="row"><h3>Configure</h3></div>
			<div class="row">
				<a href="${pageContext.request.contextPath}/config/badges">Create/Update badges</a> and associated notification settings
			</div>
			<div class="row">
				<a href="${pageContext.request.contextPath}/config/rules">Manage rules</a> and associated actions/notifications
			</div>
			
			<div class="row"><h3>Try the API</h3></div>
			<div class="row">
				<a href="${pageContext.request.contextPath}/try/activity/report">Report an activity</a>
					performed by a user or generated by the system on his/her behalf
			</div>
			<div class="row">
				<a href="${pageContext.request.contextPath}/try/badges/getUserBadgeForClass">Retrieve 
				badge</a> assigned to a user for a particular badge class
			</div>
			<div class="row">
				<a href="${pageContext.request.contextPath}/try/badges/getAllBadgesForUser">Retrieve 
				a list of all badges</a> assigned to a user
			</div>
			<div class="row">
				<a href="${pageContext.request.contextPath}/try/user/attribute/update">Update attribute
				</a> values for a user
			</div>
			<div class="row">
				<a href="${pageContext.request.contextPath}/try/user/attribute/getUserAttributeValue">
				Retrieve value for an attribute</a> assigned to a user
			</div>
			<div class="row">
				<a href="${pageContext.request.contextPath}/try/user/social/getNotificationsAfterTime">
				Retrieve social notifications</a> generated by a user for his/her network
			</div>
		</div>
	</div>
	
	<!-- Include common scripts -->
	<%@include file="includes/scripts_common.jsp"%>
	
	<script type="text/javascript">
		highlightNavBarLink('home');
	</script>
</body>
</html>