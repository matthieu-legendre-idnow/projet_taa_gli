(function() {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contact', {
            parent: 'entity',
            url: '/contact',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Contacts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contact/contacts.html',
                    controller: 'ContactController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('contact-detail', {
            parent: 'entity',
            url: '/contact/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Contact'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contact/contact-detail.html',
                    controller: 'ContactDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Contact', function($stateParams, Contact) {
                    return Contact.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'contact',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('contact-detail.edit', {
            parent: 'contact-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contact/contact-dialog.html',
                    controller: 'ContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Contact', function(Contact) {
                            return Contact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contact.new', {
            parent: 'contact',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contact/contact-dialog.html',
                    controller: 'ContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contactId: null,
                                contactNom: null,
                                contactRole: null,
                                contactSexe: null,
                                contactTel: null,
                                contactMail: null,
                                contactDateMaj: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('contact', null, { reload: 'contact' });
                }, function() {
                    $state.go('contact');
                });
            }]
        })
        .state('contact.edit', {
            parent: 'contact',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contact/contact-dialog.html',
                    controller: 'ContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Contact', function(Contact) {
                            return Contact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contact', null, { reload: 'contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contact.delete', {
            parent: 'contact',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contact/contact-delete-dialog.html',
                    controller: 'ContactDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Contact', function(Contact) {
                            return Contact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contact', null, { reload: 'contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
