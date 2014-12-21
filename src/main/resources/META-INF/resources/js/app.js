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

    // Call when the the client is confirmed
    $rootScope.$on('event:auth-loginConfirmed', function(data) {
        $rootScope.authenticated = true;
        if ($location.path() === "/login") {
            var search = $location.search();
            if (search.redirect !== undefined) {
                $location.path(search.redirect).search('redirect', null).replace();
            } else {
                $location.path('/').replace();
            }
        }
    });

    // Call when the 401 response is returned by the server
    $rootScope.$on('event:auth-loginRequired', function(rejection) {
        Session.invalidate();
        $rootScope.authenticated = false;
        if ($location.path() !== "/" && $location.path() !== "" && $location.path() !== "/register" &&
                $location.path() !== "/activate" && $location.path() !== "/login") {
            var redirect = $location.path();
            $location.path('/login').search('redirect', redirect).replace();
        }
    });

    // Call when the 403 response is returned by the server
    $rootScope.$on('event:auth-notAuthorized', function(rejection) {
        $rootScope.errorMessage = 'errors.403';
        $location.path('/error').replace();
    });

    // Call when the user logs out
    $rootScope.$on('event:auth-loginCancelled', function() {
        $location.path('');
    });

});
