(function () {
    'use strict';

    angular
        .module('projetTaaGliApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
