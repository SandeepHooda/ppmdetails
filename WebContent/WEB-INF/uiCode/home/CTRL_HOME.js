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
	 $scope.backToView = function(){
		 $scope.viewMode = true;
	 }
	 $scope.filltimeSheet = function(index){
		 $scope.viewMode = false;
		 $scope.sheet.billableHours = 40;
		 $scope.sheet.nonBillableHours = 0;
		 $scope.sheet.remarks = "";
		 $scope.editTimeSheetDate  = ""+$scope.pendingTimeSheets[index];
		
		 
		 if ($scope.holidays && $scope.holidays.holidays){
			 var nextWeek = $scope.nextWeek($scope.editTimeSheetDate);
			 for (var i=0;i<$scope.holidays.holidays.length;i++){
				 if ($scope.holidays.holidays[i].date >= $scope.editTimeSheetDate && $scope.holidays.holidays[i].date < nextWeek){
					 //Holiday in this week
					 $scope.sheet.billableHours -=8;
					 $scope.sheet.nonBillableHours +=8;
					 $scope.sheet.remarks += " "+ $scope.holidays.holidays[i].desc;
				 }
			 }
		 }
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
	 
	 $scope.nextWeek = function(thisWeek){
		 var d = new Date(thisWeek.substring(0, 4), (thisWeek.substring(4, 6)-1), thisWeek.substring(6));
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
			 return ""+d.getFullYear() +month+day; 
		 
	 }
	 $scope.pendingTimeSheets = [];
	 $scope.createFutureTimeSheets = function(userTimeSheets){
		 $scope.pendingTimeSheets = [];
		 $scope.missedTimeSheets = [];//time sheet remaining in between two sheets
		 $scope.missedTimeSheetsSorted = [];
		 var unFilledTimeStart = 0;
		 
		 //Find Monday of the current week
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
		 var currentWeekStart  = thisMonday.getFullYear() +month+day;
		//End :: Find Monday of the current week
		 
		 
		 if (userTimeSheets.allTimeSheets && userTimeSheets.allTimeSheets.length > 0 ){//Find if any time sheet is missed since the first time sheet entry
			 var timeSheetStart = ""+userTimeSheets.allTimeSheets[0].weekStartDate;
			var index_timeSheet = 1;
			 while (timeSheetStart < currentWeekStart){// Check from the first time sheet till current date
				 var nextWeek = $scope.nextWeek(timeSheetStart);//check if user has filled for this week
				 
				 if (index_timeSheet < userTimeSheets.allTimeSheets.length){//check any unfilled gap between tow time sheets 
					 if (nextWeek == userTimeSheets.allTimeSheets[index_timeSheet].weekStartDate){
						 index_timeSheet++;
					 }else {
						 $scope.missedTimeSheets.push(nextWeek); 
					 }
				 }else {
					 $scope.missedTimeSheets.push(nextWeek);  
				 }
				 
				 timeSheetStart =  nextWeek;
			 }
			 
			 for (var i=$scope.missedTimeSheets.length-1;i >=0;i--){
				 $scope.missedTimeSheetsSorted.push($scope.missedTimeSheets[i])
			 }
			 if (nextWeek){
				 unFilledTimeStart = $scope.nextWeek(nextWeek);
			 }else {
				 unFilledTimeStart = $scope.nextWeek(currentWeekStart);
				 while(true){//User might have submitted future time sheets as well so set the date after that
					 var alreaySubmitted = false;
					 for (var i=0;i<userTimeSheets.allTimeSheets.length;i++){
						 if (userTimeSheets.allTimeSheets[i].weekStartDate == unFilledTimeStart){
							 alreaySubmitted = true;
							 unFilledTimeStart = $scope.nextWeek(unFilledTimeStart);//Now check for next week if that is submitted or not
							 break;
						 }
					 }
					 
					 if (!alreaySubmitted){
						 break;//We found teh date from which user has not submitted sheet
					 }
				 }
			 }
			 
		 }else {
			 unFilledTimeStart = currentWeekStart;
		 }
		 
		 
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
		
			 $scope.pendingTimeSheets = $scope.pendingTimeSheets.concat($scope.missedTimeSheetsSorted);
		
	 }
	 $scope.getHolidays = function(){
		 $http.get(appData.getHost()+'/ws/timesheet/holidays/'+window.localStorage.getItem('clientEmail')+'/india/')
	  		.then(function(response){
	  			if (response.data ){
	  				$scope.holidays = response.data; 
	  			}
	  			
	  		},
			function(response){
	  		}); 
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