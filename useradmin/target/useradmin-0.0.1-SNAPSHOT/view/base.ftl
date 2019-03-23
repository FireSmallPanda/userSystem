<#import "index.ftl" as ui>  
<!doctype html>
<html class="no-js">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><@block name="title">标题</@block></title>
<link rel="stylesheet" href="../resources/assets/css/amazeui.min.css">
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="../resources/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
	<script src="assets/ie8/jquery.min.js"></script>
	<script src="assets/ie8/modernizr.js"></script>
	<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
	<![endif]-->
<script src="../resources/assets/js/amazeui.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<style type="text/css">
		html {
			font-size: 62.5%;
		}
		body {
			font-size: 1.6em;
		}
	<@block name="style">style</@block>	
</style>
<body>
	
	
	<@block name="content">内容区</@block>
	<!--信息弹窗-->
	<@ui.alertBox type="success" />
	<@ui.alertBox type="warning" />
	<@ui.alertBox type="error" />
	<@ui.alertBox type="msg" />
</body>
<script type="text/javascript">
	$.AMUI.progress.start();
   // 显示指定弹窗并赋值
	var alertBox = function(type,context) {
			$("#"+type+"-alertBox").find(".alertBoxText").html(context);
			$("#"+type+"-alertBox").modal();
			
			
	}
	<@block name="javascript">javasctipt</@block>
</script>
</html>