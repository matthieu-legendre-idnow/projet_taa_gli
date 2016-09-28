(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('EnqueteDeleteController',EnqueteDeleteController);

    EnqueteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Enquete'];

    function EnqueteDeleteController($uibModalInstance, entity, Enquete) {
        var vm = this;

        vm.enquete = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Enquete.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
