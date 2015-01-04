'use strict';

/* Services */

envyLeagueApp.factory('Cricket', function ($resource) {
    return $resource('rest/cricket', {}, {
    });
});

envyLeagueApp.factory('CricketLeague', function ($resource) {
    return $resource('rest/cricket/league', {}, {
        'request':   {method:'PUT'},
    });
});

envyLeagueApp.factory('AdminLeague', function ($resource) {
    return $resource('rest/admin/league', {}, {
    });
});
