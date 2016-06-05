(function() {
    'use strict';

    angular
        .module('tg24HApp')
        .controller('ResolutionDetailController', ResolutionDetailController);

    ResolutionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Resolution'];

    function ResolutionDetailController($scope, $rootScope, $stateParams, entity, Resolution) {
        var vm = this;

        vm.resolution = entity;

        var unsubscribe = $rootScope.$on('tg24HApp:resolutionUpdate', function(event, result) {
            vm.resolution = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
