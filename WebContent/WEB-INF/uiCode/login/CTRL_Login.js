APP.CONTROLLERS.controller ('CTRL_Login',['$scope','$state','$http','$ionicLoading','appData','$ionicPopup','$ionicSideMenuDelegate',
    function($scope,$state,$http,$ionicLoading,appData,$ionicPopup,$ionicSideMenuDelegate){
	var theCtrl = this;
	$scope.login = {};
	 var config = {
	            headers : {
	                'Content-Type': 'application/json;'
	            }
	        }
	 $scope.showMenu = function () {
			if(document.URL.indexOf('/menu/login') <0){//Disable hanburger in log in state
				 $ionicSideMenuDelegate.toggleLeft();
			}
		   
		  };
	 
	  
}
])