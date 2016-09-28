(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('StageDetailController', StageDetailController);

    StageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stage', 'Enseignant', 'Contact', 'Etudiant', 'Partenaire'];

    function StageDetailController($scope, $rootScope, $stateParams, previousState, entity, Stage, Enseignant, Contact, Etudiant, Partenaire) {
        var vm = this;

        vm.stage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projetTaaGliApp:stageUpdate', function(event, result) {
            vm.stage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
