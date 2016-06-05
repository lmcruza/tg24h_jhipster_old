(function() {
    'use strict';

    angular
        .module('tg24HApp')
        .controller('CompanyDialogController', CompanyDialogController);

    CompanyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Company', 'Manager'];

    function CompanyDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Company, Manager) {
        var vm = this;

        vm.company = entity;
        vm.clear = clear;
        vm.save = save;

        vm.load = load;
        vm.managers;

        $timeout(function() {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.company.id !== null) {
                Company.update(vm.company, onSaveSuccess, onSaveError);
            } else {
                Company.save(vm.company, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tg24HApp:companyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.load();

        function load (login) {
            Manager.query({}, function(result) {
                vm.managers = result;
            });
        }

    }
})();