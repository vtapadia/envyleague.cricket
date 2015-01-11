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

envyLeagueApp.controller('CricPredictionController',
    function ($scope, $cookies, CricketPrediction, CricketUserLeague, CricketMatch, $filter) {

    $scope.error = null;
    $scope.saveOrUpdateVisible = true;
    CricketUserLeague.query({},
        function(data, responseHeaders) {
            $scope.error = null;
            $scope.errorMessage = null;
            $scope.leagues = $filter('filter')(data, {userLeague: {status: 'ACTIVE'}});
            if ($cookies.preferredLeague == 'undefined') {
                $scope.selectedLeague = $cookies.preferredLeague;
            } else {
                if ($scope.leagues.length > 1) {
                    $cookies.preferredLeague = $scope.leagues[0].name;
                    $scope.selectedLeague = $cookies.preferredLeague;
                }
            }
            CricketMatch.query({},
                function(data, responseHeaders) {
                    $scope.matches = data;
                    for (var i=0;i<$scope.matches.length;i++) {
                        $scope.matches[i].winnerOptions= [
                            {value:$scope.matches[i].teamA,display:$scope.matches[i].teamA + ' Winner'},
                            {value:"",display:"Draw"},
                            {value:$scope.matches[i].teamB,display:$scope.matches[i].teamB + ' Winner'}
                        ];
                    }
                },
                function(httpResponse) {
                    $scope.error = "ERROR";
                    $scope.errorMessage = httpResponse.data;
                }
            );
        },
        function(httpResponse) {
            $scope.error = "ERROR";
            $scope.errorMessage = httpResponse.data;
        }
    );

    $scope.saveOrUpdate = function(match, prediction) {
        $scope.saveOrUpdateVisible = false;
        if (prediction.match == undefined) {
            //New request
            prediction.match = match.number;
            prediction.league = $scope.selectedLeague;
            match.predictions.push(prediction);
            CricketPrediction.save(prediction,
                function(data, responseHeaders) {
                    $scope.saveOrUpdateVisible = true;
                    match.prediction = undefined;
                },
                function(httpResponse) {
                    $scope.saveOrUpdateVisible = true;
                    $scope.error = "ERROR";
                    $scope.errorMessage = httpResponse.data;
                }
            );
        } else {
            CricketPrediction.save(prediction,
                function(data, responseHeaders) {
                    $scope.saveOrUpdateVisible = true;
                },
                function(httpResponse) {
                    $scope.saveOrUpdateVisible = true;
                    $scope.error = "ERROR";
                    $scope.errorMessage = httpResponse.data;
                }
            );
        }
    }
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
