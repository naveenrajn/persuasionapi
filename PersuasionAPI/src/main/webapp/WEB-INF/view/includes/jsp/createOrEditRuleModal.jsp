<!-- Modal for creating a new badge or editing an existing badge -->
<div class="modal modal-wide fade" id="createOrEditRuleModal" tabindex="-1"
	role="dialog" aria-labelledby="createOrEditRuleLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">


			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">Create/Edit Rule</h4>
			</div>


			<form id="createOrEditRuleModalForm" name="createOrEditRuleModalForm">
				<div class="modal-body">
					<div class="alert alert-danger collapse" role="alert"
						id="createOrEditRuleErrorDivision">
						<span>
							<p id="createOrEditRuleErrorMessage"></p>
						</span>
					</div>
					<input type="hidden" class="form-control" id="editRuleId">
					<input type="hidden" class="form-control" id="oldParentRuleId">
					<span id="currentParentRule">Current Parent:&nbsp;
					<span id="currentParentRuleValue"></span>
					</span>
					<div class="form-group input-group">
						<span class="input-group-addon" id="newParentRuleValue">Parent Rule</span> 
						<select class="form-control" id="newParentAssignmentSelect"
							aria-describedby="newParentAssignmentLabel">
						</select>
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="ruleName"
							placeholder="Rule Name" title="Rule Name">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="ruleDesc"
							placeholder="Rule Description" title="Rule Description">
					</div>
					<span id="currentBadgeAssignment">Current Badge:&nbsp;
					<span id="currentBadgeAssignmentValue"></span>
					</span>
					<div class="form-group input-group">
						<span class="input-group-addon" id="newBadgeAssignmentLabel">Badge</span> 
						<select class="form-control" id="newBadgeAssignmentSelect"
							aria-describedby="newBadgeAssignmentLabel">
						</select>
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="ruleEmailSubject"
							placeholder="Email Message Subject" title="Email Subject">
					</div>
					<div class="form-group">
						<textarea class="form-control" id="ruleEmailBody"
							placeholder="HTML Email Message Body" title="Email Body"></textarea>
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="ruleSocialUpdate"
							placeholder="Social Feed Update" title="Social Update">
					</div>
					<div class="checkbox">
						<label>
							<input type="checkbox" id="ruleNotifyAlways"> Notify Always
						</label>
					</div>
					<label class="control-label" for="field1">Custom JMS Queues</label>
					<div class="form-group" id="jmsQueueListGroup">
						<div class="jmsQueues row no-gutter aria-template">
							<div class="col-sm-7">
							<div class="form-inline">
							<div class="form-group">
							<input class="form-control customJMSQueueName" type="text"
								placeholder="Queue name" title="Queue Name"/>
							</div>
							<div class="form-group">
								<a href="javascript://" class="remove-jms-queue-icon" type="button">
									<span class="glyphicon glyphicon-minus-sign"></span>
								</a>
							</div>
							</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<a href="javascript://" class="add-jms-queue-link"><span
							class="glyphicon glyphicon-plus-sign"></span>New JMS Queue</a>
					</div>
					<label class="control-label" for="field2">Rule Comparisons</label>
					<div id="modalRuleComparisonGroup">
						<div class="modalRuleComparisons aria-template">
							<div class="row form-group modalRuleComparison">
								<div class="col-sm-12">
									<div class="form-inline">
										<input type="hidden" class="form-control editRuleComparisonId">
										<div class="form-group">
											<select class="form-control no-gutter editFeatureType"
												aria-describedby="featureTypeLabel">
												<option value="">---None---</option>
												<option value="ACTIVITY">ACTIVITY</option>
												<option value="ATTRIBUTE">ATTRIBUTE</option>
											</select>
										</div>
										<div class="form-group">
											<input type="text" class="form-control no-gutter editActivityOrAttributeName"
												placeholder="Activity/AttributeName" title="Activity/Attribute Name">
										</div>
										<div class="form-group">
											<select class="form-control no-gutter editRuleComparator"
												aria-describedby="ruleComparatorLabel">
											</select>
										</div>
										<div class="form-group">
											<input type="text" class="form-control no-gutter editRuleComparisonValue"
												placeholder="Comparison Value" title="Comparison Value">
										</div>
										<div class="form-group">
											<a href="javascript://" class="remove-rule-comparison-button"> <span
												class="glyphicon glyphicon-minus-sign"></span></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<a href="javascript://" onclick="addNewRuleComparisonRowToModal()"><span
							class="glyphicon glyphicon-plus-sign"></span>New Rule Comparison</a>
					</div>
				</div>
				</form>
				<div class="modal-footer">
					<!-- footer area with buttons for a continued dialog -->
					<button type="button" class="btn btn-primary"
						onclick="createOrEditRule();">Submit</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->