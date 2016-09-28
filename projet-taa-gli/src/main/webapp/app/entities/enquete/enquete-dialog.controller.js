(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('EnqueteDialogController', EnqueteDialogController);

    EnqueteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Enquete', 'Etudiant', 'Stage'];

    function EnqueteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Enquete, Etudiant, Stage) {
        var vm = this;

        vm.enquete = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.enquetes = Etudiant.query({filter: 'enquete-is-null'});
        $q.all([vm.enquete.$promise, vm.enquetes.$promise]).then(function() {
            if (!vm.enquete.enquete || !vm.enquete.enquete.id) {
                return $q.reject();
            }
            return Etudiant.get({id : vm.enquete.enquete.id}).$promise;
        }).then(function(enquete) {
            vm.enquetes.push(enquete);
        });
        vm.stages = Stage.query({filter: 'enquete-is-null'});
        $q.all([vm.enquete.$promise, vm.stages.$promise]).then(function() {
            if (!vm.enquete.stage || !vm.enquete.stage.id) {
                return $q.reject();
            }
            return Stage.get({id : vm.enquete.stage.id}).$promise;
        }).then(function(stage) {
            vm.stages.push(stage);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.enquete.id !== null) {
                Enquete.update(vm.enquete, onSaveSuccess, onSaveError);
            } else {
                Enquete.save(vm.enquete, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetTaaGliApp:enqueteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateEnquete = false;
        vm.datePickerOpenStatus.enqDateDebut = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
