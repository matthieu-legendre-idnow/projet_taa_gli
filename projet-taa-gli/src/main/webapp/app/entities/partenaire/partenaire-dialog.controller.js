(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('PartenaireDialogController', PartenaireDialogController);

    PartenaireDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Partenaire'];

    function PartenaireDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Partenaire) {
        var vm = this;

        vm.partenaire = entity;
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
            if (vm.partenaire.id !== null) {
                Partenaire.update(vm.partenaire, onSaveSuccess, onSaveError);
            } else {
                Partenaire.save(vm.partenaire, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetTaaGliApp:partenaireUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.pDateMaj = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
