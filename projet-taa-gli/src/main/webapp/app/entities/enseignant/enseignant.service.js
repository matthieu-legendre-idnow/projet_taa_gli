(function() {
    'use strict';
    angular
        .module('projetTaaGliApp')
        .factory('Enseignant', Enseignant);

    Enseignant.$inject = ['$resource', 'DateUtils'];

    function Enseignant ($resource, DateUtils) {
        var resourceUrl =  'api/enseignants/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.enseignantMaj = DateUtils.convertDateTimeFromServer(data.enseignantMaj);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
