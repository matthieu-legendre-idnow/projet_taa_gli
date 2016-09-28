(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('EtudiantDetailController', EtudiantDetailController);

    EtudiantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Etudiant', 'Partenaire'];

    function EtudiantDetailController($scope, $rootScope, $stateParams, previousState, entity, Etudiant, Partenaire) {
        var vm = this;

        vm.etudiant = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projetTaaGliApp:etudiantUpdate', function(event, result) {
            vm.etudiant = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
