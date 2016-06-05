(function() {
    'use strict';

    angular
        .module('tg24HApp')
        .factory('Manager', Manager);

    Manager.$inject = ['$resource'];

    function Manager ($resource) {
        var service = $resource('api/managers', {}, {
            'query': { method: 'GET', isArray: true}
        });

        return service;
    }
})();
