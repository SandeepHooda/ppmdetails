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
}])