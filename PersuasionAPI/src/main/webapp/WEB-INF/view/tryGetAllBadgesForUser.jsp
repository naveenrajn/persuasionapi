<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- Include common css -->
<%@include file="includes/css_common.jsp"%>

<title>Try PersuasionAPI: Get All Badges For User</title>
</head>
<body role="document">

	<!-- Include navigation bar -->
	<%@include file="includes/jsp/navbar.jsp"%>

	<div class="alert alert-danger collapse" role="alert"
		id="overallPageErrorDivision">
		<span>
			<p id="overallPageErrorMessage"></p>
		</span>
	</div>

	<div class="container theme-showcase" role="main">
		<div class="page-header">
			<h1>Get All Badges For User</h1>
		</div>
		<div id="request-panel" class="panel panel-default">
			<div class="panel-heading">
				<div class="panel-title">
					Request
				</div>
			</div>
			<div class="panel-body">
				<div class="col-sm-12">
					<form role="form form-horizontal" id="requestForm">
						<div class="form-group row">
							<label for="userId" class="control-label col-sm-2">User ID:</label> 
							<div class="col-sm-4"><input type="text" class="form-control input-sm" id="userId"></div>
						</div>
						<button type="submit" class="btn btn-primary">Get User Badges</button>
						<img src="${pageContext.request.contextPath}/resources/img/loading.gif" id="loading-indicator" class="aria-hidden" />
					</form>
				</div>
			</div>
		</div>
		
		<div class="alert alert-danger aria-hidden" role="alert" id="failure-response-panel"></div>
		<div id="success-response-panel" class="panel panel-default aria-hidden">
			<div class="panel-heading">
				<div class="panel-title">
					Response
				</div>
			</div>
			<div class="panel-body">
				List of badges assigned for user:
				<table class="table table-striped table-bordered table-nonfluid" id="responseTable">
					<thead><tr>
						<th>User ID</th>
						<th>Badge Class</th>
						<th>Badge Name</th>
						<th>Badge Description</th>
					</tr></thead>
					<tbody>
						<tr class="aria-template responseRowTemplate">
							<td class="userId"></td>
							<td class="badgeClass"></td>
							<td class="badgeName"></td>
							<td class="badgeDesc"></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<!-- Include common scripts -->
	<%@include file="includes/scripts_common.jsp"%>
	
	<script type="text/javascript">
		var webContextRoot = '${pageContext.request.contextPath}';
		highlightNavBarLink('try-api-dropdown');
		
		$('#requestForm').submit(function(event) {
			event.preventDefault();
			
			$(".alert").hide();
			$(".aria-clone").remove();
			$("#loading-indicator").show();
			var requestObject = {
					data: $("#userId").val()
			};
			
			$.ajax({
				'type': 'POST',
				'url': webContextRoot + '/badges/getAllBadgesForUser',
				'contentType': 'application/json',
				'data': JSON.stringify(requestObject),
				'dataType': 'json',
				'success': function(data) {
					if(data.responseType=='SUCCESS') {
						var badgeList = data.data;
						var responseTable = $('#responseTable');
						var templateRow = responseTable.find('.responseRowTemplate:first');
						$.each(badgeList, function(index, badge) {
							var newRow = $(templateRow.clone()).appendTo(responseTable);
							newRow.find('.userId').text(badge.userId);
							newRow.find('.badgeClass').text(badge.badgeClass);
							newRow.find('.badgeName').text(badge.badgeName);
							newRow.find('.badgeDesc').text(badge.badgeDesc);
							newRow.removeClass('aria-template');
							newRow.addClass('aria-clone');
						});
						$('#failure-response-panel').hide();
						$('#success-response-panel').show();
					} else if(data.responseType=='FAILURE') {
						var errorMessage = "The service returned a failure response";
						errorMessage += "<br />Error Code: " + data.errorCode;
						errorMessage += "<br />Error Message: " + data.errorMessage;
						$("#failure-response-panel").html(errorMessage);
						$('#success-response-panel').hide();
						$('#failure-response-panel').show();
					}
					$("#loading-indicator").hide();
				},
				'error': function(xhr, ajaxOptions, thrownError) {
					var errorMessage = "An unexpected error occured";
					errorMessage += "<br />Server Status: " + xhr.status;
					errorMessage += "<br />Error Message: " + xhr.responseText;
					$("#failure-reponse-panel").html(errorMessage);
					$('#success-response-panel').hide();
					$('#failure-response-panel').show();
					$("#loading-indicator").hide();
				}
			});
		});
	</script>
</body>
</html>