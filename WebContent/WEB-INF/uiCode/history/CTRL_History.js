APP.CONTROLLERS.controller ('CTRL_History',['$scope','$state','$rootScope','$ionicLoading','$http','$ionicPopup','appData','$timeout',
    function($scope,$state,$rootScope,$ionicLoading,$http,$ionicPopup, appData,$timeout){
	
	 var config = {
	            headers : {
	                'Content-Type': 'application/json;'
	            }
	        }
	 dataLayer.push({'pageTitle': 'Home'});    // Better
	 $scope.viewMode = true;
	 $scope.sheet = {};
	 $scope.filledTimeSheets = {};
	 
	 
	 $scope.getMyTimeSheets = function(){
		 $scope.$emit('showBusy');
			$http.get(appData.getHost()+'/ws/timesheet/'+window.localStorage.getItem('clientEmail'))
	  		.then(function(response){
	  			$scope.$emit('hideBusy');
	  			if (response.data ){
	  				 $scope.filledTimeSheets = response.data.allTimeSheets;
	  				
	  			}
	  			
	  		},
			function(response){
	  			$scope.$emit('hideBusy');
	  			
			});
	 }
		  
	 $scope.backToHistoryView = function(){
		 $scope.viewMode = true;
	 }
	 $scope.reworkTimeSheet = function(index){
		 $scope.viewMode = false;
		 $scope.reworkSheetTimeSheetEntry  = $scope.filledTimeSheets[index].timeSheetEntry;
		 $scope.weekStartDate = $scope.filledTimeSheets[index].weekStartDate;
		var editVersion = $scope.reworkSheetTimeSheetEntry[0].editVersion;
		$scope.reworkSheet = $scope.reworkSheetTimeSheetEntry[0];
		 for (var i=0; i<$scope.reworkSheetTimeSheetEntry.length;i++ ){
			 if (editVersion < $scope.reworkSheetTimeSheetEntry[i].editVersion){
				 editVersion = $scope.reworkSheetTimeSheetEntry[i].editVersion;
				 $scope.reworkSheet = $scope.reworkSheetTimeSheetEntry[i];
			 }
		 }
	 }
	 
	 $scope.submitRework = function(){
		 if ($scope.reworkSheet.billableHours != 40 || $scope.reworkSheet.nonBillableHours > 0) {
			 if(!$scope.reworkSheet.remarks){
				 alert("please fill the remarks");
				 return;
			 }
		 }
		 
			 //Ready to submit the timesheet
			 $scope.$emit('showBusy');
			 var dto = {}
			 dto.currentEntry = $scope.reworkSheet;
			 dto.clientEmail = window.localStorage.getItem('clientEmail');
			 dto.currentEntryDate = $scope.weekStartDate;
			 $http.put(appData.getHost()+'/ws/timesheet/', dto, config)
		  		.then(function(response){
		  			$scope.$emit('hideBusy');
		  			$scope.viewMode = true;
		  			$scope.getMyTimeSheets();
		  			
		  		
		  		},
				function(response){	
		  			$scope.$emit('hideBusy');
		  		}
		  		);
			 
		 
	 }
	 
}])