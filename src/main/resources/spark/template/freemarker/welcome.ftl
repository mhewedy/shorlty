<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome!</title>
</head>
<body style="text-align: center;">
	<h3>Welcome to (un)shorten url service (beta)</h3>

	<table border="1"
		style="vertical-align: middle; width: 90%; border: medium; margin: 0 auto; height: 100px">
		<tr>
			<td style="vertical-align: top;">
				<form method="post" action="/shorten">
					<table>
						<tr>
							<td>enter url to shorten</td>
							<td><input type="text" name="url"/>
							<input type="submit" value="shorten me" />
							</td>
						</tr>
						<#if shorten_url??>
							<tr>
								<td colspan="2">
								<input type="text" id="resultUrlTxt" value="${shorten_url}"/> <em>press ctrl+s to copy<em>									
								</td>
							</tr>
						</#if>
					</table>
				</form></td>
			<td align="right" style="vertical-align: top; width: 50%; align;">
				<form method="post" action="/unshorten">
					<table>
						<tr>
							<td>enter url to unshorten</td>
							<td><input type="text" name="url" />
							<input type="submit" value="unshorten me" />
							</td>
						</tr>
						<#if original_url??>
							<tr>
								<td colspan="2">
								<input type="text" id="resultUrlTxt" value="${original_url}"/> <em>press ctrl+s to copy<em>
								</td>
							</tr>
						</#if>
					</table>
					<em align="left" style="font-size: 10pt;">You can put the shorten url from any service 
						(ex. <b>tinyurl.com</b> or <b>bitly.com</b>) to see the original url before open.</em>
				</form></td>
		</tr>
	</table>
	
	<div id="recentDiv" style="display: none;">
	</div>
	<div id="recentLoadDiv" style="display: inline;">
		<img src="imgs/loading.gif" />
		<br />
	</div>
	
	<a href="apidoc" target="_blank">API</a>
	 | <a href="ddl/ShortUrl.crx" target="_blank"> <img src="imgs/chrome_icon.png" style="width: 16px; height:16px;" /> Chrome app</a> 
	 		<em>(<a href="html/install_chrome.html" target="_blank">install</a>)</em> 
	 | <a href="https://github.com/MuhammadHewedy/short-url" target="_blank"> source code</a> 
	 | By <a href="http://m-hewedy.blogspot.com" target="_blank">mhewedy</a> 

</body>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/recent.js"></script>
</html>
