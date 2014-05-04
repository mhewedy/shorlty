
$.getJSON( "recent", function( data ) {
	
	$("#recentLoadDiv").css("display", "none");
	$("#recentDiv").css("display", "inline");
	
	var content = "";
	console.log(data)
	if (data.length != 0){
		var content = '<br /><br /><b>recent activity</b>'+
			'<table border="1" style="vertical-align: middle; width: 70%; border: medium; margin: 0 auto; height: 100px">';
		
		content+= '<tr><td style="width: 55%;">original url</td>'+
			'<td style="width: 20%">created</td>'+
			'<td style="width: 20%">short url</td>'+
			'<td style="width: 5%">hits</td></tr>';
		
		$.each( data, function( key, val ) {
			console.log(val["hitCount"])
			
			content += '<tr><td style="text-align: left;"><a href=' + val["originalUrl"] + '>' + val["originalUrl"] + '<a></td>'
			+ '<td>' + val["createdAt"] + '</td>'
			+ '<td style="text-align: left;"><a href=' + val["shortUrl"] + '>' + val["shortUrl"] +'</a></td>'
			+ '<td>' + val["hitCount"] + '</td></tr>';
		});
		 
		content += "</table><br />"	
	}else{
		content = "<br />"
	}
	$('#recentDiv').append(content);
});