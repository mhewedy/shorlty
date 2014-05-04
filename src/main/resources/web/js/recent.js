
$.getJSON( "recent", function( data ) {
	
	$("#recentLoadDiv").css("display", "none");
	$("#recentDiv").css("display", "inline");
	
	var content = "";
	
	if (data.length != 0){
		var content = '<br /><br /><b>recent activity</b>'+
			'<table border="1" style="vertical-align: middle; border: medium; margin: 0 auto; height: 100px">';
			content+= '<tr><td >original url</td>'+
			'<td >created</td>'+
			'<td >short url</td>'+
			'<td >hits</td></tr>';
		
		$.each( data, function( key, val ) {
			
			var oringalUrl = val['originalUrl'];
			var shortUrl = val['shortUrl'];
			var createdAt = val['createdAt'];
			var hitCount = val['hitCount'];
			
			var trimAt = 70;
			var trimmedOriginalUrl = (oringalUrl.length > trimAt) ? oringalUrl.substr(0, trimAt) + '...' : oringalUrl;
			var trimmedshortUrl = (shortUrl.length > trimAt) ? shortUrl.substr(0, trimAt) + '...' : shortUrl;
			
			content += '<tr><td style="text-align: left;"><a href="' + oringalUrl + '" target="_blank">' + trimmedOriginalUrl + '<a></td>'
			+ '<td>' + createdAt + '</td>'
			+ '<td style="text-align: left;"><a href="' + shortUrl + '" target="_blank">' + trimmedshortUrl +'</a></td>'
			+ '<td>' + hitCount + '</td></tr>';
		});
		 
		content += "</table><br />"	
	}else{
		content = "<br />"
	}
	$('#recentDiv').append(content);
});