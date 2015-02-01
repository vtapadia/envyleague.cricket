'use strict';

/* Controllers */

envyLeagueApp.controller('CricMyLeaguesController', function ($scope, $cookies, $location, Session, CricketLeague) {
    $scope.error = null;
    $scope.errorMessage = null;
    $scope.updateVisible = true;
    //Ordering is important, used in template view
    $scope.tabs = [
        {
            name: 'Registered',
            disabled: false,
            hidden:false
        },
        {
            name: 'Pending',
            disabled: false,
            hidden:false
        },
        {
            name: 'Cancelled',
            disabled: false,
            hidden:false
        },
        {
            name: 'Owned',
            disabled: false,
            hidden:false
        },
    ];
    $scope.activeTab = $scope.tabs[0].name;
    $scope.setActive = function(data) {
        $scope.activeTab = data;
    };

    CricketLeague.query({},
        function(data, responseHeaders) {
            $scope.leagues = data;
            $scope.ownedLeagues = [];
            $scope.registeredLeagues = [];
            $scope.pendingLeagues = [];
            $scope.cancelledLeagues = [];
            for (var i=0; i<$scope.leagues.length; i++) {
                if (angular.equals($scope.leagues[i].owner.login,Session.login)) {
                    $scope.ownedLeagues.push($scope.leagues[i]);
                }
                if (angular.equals($scope.leagues[i].status,'ACTIVE')) {
                    for (var j=0; j<$scope.leagues[i].players.length; j++) {
                        if (angular.equals($scope.leagues[i].players[j].user,Session.login)) {
                            //Found logged in user one
                            if (angular.equals($scope.leagues[i].players[j].status,'ACTIVE')) {
                                $scope.registeredLeagues.push($scope.leagues[i]);
                            } else if (angular.equals($scope.leagues[i].players[j].status,'PENDING')) {
                                $scope.pendingLeagues.push($scope.leagues[i]);
                            } else if (angular.equals($scope.leagues[i].players[j].status,'CANCELLED')) {
                                $scope.cancelledLeagues.push($scope.leagues[i]);
                            }
                        }
                    }
                }
            }
            if ($scope.pendingLeagues.length == 0) {
                $scope.tabs[1].disabled = true;
                $scope.tabs[1].hidden = true;
            }
            if ($scope.cancelledLeagues.length == 0) {
                $scope.tabs[2].disabled = true;
                $scope.tabs[2].hidden = true;
            }
            if ($scope.ownedLeagues.length == 0) {
                $scope.tabs[3].disabled = true;
                $scope.tabs[3].hidden = true;
            }
        },
        function(httpResponse) {
            $scope.error = "ERROR";
            $scope.errorMessage = httpResponse.data.message;
        }
    );
    $scope.updatePredictions = function(data) {
        $cookies.preferredLeague = data.name;
        $location.path('/cricket/predictions').replace();
    };
    $scope.viewPerformance = function(data) {
        $cookies.preferredLeague = data.name;
        $location.path('/cricket/performance').replace();
    };
    $scope.viewLeaderBoard = function(data) {
        $cookies.preferredLeague = data.name;
        $location.path('/cricket/leaders').replace();
    };
    $scope.manageLeague = function(data) {
        $cookies.preferredLeague = data.name;
        $location.path('/cricket/manageLeague').replace();
    }
});

envyLeagueApp.controller('CricManageLeagueController', function($scope, $cookies, CricketLeague) {
    $scope.error = null;
    $scope.errorMessage = null;
    $scope.updateVisible = true;
    CricketLeague.query({owned:'true', league:$cookies.preferredLeague},
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
    };
});

envyLeagueApp.controller('CricPredictionController',
    function ($scope, $cookies, CricketPrediction, CricketUserLeague, CricketMatch, $filter, $modal) {

    $scope.error = null;
    $scope.changeLeague = function() {
        $cookies.preferredLeague = $scope.selectedLeague;
        for (var i=0;i<$scope.matches.length;i++) {
            var selectedPredictions = $filter('filter')($scope.matches[i].predictions, {league:$scope.selectedLeague});
            if (angular.isArray(selectedPredictions) && selectedPredictions.length==1) {
                $scope.matches[i].prediction = selectedPredictions[0];
            } else {
                $scope.matches[i].prediction = {};
            }
        }
    }

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
                        break;
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
                            {value:"Draw",display:"Draw"},
                            {value:$scope.matches[i].teamB,display:$scope.matches[i].teamB + ' Winner'}
                        ];
                    }
                    $scope.changeLeague();//Just to set the prediction object on match correct.
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

  $scope.viewPredictionModal = function(selectedMatch) {

      var modalInstance = $modal.open({
          templateUrl: 'predictionModalContent.html',
          controller: 'PredictionModalInstanceCtrl',
          size: 'lg',
          resolve: {
              match: function ()
                  {return selectedMatch;},
              prediction: function ()
                  {return selectedMatch.prediction;},
              league: function ()
                  {return $scope.selectedLeague;}
          }
      });

    modalInstance.result.then(function (prediction) {
        var matchIndex = -1;
        for (var i=0;i<$scope.matches.length;i++) {
            if ($scope.matches[i].number==selectedMatch.number) {
                matchIndex = i;break;
            }
        }
        var indexFound = -1;
        for (var i=0;i<$scope.matches[matchIndex].predictions.length; i++) {
            if ($scope.matches[matchIndex].predictions[i].league == prediction.league) {
                indexFound = i;break;
            }
        }
        if (indexFound == -1) {
            $scope.matches[matchIndex].predictions.push(prediction);
        } else {
            $scope.matches[matchIndex].predictions[indexFound] = prediction;
        }
        $scope.matches[matchIndex].prediction = prediction;
    }, function () {
        //When modal is dismissed
    });
  };
});
envyLeagueApp.controller('PredictionModalInstanceCtrl',
    function ($scope, $modalInstance, $filter, CricketPrediction, match, prediction, league) {
        $scope.match = match;
        $scope.selectedLeague = league;
        $scope.prediction = angular.copy(prediction);

        if (angular.isUndefined($scope.prediction.teamWinner)) {
            $scope.prediction.teamWinner = 'Draw';
        }

        $scope.ok = function () {
            if ($scope.prediction.match == undefined) {
                //New request
                $scope.prediction.match = $scope.match.number;
                $scope.prediction.league = $scope.selectedLeague;
            }
            CricketPrediction.save($scope.prediction,
                function(data, responseHeaders) {
                },
                function(httpResponse) {
                    $scope.error = "ERROR";
                    $scope.errorMessage = httpResponse.data.message;
                }
            );
            $modalInstance.close($scope.prediction);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }
);

envyLeagueApp.controller('CricPerformanceController',
    function ($scope, $cookies, $modal, CricketPrediction, CricketUserLeague, CricketMatch, $filter) {

    $scope.error = null;
    $scope.changeLeague = function() {
        $cookies.preferredLeague = $scope.selectedLeague;
        for (var i=0;i<$scope.matches.length;i++) {
            var selectedPredictions = $filter('filter')($scope.matches[i].predictions, {league:$scope.selectedLeague});
            if (angular.isArray(selectedPredictions) && selectedPredictions.length==1) {
                $scope.matches[i].prediction = selectedPredictions[0];
            } else {
                $scope.matches[i].prediction = {};
            }
        }
    }
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
                    $scope.changeLeague();//Just to set the prediction object on match correct.
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
  $scope.viewPerformanceModal = function(selectedMatch) {

      var modalInstance = $modal.open({
          templateUrl: 'performanceModalContent.html',
          controller: 'PerformanceModalInstanceCtrl',
          //size: 'sm',
          resolve: {
              match: function ()
                  {return selectedMatch;},
              prediction: function ()
                  {return selectedMatch.prediction;},
              league: function ()
                  {return $scope.selectedLeague;}
          }
      });

    modalInstance.result.then(function (prediction) {
        //Do Nothing
    }, function () {
        //When modal is dismissed
    });
  };
});

envyLeagueApp.controller('PerformanceModalInstanceCtrl',
    function ($scope, $modalInstance, $filter, CricketPrediction, match, prediction, league) {
        $scope.match = match;
        $scope.selectedLeague = league;
        $scope.prediction = angular.copy(prediction);

        if (angular.isUndefined($scope.prediction.teamWinner)) {
            $scope.prediction.teamWinner = 'Draw';
        }

        $scope.ok = function () {
            $modalInstance.dismiss('ok');
        };
    }
);

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
                data.userLeague = {statusDescription : 'Pending Approval'};
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