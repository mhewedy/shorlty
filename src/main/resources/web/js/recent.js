$.getJSON("recent", function( data ) {
	$("#recentLoadDiv").css("display", "none");
	var content = "";
	
	if (data.length != 0){
		$("#recent").css("display", "inline");
		
		var i = data.length;
		
		data = data.reverse();
		$.each( data, function( key, val ) {
			
			var oringalUrl = val['originalUrl'];
			var shortUrl = val['shortUrl'];
			var createdAt = val['createdAt'];
			var hitCount = val['hitCount'];
			
			var trimAtOriginalUrl = 80;
			var trimAtShortUrl = 30;
			
			var trimmedOriginalUrl = (oringalUrl.length > trimAtOriginalUrl) ? oringalUrl.substr(0, trimAtOriginalUrl) + '...' : oringalUrl;
			var trimmedshortUrl = (shortUrl.length > trimAtShortUrl) ? shortUrl.substr(0, trimAtShortUrl) + '...' : shortUrl;
			
			var row = $("#resultTable")[0].insertRow(1)
			row.insertCell(0).innerHTML = i--;
			row.insertCell(1).innerHTML = '<a href=' + shortUrl + ' target="_blank" >' + trimmedshortUrl + '</a>' 
											+ ' <span class="badge">' + hitCount + '</span>';
			row.insertCell(2).innerHTML = createdAt;
			row.insertCell(3).innerHTML = '<a href=' + oringalUrl + ' target="_blank" >' + trimmedOriginalUrl + '</a>';
			
		});
	}else{
		$("#recent").css("display", "none");
	}
});