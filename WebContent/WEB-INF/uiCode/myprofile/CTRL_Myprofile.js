APP.CONTROLLERS.controller ('CTRL_Myprofile',['$scope','$state','$http','$ionicLoading','appData','$ionicPopup',
    function($scope,$state,$http,$ionicLoading,appData,$ionicPopup){
	var theCtrl = this;
	$scope.login = {};
	$scope.host = appData.getHost();
	$scope.conditionAgree = false;
	 var config = {
	            headers : {
	                'Content-Type': 'application/json;'
	            }
	        }
	
	 $scope.getMyProfile = function(){
		 $scope.$emit('showBusy');
			$http.get(appData.getHost()+'/ws/myprofile/'+window.localStorage.getItem('clientEmail'))
	  		.then(function(response){
	  			$scope.$emit('hideBusy');
	  			if (response.data ){
	  				$scope.login = response.data;
	  				if(!$scope.login.clientEmail || $scope.login.inactive){
	  					$state.transitionTo('menu.login');
	  				}
	  				 
	  			}
	  			
	  		},
			function(response){
	  			$scope.$emit('hideBusy');
	  			
			});
	 }
	 
	 theCtrl.validate = function(){
			$scope.validationError = "";
			
			
			if (!$scope.login.managerInfyEmail || $scope.login.managerInfyEmail.indexOf("@") < 0 ){
				$scope.validationError = "Invalid manager email";
			}
			
			if ($scope.validationError == ""){
				return true;
			}else {
				return false;
			}
		}
	 theCtrl.updateProfile = function(){
		
			$scope.$emit('showBusy');
			
			if (theCtrl.validate()){
				$http.put(appData.getHost()+'/ws/myprofile/',$scope.login , config)
		  		.then(function(response){
		  			
		  			$scope.getMyProfile();
		  			
		  			
		  		},
				function(response){	
		  			$scope.$emit('hideBusy');
		  		}
		  		);	
			}else {
				$scope.$emit('hideBusy');
				
			}
			
			   
		}
	 
	 theCtrl.deleteProfile = function(){
		 
		 var confirmPopup = $ionicPopup.confirm({
		     title: 'Confirmation',
		     template: 'Do you want to delete your profile?'
		   });

		   confirmPopup.then(function(res) {
			   if (res){
				   
				   $scope.$emit('showBusy');
					
					if (theCtrl.validate()){
						$http.delete(appData.getHost()+'/ws/myprofile/'+window.localStorage.getItem('clientEmail'))
				  		.then(function(response){
				  			 window.localStorage.removeItem("clientEmail");
				  			window.localStorage.removeItem("userValidated");
				  			$state.transitionTo('menu.login');
				  			
				  			
				  		},
						function(response){	
				  			$scope.$emit('hideBusy');
				  		}
				  		);	
					}else {
						$scope.$emit('hideBusy');
						
					}
				   
			   }
		   });
			
			
			
			   
		}
	  
}
])