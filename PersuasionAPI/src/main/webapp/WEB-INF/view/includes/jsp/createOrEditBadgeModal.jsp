<!-- Modal for creating a new badge or editing an existing badge -->
<div class="modal fade" id="createNewBadgeModal" tabindex="-1"
	role="dialog" aria-labelledby="createNewBadgeLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">


			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">Create/Edit Badge</h4>
			</div>


			<form id="createNewBadgeModalForm" name="createNewBadgeModalForm">
				<div class="modal-body">
					<div class="alert alert-danger collapse" role="alert"
						id="badgeCreationErrorDivision">
						<span>
							<p id="badgeCreationErrorMessage"></p>
						</span>
					</div>
					<input type="hidden" class="form-control" id="tablePanelKey">
					<input type="hidden" class="form-control" id="editBadgeId">
					<div class="form-group input-group">
						<span class="input-group-addon" id="newBadgeClassLabel">Badge
							Class</span> <input type="text" class="form-control" id="newBadgeClass"
							placeholder="Badge Class" aria-describedby="newBadgeClassLabel" title="Badge Class">
					</div>
					<div class="form-group input-group">
						<span class="input-group-addon" id="newBadgeLevelLabel">Badge
							Level</span> <input type="number" class="form-control"
							id="newBadgeLevel" placeholder="Badge Level" title="Badge Level"
							aria-describedby="newBadgeLevelLabel">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="newBadgeName"
							placeholder="Badge Name" title="Badge Name">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="newBadgeDesc"
							placeholder="Badge Description" title="Badge Description">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="newBadgeEmailSubject"
							placeholder="Email Message Subject" title="Email Subject">
					</div>
					<div class="form-group">
						<textarea class="form-control" id="newBadgeEmailBody"
							placeholder="HTML Email Message Body" title="Email Body"></textarea>
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="newBadgeSocialUpdate"
							placeholder="Social Feed Update" title="Social Update">
					</div>
				</div>

				<div class="modal-footer">
					<!-- footer area with buttons for a continued dialog -->
					<button type="button" class="btn btn-primary"
						onclick="createNewBadgeForClass();">Submit</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</form>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->