var app = angular
    .module("app", [])
    .controller('bookingsController', function ($scope, $http) {
        var restUrl = "/api/bookings/";

        $scope.bookings = [];

        showAll();

        $scope.affordable = function affordable() {
            var url = restUrl + "affordable/100";
            var bookingsPromise = $http.get(url);
            bookingsPromise.then(function (response) {
                $scope.bookings = response.data;
            });
        };

        $scope.showAll = showAll;

        function showAll() {
            var bookingsPromise = $http.get(restUrl);
            bookingsPromise.then(function (response) {
                $scope.bookings = response.data;
            });
        }

        $scope.add = function add(booking) {
             $http.post(restUrl, booking)
                .then(function (response) {
                    $scope.bookings = response.data;
                });
        };

        $scope.edit = function edit(booking) {
            var url = restUrl + booking.id;
            $http.put(url, booking).then(function (response) {
                $scope.bookings = response.data;
            });
        };

        $scope.deleteBooking = function deleteBooking(id) {
            var url = restUrl + id;
            $http.delete(url).then(function (response) {
                $scope.bookings = response.data;
            });
        };

    });
