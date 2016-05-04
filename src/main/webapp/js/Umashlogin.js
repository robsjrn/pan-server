


var LoginMngt;

LoginMngt= angular.module('umashApp', ['umashApp.services'] ); 

LoginMngt.controller('loginCtrl', ['$scope','$http','$window','staffFactory', function($scope,$http,$window,staffFactory){

       $scope.Userlogin=function(user){
                        
                staffFactory.login(user)
				 		 .success(function(data) {
				 		 		
									 $window.sessionStorage.token = data.token;
									 $window.sessionStorage.names = user.username;
								     if (data.status==1){  $window.location.href="/Umash/home.html";}
								     
									   
							 }) 
						 .error(function(data) {
		
                     
							    delete $window.sessionStorage.token;
							    delete $window.sessionStorage.names;
							     
							 });	
      };


        $scope.forgotPassword=function(){
             $http.post('web/sendmail',$scope.user)
				 .success(function(data) {
					 })
				.error(function(data) {
						  })
	  
	  };
    
	}]);


