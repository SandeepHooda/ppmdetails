APP.CONTROLLERS.controller ('CTRL_MAIN',['$scope','$state','$rootScope','$ionicLoading','$http','$ionicPopup','appData','$timeout',
    function($scope,$state,$rootScope,$ionicLoading,$http,$ionicPopup, appData,$timeout){
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
	 $scope.checkAuthontication = function(){
		 var clientEmail = window.localStorage.getItem('clientEmail');
		 if (clientEmail && clientEmail.length > 5){
			 if ($scope.isUserValidated()){
				 $state.transitionTo('menu.tab.home');
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
			      duration: 10000
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