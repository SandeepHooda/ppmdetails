APP.SERVICES.service ('appData',['$window','dataRestore','$ionicPopup',
    function( $window,dataRestore, $ionicPopup){
	
	this.getHost = function () {
		var host = "https://ppmdetails.appspot.com";
		 if ($window.location.host.indexOf("localhost:8080") >=0 ){
			host = "";
		}
		
		return host;
	}
	
	this.showErrorMessage = function(httpCode){
		if ( httpCode == 403){
			var confirmPopup = $ionicPopup.confirm({
			     title: 'Password mimatch',
			     template: 'Your password donot match our records.'
			   });
			 confirmPopup.then(function(res) {
			  });
		}else {
			var confirmPopup = $ionicPopup.confirm({
			     title: 'Internal Server Error',
			     template: 'Something unusual happened at server.'
			   });
			 confirmPopup.then(function(res) {
			  });
				
		}
	}
	
	this.getCommingMondays = function ( startDate,  countOfMondays) {
		startDate = ""+startDate;
		var d = new Date(startDate.substring(0, 4),( startDate.substring(4, 6)-1), startDate.substring(6)),
	        month = d.getMonth(),
	        mondays = [];
		var i=0;
	    while (i<countOfMondays) {
	    	i++;
	        mondays.push(new Date(d.getTime()));
	        d.setDate(d.getDate() + 7);
	    }

	    return mondays;
	}
	
	
	this.getMondayOfThisWeek = function () {
	    var d = new Date(),
	        month = d.getMonth(),
	        mondays = [];

	    
	    while (d.getDay() !== 1) {
	        d.setDate(d.getDate() - 1);
	        
	    }
	   

	    return new Date(d.getTime());
	}
	
	 this.getAllMondaysOfMonth = function() {
	    var d = new Date(),
	        month = d.getMonth(),
	        mondays = [];

	    d.setDate(1);

	    // Get the first Monday in the month
	    while (d.getDay() !== 1) {
	        d.setDate(d.getDate() + 1);
	    }

	    // Get all the other Mondays in the month
	    while (d.getMonth() === month) {
	        mondays.push(new Date(d.getTime()));
	        d.setDate(d.getDate() + 7);
	    }

	    return mondays;
	}
}

]);