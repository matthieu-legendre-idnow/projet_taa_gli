(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('EnqueteDetailController', EnqueteDetailController);

    EnqueteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Enquete', 'Etudiant', 'Stage'];

    function EnqueteDetailController($scope, $rootScope, $stateParams, previousState, entity, Enquete, Etudiant, Stage) {
        var vm = this;

        vm.enquete = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projetTaaGliApp:enqueteUpdate', function(event, result) {
            vm.enquete = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
