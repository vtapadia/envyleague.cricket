'use strict';

/* Controllers */

envyLeagueApp.controller('MainController', function ($scope) {
    });

envyLeagueApp.controller('AdminController', function ($scope) {
    });
envyLeagueApp.controller('MenuController', function ($scope) {
    });

envyLeagueApp.controller('RegisterController', function ($scope) {
        $scope.success = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.error = null;
        $scope.register = function () {
            if ($scope.registerAccount.password != $scope.confirmPassword) {
                $scope.doNotMatch = "ERROR";
            } else {
                $scope.doNotMatch = null;
                $scope.success = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;
                $scope.success = 'OK';
            }
        };
    });

envyLeagueApp.controller('ActivationController', function ($scope) {
    });
envyLeagueApp.controller('LoginController', function ($scope) {
    });
envyLeagueApp.controller('LogoutController', function ($scope) {
    });

