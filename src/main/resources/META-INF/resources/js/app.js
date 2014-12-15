'use strict';

/* App Module */
var envyLeagueApp = angular.module('envyLeagueApp', ['http-auth-interceptor',
    'ngResource', 'ngRoute', 'ngCookies', 'ui.bootstrap']);

envyLeagueApp.config(
    function ($routeProvider, $httpProvider) {
        $routeProvider
            .when('/register', {
                templateUrl: 'views/register.html',
                controller: 'RegisterController'
            })
            .when('/activate', {
                templateUrl: 'views/activate.html',
                controller: 'ActivationController'
            })
            .when('/login', {
                templateUrl: 'views/login.html',
                controller: 'LoginController'
            })
            .when('/logout', {
                templateUrl: 'views/main.html',
                controller: 'LogoutController'
            })
            .otherwise({
                templateUrl: 'views/main.html',
                controller: 'MainController'
            });
    }
).run(function($rootScope, $location, $http) {
    $rootScope.authenticated = false;
});
