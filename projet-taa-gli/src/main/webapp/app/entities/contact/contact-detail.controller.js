(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .controller('ContactDetailController', ContactDetailController);

    ContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Contact', 'Partenaire'];

    function ContactDetailController($scope, $rootScope, $stateParams, previousState, entity, Contact, Partenaire) {
        var vm = this;

        vm.contact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projetTaaGliApp:contactUpdate', function(event, result) {
            vm.contact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
