(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('EtudiantDeleteController',EtudiantDeleteController);

    EtudiantDeleteController.$inject = ['$uibModalInstance', 'entity', 'Etudiant'];

    function EtudiantDeleteController($uibModalInstance, entity, Etudiant) {
        var vm = this;

        vm.etudiant = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Etudiant.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
