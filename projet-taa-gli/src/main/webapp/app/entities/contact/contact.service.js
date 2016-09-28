(function() {
    'use strict';
    angular
        .module('projetTaaGliApp')
        .factory('Contact', Contact);

    Contact.$inject = ['$resource', 'DateUtils'];

    function Contact ($resource, DateUtils) {
        var resourceUrl =  'api/contacts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.contactDateMaj = DateUtils.convertDateTimeFromServer(data.contactDateMaj);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
