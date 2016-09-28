(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('PartenaireDeleteController',PartenaireDeleteController);

    PartenaireDeleteController.$inject = ['$uibModalInstance', 'entity', 'Partenaire'];

    function PartenaireDeleteController($uibModalInstance, entity, Partenaire) {
        var vm = this;

        vm.partenaire = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Partenaire.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
