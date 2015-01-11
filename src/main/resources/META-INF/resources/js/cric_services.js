'use strict';

/* Services */

envyLeagueApp.factory('Cricket', function ($resource) {
    return $resource('rest/cricket', {}, {
    });
});

envyLeagueApp.factory('CricketUserLeague', function ($resource) {
    return $resource('rest/cricket/userLeague', {}, {
    });
});

envyLeagueApp.factory('CricketLeague', function ($resource) {
    return $resource('rest/cricket/league', {}, {
        'request':   {method:'PUT'},
    });
});

envyLeagueApp.factory('CricketPrediction', function ($resource) {
    return $resource('rest/cricket/prediction', {}, {
    });
});

envyLeagueApp.factory('CricketMatch', function ($resource) {
    return $resource('rest/cricket/match', {}, {
    });
});

envyLeagueApp.factory('AdminLeague', function ($resource) {
    return $resource('rest/admin/league', {}, {
    });
});
