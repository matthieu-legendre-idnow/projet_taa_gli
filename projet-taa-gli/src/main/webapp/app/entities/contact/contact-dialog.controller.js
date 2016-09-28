(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('ContactDialogController', ContactDialogController);

    ContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Contact', 'Partenaire'];

    function ContactDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Contact, Partenaire) {
        var vm = this;

        vm.contact = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.contacts = Partenaire.query({filter: 'contact-is-null'});
        $q.all([vm.contact.$promise, vm.contacts.$promise]).then(function() {
            if (!vm.contact.contact || !vm.contact.contact.id) {
                return $q.reject();
            }
            return Partenaire.get({id : vm.contact.contact.id}).$promise;
        }).then(function(contact) {
            vm.contacts.push(contact);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contact.id !== null) {
                Contact.update(vm.contact, onSaveSuccess, onSaveError);
            } else {
                Contact.save(vm.contact, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetTaaGliApp:contactUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.contactDateMaj = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
