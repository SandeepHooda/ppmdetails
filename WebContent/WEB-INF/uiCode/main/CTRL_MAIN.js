APP.CONTROLLERS.controller ('CTRL_MAIN',['$scope','$state','$rootScope','$ionicLoading','$http','$ionicPopup','appData','$timeout',
    function($scope,$state,$rootScope,$ionicLoading,$http,$ionicPopup, appData,$timeout){
	 var config = {
	            headers : {
	                'Content-Type': 'application/json;'
	            }
	        }
	 dataLayer.push({'pageTitle': 'Home'});    // Better
	 $scope.isUserValidated = function(){
		 
	 }
	 $scope.checkAuthontication = function(){
		 var workEmail = window.localStorage.getItem('workEmail');
		 if (workEmail && workEmail.length > 5){
			 if ($scope.isUserValidated()){
				 $state.transitionTo('menu.tab.home');
			 }
		 }else {
			 
			 $state.transitionTo('menu.login');
		 }
	 }	
	 $scope.checkAuthontication();
}])