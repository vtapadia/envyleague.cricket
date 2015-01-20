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
            $scope.errorMessage = httpResponse.data.message;
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
                $scope.errorMessage = httpResponse.data.message;
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
                if ($scope.leagues.length > 1) {
                    $cookies.preferredLeague = $scope.leagues[0].name;
                    $scope.selectedLeague = $cookies.preferredLeague;
                }
            } else {
                for (var i=0;i<$scope.leagues.length; i++) {
                    if ($scope.leagues[i].name == $cookies.preferredLeague) {
                        $scope.selectedLeague = $cookies.preferredLeague;
                    }
                }
                if ($scope.selectedLeague == undefined && $scope.leagues.length > 0) {
                    $cookies.preferredLeague = $scope.leagues[0].name;
                    $scope.selectedLeague = $cookies.preferredLeague;
                }
            }
            CricketMatch.query({predictions:'true', future:'true'},
                function(data, responseHeaders) {
                    $scope.matches = data;
                    for (var i=0;i<$scope.matches.length;i++) {
                        $scope.matches[i].winnerOptions= [
                            {value:$scope.matches[i].teamA,display:$scope.matches[i].teamA + ' Winner'},
                            {value:"",display:"Draw"},
                            {value:$scope.matches[i].teamB,display:$scope.matches[i].teamB + ' Winner'}
                        ];
                        $scope.matches[i].prediction = {};
                    }
                },
                function(httpResponse) {
                    $scope.error = "ERROR";
                    $scope.errorMessage = httpResponse.data.message;
                }
            );
        },
        function(httpResponse) {
            $scope.error = "ERROR";
            $scope.errorMessage = httpResponse.data.message;
        }
    );

    $scope.changeLeague = function() {
        $cookies.preferredLeague = $scope.selectedLeague;
    }

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
                    $scope.errorMessage = httpResponse.data.message;
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

envyLeagueApp.controller('CricPerformanceController',
    function ($scope, $cookies, CricketPrediction, CricketUserLeague, CricketMatch, $filter) {

    $scope.error = null;
    CricketUserLeague.query({},
        function(data, responseHeaders) {
            $scope.error = null;
            $scope.errorMessage = null;
            $scope.leagues = $filter('filter')(data, {userLeague: {status: 'ACTIVE'}});
            if ($cookies.preferredLeague == 'undefined') {
                if ($scope.leagues.length > 1) {
                    $cookies.preferredLeague = $scope.leagues[0].name;
                    $scope.selectedLeague = $cookies.preferredLeague;
                }
            } else {
                for (var i=0;i<$scope.leagues.length; i++) {
                    if ($scope.leagues[i].name == $cookies.preferredLeague) {
                        $scope.selectedLeague = $cookies.preferredLeague;
                    }
                }
                if ($scope.selectedLeague == undefined && $scope.leagues.length > 0) {
                    $cookies.preferredLeague = $scope.leagues[0].name;
                    $scope.selectedLeague = $cookies.preferredLeague;
                }
            }
            CricketMatch.query({predictions:'true', future:'false'},
                function(data, responseHeaders) {
                    $scope.matches = data;
                },
                function(httpResponse) {
                    $scope.error = "ERROR";
                    $scope.errorMessage = httpResponse.data.message;
                }
            );
        },
        function(httpResponse) {
            $scope.error = "ERROR";
            $scope.errorMessage = httpResponse.data.message;
        }
    );

    $scope.changeLeague = function() {
        $cookies.preferredLeague = $scope.selectedLeague;
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


envyLeagueApp.controller('CricLeadersController',
    function ($scope, $cookies, CricketPrediction, CricketUserLeague, CricketMatch, CricketLeaders, $filter) {
    $scope.error = null;
    CricketUserLeague.query({},
        function(data, responseHeaders) {
            $scope.error = null;
            $scope.errorMessage = null;
            $scope.leagues = $filter('filter')(data, {userLeague: {status: 'ACTIVE'}});
            if ($cookies.preferredLeague == 'undefined') {
                if ($scope.leagues.length > 0) {
                    $cookies.preferredLeague = $scope.leagues[0].name;
                    $scope.selectedLeague = $cookies.preferredLeague;
                }
            } else {
                for (var i=0;i<$scope.leagues.length; i++) {
                    if ($scope.leagues[i].name == $cookies.preferredLeague) {
                        $scope.selectedLeague = $cookies.preferredLeague;
                    }
                }
                if ($scope.selectedLeague == undefined && $scope.leagues.length > 0) {
                    $cookies.preferredLeague = $scope.leagues[0].name;
                    $scope.selectedLeague = $cookies.preferredLeague;
                }
            }
            if ($scope.selectedLeague != undefined) {
                CricketLeaders.query({'league': $scope.selectedLeague},
                    function(data, responseHeaders) {
                        $scope.users = data;
                    },
                    function(httpResponse) {
                        $scope.error = "ERROR";
                        $scope.errorMessage = httpResponse.data.message;
                    }
                );
            }
        },
        function(httpResponse) {
            $scope.error = "ERROR";
            $scope.errorMessage = httpResponse.data;
        }
    );
    $scope.changeLeague = function() {
        $scope.error = null;
        $scope.users = null;
        $cookies.preferredLeague = $scope.selectedLeague;
        CricketLeaders.query({'league': $scope.selectedLeague},
            function(data, responseHeaders) {
                $scope.users = data;
            },
            function(httpResponse) {
                $scope.error = "ERROR";
                $scope.errorMessage = httpResponse.data;
            }
        );
    }

});

envyLeagueApp.controller('CricRulesController', function ($scope) {
});

//Admin Controllers
envyLeagueApp.controller('AdminLeagueController', function ($scope, AdminLeague) {
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

envyLeagueApp.controller('AdminMatchController', function ($scope, CricketMatch, AdminMatch) {
    $scope.updateVisible = true;
    CricketMatch.query({predictions:'false', future:'false'},
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
    $scope.finalize = function(match) {
        $scope.updateVisible = false;
        AdminMatch.save(match,
            function(data, responseHeaders) {
                $scope.updateVisible = true;
                match.finalized = true;
            },
            function(httpResponse) {
                $scope.updateVisible = true;
                $scope.error = "ERROR";
                $scope.errorMessage = httpResponse.data;
            }
        );
    }
});