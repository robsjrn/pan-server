/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


angular.module('umashApp', ['umashApp.controller','umashApp.services','ngRoute','angularUtils.directives.dirPagination','ngDialog'])

.config(function($routeProvider) {
        $routeProvider

            // route for the home page
            .when('/', {
                templateUrl : 'views/Registrations.html',
                controller  : 'RegController'
            })

            // route for the about page
            .when('/requests', {
                templateUrl : 'views/requests.html',
                controller  : 'RequestCtrl'
            })
            .when('/clientdetails/:clientid', {
                templateUrl : 'views/clientdetails.html',
                controller  : 'clientdetCtrl'
            })
            
            .when('/iprserror', {
                templateUrl : 'views/iprserrors.html',
                controller  : 'IprsErrorCtrl'
            })
            .when('/mpesatrxn', {
                templateUrl : 'views/Mpesatrxn.html',
                controller  : 'MpesaCtrl'
            })

            .when('/payments', {
                templateUrl : 'views/payments.html',
                controller  : 'paymentsCtrl'
            })

             .when('/overpayments', {
                templateUrl : 'views/Overpayment.html',
                controller  : 'overpaymentCtrl'
            })
             .when('/underpayments', {
                templateUrl : 'views/Underpayments.html',
                controller  : 'underpaymentCtrl'
            })

           .when('/summary', {
                templateUrl : 'views/summary.html',
                controller  : 'summaryCtrl'
            })
          .when('/beneficiaryadd', {
                templateUrl : 'views/beneficiaryAdd.html',
                controller  : 'benAddCtrl'
            })
          .when('/beneficiaryedit', {
                templateUrl : 'views/beneficiaryEdit.html',
                controller  : 'benEditCtrl'
            })
           .when('/claims', {
                templateUrl : 'views/claims.html',
                controller  : 'claimCtrl'
            })
             
            // route for the contact page
            .when('/paid', {
                templateUrl : 'views/paidRegistrations.html',
                controller  : 'PaidCtrl'
            })

            .when('/sms', {
                templateUrl : 'views/sms.html',
                controller  : 'SmsCtrl'
            })
              .when('/teUploads', {
                templateUrl : 'views/teUploads.html',
                controller  : 'teUploadCtrl'
            })
            .when('/unpaid', {
                templateUrl : 'views/unpaid.html',
                controller  : 'unpaidCtrl'
            });
    });

