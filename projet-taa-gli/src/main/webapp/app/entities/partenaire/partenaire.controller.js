(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('PartenaireController', PartenaireController);

    PartenaireController.$inject = ['$scope', '$state', 'Partenaire'];

    function PartenaireController ($scope, $state, Partenaire) {
        var vm = this;
        
        vm.partenaires = [];

        loadAll();

        function loadAll() {
            Partenaire.query(function(result) {
                vm.partenaires = result;
            });
        }
    }
})();
