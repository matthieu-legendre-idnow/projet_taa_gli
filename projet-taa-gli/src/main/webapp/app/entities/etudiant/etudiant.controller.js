(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('EtudiantController', EtudiantController);

    EtudiantController.$inject = ['$scope', '$state', 'Etudiant'];

    function EtudiantController ($scope, $state, Etudiant) {
        var vm = this;
        
        vm.etudiants = [];

        loadAll();

        function loadAll() {
            Etudiant.query(function(result) {
                vm.etudiants = result;
            });
        }
    }
})();
