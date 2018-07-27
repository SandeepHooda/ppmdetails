APP.CONTROLLERS.controller ('CTRL_Login',['$scope','$state','$http','$ionicLoading','appData','$ionicPopup','$ionicSideMenuDelegate',
    function($scope,$state,$http,$ionicLoading,appData,$ionicPopup,$ionicSideMenuDelegate){
	var theCtrl = this;
	$scope.login = {};
	$scope.validationError = "";
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
	
	theCtrl.validate = function(){
		$scope.validationError = "";
		
		
		if (!$scope.login.managerInfyEmail || $scope.login.managerInfyEmail.indexOf("@") < 0 ){
			$scope.validationError = "Invalid manager email";
		}
		if (!$scope.login.infyEmail || $scope.login.infyEmail.indexOf("@") < 0 ){
			$scope.validationError = "Invalid corporate email";
		}
		if (!$scope.login.clientEmail || $scope.login.clientEmail.indexOf("@") <0 ){
			$scope.validationError = "Invalid client email";
		}
		if ($scope.validationError == ""){
			return true;
		}else {
			return false;
		}
	}
	theCtrl.signIN = function(){
		$scope.$emit('showBusy');
		
		if (theCtrl.validate()){
			$http.post('/ws/login/',$scope.login , config)
	  		.then(function(response){
	  			$scope.$emit('hideBusy');
	  			if (response.data.emailOTP && response.data.emailOTP.length > 5){
	  				window.localStorage.setItem('clientEmail',response.data.clientEmail ) ;
		  			window.localStorage.setItem('emailOTP',response.data.emailOTP ) ;
		  			$state.transitionTo('menu.verifyEmail');
	  			}
	  			
	  		
	  		},
			function(response){	
	  			$scope.$emit('hideBusy');
	  		}
	  		);	
		}else {
			$scope.$emit('hideBusy');
			
		}
		
		   
	}
	theCtrl.proceedsToHome = function(){
		$scope.$emit('showBusy');
		$http.get(appData.getHost()+'/ws/login/isUserVerified/'+window.localStorage.getItem('emailOTP'))
  		.then(function(response){
  			$scope.$emit('hideBusy');
  			if (response.data && response.data.verified){
  				 window.localStorage.setItem('userValidated',"true");
  				$state.transitionTo('menu.tab.home');
  			}else {
  				alert("Please verify your email address first")
  			}
  			
  		},
		function(response){
  			$scope.$emit('hideBusy');
  			
		});
		
	}
	  
	theCtrl.backToLogin = function(){
		$state.transitionTo('menu.login');
	}
}
])