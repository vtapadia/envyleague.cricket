'use strict';

/* Controllers */

envyLeagueApp.controller('CricMyLeaguesController', function ($scope, SocialService) {
});

envyLeagueApp.controller('CricPredictionController', function ($scope, SocialService) {
});

envyLeagueApp.controller('CricNewLeagueController', function ($scope, CricketLeague) {
    $scope.success = null;
    $scope.error = null;
    $scope.errorLeagueExists = null;
    $scope.request = function() {
        CricketLeague.save($scope.league,
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

envyLeagueApp.controller('CricAllLeaguesController', function ($scope, SocialService) {
});

//Admin Controllers
envyLeagueApp.controller('AdminLeagueController', function ($scope, SocialService) {
});
