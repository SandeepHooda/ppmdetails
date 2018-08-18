APP.CONTROLLERS.controller ('CTRL_Reporting',['$scope','$state','$http','$ionicLoading','appData','$ionicPopup','ionicDatePicker','$rootScope',
    function($scope,$state,$http,$ionicLoading,appData,$ionicPopup, ionicDatePicker,$rootScope){
	var theCtrl = this;
	$scope.login = {};
	$scope.host = appData.getHost();
	if (window.localStorage.getItem('manager') == 'true'){
		$scope.manager = true;
	}else {
		$scope.manager = false;
	}
	$scope.messageToTeam = "Please fill you advance timesheet and its details on the portal till this month end. "
	$scope.messageSend = "";
		
	 $scope.selectedObo = {};
	 
	 var config = {
	            headers : {
	                'Content-Type': 'application/json;'
	            }
	        }
	 if ($rootScope.login && $rootScope.login.obo && $rootScope.login.obo.length > 0){
		 $scope.selectedObo.managerID = $rootScope.login.obo[0];
		 if ($rootScope.login.obo.length > 1){
			 $scope.oboList = $rootScope.login.obo;
		 }
	 }
	$scope.updateManager = function(){
		$scope.getDefaulterList();
		 $scope.getReporteeList();
	}
	 $scope.getDefaulterList = function(){
		 $scope.$emit('showBusy');
		 var obo_manager = window.localStorage.getItem('clientEmail');
			if ($scope.selectedObo.managerID){
				obo_manager = $scope.selectedObo.managerID;
			}
		 $http.get(appData.getHost()+'/ws/timesheet/defaulter/'+obo_manager)
	  		.then(function(response){
	  			$scope.$emit('hideBusy');
	  			if (response.data ){
	  				
	  				 $scope.defaulters = response.data.defaulters; 
	  			}
	  			
	  		},
			function(response){
	  			$scope.$emit('hideBusy');
	  			
			});
	 }
	 $scope.getReporteeList = function(){
		
		 var managerClientEmail = window.localStorage.getItem('clientEmail');
		
			if ($scope.selectedObo.managerID){
				managerClientEmail = $scope.selectedObo.managerID;
			}
		 $http.get(appData.getHost()+'/ws/timesheet/reportee/'+managerClientEmail)
	  		.then(function(response){
	  			
	  			if (response.data ){
	  				 var reporteeList = response.data; 
	  				 $scope.reporteeList = [];
	  				 for (var index=0;index<reporteeList.length;index++){
	  					 var reportee = reporteeList[index];
	  					reportee.hasMyOboRole = false;
	  					 if (reportee.obo && reportee.obo.length > 0){//check the obo list to find who all reportee is obo to
	  						 for (var i=0;i<reportee.obo.length;i++){
	  							 if (reportee.obo[i] == managerClientEmail ){// if reportee is obo to selected manager
	  								reportee.hasMyOboRole = true;
	  								break;
	  							 }
	  						 }
	  					 }
	  					 if (reportee._id != managerClientEmail){//don't push manager itself
	  						$scope.reporteeList.push(reportee)
	  					 }
	  				 }
	  			}
	  			
	  		},
			function(response){
	  		});
	 }
	 $scope.remmindDefaulters = function(){
		 $scope.$emit('showBusy');
		 var dto = {};
		 dto.managerClientID = window.localStorage.getItem('clientEmail');
		 
			if ($scope.selectedObo.managerID){
				dto.managerClientID = $scope.selectedObo.managerID;
			}
		 dto.defaulters = $scope.defaulters;
		 $http.post(appData.getHost()+'/ws/timesheet/remindDefaulters', dto, config)
	  		.then(function(response){
	  			$scope.$emit('hideBusy');
	  			alert("Reminder email has been sent them.")
	  			
	  		
	  		},
			function(response){	
	  			$scope.$emit('hideBusy');
	  		}
	  		);
	 }
	$scope.sendMsgToTeam = function(){
		 $scope.messageSend = "";
		 $scope.$emit('showBusy');
		 var obo_manager = window.localStorage.getItem('clientEmail');
			if ($scope.selectedObo.managerID){
				obo_manager = $scope.selectedObo.managerID;
			}
			$http.get(appData.getHost()+'/ws/timesheet/sendmessage/'+obo_manager+"/"+$scope.messageToTeam )
	  		.then(function(response){
	  			$scope.$emit('hideBusy');
	  			if (response.data ){
	  				
	  				 $scope.messageSend = response.data._id; 
	  			}
	  			
	  		},
			function(response){
	  			$scope.$emit('hideBusy');
	  			
			});
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
		var obo_manager = window.localStorage.getItem('clientEmail');
		if ($scope.selectedObo.managerID){
			obo_manager = $scope.selectedObo.managerID;
		}
		window.open(appData.getHost()+'/TimeSheetReport?id='+obo_manager+"&from="+$scope.fromDate +"&to="+$scope.toDate );
		
		
	}
	$scope.updateOboRole = function( reportee){
		var countOfObos =0;;
		for( var i=0;i< $scope.reporteeList.length;i++){
			if ($scope.reporteeList[i].hasMyOboRole){
				countOfObos++;
			}
		}
		if (countOfObos <=0){
			for( var i=0;i< $scope.reporteeList.length;i++){
				if ($scope.reporteeList[i].clientEmail == reportee.clientEmail){
					reportee.hasMyOboRole = true;
					break;
				}
			}
			alert("At lest one OBO is required");
			return;
		}
		var obo_manager = window.localStorage.getItem('clientEmail');
		if ($scope.selectedObo.managerID){
			obo_manager = $scope.selectedObo.managerID;
		}
		var updateObo = {};
		updateObo.hasMyOboRole = reportee.hasMyOboRole;
		updateObo.clientEmail = reportee.clientEmail;
		updateObo.managerClientEmail = obo_manager;
		 $scope.$emit('showBusy');
		$http.post(appData.getHost()+'/ws/login/updateOboRole', updateObo, config)
  		.then(function(response){
  			$scope.$emit('hideBusy');
  		},
		function(response){	
  			$scope.$emit('hideBusy');
  		}
  		);
	}
}
])