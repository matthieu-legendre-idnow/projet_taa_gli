(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('EnqueteController', EnqueteController);

    EnqueteController.$inject = ['$scope', '$state', 'Enquete'];

    function EnqueteController ($scope, $state, Enquete) {
        var vm = this;
        
        vm.enquetes = [];

        loadAll();

        function loadAll() {
            Enquete.query(function(result) {
                vm.enquetes = result;
            });
        }
    }
})();
