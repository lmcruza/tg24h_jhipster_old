(function() {
    'use strict';
    angular
        .module('tg24HApp')
        .factory('Product', Product);

    Product.$inject = ['$resource', 'DateUtils'];

    function Product ($resource, DateUtils) {
        var resourceUrl =  'api/products/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.activationDate = DateUtils.convertLocalDateFromServer(data.activationDate);
                        data.deactivationDate = DateUtils.convertLocalDateFromServer(data.deactivationDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.activationDate = DateUtils.convertLocalDateToServer(data.activationDate);
                    data.deactivationDate = DateUtils.convertLocalDateToServer(data.deactivationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.activationDate = DateUtils.convertLocalDateToServer(data.activationDate);
                    data.deactivationDate = DateUtils.convertLocalDateToServer(data.deactivationDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
