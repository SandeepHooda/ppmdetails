angular.module('starter').run(['$templateCache', function($templateCache) {
  'use strict';

  $templateCache.put('tabs.html',
    "<ion-tabs class=\"tabs-positive tabs-icon-bottom\"><ion-tab title=\"Home\" icon-on=\"icon ion-android-home\" icon-off=\"icon ion-android-home\" href=\"#/menu/tab/home\"><ion-nav-view name=\"tab-home\"></ion-nav-view></ion-tab><ion-tab title=\"History\" icon-on=\"icon ion-android-checkbox-outline\" icon-off=\"icon ion-android-checkbox-outline\" href=\"#/menu/tab/history\"><ion-nav-view name=\"tab-history\"></ion-nav-view></ion-tab></ion-tabs>"
  );


  $templateCache.put('menu.html',
    "<ion-view><ion-side-menus><ion-side-menu-content ng-controller=\"CTRL_Login as $ctrl\"><ion-nav-bar class=\"bar-positive\"><ion-nav-back-button class=\"button-icon ion-arrow-left-c\"></ion-nav-back-button><ion-nav-buttons side=\"left\"><button class=\"button button-icon button-clear ion-navicon\" ng-click=\"showMenu()\"></button></ion-nav-buttons></ion-nav-bar><ion-nav-view animation=\"slide-left-right\"></ion-nav-view></ion-side-menu-content><ion-side-menu side=\"left\"><ion-header-bar class=\"bar bar-header bar-positive\"><h1 class=\"title\">Profile</h1></ion-header-bar><ion-content has-header=\"true\" class=\"custom\"><ion-list><ion-item nav-clear menu-close href=\"#menu/tab/home\"><i class=\"icon ion-android-home\"></i> Home <i class=\"ion-ios-arrow-forward pull-right\"></i></ion-item><ion-item nav-clear menu-close href=\"#menu/myprofile\"><i class=\"icon ion-android-happy\"></i> My Profile <i class=\"ion-ios-arrow-forward pull-right\"></i></ion-item></ion-list></ion-content></ion-side-menu></ion-side-menus></ion-view>"
  );


  $templateCache.put('myprofile/myprofile.html',
    "<ion-view title=\"My Profile\" class=\"white\" ng-controller=\"CTRL_Login as $ctrl\" cache-view=\"false\"><ion-header-bar class=\"bar-positive\"><h1 class=\"title\">My Profile</h1></ion-header-bar><ion-content class=\"padding\">My Profile</ion-content></ion-view>"
  );


  $templateCache.put('login/login.html',
    "<ion-view title=\"Login\" class=\"white\" ng-controller=\"CTRL_Login as $ctrl\" cache-view=\"false\"><ion-header-bar class=\"bar-positive\"><h1 class=\"title\">Login</h1></ion-header-bar><ion-content class=\"padding\"></ion-content></ion-view>"
  );


  $templateCache.put('home/home.html',
    "<ion-view title=\"Home\" cache-view=\"false\" ng-controller=\"CTRL_HOME as $ctrl\"><ion-header-bar class=\"bar-positive\"><h1 class=\"title\">Home</h1></ion-header-bar><ion-content class=\"padding\"><div>Home</div></ion-content></ion-view>"
  );


  $templateCache.put('history/history.html',
    "<ion-view title=\"History\" cache-view=\"false\" ng-controller=\"CTRL_History as $ctrl\"><ion-header-bar class=\"bar-positive\"><h1 class=\"title\">History</h1></ion-header-bar><ion-content class=\"padding\"><div>History</div></ion-content></ion-view>"
  );

}]);
