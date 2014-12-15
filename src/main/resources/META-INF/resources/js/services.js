'use strict';

/* Services */

envyLeagueApp.factory('Register', function ($resource) {
        return $resource('rest/user/register', {}, {
        });
    });

envyLeagueApp.factory('Activate', function ($resource) {
        return $resource('rest/user/activate', {}, {
        });
    });
