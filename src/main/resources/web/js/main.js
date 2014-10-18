
function showHits(objectId){
	$("#recentLoadDiv").css("visibility", "visible");
	
	$.getJSON("hits?objectId=" + objectId, function(data){
		$("#recentLoadDiv").css("visibility", "hidden");
		
		if (data.length != 0){
			
			var $tableDiv = $('<div></div>');
			var $tableHeader = '<div class="col-md-0">' +
								  '<table class="table table-striped">' +
								    '<thead>' +
								      '<tr>' +
								        '<th>Clicked</th>' +
								        '<th>City</th>' +
								      '</tr>' +
								    '</thead>' +
								    '<tbody>';
			var $tableFooter = 		'</tbody>' +
								   '</table>' +
								 '</div>';
	        $tableDiv.append($tableHeader.toString());
	        $.each( data, function( key, val ) {
				var createdAt = val['createdAt'];
				var location = val['location'];
				
				$tableDiv.append('<tr><td>' + new Date(createdAt).toLocaleString() + '</td><td>' + location + '</td></tr>');	
			});
	        $tableDiv.append($tableFooter.toString());
	        
	        BootstrapDialog.show({
	            title: 'URL Click details',
	            message: $tableDiv,
	            buttons: [{
	                label: 'Close',
	                action: function(dialogRef){
	                    dialogRef.close();
	                }
	            }]
	        });
		}
	})	
};


// Get Recent
$.getJSON("recent", function( data ) {
	$("#recentLoadDiv").css("visibility", "hidden");
	
	if (data.length != 0){
		$("#recent").css("display", "inline");
		
		var i = data.length;
		
		data = data.reverse();
		$.each( data, function( key, val ) {
			
			var objectId = val['objectId'];
			var oringalUrl = val['originalUrl'];
			var shortUrl = val['shortUrl'];
			var createdAt = val['createdAt'];
			var hitCount = val['hitCount'];
			
			shortUrl = document.location.origin + '/' + shortUrl;
			
			var trimAtOriginalUrl = 80;
			var trimAtShortUrl = 30;
			
			var trimmedOriginalUrl = (oringalUrl.length > trimAtOriginalUrl) ? oringalUrl.substr(0, trimAtOriginalUrl) + '...' : oringalUrl;
			var trimmedshortUrl = (shortUrl.length > trimAtShortUrl) ? shortUrl.substr(0, trimAtShortUrl) + '...' : shortUrl;
			
			var row = $("#resultTable")[0].insertRow(1)
			row.insertCell(0).innerHTML = i--;
			row.insertCell(1).innerHTML = '<a href=' + shortUrl + ' target="_blank" >' + trimmedshortUrl + '</a>'
											+ ((hitCount > 0) ? ' <a href="javascript:showHits(\'' + objectId + '\')">' : '')
											+ ' <span class="badge">' + hitCount + '</span></a>'
											+ ((hitCount > 0) ? '</a>' : '')
											;
			row.insertCell(2).innerHTML = new Date(createdAt).toLocaleString();
			row.insertCell(3).innerHTML = '<a href=' + oringalUrl + ' target="_blank" >' + trimmedOriginalUrl + '</a>';
			
		});
	}else{
		$("#recent").css("display", "none");
	}
});
// ~ Get Recent


