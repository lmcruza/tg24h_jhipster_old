(function() {
    'use strict';
    angular
        .module('tg24HApp')
        .factory('Instance', Instance);

    Instance.$inject = ['$resource', 'DateUtils'];

    function Instance ($resource, DateUtils) {
        var resourceUrl =  'api/instances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertLocalDateFromServer(data.createDate);
                        data.updateDate = DateUtils.convertLocalDateFromServer(data.updateDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocalDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocalDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocalDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocalDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
