'use strict';

/* Controllers */

envyLeagueApp.controller('CricMyLeaguesController', function ($scope, CricketLeague) {
    $scope.error = null;
    $scope.errorMessage = null;
    $scope.updateVisible = true;
    CricketLeague.query({},
        function(data, responseHeaders) {
            $scope.leagues = data;
        },
        function(httpResponse) {
            $scope.error = "ERROR";
            $scope.errorMessage = httpResponse.data;
        }
    );
    $scope.update = function(data) {
        $scope.updateVisible = false;
        $scope.error = null;
        $scope.errorMessage = null;
        CricketLeague.save(data,
            function(data, responseHeaders) {
                $scope.updateVisible = true;
            },
            function(httpResponse) {
                $scope.updateVisible = true;
                $scope.error = "ERROR";
                $scope.errorMessage = httpResponse.data;
            }
        );
    };});

envyLeagueApp.controller('CricPredictionController', function ($scope, SocialService) {
});

envyLeagueApp.controller('CricNewLeagueController', function ($scope, CricketLeague) {
    $scope.success = null;
    $scope.error = null;
    $scope.errorLeagueExists = null;
    $scope.request = function() {
        CricketLeague.request($scope.league,
            function (value, responseHeaders) {
                $scope.success = 'OK';
            },
            function (httpResponse) {
                if (httpResponse.status === 400 && httpResponse.data === "League name already in use") {
                    $scope.error = null;
                    $scope.errorLeagueExists = "ERROR";
                } else {
                    $scope.error = "ERROR";
                }
            }
        );
    }
});

envyLeagueApp.controller('CricAllLeaguesController', function ($scope, CricketUserLeague) {
    $scope.registerVisible = true;
    CricketUserLeague.query({},
        function(data, responseHeaders) {
            $scope.leagues = data;
        },
        function(httpResponse) {
            $scope.error = "ERROR";
            $scope.errorMessage = httpResponse.data;
        }
    );
    $scope.register = function(data) {
        CricketUserLeague.save(data,
            function(responseData, responseHeaders) {
                data.userLeague = {status : 'PENDING'};
                $scope.registerVisible = true;
            },
            function(httpResponse) {
                $scope.registerVisible = true;
                $scope.error = "ERROR";
                $scope.errorMessage = httpResponse.data;
            }
        );
    };
});

//Admin Controllers
envyLeagueApp.controller('AdminLeagueController', function ($scope, SocialService, AdminLeague) {
    $scope.updateVisible = true;
    AdminLeague.query({},
        function(data, responseHeaders) {
            $scope.leagues = data;
        },
        function(httpResponse) {
            $scope.error = "ERROR";
            $scope.errorMessage = httpResponse.data;
        }
    );

    $scope.update = function(data) {
        $scope.updateVisible = false;
        $scope.error = null;
        $scope.errorMessage = null;
        AdminLeague.save(data,
            function(data, responseHeaders) {
                $scope.updateVisible = true;
            },
            function(httpResponse) {
                $scope.updateVisible = true;
                $scope.error = "ERROR";
                $scope.errorMessage = httpResponse.data;
            }
        );
    };
});
