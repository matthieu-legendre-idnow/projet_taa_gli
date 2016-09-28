(function() {
    'use strict';
    angular
        .module('projetTaaGliApp')
        .factory('Enquete', Enquete);

    Enquete.$inject = ['$resource', 'DateUtils'];

    function Enquete ($resource, DateUtils) {
        var resourceUrl =  'api/enquetes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateEnquete = DateUtils.convertDateTimeFromServer(data.dateEnquete);
                        data.enqDateDebut = DateUtils.convertDateTimeFromServer(data.enqDateDebut);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
