<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

<!-- Include common css -->
<%@include file="includes/css_common.jsp"%>

<title>Configure Rules</title>
</head>
<body role="document">

	<!-- Include navigation bar -->
	<%@include file="includes/jsp/navbar.jsp"%>

	<div class="container theme-showcase" role="main">
		
		<div class="alert alert-danger collapse" role="alert"
			id="overallPageErrorDivision">
			<span>
				<p id="overallPageErrorMessage"></p>
			</span>
		</div>
	
		<div class="page-header">
			<h1>Rules</h1>
		</div>
		
		<img src="${pageContext.request.contextPath}/resources/img/loading.gif" 
			id="loading-indicator" class="aria-hidden" />

		<div class="container row">
			<button class="btn btn-xs btn-primary" id="rule-details-toggle-button" 
			style="float: left;" title="Show/hide rule details for all rules">
				+/- all rule details</button>
			
			<a href="javascript://" id="add-new-top-level-rule-link" style="float: right;"
				title="Add a new top level rule to the hierarchy">
				<span class="glyphicon glyphicon-plus-sign"></span>&nbsp;Add new parent rule
			</a>
		</div>
			
		<div id="rules-table" class="container">
			<div class="table-header row">
				<div class="col-lg-1">Level</div>
				<div class="col-lg-3">Rule Name</div>
				<div class="col-lg-7">Rule Description</div>
				<div class="col-lg-1">Actions</div>
			</div>
			<div class="single-rule-container aria-template" id="rule-row-"
				draggable="true" ondragstart="ruleRowDragStart(event)"
				ondrop="ruleRowDrop(event)" ondragover="allowRuleRowDrop(event)">
				<div class="row rule-row">
					<div class="col-lg-1 ruleLevel"></div>
					<div class="col-lg-3 ruleName rule-row-clickable" title="Click to show/hide rule details. Drag/drop to modify rule hierarchy"
					data-toggle="tooltip" data-placement="top" data-container="body"></div>
					<div class="col-lg-7 ruleDesc rule-row-clickable" title="Click to show/hide rule details. Drag/drop to modify rule hierarchy"
					data-toggle="tooltip" data-placement="top" data-container="body"></div>
					<div class="col-lg-1">
						<a href="javascript://" class="edit-rule-icon"
						data-toggle="tooltip" data-placement="top" data-container="body" title="Edit Rule">
							<span class="glyphicon glyphicon-pencil"></span></a>
						<a href="javascript://" class="deletePopoverAria" data-toggle="popover">
							<span class="glyphicon glyphicon-remove"></span></a>
						<a href="javascript://" class="add-child-rule-icon"
						data-toggle="tooltip" data-placement="top" data-container="body" title="Add child rule">
							<span class="glyphicon glyphicon-plus-sign"></span>
						</a>
					</div>
				</div>
				<div class="rule-details-row small aria-hidden">
					<div class="row single-rule-detail-row aria-template">
						<div class="col-lg-1"></div>
						<div class="col-lg-2 ruleAttributeName"></div>
						<div class="col-lg-8 ruleAttributeValue"></div>
						<div class="col-lg-1"></div>
					</div>
				</div>
				<div class="child-container"></div>
			</div>
		</div>
	</div>

	<!-- Include additional pluggable modals -->
	<%@include file="includes/jsp/createOrEditRuleModal.jsp"%>
	<%@include file="includes/jsp/errorMessageModal.jsp"%>

	<!-- Include common scripts -->
	<%@include file="includes/scripts_common.jsp"%>

	<script type="text/javascript">
		var webContextRoot = '${pageContext.request.contextPath}';
		highlightNavBarLink('configure-api-dropdown');
		
		$(document).ready(function() {
			$("#overallPageErrorMessage").text('');
			$("#overallPageErrorDivision").hide();
			loadAndPopulateRules()
			loadBadgesList();
			loadRuleComparatorList();
		});
		
		$('#createOrEditRuleModal').on('hidden.bs.modal', function (e) {
			clearModal();
		});
		$('#errorMessageModal').on('hidden.bs.modal', function (e) {
			clearModal();
		});
		function clearModal() {
			$("#createOrEditRuleModalForm").get(0).reset();
			$("#createOrEditRuleErrorMessage").text('');
			$("#createOrEditRuleErrorDivision").hide();
			$(".jmsQueues.aria-clone").remove();
			$(".modalRuleComparisons.aria-clone").remove();
			$("#errorMessageModalContent").text('');
		};
		
		$('#rule-details-toggle-button').click(function() {
			if($(".rule-details-row").is(":visible")) {
				$(".rule-details-row").hide("fast");
				$(".rule-row").removeClass("rule-details-open");
			} else {
				$(".rule-details-row").show("fast");
				$(".rule-row").addClass("rule-details-open");
			}
		});
		
		$('#add-new-top-level-rule-link').click(function() {
			toggleAddChildRuleModal("");
		});
		
		$(document).on('click', '.rule-row-clickable', function(event) {
			event.preventDefault();
			
			var ruleContainer = $(this).parents(".single-rule-container:first");
			var ruleRow = $(this).parents(".rule-row:first");
			ruleRow.toggleClass("rule-details-open");
			var tableDetailRowSelector = "#rule-detail-row-" + ruleContainer.data('pk');
			$(tableDetailRowSelector).toggle("fast");
		});
		
		$(document).on('click', '.edit-rule-icon', function(event) {
			event.preventDefault();
			
			var ruleContainer = $(this).parents(".single-rule-container:first");
			var ruleId = ruleContainer.data('pk'); //read the ruleId
			
			//call service to get most current details of the rule
			var dataObject = {
				'data': ruleId
			};
			$.ajax({
				'type': 'POST',
				'url': webContextRoot + '/rules/getRuleById',
				'contentType': 'application/json',
				'data': JSON.stringify(dataObject),
				'dataType': 'json',
				'success': function(data) {
					if(data.responseType=='SUCCESS') {
						toggleEditRuleModal(data.data);
					} else if(data.responseType=='FAILURE') {
						$("#errorMessageModalContent").text('Failed to retrive rule from server for editing. Error: ' + data.errorMessage);
						$("#errorMessageModal").modal('show');
					}
				},
				'error': function(xhr, ajaxOptions, thrownError) {
					$("#errorMessageModalContent").text('An unexpected error occured. Failed to retrive rule from server for editing. Server status: ' + xhr.status);
					$("#errorMessageModal").modal('show');
				}
			});
		});
		
		function prepareTooltipsAndPopovers() {
			$('[data-toggle="tooltip"]').tooltip();
			$(".deletePopoverAria").each(function() {
				$(this).popover({
					content: function() {
						$(this).tooltip('hide');
						$('.deletePopoverAria').not(this).popover('hide');
						var ruleContainer = $(this).parents(".single-rule-container:first");
						var ruleId = ruleContainer.data('pk'); //read the ruleId
						return prepareDeletePopoverHTML(ruleId);
					},
					html: true,
					placement: 'top',
					title: 'Are you sure?'
				});
				$(this).tooltip({    
					placement: 'top',
					title: 'Delete rule',
					container: 'body'
				});
			});
		};
		
		$(document).on('click', '.add-child-rule-icon', function(event) {
			event.preventDefault();
			var ruleContainer = $(this).parents(".single-rule-container:first");
			var ruleId = ruleContainer.data('pk'); //read the ruleId
			toggleAddChildRuleModal(ruleId);
		});
		
		$(document).on('click', '.remove-rule-comparison-button', function() {
			removeRuleComparisonFromModal(this);
		});
		
		$(document).on('click', '.add-jms-queue-link', function(e) {
	       e.preventDefault();
	
	       var controlForm = $('#jmsQueueListGroup'),
	           currentEntry = $(controlForm).find('.jmsQueues.aria-template:first'),
	           newEntry = $(currentEntry.clone()).appendTo(controlForm);
	
	       newEntry.find('input').val('');
	       newEntry.removeClass("aria-template");
	       newEntry.removeClass("aria-clone");
		});
		
		$(document).on('click', '.remove-jms-queue-icon', function(e) {
			e.preventDefault();
			$(this).parents('.jmsQueues:first').remove();
		});
		
		//Triggers to automatically close the popovers if cliked outside of them
		$(document).click(function(e) {
			if ($(e.target).data('toggle') !== 'popover'
				&& $(e.target).parents('[data-toggle="popover"]').length === 0
				&& $(e.target).parents('.popover.in').length === 0) { 
				$('[data-toggle="popover"]').popover('hide');
			}
		});
		
		function loadAndPopulateRules() {
			$.ajax({
				'type': 'GET',
				'url': webContextRoot + '/rules/getAllRules',
				'dataType': 'json',
				'success': function(data) {
					if(data.responseType=='SUCCESS') {
						populateRules(data.data);
					} else if(data.responseType=='FAILURE') {
						$("#overallPageErrorMessage").text(data.errorMessage);
						$("#overallPageErrorDivision").show();
					}
				},
				'error': function(xhr, ajaxOptions, thrownError) {
					$("#overallPageErrorMessage").text('An unexpected error occured. Server status: ' + xhr.status);
					$("#overallPageErrorDivision").show();
				}
			});
		};
		
		function loadAndPopulateChildRules(parentRuleId) {
			var dataObject = {
				data: parentRuleId
			};
			$.ajax({
				'type': 'POST',
				'url': webContextRoot + '/rules/getChildRulesForRule',
				'contentType': 'application/json',
				'data': JSON.stringify(dataObject),
				'dataType': 'json',
				'success': function(data) {
					if(data.responseType=='SUCCESS') {
						populateRules(data.data);
					} else if(data.responseType=='FAILURE') {
						$("#overallPageErrorMessage").text(data.errorMessage);
						$("#overallPageErrorDivision").show();
					}
				},
				'error': function(xhr, ajaxOptions, thrownError) {
					$("#overallPageErrorMessage").text('An unexpected error occured. Server status: ' + xhr.status);
					$("#overallPageErrorDivision").show();
				}
			});
		};
		
		function populateRules(rulesList) {
			$.each(rulesList, function(index, rule) {
				displaySingleRule(rule);
				loadAndPopulateChildRules(rule.ruleId);
			});
		};
		
		function displaySingleRule(rule) {
			var rulesTable = $("#rules-table"),
				ruleRowContainerTemplate = rulesTable.find(".single-rule-container.aria-template:first");
			
			var parentContainer = rulesTable;
			var level = 1;
			if(rule.parentRuleId!=null && rule.parentRuleId!='') {
				var parentRuleRow = $("#rule-row-" + rule.parentRuleId);
				if(parentRuleRow.length==0) { //parent container does not exist. show error
					$("#errorMessageModalContent").text("Something seems to have changed in the meantime."
							+ ". Please refresh the page and try again");
					$("#errorMessageModal").modal('show');
				}
				parentContainer = parentRuleRow.find(".child-container:first");
				level = parentRuleRow.data('level') + 1;
			}
			
			var currentRuleRowId = "rule-row-" + rule.ruleId;
			var ruleRow = $("#"+currentRuleRowId);
			if(ruleRow.length>0) { //rule row already exists
				var currentParentRuleId = ruleRow.parents(".single-rule-container:first").data('pk');
				if(rule.parentRuleId!=currentParentRuleId) { //parent changed. move under right parent
					ruleRow.detach();
					parentContainer.append(ruleRow);
				} //else, rule is in right place. do nothing
			} else { //create new row
				ruleRow = $(ruleRowContainerTemplate.clone()).appendTo(parentContainer);
			}
			
			ruleRow.attr("id", currentRuleRowId);
			ruleRow.data("pk", rule.ruleId);
			ruleRow.data("level", level);
			
			var spacesIndent = '';
			for(i=1; i<level; i++) {
				spacesIndent += '&nbsp;&nbsp;';
			}
			ruleRow.find(".ruleLevel:first").html(spacesIndent + '|');
			
			var processedRuleName = (rule.ruleName!='') ? rule.ruleName : '&nbsp;';
			ruleRow.find(".ruleName:first").html(processedRuleName);
			
			var processedRuleDesc = (rule.ruleDesc!='') ? rule.ruleDesc : '&nbsp;';
			ruleRow.find(".ruleDesc:first").html(processedRuleDesc);
			
			ruleRow.removeClass("aria-template");
			ruleRow.addClass("aria-clone");
			
			var ruleDetailsRow = ruleRow.find(".rule-details-row:first");
			ruleDetailsRow.find(".single-rule-detail-row.aria-clone").remove(); //remove rule details to populate with new data
			
			ruleDetailsRow.attr("id","rule-detail-row-" + rule.ruleId);
			ruleDetailsRow.data("badgeId", rule.badgeId);
			
			addSingleDetailsRow(ruleDetailsRow, 'badgeClass', 'Badge Class', rule.badgeClass);
			addSingleDetailsRow(ruleDetailsRow, 'badgeName', 'Badge Name', rule.badgeName);
			addSingleDetailsRow(ruleDetailsRow, 'badgeDesc', 'Badge Description', rule.badgeDescription);
			addSingleDetailsRow(ruleDetailsRow, 'emailSubject', 'Email Subject', rule.emailSubject);
			addSingleDetailsRow(ruleDetailsRow, 'emailMsg', 'Email Body', rule.emailMsg);
			addSingleDetailsRow(ruleDetailsRow, 'socialUpdate', 'Social Update', rule.socialUpdate);
			var notifyAlways = (rule.notifyAlways) ? "Yes":"No";
			addSingleDetailsRow(ruleDetailsRow, 'notifyAlways', 'Notify Always?', notifyAlways);
			var customJMSQueues = prepareCustomJMSQueueList(rule.ruleQueueMappings);
			addSingleDetailsRow(ruleDetailsRow, 'customJMSQueues', 'Custom JMS Queue(s)', customJMSQueues);
			var ruleComparisonsTable = prepareRuleComparisonsTable(rule.ruleComparisons);
			addSingleDetailsRow(ruleDetailsRow, 'ruleComparisons', 'Rule Comparison(s)', ruleComparisonsTable)
			
			prepareTooltipsAndPopovers();
		};
		
		function addSingleDetailsRow(ruleDetailsRow, fieldName, fieldDesc, fieldValue) {
			if (fieldValue != null && fieldValue.toString() != '') {
				var singleRuleDetailRowTemplate =
					ruleDetailsRow.find(".single-rule-detail-row.aria-template:first");
				
				var newRuleDetailRow = $(singleRuleDetailRowTemplate.clone()).appendTo(ruleDetailsRow);
				newRuleDetailRow.find(".ruleAttributeName:first").text(fieldDesc);
				newRuleDetailRow.find(".ruleAttributeValue:first").html(fieldValue.toString());
				newRuleDetailRow.find(".ruleAttributeValue:first").addClass(fieldName);
				
				newRuleDetailRow.removeClass("aria-template");
				newRuleDetailRow.addClass("aria-clone");
			}
		};
		
		function prepareRuleComparisonsTable(ruleComparisons, ruleId) {
			if(ruleComparisons==null || ruleComparisons.length==0) {
				return "None";
			}
			
			var tableHTML = '<table id="rule-comparisons-table-"' + ruleId + ' class="table table-bordered table-striped" style="width: auto;">';
			tableHTML += '<thead><tr>'
				+ '<th>Activity/Attribute</th>'
				+ '<th>Activity/Attribute Name</th>'
				+ '<th>Comparator</th>'
				+ '<th>Comparison Value</th>'
				+ '</tr></thead>';
			tableHTML += '<tbody>';
			$.each(ruleComparisons, function(index, comparison) {
				tableHTML += '<tr data-pk="' + comparison.ruleComparisonId + '">'
					+ '<td>' + comparison.featureType + '</td>'
					+ '<td>' + comparison.activityOrAttributeName + '</td>'
					+ '<td>' + comparison.ruleComparator + '</td>'
					+ '<td>' + comparison.value + '</td>'
					+ '</tr>';
			});
			tableHTML += '</tbody></table>';
			return tableHTML;
		};
		
		function prepareCustomJMSQueueList(ruleQueueMappings) {
			var htmlList = '';
			$.each(ruleQueueMappings, function(index, queueName) {
				htmlList += '<li>' + queueName + '</li>';
			});
			if(htmlList != '') return '<ul style="list-style-type: none;padding:0; margin:0;">' + htmlList + '</ul>';
			return htmlList;
		};
		
		function defineRowClickActions() {
			$(".rule-row-clickable").click(function() {
				var ruleRow = $(this).parents(".rule-row:first");
				var tableDetailRow = "#rule-detail-row-" + ruleRow.data('pk');
				$(tableDetailRow).toggle();
			});
		};
		
		function loadBadgesList() {
			$.ajax({
				'type': 'GET',
				'url': webContextRoot + '/badges/getAllBadges',
				'dataType': 'json',
				'success': function(data) {
					if(data.responseType=='SUCCESS') {
						var badgesList = data.data;
						buildBadgesOptionHTML(badgesList);
					} else if(data.responseType=='FAILURE') {
						/* $("#overallPageErrorMessage").text(data.errorMessage);
						$("#overallPageErrorDivision").show(); */
						//Handle appropriately
					}
				},
				'error': function(xhr, ajaxOptions, thrownError) {
					/* $("#overallPageErrorMessage").text('An unexpected error occured. Server status: ' + xhr.status);
					$("#overallPageErrorDivision").show(); */
					//Handle appropriately
				}
			});
		};
		
		function buildBadgesOptionHTML(badgesList) {
			var badgesOptionHTML = '<option value="">---None---</option>';
			$.each(badgesList, function(key, listOfBadgesInClass) {
				badgesOptionHTML += '<optgroup label="Badge Class: ' + key + '">'
				$.each(listOfBadgesInClass, function(index, badge) {
					badgesOptionHTML += '<option value=' + badge.badgeId + '>'
						+ badge.badgeName + '</option>';
				});
				badgesOptionHTML += '</optgroup>';
			});
			$('#newBadgeAssignmentSelect').html(badgesOptionHTML);
		};
		
		function loadRuleComparatorList() {
			$.ajax({
				'type': 'GET',
				'url': webContextRoot + '/rules/getAllRuleComparators',
				'dataType': 'json',
				'success': function(data) {
					if(data.responseType=='SUCCESS') {
						comparatorList = data.data;
						buildRuleComparatorOptionsHTML(comparatorList);
					} else if(data.responseType=='FAILURE') {
						/* $("#overallPageErrorMessage").text(data.errorMessage);
						$("#overallPageErrorDivision").show(); */
						//Handle appropriately
					}
				},
				'error': function(xhr, ajaxOptions, thrownError) {
					/* $("#overallPageErrorMessage").text('An unexpected error occured. Server status: ' + xhr.status);
					$("#overallPageErrorDivision").show(); */
					//Handle appropriately
				}
			});
		};
		
		function buildRuleComparatorOptionsHTML(comparatorList) {
			var comparatorOptionHTML = '<option value="">---None---</option>';
			$.each(comparatorList, function(index, ruleComparator) {
				comparatorOptionHTML += '<option>' + ruleComparator + '</option>';
			});
			$(".editRuleComparator").html(comparatorOptionHTML);
		};
		
		//Set variables and display the modal for editing an existing rule
		function toggleEditRuleModal(rule) {
			$('#editRuleId').val(rule.ruleId);
			$('#oldParentRuleId').val(rule.parentRuleId);
			
			if(rule.parentRuleId!=null && rule.parentRuleId!='' &&
					$("#rule-row-"+rule.parentRuleId).length<=0) {
				$("#errorMessageModalContent").text("Something doesn't seem right. Please refresh the page and try again.");
				$("#errorMessageModal").modal('show');
				return;
			}
			
			var currentParentName = $("#rule-row-"+rule.parentRuleId).find(".ruleName:first").text();
			currentParentName = (currentParentName=='') ? 'None' : currentParentName;
			$('#currentParentRuleValue').text(currentParentName);
			var parentOptions = preparePossibleParentChoices(rule.ruleId);
			$('#newParentAssignmentSelect').html(parentOptions);
			$('#newParentAssignmentSelect').val(rule.parentRuleId);
			
			$('#ruleName').val(rule.ruleName);
			$('#ruleDesc').val(rule.ruleDesc);
			
			var currentBadge = '';
			if(rule.badgeClass==null || rule.badgeName==null) {
				currentBadge = "None";
			} else {
				currentBadge = rule.badgeClass + ": " + rule.badgeName;
			}
			$('#currentBadgeAssignmentValue').text(currentBadge);
			if(rule.badgeId==null || rule.badgeId=='') {
				$('#newBadgeAssignmentSelect').val('');
			} else {
				$('#newBadgeAssignmentSelect').val(rule.badgeId);
			}
			$('#ruleEmailSubject').val(rule.emailSubject);
			$('#ruleEmailBody').val(rule.emailMsg);
			$('#ruleSocialUpdate').val(rule.socialUpdate);
			$('#ruleNotifyAlways').prop("checked", rule.notifyAlways);
			$.each(rule.ruleQueueMappings, function(index, queueName) {
				var controlForm = $('#jmsQueueListGroup'),
					currentEntry = $(controlForm).find('.jmsQueues.aria-template:first'),
					newEntry = $(currentEntry.clone()).appendTo(controlForm);
		
					newEntry.find('input').val(queueName);
					newEntry.removeClass("aria-template");
					newEntry.removeClass("aria-clone");
			});
			$.each(rule.ruleComparisons, function(index, ruleComparison) {
				populateRuleComparisonInModal(ruleComparison);
			});
			
			$('#createOrEditRuleModal').modal('show');
		};
		
		function populateRuleComparisonInModal(ruleComparison) {
			var newEntry = addNewRuleComparisonRowToModal();
	
			newEntry.find('input.editRuleComparisonId').val(ruleComparison.ruleComparisonId);
			newEntry.find('select.editFeatureType').val(ruleComparison.featureType);
			newEntry.find('input.editActivityOrAttributeName').val(ruleComparison.activityOrAttributeName);
			newEntry.find('select.editRuleComparator').val(ruleComparison.ruleComparator);
			newEntry.find('input.editRuleComparisonValue').val(ruleComparison.value);
		};
		
		function addNewRuleComparisonRowToModal() {
			var controlForm = $('#modalRuleComparisonGroup'),
	           currentEntry = $('.modalRuleComparisons.aria-template:first'),
	           newEntry = $(currentEntry.clone()).appendTo(controlForm);
			newEntry.removeClass('aria-template');
			newEntry.addClass('aria-clone');
			newEntry.addClass('aria-active');
			return newEntry;
		}
		
		function removeRuleComparisonFromModal(element) {
			var comparisonToRemove = $(element).parents('.modalRuleComparisons.aria-clone:first');
			$(comparisonToRemove).remove();
		};
		
		function createOrEditRule() {
			var ruleId = $("#editRuleId").val();
			var oldParentRuleId = $("#oldParentRuleId").val();
			
			//Builing list of custom jms queue names
			var queueNames = $('.customJMSQueueName').map(function() {
				return $(this).val();
			}).get();
			
			//Building list of rule comparisons
			var editRuleComparisons = $('#modalRuleComparisonGroup .modalRuleComparisons.aria-clone').map(function() {
				var ruleComparison = {};
				ruleComparison.ruleComparisonId = $(this).find('input.editRuleComparisonId').val();
				ruleComparison.featureType = $(this).find('select.editFeatureType').val();
				ruleComparison.activityOrAttributeName = $(this).find('input.editActivityOrAttributeName').val();
				ruleComparison.ruleComparator = $(this).find('select.editRuleComparator').val();
				ruleComparison.value = $(this).find('input.editRuleComparisonValue').val();
				return ruleComparison;
			}).get();
			
			var dataContent = {
					ruleId: $("#editRuleId").val(),
					parentRuleId: $("#newParentAssignmentSelect").val(),
					ruleName: $("#ruleName").val(),
					ruleDesc: $("#ruleDesc").val(),
					badgeId: $("#newBadgeAssignmentSelect").val(),
					emailSubject: $("#ruleEmailSubject").val(),
					emailMsg: $("#ruleEmailBody").val(),
					socialUpdate: $("#ruleSocialUpdate").val(),
					notifyAlways: $("#ruleNotifyAlways").prop("checked"),
					ruleQueueMappings: queueNames,
					ruleComparisons: editRuleComparisons
			};
			var dataObject = {
					data: dataContent
			};
			
			$.ajax({
				'type': 'POST',
				'url': webContextRoot + '/rules/editRule',
				'contentType': 'application/json',
				'data': JSON.stringify(dataObject),
				'dataType': 'json',
				'success': function(data) {
					if(data.responseType=='SUCCESS') {
						var rule = data.data;
						displaySingleRule(rule);
						$('#createOrEditRuleModal').modal('hide');
					} else if(data.responseType=='FAILURE') {
						$("#createOrEditRuleErrorMessage").text(data.errorMessage);
						$("#createOrEditRuleErrorDivision").show();
					}
				},
				'error': function(xhr, ajaxOptions, thrownError) {
					$("#createOrEditRuleErrorMessage").text('An unexpected error occured. Server status: ' + xhr.status);
					$("#createOrEditRuleErrorDivision").show();
				}
			});
		};
		
		function toggleAddChildRuleModal(parentRuleId) {
			$('#editRuleId').val("");
			$('#oldParentRuleId').val("");
			$('#currentParentRuleValue').text("None");
			$('#currentBadgeAssignmentValue').text("None");
			var parentOptions = preparePossibleParentChoices('');
			$('#newParentAssignmentSelect').html(parentOptions);
			$('#newParentAssignmentSelect').val(parentRuleId);
			$('#createOrEditRuleModal').modal('show');
		};
		
		function preparePossibleParentChoices(ruleId) {
			var baseIgnoreLevel = -1;
			var optionHTML = '<option value="">---None---</option>';
			$('.single-rule-container.aria-clone').each(function() {
				var currentRuleId = $(this).data('pk');
				if(baseIgnoreLevel == -1) {
					if(currentRuleId == ruleId) {
						baseIgnoreLevel = $(this).data('level');
						//ignore rule
					} else {
						var currentRuleName = $(this).find('.ruleLevel:first').text();
						currentRuleName += $(this).find('.ruleName:first').text();
						optionHTML += '<option value="' + currentRuleId + '">' + currentRuleName + '</option>';
					}
				} else {
					var currentLevel = $(this).data('level');
					if(currentLevel<=baseIgnoreLevel) {
						baseIgnoreLevel = -1;
						var currentRuleName = $(this).find('.ruleLevel:first').text();
						currentRuleName += $(this).find('.ruleName:first').text();
						optionHTML += '<option value="' + currentRuleId + '">' + currentRuleName + '</option>';
					} else {
						//ignore rule
					}
				}
			});
			return optionHTML;
		};
		
		/*==============[START] Functions for deleting existing rule==============*/
		//Prepare the delete confirmation popover content with appropriate rule ID for each icon
		function prepareDeletePopoverHTML(ruleId) {
			var htmlContent = '<h6>Remember, this will delete the rule and all subrules. However, the badge assignments and notifications already processed will remain. Continue?</h6>'
				+ '<a class="btn btn-xs btn-primary" href="javascript://"'
				+ ' onclick="deleteRule(' + ruleId + ');">'
				+ '<span class="glyphicon glyphicon-ok-sign"></span>Yes</a>';
			htmlContent += '&nbsp;<a class="btn btn-xs btn-default" href="javascript://"'
				+ ' onclick="closeDeleteConfirmationPopovers();">'
				+ '<span class="glyphicon glyphicon-remove-sign"></span>No</a>';
			return htmlContent;
		};

		//Call the service to delete the rule
		function deleteRule(ruleId) {
			var dataObject = {
					'data': ruleId	
			};
			$.ajax({
				'type': 'POST',
				'url': webContextRoot + '/rules/delete',
				'contentType': 'application/json',
				'data': JSON.stringify(dataObject),
				'dataType': 'json',
				'success': function(data) {
					if(data.responseType=='SUCCESS') {
						var ruleContainer = $('#rule-row-' + ruleId);
						ruleContainer.remove();
					} else if(data.responseType=='FAILURE') {
						$("#errorMessageModalContent").text('Failed to delete rule. Error: ' + data.errorMessage);
						$("#errorMessageModal").modal('show');
					}
				},
				'error': function(xhr, ajaxOptions, thrownError) {
					$("#errorMessageModalContent").text('An unexpected error occured. Server status: ' + xhr.status);
					$("#errorMessageModal").modal('show');
				}
			});
			closeDeleteConfirmationPopovers();
		};

		//Close any open delete confirmation popovers
		function closeDeleteConfirmationPopovers() {
			$('.deletePopoverAria').popover('hide');
		};
		/*==============[END] Functions for deleting existing rule==============*/
		
		function ruleRowDragStart(event) {
			ruleId = $(event.target).data('pk');
			event.dataTransfer.setData("ruleId", ruleId);
		};
		
		function allowRuleRowDrop(event) {
		    event.preventDefault();
		};

		function ruleRowDrop(event) {
		    event.preventDefault();
		    event.stopPropagation();
		    
		    var droppedRuleId = event.dataTransfer.getData("ruleId");
		    var newParentRuleId;
		    if($(event.target).hasClass("single-rule-container")) {
		    	newParentRuleId = $(event.target).data('pk');
		    } else {
		    	newParentRuleId = $(event.target).parents(".single-rule-container:first").data('pk');
		    }
		    updateParentRule(droppedRuleId, newParentRuleId);
		};
		
		function updateParentRule(ruleId, parentRuleId) {
			if(!isValidParent(ruleId, parentRuleId)) {
				return;
			}
			var dataObject = {
				data: {
					'ruleId': ruleId,
					'parentRuleId': parentRuleId
				}	
			};
			$.ajax({
				'type': 'POST',
				'url': webContextRoot + '/rules/updateParent',
				'contentType': 'application/json',
				'data': JSON.stringify(dataObject),
				'dataType': 'json',
				'success': function(data) {
					if(data.responseType=='SUCCESS') {
						var parentRuleRow = $("#rule-row-" + parentRuleId);
						var parentContainer = parentRuleRow.find(".child-container:first");
						
						var childRuleDivision = $("#rule-row-"+ruleId);
						var level = parentRuleRow.data('level')+1;
						var spacesIndent = '';
						for(i=1; i<level; i++) {
							spacesIndent += '&nbsp;&nbsp;';
						}
						childRuleDivision.data('level', level);
						childRuleDivision.find(".ruleLevel:first").html(spacesIndent + '|');
						
						childRuleDivision.detach();
						parentContainer.append(childRuleDivision);
					} else if(data.responseType=='FAILURE') {
						$("#errorMessageModalContent").text('Failed to update rule hierarchy. Error: ' + data.errorMessage);
						$("#errorMessageModal").modal('show');
					}
				},
				'error': function(xhr, ajaxOptions, thrownError) {
					$("#errorMessageModalContent").text('An unexpected error occured. Server status: ' + xhr.status);
					$("#errorMessageModal").modal('show');
				}
			});
		};
		
		function isValidParent(ruleId, parentRuleId) {
			var baseIgnoreLevel = -1;
			var isValid = false;
			$('.single-rule-container.aria-clone').each(function() {
				var currentRuleId = $(this).data('pk');
				if(baseIgnoreLevel == -1) {
					if(currentRuleId == ruleId) {
						baseIgnoreLevel = $(this).data('level'); //set to ignore this and all children
					} else {
						if(parentRuleId == currentRuleId) isValid = true;
					}
				} else {
					var currentLevel = $(this).data('level');
					if(currentLevel<=baseIgnoreLevel) {
						if(parentRuleId == currentRuleId) isValid = true;
					} else {
						//currently processing children of ruleId, ignore rule
					}
				}
			});
			return isValid;
		};
	</script>
</body>
</html>