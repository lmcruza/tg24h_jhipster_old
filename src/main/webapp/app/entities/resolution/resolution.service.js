(function() {
    'use strict';
    angular
        .module('tg24HApp')
        .factory('Resolution', Resolution);

    Resolution.$inject = ['$resource'];

    function Resolution ($resource) {
        var resourceUrl =  'api/resolutions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
