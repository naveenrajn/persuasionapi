<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- Include common css -->
<%@include file="includes/css_common.jsp"%>

<link href="${pageContext.request.contextPath}/resources/css/docs.min.css" rel="stylesheet" />

<title>Persuasion API</title>
</head>
<body role="document">
	<!-- Include navigation bar -->
	<div id="top"></div>
	<%@include file="includes/jsp/navbar.jsp"%>

	<div class="container" role="main">
		<div class="help"></div>
	</div>

	<div class="container bs-docs-container">
		<div class="row">
			<div class="col-md-9" role="main">
				<div class="bs-docs-section">
					<h1 id="config" class="page-header">Configuration</h1>
					<p>Configure the Persuasion API to fit your needs.</p>
					
					
					<!-- ------------[START] Configuring badges------------ -->
					<h2 id="config-badges">Badges</h2>
					<p>Learn how to create and manage user badges. Configure how you notify the users and their network of their recent achievement. Proceed to <a href="${pageContext.request.contextPath}/config/badges" target="_blank">badge configuration</a>.</p>
					
					<h4 id="config-badges-overview">Overview</h4>
					<p class="text-justify">Badges are classified into different categories called classes. Each badge class can have a set of badges, ordered in numeric levels - higher the level, higher the value. Badges needs to be grouped into different classes keeping in mind that a user can be awarded only one badge from a badge class. However, (s)he can be awarded multiple badges - one from each badge classes.</p>
					<p class="text-justify">Here is a brief description of the information you will notice to be associated with every badge:
						<ul>
							<li><em>Badge Name</em>: Name of the badge</li>
							<li><em>Badge Description</em>: A user friendly description of the badge</li>
							<li><em>Badge Level</em>: Level of the badge inside the badge class</li>
							<li><em>Email Subject</em>: Subject the email notification to be sent when the user is assigned a badge</li>
							<li><em>Email Body</em>: HTML message body of the email notification</li>
							<li><em>Social Update</em>: Text of social notification to be posted to the user's network when (s)he is awarded the badge</li>
						</ul></p>
					
					<h4 id="config-badges-new">Creating New Badges</h4>
					<!-- <p class="text-justify">A badge with a new level can be created under an existing badge class, or a new class can be created.</p> -->
					<p class="text-justify">To create a badge under an existing class, locate your badge class in the <a href="${pageContext.request.contextPath}/config/badges" target="_blank">badge configuration</a> page. And click the <span class="glyphicon glyphicon-plus-sign"></span> sign to the top right corner of the corresponding panel that contains the list of badges under the class.<!--  A new panel with open up asking for details of the new badge such as level, name and so on. Fill in the details and click Submit to create the new badge. --></p>
					<p class="text-justify">To create a new badge class, click on the <span class="text-primary"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;New badge class</span> link at the far bottom of the <a href="${pageContext.request.contextPath}/config/badges" target="_blank">badge configuration</a> page. <!-- A new panel asking for the new badge class name and details for an associated badge.  -->A badge class without a badge does not serve any purpose. Hence, a new badge also needs to be created in order for the badge class to take effect.<!--  Fill in the details and click Submit to create the badge class and the associated badge. --></p>
					<div class="bs-callout bs-callout-info">
    					<p>Remember, badges in a badge class should contain a unique badge level. An attempt to create two badges with the same level will result in an error.</p>
  					</div>
  					<p class="text-justify">Trivia: The badge levels in a class are not required to be sequentially ordered. It is allowed to create a badge class of any numerical level - for example, badges that will always take higher precedence. Remember higher the level, higher the value.
					
					<h4 id="config-badges-edit">Modifying a badge</h4>
					<p class="text-justify">To modify an existing badge, locate the badge in the <a href="${pageContext.request.contextPath}/config/badges" target="_blank">badge configuration</a> page.</p>
					
					<h4 id="config-badges-delete">Deleting a badge</h4>
					
					<h4 id="config-badges-class">Modifying badge class name</h4>
					
					<h4 id="config-badges-notif">Configuring notifications</h4>
					<!-- ------------[END] Configuring badges------------ -->

					<!-- ------------[START] Configuring rules------------ -->
					<h2 id="config-rules">Configure Rules</h2>
					<p>Configure rules.</p>
					<!-- ------------[END] Configuring rules------------ -->
				</div>
				<div class="bs-docs-section">
					<h1 id="reference" class="page-header">API Reference</h1>
					<p class="lead">------Text here-------</p>
					<h2 id="reference-report-activity">Report Activity</h2>
					<h2 id="reference-get-user-badge">Get User Badge For Class</h2>
					<h2 id="reference-get-all-badges">Get All Badges For User</h2>
					<h2 id="reference-update-attribute">Update User Attributes</h2>
					<h2 id="reference-get-user-attribute">Get User Attribute Value</h2>
					<h2 id="reference-get-social">Get User Social Notifications</h2>
				</div>
			</div>

			<div class="col-md-3" role="complementary">
				<nav class="bs-docs-sidebar hidden-print hidden-xs hidden-sm">
				<ul class="nav bs-docs-sidenav">

					<li><a href="#config">Configuration</a>
						<ul class="nav">
							<li><a href="#config-badges">Badges</a>
							<ul class="nav nav-secondary">
								<li><a href="#config-badges-overview">Overview</a></li>
								<li><a href="#config-badges-new">Creating new badges</a></li>
								<li><a href="#config-badges-edit">Modifying a badge</a></li>
								<li><a href="#config-badges-delete">Deleting a badge</a></li>
								<li><a href="#config-badges-class">Updating badge class</a></li>
								<li><a href="#config-badges-notif">Configuring notifications</a></li>
							</ul></li>
							<li><a href="#config-rules">Rules</a></li>
						</ul></li>
					<li><a href="#reference">API Reference</a>
						<ul class="nav">
							<li><a href="#reference-report-activity">Report Activity</a></li>
							<li><a href="#reference-get-user-badge">Retrieve User
									Badge</a></li>
							<li><a href="#reference-get-all-badges">Retrieve All
									User Badges</a></li>
							<li><a href="#reference-update-attribute">Update User
									Attributes</a></li>
							<li><a href="#reference-get-user-attribute">Retrieve
									User Attribute</a></li>
							<li><a href="#reference-get-social">Retrieve Social
									Notifications</a></li>
						</ul></li>


				</ul>
				<a class="back-to-top" href="#top"> Back to top </a> </nav>
			</div>
		</div>
	</div>



	<!-- Include common scripts -->
	<%@include file="includes/scripts_common.jsp"%>

	<script
		src="${pageContext.request.contextPath}/resources/js/docs.min.js"></script>

	<script type="text/javascript">
		highlightNavBarLink('home');
	</script>
</body>
</html>