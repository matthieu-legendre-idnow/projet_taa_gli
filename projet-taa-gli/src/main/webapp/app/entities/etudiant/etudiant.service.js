(function() {
    'use strict';
    angular
        .module('projetTaaGliApp')
        .factory('Etudiant', Etudiant);

    Etudiant.$inject = ['$resource', 'DateUtils'];

    function Etudiant ($resource, DateUtils) {
        var resourceUrl =  'api/etudiants/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.etuDateMaj = DateUtils.convertDateTimeFromServer(data.etuDateMaj);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
