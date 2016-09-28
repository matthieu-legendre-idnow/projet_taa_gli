'use strict';

describe('Controller Tests', function() {

    describe('Enquete Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEnquete, MockEtudiant, MockStage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEnquete = jasmine.createSpy('MockEnquete');
            MockEtudiant = jasmine.createSpy('MockEtudiant');
            MockStage = jasmine.createSpy('MockStage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Enquete': MockEnquete,
                'Etudiant': MockEtudiant,
                'Stage': MockStage
            };
            createController = function() {
                $injector.get('$controller')("EnqueteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projetTaaGliApp:enqueteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
