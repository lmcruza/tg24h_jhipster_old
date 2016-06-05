(function() {
    'use strict';

    angular
        .module('tg24HApp')
        .controller('InstanceDialogController', InstanceDialogController);

    InstanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Instance'];

    function InstanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Instance) {
        var vm = this;

        vm.instance = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.instance.id !== null) {
                Instance.update(vm.instance, onSaveSuccess, onSaveError);
            } else {
                Instance.save(vm.instance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tg24HApp:instanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;
        vm.datePickerOpenStatus.updateDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
