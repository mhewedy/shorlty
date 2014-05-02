<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome!</title>
</head>
<body style="">
	Welcome to shorten url service

	<table border="1"
		style="vertical-align: middle; width: 90%; border: medium;">
		<tr>
			<td align="left"><b> Shorten</b>
				<form method="post" action="/shorten">
					<table>
						<tr>
							<td>Enter URL</td>
							<td><input type="text" name="url" /></td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" value="shorten me" />
							</td>
						</tr>
						<#if shorten_url??>
							<tr>
								<td colspan="2"><b> Short URL:</b> <a href="${shorten_url}" target="_blank">
									${shorten_url}</a></td>
							</tr>
						</#if>
					</table>
				</form></td>
			<td align="right" style="width: 50%;"><b> UnShorten</b>
			<p>You can put the shorten url from any service (ex. tinyurl.com or bitly.com) to see the original url before open.</p>
				<form method="post" action="/unshorten">
					<table>
						<tr>
							<td>Enter URL</td>
							<td><input type="text" name="url" /></td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" value="unshorten me" />
							</td>
						</tr>
						<#if original_url??>
							<tr>
								<td colspan="2"><b> Original URL:</b> <a href="${original_url}" target="_blank"> 
								${original_url}</a></td>
							</tr>
						</#if>
					</table>
				</form></td>
		</tr>
	</table>
	By <a href="http://m-hewedy.blogspot.com">mhewedy</a> 


</body>
</html>
