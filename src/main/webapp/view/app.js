var app = angular.module('app',[]);

app.controller('BlogCRUDCtrl', ['$scope','BlogCRUDService', function ($scope,BlogCRUDService) {

    $scope.updateBlog = function () {
        BlogCRUDService.updateBlog($scope.blog.id,$scope.blog.title,$scope.blog.contents)
          .then(function success(response){
              $scope.message = 'Blog data updated!';
              $scope.errorMessage = '';
          },
          function error(response){
              $scope.errorMessage = 'Error updating blog!';
              $scope.message = '';
          });
          $scope.blogs = null;
    }
    
    $scope.getBlog = function () {
        var id = $scope.blog.id;
        BlogCRUDService.getBlog($scope.blog.id)
          .then(function success(response){
              $scope.blog = response.data;
              $scope.blog.id = id;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message = '';
              if (response.status === 404){
                  $scope.errorMessage = 'Blog not found!';
              }
              else {
                  $scope.errorMessage = "Error getting blog!";
              }
          });
          $scope.blogs = null;
    }
    
    $scope.addBlog = function () {
        if ($scope.blog != null && $scope.blog.title && $scope.blog.contents) {
            BlogCRUDService.addBlog($scope.blog.title, $scope.blog.contents)
              .then (function success(response){
                  $scope.message = 'Blog added!';
                  $scope.errorMessage = '';
              },
              function error(response){
                  $scope.errorMessage = 'Error adding blog!';
                  $scope.message = '';
            });
        }
        else {
            $scope.errorMessage = 'Please enter a name!';
            $scope.message = '';
        }
        $scope.blogs = null;
    }
    
    $scope.deleteBlog = function () {
        BlogCRUDService.deleteBlog($scope.blog.id)
          .then (function success(response){
              $scope.message = 'Blog deleted!';
              $scope.blog = null;
              $scope.errorMessage='';
          },
          function error(response){
              $scope.errorMessage = 'Error deleting blog!';
              $scope.message='';
          })
          $scope.blogs = null;
    }
    
    $scope.getAllBlogs = function () {
        BlogCRUDService.getAllBlogs()
          .then(function success(response){
              $scope.blogs = response.data._embedded.blogs;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message='';
              $scope.errorMessage = 'Error getting blogs!';
          });
    }

    $scope.getImageUrl =  function () {
        var base64ImageString = arguments[0];
        var newTab = window.open();
        newTab.document.body.innerHTML = '<img src="data:image/png;base64,' + base64ImageString + '" width="100px" height="100px">';

    }

}]);

app.service('BlogCRUDService',['$http', function ($http) {
	
    this.getBlog = function getBlog(blogId){
        return $http({
          method: 'GET',
          url: 'blogs/'+blogId
        });
	}
	
    this.addBlog = function addBlog(title, contents){
        return $http({
          method: 'POST',
          url: 'blogs',
          data: {title:title, contents:contents}
        });
    }
	
    this.deleteBlog = function deleteBlog(id){
        return $http({
          method: 'DELETE',
          url: 'blogs/'+id
        })
    }
	
    this.updateBlog = function updateBlog(id,title,contents){
        return $http({
          method: 'PATCH',
          url: 'blogs/'+id,
          data: {title:title, contents:contents}
        })
    }
	
    this.getAllBlogs = function getAllBlogs(){
        return $http({
          method: 'GET',
          url: 'blogs'
        });
    }


}]);

