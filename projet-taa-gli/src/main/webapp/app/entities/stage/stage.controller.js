(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('StageController', StageController);

    StageController.$inject = ['$scope', '$state', 'Stage'];

    function StageController ($scope, $state, Stage) {
        var vm = this;
        
        vm.stages = [];

        loadAll();

        function loadAll() {
            Stage.query(function(result) {
                vm.stages = result;
            });
        }
    }
})();
