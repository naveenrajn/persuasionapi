<nav class="navbar navbar-inverse">
	<div class="container" id="navbarContainer">
	  <div class="navbar-header">
	    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
	      <span class="sr-only">Toggle navigation</span>
	      <span class="icon-bar"></span>
	      <span class="icon-bar"></span>
	      <span class="icon-bar"></span>
	    </button>
	    <a class="navbar-brand" href="${pageContext.request.contextPath}">Persuasion API</a>
	  </div>
	  <div class="navbar-collapse collapse">
	    <ul class="nav navbar-nav">
	      <li class="home active"><a href="${pageContext.request.contextPath}">Home</a></li>
	      <li class="dropdown configure-api-dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Configure <span class="caret"></span></a>
	          <ul class="dropdown-menu" role="menu">
	            <li><a href="${pageContext.request.contextPath}/config/badges">Badges</a></li>
	      		<li><a href="${pageContext.request.contextPath}/config/rules">Rules</a></li>
	          </ul>
          </li>
	      <li class="dropdown try-api-dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Try <span class="caret"></span></a>
	          <ul class="dropdown-menu" role="menu">
	            <li><a href="${pageContext.request.contextPath}/try/activity/report">Report Activity</a></li>
	            <li><a href="${pageContext.request.contextPath}/try/badges/getUserBadgeForClass">Get User Badge For Class</a></li>
	            <li><a href="${pageContext.request.contextPath}/try/badges/getAllBadgesForUser">Get All Badges For User</a></li>
	            <li><a href="${pageContext.request.contextPath}/try/user/attribute/update">Update User Attributes</a></li>
	            <li><a href="${pageContext.request.contextPath}/try/user/attribute/getUserAttributeValue">Get User Attribute Value</a></li>
	            <li><a href="${pageContext.request.contextPath}/try/user/social/getNotificationsAfterTime">Get User Social Notifications</a></li>
	          </ul>
          </li>
          <li class="config-help"><a href="${pageContext.request.contextPath}/help">Help</a></li>
	    </ul>
	  </div><!--/.nav-collapse -->
	</div>
</nav>