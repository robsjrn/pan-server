/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


angular.module('umashApp.controller', [])

.controller('MainCtrl', function($scope,staffFactory,$window) {
        // create a message to display in our view
        $scope.user = $window.sessionStorage.names;
      
       

          staffFactory.getSummary()
          .success(function(data) { 
          $scope.umash=data.umash; 
           $scope.cashplan=data.cashplan;
            $scope.total=$scope.umash+$scope.cashplan;
          console.log(data);
            
                                                                                
               }) 
             .error(function(data) {                                                 
              
               });
    })
.controller('RegController', function($scope,staffFactory) {
        // create a message to display in our view
        $scope.user = 'Test';
    })  
.controller('summaryCtrl', function($scope,staffFactory) {
  
    }) 

 .controller('overpaymentCtrl', function($scope,staffFactory) {

       staffFactory.getOverpayment()
       .success(function(data) { 
        $scope.request=data; 
                                                                                         
               }) 
             .error(function(data) {                                                 
              
               });
  
    }) 
 .controller('underpaymentCtrl', function($scope,staffFactory) {

     staffFactory.getUnderpayment()
       .success(function(data) { 
        $scope.request=data; 
                                                                                         
               }) 
             .error(function(data) {                                                 
              
               });
  
    }) 

.controller('paymentsCtrl', function($scope,staffFactory) {

        staffFactory.getPaymentTrxn()
       .success(function(data) { 
        $scope.request=data; 
                                                                                         
               }) 
             .error(function(data) {                                                 
              
               });
  
    }) 

.controller('MpesaCtrl', function($scope,staffFactory) {
     staffFactory.getMpesaTrxn()
       .success(function(data) { 
        $scope.request=data; 
                                                                                         
               }) 
             .error(function(data) {                                                 
              
               });
  
    })


.controller('claimCtrl', function($scope,staffFactory) {
        staffFactory.getClaimRequest()
       .success(function(data) { 
        $scope.request=data; 
                                                                                         
               }) 
             .error(function(data) {                                                 
              
               });
    }) 


.controller('benAddCtrl', function($scope,staffFactory) {
  staffFactory.getbeneficiaryAddChangeRequest()
       .success(function(data) { 
        $scope.request=data; 
                                                                                         
               }) 
             .error(function(data) {                                                 
              
               });
  
    }) 
.controller('benEditCtrl', function($scope,staffFactory) {
  staffFactory.getbeneficiaryEditChangeRequest()
       .success(function(data) { 
          $scope.request=data; 
                                                                                         
               }) 
             .error(function(data) {                                                 
              
               });
  
    }) 


.controller('clientdetCtrl', function($scope,staffFactory,$routeParams) {

  staffFactory.getCustomerDetails($routeParams.clientid)
       .success(function(data) { 
          $scope.client=data; 
          console.log("customer details" + data);
                                                                                         
               }) 
             .error(function(data) {                                                 
              
               });
  
    }) 

     
  .controller('IprsErrorCtrl', function($scope,staffFactory) {

        staffFactory.getiprserrors()
          .success(function(data) { 
          $scope.requests=data; 
                                                                                         
               }) 
             .error(function(data) {                                                 
              
               });

            $scope.process=function(req){

            };

             $scope.delete=function(req){

            };
    })    

  .controller('RequestCtrl', function($scope,staffFactory) {
        
        staffFactory.getnewRequests()
          .success(function(data) { 
          $scope.requests=data; 
          console.log(data);
            
                                                                                
               }) 
             .error(function(data) {                                                 
              
               });

    })  
.controller('unpaidCtrl', function($scope,staffFactory) {
        staffFactory.getUnpaidRequests()
          .success(function(data) { 
          $scope.requests=data; 
          console.log(data);
            
                                                                                
               }) 
             .error(function(data) {                                                 
              
               });
    }) 

.controller('PaidCtrl', function($scope,staffFactory,ngDialog,$location) {
        staffFactory.getpaidClients()
          .success(function(data) { 
          $scope.requests=data; 
          console.log(data);
            
                                                                                
               }) 
             .error(function(data) {                                                 
              
               });
         $scope.viewclient=function(id) {
          $location.path('/clientdetails/'+id);
         }   

        $scope.process=function(client){
        	 ngDialog.openConfirm({
                    template: 'staffFeedbacktmpl',
                    className: 'ngdialog-theme-default',
                    scope: $scope
                }).then(function (value) {
                     var dat={};
                       dat.docId=client.docid;
                       dat.policyid=value.policyid;
                       dat.comments=value.comment;
                       dat.trxnid=client.paymentid;
                       dat.client=client;
                    staffFactory.clientConfirmation(dat)
                        .success(function(data) { 
                          alert("reponse Saved");
                        })
                        .error(function(data) {                                                 
                            alert("Error Occured");
                        });
                   
                }, function (reason) {
                    console.log('Modal promise rejected. Reason: ', reason);
                });

        };   


     


    }) 

.controller('SmsCtrl', function($scope,staffFactory,ngDialog) {
        staffFactory.getSMS()
          .success(function(data) { 
          $scope.requests=data; 
          console.log(data);
         
                                                                                
               }) 
             .error(function(data) {                                                 
              
               });

      
    }) 

.controller('teUploadCtrl', function($scope,staffFactory,ngDialog,$window) {
        staffFactory.getteUploads()
          .success(function(data) { 
          $scope.requests=data; 
          console.log(data);
         
                                                                                
               }) 
             .error(function(data) {                                                 
              
               });

             $scope.generatefile=function(det){

              
              staffFactory.generateFile(det)
          .success(function(data) { 
                console.log(data);
                         if (data.status==1){
                                                     
                            $window.open(staffFactory.getFileloc(data.file), 'Pan Africa TE File', 'width=500,height=200');
                         }
          
         
                                                                                
               }) 
             .error(function(data) {                                                 
              
               });

             }
        
      
    }) 



