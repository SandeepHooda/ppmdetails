APP.CONTROLLERS.controller ('CTRL_Myprofile',['$scope','$state','$http','$ionicLoading','appData','$ionicPopup',
    function($scope,$state,$http,$ionicLoading,appData,$ionicPopup){
	var theCtrl = this;
	$scope.host = appData.getHost();
	$scope.conditionAgree = false;
	 var config = {
	            headers : {
	                'Content-Type': 'application/json;'
	            }
	        }
	
	 
	  
}
])