(function() {
    'use strict';

    angular
        .module('tg24HApp')
        .controller('InstanceDetailController', InstanceDetailController);

    InstanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Instance'];

    function InstanceDetailController($scope, $rootScope, $stateParams, entity, Instance) {
        var vm = this;

        vm.instance = entity;

        var unsubscribe = $rootScope.$on('tg24HApp:instanceUpdate', function(event, result) {
            vm.instance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
