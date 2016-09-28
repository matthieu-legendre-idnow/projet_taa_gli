(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('stage', {
            parent: 'entity',
            url: '/stage',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Stages'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stage/stages.html',
                    controller: 'StageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('stage-detail', {
            parent: 'entity',
            url: '/stage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Stage'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stage/stage-detail.html',
                    controller: 'StageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Stage', function($stateParams, Stage) {
                    return Stage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'stage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('stage-detail.edit', {
            parent: 'stage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stage/stage-dialog.html',
                    controller: 'StageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Stage', function(Stage) {
                            return Stage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('stage.new', {
            parent: 'stage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stage/stage-dialog.html',
                    controller: 'StageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateDebut: null,
                                pIdent: null,
                                pIdentConvention: null,
                                etuIdent: null,
                                enseignId: null,
                                encId: null,
                                nomEnc: null,
                                sujet: null,
                                lang: null,
                                motscles: null,
                                joursTravaille: null,
                                salaire: null,
                                finConv: null,
                                finStage: null,
                                soutenance: null,
                                rapport: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('stage', null, { reload: 'stage' });
                }, function() {
                    $state.go('stage');
                });
            }]
        })
        .state('stage.edit', {
            parent: 'stage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stage/stage-dialog.html',
                    controller: 'StageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Stage', function(Stage) {
                            return Stage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('stage', null, { reload: 'stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('stage.delete', {
            parent: 'stage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stage/stage-delete-dialog.html',
                    controller: 'StageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Stage', function(Stage) {
                            return Stage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('stage', null, { reload: 'stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
