'use strict';

describe('Controller Tests', function() {

    describe('Resolution Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResolution;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResolution = jasmine.createSpy('MockResolution');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Resolution': MockResolution
            };
            createController = function() {
                $injector.get('$controller')("ResolutionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tg24HApp:resolutionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
