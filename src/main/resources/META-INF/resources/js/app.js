'use strict';

/* App Module */
var envyLeagueApp = angular.module('envyLeagueApp', ['http-auth-interceptor',
    'ngResource', 'ngRoute', 'ngCookies', 'ui.bootstrap']);

envyLeagueApp.constant('USER_ROLES', {
        'all': '*',
        'admin': 'ADMIN',
        'league': 'LEAGUE',
        'user': 'USER'
    });

envyLeagueApp.config(
    function ($routeProvider, $httpProvider, USER_ROLES) {
        $routeProvider
            .when('/register', {
                templateUrl: 'views/register.html',
                controller: 'RegisterController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/activate', {
                templateUrl: 'views/activate.html',
                controller: 'ActivationController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/login', {
                templateUrl: 'views/login.html',
                controller: 'LoginController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/logout', {
                templateUrl: 'views/main.html',
                controller: 'LogoutController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            //Cricket Leagues
            .when('/cricket/leagues', {
                templateUrl: 'views/cricket/my_leagues.html',
                controller: 'CricMyLeaguesController',
                access: {
                    authorizedRoles: [USER_ROLES.user]
                }
            })
            .when('/cricket/predictions', {
                templateUrl: 'views/cricket/predictions.html',
                controller: 'CricPredictionController',
                access: {
                    authorizedRoles: [USER_ROLES.user]
                }
            })
            .when('/cricket/league', {
                templateUrl: 'views/cricket/new_league.html',
                controller: 'CricNewLeagueController',
                access: {
                    authorizedRoles: [USER_ROLES.user]
                }
            })
            .when('/cricket/all/leagues', {
                templateUrl: 'views/cricket/all_leagues.html',
                controller: 'CricAllLeaguesController',
                access: {
                    authorizedRoles: [USER_ROLES.users]
                }
            })
            .otherwise({
                templateUrl: 'views/main.html',
                controller: 'MainController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            });
    $httpProvider.interceptors.push(['$rootScope', function($rootScope) {
      return {
        'request': function(config) {
               // same as above
               config.headers['Access-Control-Allow-Origin'] = '*';
               return config;
            }
        }
      }
      ]);
    }
).run(function($rootScope, $location, $http, AuthenticationSharedService, USER_ROLES, Session) {
    $rootScope.authenticated = false;

    $rootScope.$on('$routeChangeStart', function (event, next) {
        $rootScope.isAuthorized = AuthenticationSharedService.isAuthorized;
        $rootScope.userRoles = USER_ROLES;
        AuthenticationSharedService.valid(next.access.authorizedRoles);
    });
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
        $location.path('/').replace();
    });

});
