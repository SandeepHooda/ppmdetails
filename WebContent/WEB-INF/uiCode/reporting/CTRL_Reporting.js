APP.CONTROLLERS.controller ('CTRL_Reporting',['$scope','$state','$http','$ionicLoading','appData','$ionicPopup','ionicDatePicker',
    function($scope,$state,$http,$ionicLoading,appData,$ionicPopup, ionicDatePicker){
	var theCtrl = this;
	$scope.login = {};
	$scope.host = appData.getHost();
	if (window.localStorage.getItem('manager') == 'true'){
		$scope.manager = true;
	}else {
		$scope.manager = false;
	}
	 
	 var config = {
	            headers : {
	                'Content-Type': 'application/json;'
	            }
	        }
	 
	 var fromDateObj = {
		      callback: function (val) {  //Mandatory
		        var date = new Date(val);
		        $scope.fromDate = date.getFullYear() +"/"+(date.getMonth() +1) +"/"+date.getDate();
		      },
		     /* disabledDates: [            //Optional
		        new Date(2016, 2, 16),
		        new Date(2015, 3, 16),
		        new Date(2015, 4, 16),
		        new Date(2015, 5, 16),
		        new Date('Wednesday, August 12, 2015'),
		        new Date("08-16-2016"),
		        new Date(1439676000000)
		      ],
		      from: new Date(2012, 1, 1), //Optional
		      to: new Date(2016, 10, 30), //Optional
		      inputDate: new Date(),      //Optional
		      mondayFirst: true,          //Optional
		      disableWeekdays: [0],       //Optional
		      closeOnSelect: false,       //Optional
		      templateType: 'popup'       //Optional*/
		    };
	 var toDateObj = {
		      callback: function (val) {  //Mandatory
		    	  var date = new Date(val);
			      $scope.toDate = date.getFullYear() +"/"+(date.getMonth() +1) +"/"+date.getDate();
		       
		      },
		     /* disabledDates: [            //Optional
		        new Date(2016, 2, 16),
		        new Date(2015, 3, 16),
		        new Date(2015, 4, 16),
		        new Date(2015, 5, 16),
		        new Date('Wednesday, August 12, 2015'),
		        new Date("08-16-2016"),
		        new Date(1439676000000)
		      ],
		      from: new Date(2012, 1, 1), //Optional
		      to: new Date(2016, 10, 30), //Optional
		      inputDate: new Date(),      //Optional
		      mondayFirst: true,          //Optional
		      disableWeekdays: [0],       //Optional
		      closeOnSelect: false,       //Optional
		      templateType: 'popup'       //Optional*/
		    };
	
	 $scope.openFromDatePicker = function(){
	    ionicDatePicker.openDatePicker(fromDateObj);
	 };
	 $scope.openToDatePicker = function(){
		 ionicDatePicker.openDatePicker(toDateObj);
	};
	  
	$scope.download = function(){
		
		window.open(appData.getHost()+'/TimeSheetReport?id='+window.localStorage.getItem('clientEmail')+"&from="+$scope.fromDate +"&to="+$scope.toDate );
		
		
	}
}
])