angular.module('umashApp.services', [])

.factory('staffFactory', ['$http','API_ENDPOINT',function($http,API_ENDPOINT) {
  
        
  var data = { 
            getSummary:function () {
              return $http.get('/Umash/rest/portal/summary');
            },  
            getnewRequests:function () {
              return $http.get('/Umash/rest/portal/newrequests');
            },
             getUnpaidRequests:function () {
              return $http.get('/Umash/rest/portal/unpaid');
            },
             getSMS:function () {
              return $http.get('/Umash/rest/portal/sms');
            },
             getpaidClients:function () {
              return $http.get('/Umash/rest/portal/paid');
            },
             getbeneficiaryAddChangeRequest:function () {
              return $http.get('/Umash/rest/portal/beneficiaryAdd');
            },
             getbeneficiaryEditChangeRequest:function () {
              return $http.get('/Umash/rest/portal/beneficiaryEdit');
            },

            getClaimRequest:function () {
              return $http.get('/Umash/rest/portal/ClaimRequest');
            },
           
             getteUploads:function () {
              return $http.get('/Umash/rest/portal/TEuploads');
            },
            getMpesaTrxn:function () {
              return $http.get('/Umash/rest/portal/Mpesatrxn');
            },
            getPaymentTrxn:function () {
              return $http.get('/Umash/rest/portal/Paymenttrxn');
            },
            getOverpayment:function () {
              return $http.get('/Umash/rest/portal/Overpayments');
            },
             getUnderpayment:function () {
              return $http.get('/Umash/rest/portal/Underpayments');
            },
            
             getCustomerDetails:function (clientid) {
              return $http.get('Umash/rest/client/details?clientid='+clientid);
            },

            getiprserrors:function () {
              return $http.get('/Umash/rest/portal/iprserrors');
            },
            
            getCustomerChildrens:function (clientid) {
              return $http.get('/Umash/rest/client/children?clientid='+clientid);
            },
             generateFile:function (data) {
                 
                  return $http.post('/Umash/rest/portal/generatefile',data);
               },
              generateFiles:function () {
                 
                  return $http.post('/Umash/rest/portal/generatefiles');
               },  
                getFile:function (fileloc) {
                 
                  return $http.post('/Umash/rest/portal/getfile?fileloc='+fileloc);
               },
               getFileloc:function (fileloc) {
                 
                  return 'Umash/rest/portal/getfile?fileloc='+fileloc;
               },
             login:function (data) {
                 
                  return '/Umash/rest/Staffauthentication',data;
               },
            clientConfirmation:function (data) {
                 
              return $http.post('/Umash/rest/portal/process',data);
               },
             RegisterUser:function (user) { 2
         return $http.post('/Umash/rest/Registration',user);
            },
            
          
  }
  return data;
}])