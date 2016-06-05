(function() {
    'use strict';

    angular
        .module('tg24HApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('instance', {
            parent: 'entity',
            url: '/instance',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tg24HApp.instance.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/instance/instances.html',
                    controller: 'InstanceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('instance');
                    $translatePartialLoader.addPart('instanceStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('instance-detail', {
            parent: 'entity',
            url: '/instance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tg24HApp.instance.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/instance/instance-detail.html',
                    controller: 'InstanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('instance');
                    $translatePartialLoader.addPart('instanceStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Instance', function($stateParams, Instance) {
                    return Instance.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('instance.new', {
            parent: 'instance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/instance/instance-dialog.html',
                    controller: 'InstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                productId: null,
                                manager: null,
                                user: null,
                                status: null,
                                createDate: null,
                                updateDate: null,
                                data: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('instance', null, { reload: true });
                }, function() {
                    $state.go('instance');
                });
            }]
        })
        .state('instance.edit', {
            parent: 'instance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/instance/instance-dialog.html',
                    controller: 'InstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Instance', function(Instance) {
                            return Instance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('instance', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('instance.delete', {
            parent: 'instance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/instance/instance-delete-dialog.html',
                    controller: 'InstanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Instance', function(Instance) {
                            return Instance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('instance', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
