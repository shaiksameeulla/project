function serverValidation(url,functionName)
			{
				
				
				if (window.XMLHttpRequest) {
					req = new XMLHttpRequest();
				} else if (window.ActiveXObject) {
					req = new ActiveXObject("Microsoft.XMLHTTP");
				}
			//	var url = document.getElementById('frBooking:magic').value+"/centralValidation.ajx?cnnumber="+cnumber+"&#38;datagridIndex="+datagridIndex;
				if (req != null) {
				    req.open("GET", url, true);
				    req.onreadystatechange = functionName;
				    req.send(null);
				}
			}