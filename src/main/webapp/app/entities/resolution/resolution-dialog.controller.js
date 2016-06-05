(function() {
    'use strict';

    angular
        .module('tg24HApp')
        .controller('ResolutionDialogController', ResolutionDialogController);

    ResolutionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Resolution'];

    function ResolutionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Resolution) {
        var vm = this;

        vm.resolution = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.resolution.id !== null) {
                Resolution.update(vm.resolution, onSaveSuccess, onSaveError);
            } else {
                Resolution.save(vm.resolution, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tg24HApp:resolutionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
