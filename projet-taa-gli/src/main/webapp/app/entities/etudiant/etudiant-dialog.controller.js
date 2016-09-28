(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('EtudiantDialogController', EtudiantDialogController);

    EtudiantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Etudiant', 'Partenaire'];

    function EtudiantDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Etudiant, Partenaire) {
        var vm = this;

        vm.etudiant = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.stagiaires = Partenaire.query({filter: 'etudiant-is-null'});
        $q.all([vm.etudiant.$promise, vm.stagiaires.$promise]).then(function() {
            if (!vm.etudiant.stagiaire || !vm.etudiant.stagiaire.id) {
                return $q.reject();
            }
            return Partenaire.get({id : vm.etudiant.stagiaire.id}).$promise;
        }).then(function(stagiaire) {
            vm.stagiaires.push(stagiaire);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.etudiant.id !== null) {
                Etudiant.update(vm.etudiant, onSaveSuccess, onSaveError);
            } else {
                Etudiant.save(vm.etudiant, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetTaaGliApp:etudiantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.etuDateMaj = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
