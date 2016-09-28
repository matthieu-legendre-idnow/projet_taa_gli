(function() {
    'use strict';
    angular
        .module('projetTaaGliApp')
        .factory('Stage', Stage);

    Stage.$inject = ['$resource', 'DateUtils'];

    function Stage ($resource, DateUtils) {
        var resourceUrl =  'api/stages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateDebut = DateUtils.convertDateTimeFromServer(data.dateDebut);
                        data.finConv = DateUtils.convertDateTimeFromServer(data.finConv);
                        data.finStage = DateUtils.convertDateTimeFromServer(data.finStage);
                        data.soutenance = DateUtils.convertDateTimeFromServer(data.soutenance);
                        data.rapport = DateUtils.convertDateTimeFromServer(data.rapport);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
