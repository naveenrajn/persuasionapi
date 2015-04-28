<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- Include common css -->
<%@include file="includes/css_common.jsp"%>

<title>Try PersuasionAPI: Update User Attributes</title>
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
			<h1>Update User Attributes</h1>
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
						<div class="form-group row">
							<label for="userAttributes" class="control-label col-sm-2">User Attributes:</label>
						</div>
						<div id="userAttributes">
							<div class="userAttribute aria-template form-group row col-sm-12">
								<div class="form-inline">
									<div class="form-group">
									<input type="text" class="form-control attributeName col-sm-3 input-md" placeholder="Attribute Name" title="Attribute Name">
									</div>
									<div class="form-group">
									<input type="text" class="form-control attributeValue col-sm-3 input-md" placeholder="Attribute Value" title="Attribute Value">
									</div>
									<div class="form-group">
									<a href="javascript://" class="remove-attribute-link"><span class="glyphicon glyphicon-minus-sign" title="Remove Attribute"></span></a>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group row col-sm-12">
							<a href="javascript://" class="add-attribute-link"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;Add Attribute</a>
						</div>
						<div class="form-group row col-sm-12">
							<button type="submit" class="btn btn-primary">Update Attributes</button>
						</div>
						<img src="${pageContext.request.contextPath}/resources/img/loading.gif" id="loading-indicator" class="aria-hidden" />
					</form>
				</div>
			</div>
		</div>
		
		<div class="alert alert-success aria-hidden" role="alert" id="success-response"></div>
		<div class="alert alert-danger aria-hidden" role="alert" id="failure-response"></div>
	</div>
	
	<!-- Include common scripts -->
	<%@include file="includes/scripts_common.jsp"%>
	
	<script type="text/javascript">
		var webContextRoot = '${pageContext.request.contextPath}';
		highlightNavBarLink('try-api-dropdown');
		
		$('#requestForm').submit(function(event) {
			event.preventDefault();
			
			$(".alert").hide();
			$("#loading-indicator").show();
			
			var userAttributesList = $('#userAttributes .userAttribute.aria-clone').map(function() {
				var attributeObject = {
					attributeName: $(this).find(".attributeName:first").val(),
					value: $(this).find(".attributeValue:first").val()
				};
				return attributeObject;
			}).get();
			
			var requestData = {
				userId: $("#userId").val(),
				attributes: userAttributesList
			};
			var requestObject = {
					data: requestData
			};
			
			$.ajax({
				'type': 'POST',
				'url': webContextRoot + '/user/attribute/update',
				'contentType': 'application/json',
				'data': JSON.stringify(requestObject),
				'dataType': 'json',
				'success': function(data) {
					if(data.responseType=='SUCCESS') {
						$('#success-response').html("Attributes successfully updated");
						$('#failure-response').hide();
						$('#success-response').show();
					} else if(data.responseType=='FAILURE') {
						var errorMessage = "The service returned a failure response";
						errorMessage += "<br />Error Code: " + data.errorCode;
						errorMessage += "<br />Error Message: " + data.errorMessage;
						$("#failure-response").html(errorMessage);
						$('#success-response').hide();
						$('#failure-response').show();
					}
					$("#loading-indicator").hide();
				},
				'error': function(xhr, ajaxOptions, thrownError) {
					var errorMessage = "An unexpected error occured";
					errorMessage += "<br />Server Status: " + xhr.status;
					errorMessage += "<br />Error Message: " + xhr.responseText;
					$("#failure-reponse").html(errorMessage);
					$('#success-response').hide();
					$('#failure-response').show();
					$("#loading-indicator").hide();
				}
			});
		});
		
		$(document).on('click', '.add-attribute-link', function(event) {
			event.preventDefault();
			
			var attributesContainer = $("#userAttributes");
			var templateEntry = attributesContainer.find('.userAttribute.aria-template:first');
			var newEntry = $(templateEntry.clone()).appendTo(attributesContainer);
			
			newEntry.removeClass("aria-template");
			newEntry.addClass("aria-clone");
		});
		
		$(document).on('click', '.remove-attribute-link', function(event) {
			event.preventDefault();
			
			var currentEntry = $(this).parents('.userAttribute.aria-clone:first');
			currentEntry.remove();
		});
	</script>
</body>
</html>