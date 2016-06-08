(function() {
    'use strict';

    angular
        .module('tg24HApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('company', {
            parent: 'entity',
            url: '/company',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tg24HApp.company.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/company/companies.html',
                    controller: 'CompanyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('company');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('company-detail', {
            parent: 'entity',
            url: '/company/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tg24HApp.company.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/company/company-detail.html',
                    controller: 'CompanyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('company');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Company', function($stateParams, Company) {
                    return Company.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('company.new', {
            parent: 'company',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company/company-dialog.html',
                    controller: 'CompanyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                manager: null,
                                name: null,
                                description: null,
                                active: null,
                                email: null,
                                phoneNumber: null,
                                web: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('company', null, { reload: true });
                }, function() {
                    $state.go('company');
                });
            }]
        })
        .state('company.edit', {
            parent: 'company',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company/company-dialog.html',
                    controller: 'CompanyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Company', function(Company) {
                            return Company.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('company', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('company.delete', {
            parent: 'company',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company/company-delete-dialog.html',
                    controller: 'CompanyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Company', function(Company) {
                            return Company.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('company', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
