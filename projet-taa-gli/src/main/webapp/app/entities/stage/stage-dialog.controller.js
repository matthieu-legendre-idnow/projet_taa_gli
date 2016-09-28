(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('StageDialogController', StageDialogController);

    StageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Stage', 'Enseignant', 'Contact', 'Etudiant', 'Partenaire'];

    function StageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Stage, Enseignant, Contact, Etudiant, Partenaire) {
        var vm = this;

        vm.stage = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.referents = Enseignant.query({filter: 'stage-is-null'});
        $q.all([vm.stage.$promise, vm.referents.$promise]).then(function() {
            if (!vm.stage.referent || !vm.stage.referent.id) {
                return $q.reject();
            }
            return Enseignant.get({id : vm.stage.referent.id}).$promise;
        }).then(function(referent) {
            vm.referents.push(referent);
        });
        vm.contact1s = Contact.query({filter: 'stage-is-null'});
        $q.all([vm.stage.$promise, vm.contact1s.$promise]).then(function() {
            if (!vm.stage.contact1 || !vm.stage.contact1.id) {
                return $q.reject();
            }
            return Contact.get({id : vm.stage.contact1.id}).$promise;
        }).then(function(contact1) {
            vm.contact1s.push(contact1);
        });
        vm.contact2s = Contact.query({filter: 'stage-is-null'});
        $q.all([vm.stage.$promise, vm.contact2s.$promise]).then(function() {
            if (!vm.stage.contact2 || !vm.stage.contact2.id) {
                return $q.reject();
            }
            return Contact.get({id : vm.stage.contact2.id}).$promise;
        }).then(function(contact2) {
            vm.contact2s.push(contact2);
        });
        vm.etudiants = Etudiant.query({filter: 'stage-is-null'});
        $q.all([vm.stage.$promise, vm.etudiants.$promise]).then(function() {
            if (!vm.stage.etudiant || !vm.stage.etudiant.id) {
                return $q.reject();
            }
            return Etudiant.get({id : vm.stage.etudiant.id}).$promise;
        }).then(function(etudiant) {
            vm.etudiants.push(etudiant);
        });
        vm.partenaires = Partenaire.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.stage.id !== null) {
                Stage.update(vm.stage, onSaveSuccess, onSaveError);
            } else {
                Stage.save(vm.stage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetTaaGliApp:stageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateDebut = false;
        vm.datePickerOpenStatus.finConv = false;
        vm.datePickerOpenStatus.finStage = false;
        vm.datePickerOpenStatus.soutenance = false;
        vm.datePickerOpenStatus.rapport = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
