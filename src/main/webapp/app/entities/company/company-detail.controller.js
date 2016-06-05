(function() {
    'use strict';

    angular
        .module('tg24HApp')
        .controller('CompanyDetailController', CompanyDetailController);

    CompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Company', 'User'];

    function CompanyDetailController($scope, $rootScope, $stateParams, entity, Company, User) {
        var vm = this;

        vm.company = entity;

        vm.load = load;
        vm.manager;

        var unsubscribe = $rootScope.$on('tg24HApp:companyUpdate', function(event, result) {
            vm.company = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.load();

        function load () {
            User.get({login: vm.company.manager}, function(result) {
                vm.manager = result;
            });
        }
    }
})();
