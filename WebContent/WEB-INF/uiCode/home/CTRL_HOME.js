APP.CONTROLLERS.controller ('CTRL_HOME',['$scope','$state','$rootScope','$ionicLoading','$http','$ionicPopup','appData','$timeout',
    function($scope,$state,$rootScope,$ionicLoading,$http,$ionicPopup, appData,$timeout){
	//cordova plugin add cordova-plugin-googleplus --variable REVERSED_CLIENT_ID=myreversedclientid
	//cordova plugin add cordova-plugin-keyboard
	//https://github.com/apache/cordova-plugin-geolocation
	//cordova plugin add phonegap-nfc 
	//cordova plugin add cordova-plugin-vibration
	//cordova plugin add https://github.com/katzer/cordova-plugin-email-composer.git#0.8.2
	//cordova plugin add https://github.com/cowbell/cordova-plugin-geofence
	//cordova plugin add cordova-plugin-vibration
	//cordova plugin add cordova-plugin-device-motion
	//cordova plugin add cordova-plugin-whitelist
	//cordova plugin add cordova-plugin-shake
	//cordova plugin add cordova-plugin-sms
	//cordova plugin add cordova-plugin-android-permissions@0.6.0
	//cordova plugin add cordova-plugin-tts
	//cordova plugin add https://github.com/macdonst/SpeechRecognitionPlugin org.apache.cordova.speech.speechrecognition
	//cordova plugin add https://github.com/SandeepHooda/Speachrecognization org.apache.cordova.speech.speechrecognition
	//cordova plugin add https://github.com/katzer/cordova-plugin-background-mode.git
//	cordova plugin add cordova-plugin-http
	//cordova plugin add cordova-plugin-contacts-phonenumbers
	//cordova plugin add https://github.com/boltex/cordova-plugin-powermanagement
	//cordova plugin add https://github.com/katzer/cordova-plugin-local-notifications de.appplant.cordova.plugin.local-notification
	 var config = {
	            headers : {
	                'Content-Type': 'application/json;'
	            }
	        }
	 dataLayer.push({'pageTitle': 'Home'});    // Better
	 $scope.viewMode = true;
	 $scope.sheet = {};
	 $scope.editTimeSheetDate = "";
	 $scope.filltimeSheet = function(index){
		 $scope.viewMode = false;
		 $scope.sheet.billableHours = 40;
		
		 $scope.editTimeSheetDate  = ""+$scope.pendingTimeSheets[index];
	 }
	 
	 
	 $scope.submit =  function(){
		 if ($scope.sheet.billableHours != 40 || $scope.sheet.nonBillableHours > 0) {
			 if(!$scope.sheet.remarks){
				 alert("please fill the remarks");
				 return;
			 }
		 }
		 
			 //Ready to submit the timesheet
			 $scope.$emit('showBusy');
			 var dto = {}
			 dto.currentEntry = $scope.sheet;
			 dto.clientEmail = window.localStorage.getItem('clientEmail');
			 dto.currentEntryDate = $scope.editTimeSheetDate;
			 $http.post(appData.getHost()+'/ws/timesheet/', dto, config)
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
	 
	 
	 $scope.pendingTimeSheets = [];
	 $scope.createFutureTimeSheets = function(userTimeSheets){
		 
		 var unFilledTimeStart = 0;
		 if (userTimeSheets.allTimeSheets && userTimeSheets.allTimeSheets.length > 0 ){
			 unFilledTimeStart = userTimeSheets.allTimeSheets[0].weekStartDate;
			 unFilledTimeStart = ""+unFilledTimeStart;
			var d = new Date(unFilledTimeStart.substring(0, 4), (unFilledTimeStart.substring(4, 6)-1), unFilledTimeStart.substring(6));
			d.setDate(d.getDate() + 7);
			var month = d.getMonth();
			 var day = d.getDate();
			 month++;
			 if (month <10){
				 month = "0"+month;
			 }
			 if (day <10){
				 day = "0"+day;
			 }
			 unFilledTimeStart = d.getFullYear() +month+day; 
		 }else {
			 var thisMonday = appData.getMondayOfThisWeek();
			 var month = thisMonday.getMonth();
			 var day = thisMonday.getDate();
			 month++;
			 if (month <10){
				 month = "0"+month;
			 }
			 if (day <10){
				 day = "0"+day;
			 }
			 unFilledTimeStart = thisMonday.getFullYear() +month+day;
		 }
		 
		 $scope.pendingTimeSheets = [];
		 var pendingSheets= appData.getCommingMondays(unFilledTimeStart,4);
		 for (i=pendingSheets.length-1;i>=0;i--){
			 var date = pendingSheets[i];
			 var month = date.getMonth();
			 month++;
			 var day = date.getDate();
			 if (month <10){
				 month = "0"+month;
			 }
			 if (day <10){
				 day = "0"+day;
			 }
			 $scope.pendingTimeSheets.push(date.getFullYear()+month+day );
			// $scope.pendingTimeSheets.push(date)
		 }
		 
	 }
	 $scope.getMyTimeSheets = function(){
		 $scope.$emit('showBusy');
			$http.get(appData.getHost()+'/ws/timesheet/'+window.localStorage.getItem('clientEmail'))
	  		.then(function(response){
	  			$scope.$emit('hideBusy');
	  			if (response.data ){
	  				
	  				$scope.createFutureTimeSheets(response.data); 
	  			}
	  			
	  		},
			function(response){
	  			$scope.$emit('hideBusy');
	  			
			});
	 }
	 
}])