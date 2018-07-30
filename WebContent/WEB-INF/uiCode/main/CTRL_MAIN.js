APP.CONTROLLERS.controller ('CTRL_MAIN',['$scope','$state','$rootScope','$ionicLoading','$http','$ionicPopup','appData','$timeout','$window',
    function($scope,$state,$rootScope,$ionicLoading,$http,$ionicPopup, appData,$timeout,$window){
	 var config = {
	            headers : {
	                'Content-Type': 'application/json;'
	            }
	        }
	 dataLayer.push({'pageTitle': 'Home'});    // Better
	 $scope.isUserValidated = function(){
		var userValidated = window.localStorage.getItem('userValidated');
		if (userValidated && userValidated.length > 1){
			return true;
		}else {
			return false;
		}
	 }
	 $scope.isUserStillActiveInDB = function(){
		
		 
		 $scope.$emit('showBusy');
			$http.get(appData.getHost()+'/ws/myprofile/'+window.localStorage.getItem('clientEmail'))
	  		.then(function(response){
	  			$scope.$emit('hideBusy');
	  			if (response.data ){
	  				$scope.login = response.data;
	  				if(!$scope.login.clientEmail || $scope.login.inactive){
	  					 window.localStorage.removeItem("clientEmail");
	 		  			window.localStorage.removeItem("userValidated");
	  					$state.transitionTo('menu.login');
	  				}else {
	  					
	  					window.localStorage.setItem('manager',$scope.login.manager) ;
	  					 $state.transitionTo('menu.tab.home');
	  				}
	  				 
	  			}
	  			
	  		},
			function(response){
	  			$scope.$emit('hideBusy');
	  			
			});
	}
	 $scope.checkAuthontication = function(){
		 if ($window.location.protocol != 'https:' && $window.location.host != 'localhost:8080'){
			 window.open("https://ppmdetails.appspot.com/","_self");
			 return;
		 }
		 var clientEmail = window.localStorage.getItem('clientEmail');
		 if (clientEmail && clientEmail.length > 5){
			 if ($scope.isUserValidated()){
				 $scope.isUserStillActiveInDB();
				 
			 }else {
				 $state.transitionTo('menu.verifyEmail');
			 }
		 }else {
			 
			 $state.transitionTo('menu.login');
		 }
	 }	
	 $scope.checkAuthontication();
	 
		//Busy icon
	 $rootScope.$on('showBusy',function(event){
			$scope.gettingUserReminderList = true;
		    $ionicLoading.show({
			      template: 'Please Wait...',
			      duration: 30000
			    }).then(function(){
			       console.log("The loading indicator is now displayed");
			    });
			
		});
	 

	 $rootScope.$on('hideBusy',function(event){
		 $scope.gettingUserReminderList = false;
		    $ionicLoading.hide().then(function(){
		       console.log("The loading indicator is now hidden");
		    });
			
		});
	
	 
		 
}])