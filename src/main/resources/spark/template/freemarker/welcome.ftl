<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>${appname}</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="css/bootstrap-theme.min.css" rel="stylesheet">
<link href="css/bootstrap-dialog.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/theme.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-10490969-3', 'auto');
  ga('send', 'pageview');

</script>
</head>

<body role="document">

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.4";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

	<!-- Fixed navbar -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> 
					<span class="icon-bar"></span> <span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
				<div id="recentLoadDiv" style="float: left; margin-top: 15px; margin-right: 5px;">
					<img src="imgs/loading.gif" />
					<br />
				</div>
				<a class="navbar-brand" href="/">${appname}</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/">Home</a></li>
					<li><a href="apidoc" target="_blank">API</a></li>
					<li><a href="html/install_chrome.html" target="_blank">Chrome App</a></li>
					<li><a href="https://github.com/MuhammadHewedy/short-url" target="_blank">Source Code</a></li>
					<li><a href="https://github.com/MuhammadHewedy/short-url/issues/new" target="_blank">Support</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>

	<div class="container theme-showcase" role="main">

		<!-- Main jumbotron for a primary marketing message or call to action -->
		<div class="jumbotron">
			<h2>${appname}</h2>
			<p>
				${appname} is an open source URL <i>shorten</i>, <i>unshorten</i> and <i> tracker</i> website.
			</p>

			<div class="row marketing">
				<div class="row">
					<div class="col-sm-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3 class="panel-title">Shorten URL</h3>
							</div>
							<form method="post" action="/shorten">
								<div class="input-group">
									<input type="input" id="short" name="url" class="form-control" placeholder="Enter URL to shorten" 
										required="" <#if shorten_url??>value="${shorten_url}"</#if>
										<#if !shorten_url?? && !original_url??> autofocus="autofocus" </#if> />
									<input type="hidden" name="isCookieEnabled" id="isCookieEnabled" />
									<span class="input-group-btn">
										<input type="submit" id="submit" class="btn btn-default" value="Go!" />
									</span>
								</div>
							</form>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="panel panel-warning">
							<div class="panel-heading">
								<h3 class="panel-title">Extract URL</h3>
							</div>
							<form method="post" action="/unshorten">
								<div class="input-group">
									<input type="input" id="unshort" name="url" class="form-control" placeholder="Enter short URL to extract" 
										required="" <#if original_url??>value="${original_url}"</#if> />
									<span class="input-group-btn">
										<input type="submit" class="btn btn-default" value="Go!" />
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			
			<#if shorten_url?? || original_url??>
				<div style="text-align: center;">
					<h4><span class="label label-success">Success! Press CTRL+C to copy</span></h4>
				</div>
			</#if>

			<div class="fb-like" data-href="https://www.facebook.com/shortly.pw" data-layout="standard" data-action="like" data-show-faces="true" data-share="true"></div>

		</div>

		<div class="alert alert-info" role="alert">
			<strong>Heads up!</strong> You can put the URL that generated from any
			service (ex. <strong>tinyurl.com</strong> or <strong>bitly.com</strong>) to see the original url before
			open.
		</div>
		
		<div id="recent" style="display: none;">
			<div class="col-md-12">
				<table class="table" id="resultTable">
					<thead>
						<tr>
							<th>#</th>
							<th>Short URL</th>
							<th>Created</th>
							<th>Original URL</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		<!-- // table -->
		</div>

	</div>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-dialog.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="js/main.js"></script>
	
	<script type="text/javascript">
		<#if shorten_url??>
			$("#short").select();
		</#if>
		<#if original_url??>
			$("#unshort").select();
		</#if>

		function checkCookie(){
			var cookieEnabled=(navigator.cookieEnabled)? true : false;
			if (typeof navigator.cookieEnabled=="undefined" && !cookieEnabled){
				document.cookie="testcookie";
				cookieEnabled=(document.cookie.indexOf("testcookie")!=-1)? true : false;
			}
			return cookieEnabled
		}

		$(function () {
            $("#submit").on("click", function () {
                $("#isCookieEnabled").val(checkCookie());
            });
        });


	</script>
</body>
</html>
