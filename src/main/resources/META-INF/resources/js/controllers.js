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
    var strength = {
        colors: ['#F00', '#F90', '#FF0', '#9F0', '#0F0'],
        mesureStrength: function (p) {

            var _force = 0;
            var _regex = /[$-/:-?{-~!"^_`\[\]]/g; // "

            var _lowerLetters = /[a-z]+/.test(p);
            var _upperLetters = /[A-Z]+/.test(p);
            var _numbers = /[0-9]+/.test(p);
            var _symbols = _regex.test(p);

            var _flags = [_lowerLetters, _upperLetters, _numbers, _symbols];
            var _passedMatches = $.grep(_flags, function (el) { return el === true; }).length;

            _force += 2 * p.length + ((p.length >= 10) ? 1 : 0);
            _force += _passedMatches * 10;

            // penality (short password)
            _force = (p.length <= 6) ? Math.min(_force, 10) : _force;

            // penality (poor variety of characters)
            _force = (_passedMatches == 1) ? Math.min(_force, 10) : _force;
            _force = (_passedMatches == 2) ? Math.min(_force, 20) : _force;
            _force = (_passedMatches == 3) ? Math.min(_force, 40) : _force;

            return _force;

        },
        getColor: function (s) {

            var idx = 0;
            if (s <= 10) { idx = 0; }
            else if (s <= 20) { idx = 1; }
            else if (s <= 30) { idx = 2; }
            else if (s <= 40) { idx = 3; }
            else { idx = 4; }

            return { idx: idx + 1, col: this.colors[idx] };
        }
    };
    $scope.passwordStrength = '<div id="strength"><ul id="strengthBar">' +
                           '<li class="point"></li><li class="point"></li><li class="point"></li><li class="point"></li><li class="point"></li>' +
                         '</ul></div>';
    $scope.$watch("registerAccount.password", function(newValue, oldValue) {
       if (newValue) {
           var iElement = angular.element(document.getElementById('strength'));
           var c = strength.getColor(strength.mesureStrength(newValue));
           iElement.removeClass('ng-hide');
           iElement.find('ul').children('li')
               .css({ "background": "#DDD" })
               .slice(0, c.idx)
               .css({ "background": c.col });

           console.log("newValue " + newValue);
       }
    });

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

