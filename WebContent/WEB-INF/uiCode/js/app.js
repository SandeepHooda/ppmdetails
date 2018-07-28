// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
var APP = {};
APP.DIRECTIVE = angular.module('allDirective',[]);
APP.CONTROLLERS = angular.module('allControllers',[]);
APP.SERVICES = angular.module('allServices',[]);
APP.FACTORY = angular.module('allFact',[]);
APP.DEPENDENCIES = ['allControllers',
                    'allServices',
                    'allDirective',
                    'allFact'
                    ];
APP.OTHERDEPENDENCIES = ['ionic','ngCordova','ionic-numberpicker', 'ionic-datepicker'];
angular.module('starter', APP.DEPENDENCIES.concat(APP.OTHERDEPENDENCIES))
.config(['$urlRouterProvider','$stateProvider','$ionicConfigProvider','ionicDatePickerProvider',
         function($urlRouterProvider,$stateProvider,$ionicConfigProvider, ionicDatePickerProvider){
	
	var datePickerObj = {
		      inputDate: new Date(),
		      titleLabel: 'Select a Date',
		      setLabel: 'Set',
		      todayLabel: 'Today',
		      closeLabel: 'Close',
		      mondayFirst: false,
		      weeksList: ["S", "M", "T", "W", "T", "F", "S"],
		      monthsList: ["Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"],
		      templateType: 'popup',
		      from: new Date(2017, 8, 1),
		      to: new Date(2098, 8, 1),
		      showTodayButton: false,
		      dateFormat: 'dd MMMM yyyy',
		      closeOnSelect: true,
		      disableWeekdays: []
		    };
		    ionicDatePickerProvider.configDatePicker(datePickerObj);
		    
	$ionicConfigProvider.tabs.position('bottom');
	 // setup an abstract state for the tabs directive
				$stateProvider.state('menu',{
					url:'/menu',
					abstract: true,
					templateUrl:'menu.html'	
					 
					
				}).state('menu.tab',{
					url:'/tab',
					abstract: true,
					templateUrl:'tabs.html'	
					 
					
				}).state('menu.tab.home',{
					url:'/home',
					views: {
						 'tab-home': {
						 templateUrl: 'home/home.html',
						 controller: 'CTRL_HOME'
						 }
					}	
					
				}).state('menu.tab.history',{
					url:'/history',
					views: {
						 'tab-history': {
						 templateUrl: 'history/history.html',
						 controller: 'CTRL_History'
						 }
					}	
					
				}).state('menu.myprofile',{
					url:'/myprofile',
					templateUrl: 'myprofile/myprofile.html',
					controller: 'CTRL_Myprofile'
				}).state('menu.reporting',{
					url:'/reporting',
					templateUrl: 'reporting/reporting.html',
					controller: 'CTRL_Reporting'
				}).state('menu.login',{
					url:'/login',
					templateUrl: 'login/login.html',
					controller: 'CTRL_Login'
				}).state('menu.verifyEmail',{
					url:'/verifyEmail',
					templateUrl: 'login/verifyEmail.html',
					controller: 'CTRL_Login'
				})
				$urlRouterProvider.otherwise('/menu/tab/home');
			}
         ])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

      // Don't remove this line unless you know what you are doing. It stops the viewport
      // from snapping when text inputs are focused. Ionic handles this internally for
      // a much nicer keyboard experience.
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})
