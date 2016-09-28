'use strict';

describe('Controller Tests', function() {

    describe('Stage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStage, MockEnseignant, MockContact, MockEtudiant, MockPartenaire;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStage = jasmine.createSpy('MockStage');
            MockEnseignant = jasmine.createSpy('MockEnseignant');
            MockContact = jasmine.createSpy('MockContact');
            MockEtudiant = jasmine.createSpy('MockEtudiant');
            MockPartenaire = jasmine.createSpy('MockPartenaire');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Stage': MockStage,
                'Enseignant': MockEnseignant,
                'Contact': MockContact,
                'Etudiant': MockEtudiant,
                'Partenaire': MockPartenaire
            };
            createController = function() {
                $injector.get('$controller')("StageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projetTaaGliApp:stageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
