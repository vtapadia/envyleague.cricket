'use strict';

/* Controllers */

envyLeagueApp.controller('HomeController', function ($scope) {
});

envyLeagueApp.controller('MainController', function ($scope) {
});

envyLeagueApp.controller('AdminController', function ($scope) {
});

envyLeagueApp.controller('MenuController', function ($scope) {
});

envyLeagueApp.controller('PasswordController', function ($scope, Password) {
    $scope.success = null;
    $scope.error = null;
    $scope.doNotMatch = null;
    $scope.changePassword = function () {
        if ($scope.password != $scope.confirmPassword) {
            $scope.doNotMatch = "ERROR";
        } else {
            $scope.doNotMatch = null;
            Password.save($scope.password,
                function (value, responseHeaders) {
                    $scope.error = null;
                    $scope.success = 'OK';
                    $scope.password = null;
                    $scope.confirmPassword = null;
                },
                function (httpResponse) {
                    $scope.success = null;
                    $scope.error = "ERROR";
                });
        }
    };
});

envyLeagueApp.controller('RegisterController', function ($scope, Register) {
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
            Register.save($scope.registerAccount,
                function (value, responseHeaders) {
                    $scope.success = 'OK';
                },
                function (httpResponse) {
                    if (httpResponse.status === 400 && httpResponse.data === "login already in use") {
                        $scope.error = null;
                        $scope.errorUserExists = "ERROR";
                    } else if (httpResponse.status === 400 && httpResponse.data === "e-mail address already in use") {
                        $scope.error = null;
                        $scope.errorEmailExists = "ERROR";
                    } else {
                        $scope.error = "ERROR";
                    }
                }
            );
        }
    };
});

envyLeagueApp.controller('ActivationController', function ($scope, $routeParams, Activate) {
    Activate.get({key: $routeParams.key},
        function (value, responseHeaders) {
            $scope.error = null;
            $scope.success = 'OK';
        },
        function (httpResponse) {
            $scope.success = null;
            $scope.error = "ERROR";
        }
    );
});
envyLeagueApp.controller('LoginController', function ($scope, AuthenticationSharedService) {
    $scope.rememberMe = false; //TODO Need to revisit remember me
    $scope.login = function () {
        AuthenticationSharedService.login({
            username: $scope.username,
            password: $scope.password,
            rememberMe: $scope.rememberMe
        });
    }
});
envyLeagueApp.controller('LogoutController', function ($scope, AuthenticationSharedService) {
    AuthenticationSharedService.logout();
});

