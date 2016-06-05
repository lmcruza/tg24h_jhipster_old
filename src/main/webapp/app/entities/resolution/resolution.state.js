(function() {
    'use strict';

    angular
        .module('tg24HApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('resolution', {
            parent: 'entity',
            url: '/resolution',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tg24HApp.resolution.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resolution/resolutions.html',
                    controller: 'ResolutionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resolution');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('resolution-detail', {
            parent: 'entity',
            url: '/resolution/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tg24HApp.resolution.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resolution/resolution-detail.html',
                    controller: 'ResolutionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resolution');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Resolution', function($stateParams, Resolution) {
                    return Resolution.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('resolution.new', {
            parent: 'resolution',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resolution/resolution-dialog.html',
                    controller: 'ResolutionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                productId: null,
                                instanceId: null,
                                manager: null,
                                data: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('resolution', null, { reload: true });
                }, function() {
                    $state.go('resolution');
                });
            }]
        })
        .state('resolution.edit', {
            parent: 'resolution',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resolution/resolution-dialog.html',
                    controller: 'ResolutionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Resolution', function(Resolution) {
                            return Resolution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('resolution', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('resolution.delete', {
            parent: 'resolution',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resolution/resolution-delete-dialog.html',
                    controller: 'ResolutionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Resolution', function(Resolution) {
                            return Resolution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('resolution', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
