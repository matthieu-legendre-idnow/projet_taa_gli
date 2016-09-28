(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('EnseignantDeleteController',EnseignantDeleteController);

    EnseignantDeleteController.$inject = ['$uibModalInstance', 'entity', 'Enseignant'];

    function EnseignantDeleteController($uibModalInstance, entity, Enseignant) {
        var vm = this;

        vm.enseignant = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Enseignant.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
