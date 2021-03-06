'use strict';

/* App Module */
var envyLeagueApp = angular.module('envyLeagueApp', ['http-auth-interceptor',
    'ngResource', 'ngRoute', 'ngCookies', 'ui.bootstrap', 'ui.slider']);


envyLeagueApp.filter('unsafe', ['$sce', function ($sce) {
     return function (val) {
         return $sce.trustAsHtml(val);
     };
 }]);

envyLeagueApp.constant('USER_ROLES', {
        'all': '*',
        'admin': 'ADMIN',
        'league': 'LEAGUE',
        'user': 'USER'
    });

envyLeagueApp.constant('STATUS',
    ['ACTIVE', 'PENDING', 'CANCELLED']
);

envyLeagueApp.config(
    function ($routeProvider, $httpProvider, $provide, USER_ROLES) {
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
            .when('/password', {
                templateUrl: 'views/password.html',
                controller: 'PasswordController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            //Cricket Leagues
            .when('/cricket/myleagues', {
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
            .when('/cricket/performance', {
                templateUrl: 'views/cricket/performance.html',
                controller: 'CricPerformanceController',
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
            .when('/cricket/manageLeague', {
                templateUrl: 'views/cricket/manage_league.html',
                controller: 'CricManageLeagueController',
                access: {
                    authorizedRoles: [USER_ROLES.league]
                }
            })
            .when('/cricket/leaders', {
                templateUrl: 'views/cricket/leaders.html',
                controller: 'CricLeadersController',
                access: {
                    authorizedRoles: [USER_ROLES.user]
                }
            })
            .when('/cricket/all/leagues', {
                templateUrl: 'views/cricket/all_leagues.html',
                controller: 'CricAllLeaguesController',
                access: {
                    authorizedRoles: [USER_ROLES.user]
                }
            })
            .when('/cricket/rules', {
                templateUrl: 'views/cricket/rules.html',
                controller: 'CricRulesController',
                access: {
                    authorizedRoles: [USER_ROLES.user]
                }
            })
            //Admin League
            .when('/admin/league', {
                templateUrl: 'views/admin/league.html',
                controller: 'AdminLeagueController',
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .when('/admin/match', {
                templateUrl: 'views/admin/match.html',
                controller: 'AdminMatchController',
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .when('/error', {
                templateUrl: 'views/error.html',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .otherwise({
                templateUrl: 'views/main.html',
                controller: 'MainController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            });
//    $provide.decorator('tabDirective', function($delegate) {
//        var directive = $delegate[0];
//        angular.extend(directive.scope, {
//            hidden:'@'
//        });
//        return $delegate;
//    });
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
).run(function($rootScope, $location, $templateCache, $http, AuthenticationSharedService, USER_ROLES, STATUS, Session) {
    $rootScope.authenticated = false;
    $templateCache.put("template/popover/popover.html",
      "<div class=\"popover popover-envy {{placement}}\" ng-class=\"{ in: isOpen(), fade: animation() }\">\n" +
      "  <div class=\"arrow\"></div>\n" +
      "\n" +
      "  <div class=\"popover-inner\">\n" +
      "      <h3 class=\"popover-title\" ng-bind-html=\"title | unsafe\" ng-show=\"title\"></h3>\n" +
      "      <div class=\"popover-content\"ng-bind-html=\"content | unsafe\"></div>\n" +
      "  </div>\n" +
      "</div>\n" +
      "");
    $templateCache.put("template/tabs/tab.html",
        "<li ng-class=\"{active: active, disabled: disabled, hidden: hidden}\">\n" +
        "  <a href ng-click=\"select()\" tab-heading-transclude>{{heading}}</a>\n" +
        "</li>\n" +
        "");
    $rootScope.$on('$routeChangeStart', function (event, next) {
        $rootScope.isAuthorized = AuthenticationSharedService.isAuthorized;
        $rootScope.userRoles = USER_ROLES;
        $rootScope.STATUS = STATUS;
        AuthenticationSharedService.valid(next.access.authorizedRoles);
    });
    // Call when the the client is confirmed
    $rootScope.$on('event:auth-loginConfirmed', function(data) {
        $rootScope.authenticated = true;
        $rootScope.authenticatedName = Session.name;
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
