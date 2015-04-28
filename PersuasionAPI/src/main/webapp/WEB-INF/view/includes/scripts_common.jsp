<!-- Placed at the end of the document so the pages load faster -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script
	src="https://code.jquery.com/ui/1.11.2/jquery-ui.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	
<script type="text/javascript">
	function highlightNavBarLink(pageHandle) {
		var navbarContainer = $("#navbarContainer");
		navbarContainer.find('.active').removeClass('active');
		navbarContainer.find('.' + pageHandle + ':first').addClass('active');
	};
</script>