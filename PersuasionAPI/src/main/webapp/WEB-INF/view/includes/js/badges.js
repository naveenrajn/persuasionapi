/*==============[START] Functions for updating badge class==============*/
function editAndPostBadgeClass(e, badgeClass) {
	var className = '#class-' + badgeClass;
	$(className).editable({
		url: webContextRoot + '/badges/updateBadgeClass',
		ajaxOptions: {contentType: 'application/json', dataType: 'json' },
		params: function(params) {
			params.pk = $(className).data('pk'); //update actual pk
			return JSON.stringify(params);
		},
		success: function(response, newValue) {
			if(response.responseType=='SUCCESS') $(className).data('pk', newValue);
			if(response.responseType=='FAILURE') return response.errorMessage;
		}
	});
	e.stopPropagation();
	$(className).editable('toggle');
};
/*==============[END] Functions for updating badge class==============*/

/*==============[START] Functions for creating and editing badge==============*/
//Display the modal for creating new badge for existing badge class
function toggleNewBadgeModal(e, tablePanelKey) {
	var className = '#class-' + tablePanelKey;
	$('#tablePanelKey').val(tablePanelKey);
	$('#newBadgeClass').val($(className).data('pk'));
	if ($('#newBadgeClass').val() != '')
		$("#newBadgeClass").attr("disabled", "disabled");
	else
		$("#newBadgeClass").removeAttr("disabled");
	$('#editBadgeId').val('');
	$('#createNewBadgeModal').modal('show');
};

//Create or edit badge using data from the create/edit modal
function createNewBadgeForClass() {
	var tablePanelKey = $('#tablePanelKey').val();

	var isNewBadgeClass = false;
	if(tablePanelKey == '') isNewBadgeClass = true;
	
	var dataObject = {
			newBadgeClass: isNewBadgeClass,
			badgeId: $("#editBadgeId").val(),
			badgeClass: $('#newBadgeClass').val(),
			badgeLevel: $('#newBadgeLevel').val(),
			badgeName: $('#newBadgeName').val(),
			badgeDescription: $('#newBadgeDesc').val(),
			emailSubject: $('#newBadgeEmailSubject').val(),
			emailBody: $('#newBadgeEmailBody').val(),
			socialUpdate: $('#newBadgeSocialUpdate').val()
	};

	$.ajax({
		'type': 'POST',
		'url': webContextRoot + '/badges/createOrEdit',
		'contentType': 'application/json',
		'data': JSON.stringify(dataObject),
		'dataType': 'json',
		'success': function(data) {
			if(data.responseType=='SUCCESS') {
				//add the row, close the modal
				if(isNewBadgeClass) {
					location.reload(); //reload the page to add the panel and contents
				} else {
					replaceOrAppendRowToTable(tablePanelKey, data);
					$('#createNewBadgeModal').modal('hide');
				}
			} else if(data.responseType=='FAILURE') {
				$("#badgeCreationErrorMessage").text(data.errorMessage);
				$("#badgeCreationErrorDivision").show();
			}
		},
		'error': function(xhr, ajaxOptions, thrownError) {
			$("#badgeCreationErrorMessage").text('An unexpected error occured. Server status: ' + xhr.status);
			$("#badgeCreationErrorDivision").show();
		}
	});
};

//Add a new row for a new badge or replace the row with edits for existing badge
function replaceOrAppendRowToTable(tablePanelKey, response) {
	var tableName = '#badge-table-' + tablePanelKey;

	var tableRowId = "#badge-row-" + response.data.badgeId;
	var tableRowContent = buildBadgeTableRow(tablePanelKey, response.data);
	if($(tableRowId).length > 0) {
		$(tableRowId).replaceWith(tableRowContent);
	} else {
		var tableLastRow = tableName + ' tr:last';
		$(tableLastRow).after(tableRowContent);
	}
	prepareTooltipsAndPopovers();
};

//Build the table tr new badge contents
function buildBadgeTableRow(tablePanelKey, data) {
	var tableRow = '<tr id=badge-row-' + data.badgeId + '>'
	+ '<td class="badgeLevel">' + data.badgeLevel + '</td>'
	+ '<td class="badgeName">' + data.badgeName + '</td>'
	+ '<td class="badgeDesc">' + data.badgeDesc + '</td>'
	+ '<td class="emailSubject">' + data.emailSubject + '</td>'
	+ '<td class="emailMsg">' + data.emailMsg + '</td>'
	+ '<td class="publicRecognition">' + data.publicRecognition + '</td>'
	+ '<td><a href="javascript://"' 
	+ ' onclick=toggleEditBadgeModal("' + tablePanelKey + '","' + data.badgeId + '");'
	+ ' data-toggle="tooltip" data-placement="top" title="Edit Badge">'
	+ '<span class="glyphicon glyphicon-pencil"></span></a>'
	+ '&nbsp;&nbsp;&nbsp;'
	+ '<a href="javascript://" class="deletePopoverAria" data-toggle="popover" data-pk="' + data.badgeId + '">'
	+ '<span class="glyphicon glyphicon-remove"></span></a></td>'
	+ '</tr>';
	return tableRow;
};

//Set variables and display the modal for editing an existing badge
function toggleEditBadgeModal(tablePanelKey, badgeId) {
	var className = '#class-' + tablePanelKey;
	$('#tablePanelKey').val(tablePanelKey);
	$('#newBadgeClass').val($(className).data('pk'));
	if ($('#newBadgeClass').val() != '')
		$("#newBadgeClass").attr("disabled", "disabled");
	else
		$("#newBadgeClass").removeAttr("disabled");

	var badgeRowClass = "#badge-row-" + badgeId;
	$('#editBadgeId').val(badgeId);
	$('#newBadgeLevel').val($(badgeRowClass).find('td.badgeLevel').text());
	$('#newBadgeName').val($(badgeRowClass).find('td.badgeName').text());
	$('#newBadgeDesc').val($(badgeRowClass).find('td.badgeDesc').text());
	$('#newBadgeEmailSubject').val($(badgeRowClass).find('td.emailSubject').text());
	$('#newBadgeEmailBody').val($(badgeRowClass).find('td.emailMsg').text());
	$('#newBadgeSocialUpdate').val($(badgeRowClass).find('td.publicRecognition').text());

	$('#createNewBadgeModal').modal('show');
};
/*==============[END] Functions for creating and editing badge ==============*/

/*==============[START] Functions for deleting existing badge==============*/
//Prepare the popovers for delete confirmation
function prepareTooltipsAndPopovers() {
	$('[data-toggle="tooltip"]').tooltip();
	$(".deletePopoverAria").each(function() {
		$(this).popover({
			content: function() {
				$(this).tooltip('hide');
				$('.deletePopoverAria').not(this).popover('hide');
				return prepareDeletePopoverHTML(this);
			},
			html: true,
			placement: 'top',
			title: 'Are you sure?'
		})
		$(this).tooltip({    
			placement: 'top',
			title: 'Delete Badge'
		})
	});
};

//Prepare the delete confirmation popover content with appropriate badge ID for each icon
function prepareDeletePopoverHTML(element) {
	var badgeId = $(element).data('pk');
	var htmlContent = '<h6>Remember, this will delete associated badge assignments for all users. The notifications already genereated, however, will remain. Continue?</h6>'
		+ '<a class="btn btn-xs btn-primary" href="javascript://"'
		+ ' onclick="deleteBadge(' + badgeId + ');">'
		+ '<span class="glyphicon glyphicon-ok-sign"></span>Yes</a>';
	htmlContent += '&nbsp;<a class="btn btn-xs btn-default" href="javascript://"'
		+ ' onclick="closeDeleteConfirmationPopovers();">'
		+ '<span class="glyphicon glyphicon-remove-sign"></span>No</a>';
	return htmlContent;
};

//Call the service to delete the badge
function deleteBadge(badgeId) {
	var dataObject = {
			'data': badgeId	
	};
	$.ajax({
		'type': 'POST',
		'url': webContextRoot + '/badges/delete',
		'contentType': 'application/json',
		'data': JSON.stringify(dataObject),
		'dataType': 'json',
		'success': function(data) {
			if(data.responseType=='SUCCESS') {
				removeBadgeRow(badgeId);
			} else if(data.responseType=='FAILURE') {
				$("#errorMessageModalContent").text('Failed to delete badge. Error: ' + data.errorMessage);
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

//Remove the delted badge row on delete success
function removeBadgeRow(badgeId) {
	var tableRowId = "#badge-row-" + badgeId;
	$(tableRowId).remove();
};

//Close any open delete confirmation popovers
function closeDeleteConfirmationPopovers() {
	$('.deletePopoverAria').popover('hide');
};
/*==============[END] Functions for deleting existing badge==============*/

/*==============[START] Helper functions for cleaup==============*/
//Setup startup parameters - clear modal fields
$(document).ready(function() {
	$.fn.editable.defaults.mode = 'popup';
	clearModal();
	prepareTooltipsAndPopovers();
});

//Triggers to automatically close the popovers if cliked outside of them
$('body').on('click', function(e) {
	/*if ($(e.target).data('toggle') !== 'popover'
		&& $(e.target).parents('[data-toggle="popover"]').length === 0
		&& $(e.target).parents('.popover.in').length === 0) { 
		$('[data-toggle="popover"]').popover('hide');
	}*/
	$('[data-toggle="popover"]').each(function () {
        //the 'is' for buttons that trigger popups
        //the 'has' for icons within a button that triggers a popup
        if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
            $(this).popover('hide');
        }
    });	
});

//Clear the modal fields on close
$('#createNewBadgeModal').on('hidden.bs.modal', function (e) {
	clearModal();
});
$('#errorMessageModal').on('hidden.bs.modal', function (e) {
	clearModal();
});
function clearModal() {
	$("#createNewBadgeModalForm").get(0).reset();
	$("#badgeCreationErrorMessage").text('');
	$("#badgeCreationErrorDivision").hide();
	$("#errorMessageModalContent").text('');
};
/*==============[END] Helper functions for cleanup==============*/