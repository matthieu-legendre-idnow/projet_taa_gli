(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('StageDeleteController',StageDeleteController);

    StageDeleteController.$inject = ['$uibModalInstance', 'entity', 'Stage'];

    function StageDeleteController($uibModalInstance, entity, Stage) {
        var vm = this;

        vm.stage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Stage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
