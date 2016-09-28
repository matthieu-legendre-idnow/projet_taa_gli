(function() {
    'use strict';
    angular
        .module('projetTaaGliApp')
        .factory('Partenaire', Partenaire);

    Partenaire.$inject = ['$resource', 'DateUtils'];

    function Partenaire ($resource, DateUtils) {
        var resourceUrl =  'api/partenaires/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.pDateMaj = DateUtils.convertDateTimeFromServer(data.pDateMaj);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
