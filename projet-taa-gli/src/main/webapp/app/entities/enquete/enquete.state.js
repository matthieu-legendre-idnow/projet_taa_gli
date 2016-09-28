(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('enquete', {
            parent: 'entity',
            url: '/enquete',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Enquetes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enquete/enquetes.html',
                    controller: 'EnqueteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('enquete-detail', {
            parent: 'entity',
            url: '/enquete/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Enquete'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enquete/enquete-detail.html',
                    controller: 'EnqueteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Enquete', function($stateParams, Enquete) {
                    return Enquete.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'enquete',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('enquete-detail.edit', {
            parent: 'enquete-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enquete/enquete-dialog.html',
                    controller: 'EnqueteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Enquete', function(Enquete) {
                            return Enquete.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enquete.new', {
            parent: 'enquete',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enquete/enquete-dialog.html',
                    controller: 'EnqueteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                enqId: null,
                                modeEnquete: null,
                                dateEnquete: null,
                                etuNom: null,
                                etuRue: null,
                                etuVille: null,
                                etuCodeDep: null,
                                enqDateDebut: null,
                                enqDureeRecherche: null,
                                enqQuantite: null,
                                enqQuestion: null,
                                enqReponse: null,
                                enqSalaire: null,
                                enqSalaireDevise: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('enquete', null, { reload: 'enquete' });
                }, function() {
                    $state.go('enquete');
                });
            }]
        })
        .state('enquete.edit', {
            parent: 'enquete',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enquete/enquete-dialog.html',
                    controller: 'EnqueteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Enquete', function(Enquete) {
                            return Enquete.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enquete', null, { reload: 'enquete' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enquete.delete', {
            parent: 'enquete',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enquete/enquete-delete-dialog.html',
                    controller: 'EnqueteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Enquete', function(Enquete) {
                            return Enquete.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enquete', null, { reload: 'enquete' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
