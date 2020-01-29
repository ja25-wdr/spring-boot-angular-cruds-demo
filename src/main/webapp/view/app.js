var app = angular.module('app',[]);

app.controller('StoryCRUDCtrl', ['$scope','StoryCRUDService', function ($scope,StoryCRUDService) {

    $scope.updateStory = function () {
        StoryCRUDService.updateStory($scope.story.id,$scope.story.title,$scope.story.contents)
          .then(function success(response){
              $scope.message = 'Story data updated!';
              $scope.errorMessage = '';
          },
          function error(response){
              $scope.errorMessage = 'Error updating story!';
              $scope.message = '';
          });
          $scope.stories = null;
    }
    
    $scope.getStory = function () {
        var id = $scope.story.id;
        StoryCRUDService.getStory($scope.story.id)
          .then(function success(response){
              $scope.story = response.data;
              $scope.story.id = id;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message = '';
              if (response.status === 404){
                  $scope.errorMessage = 'Story not found!';
              }
              else {
                  $scope.errorMessage = "Error getting story!";
              }
          });
          $scope.stories = null;
    }
    
    $scope.addStory = function () {
        if ($scope.story != null && $scope.story.title && $scope.story.contents) {
            StoryCRUDService.addStory($scope.story.title, $scope.story.contents)
              .then (function success(response){
                  $scope.message = 'Story added!';
                  $scope.errorMessage = '';
              },
              function error(response){
                  $scope.errorMessage = 'Error adding story!';
                  $scope.message = '';
            });
        }
        else {
            $scope.errorMessage = 'Please enter a name!';
            $scope.message = '';
        }
        $scope.stories = null;
    }
    
    $scope.deleteStory = function () {
        StoryCRUDService.deleteStory($scope.story.id)
          .then (function success(response){
              $scope.message = 'Story deleted!';
              $scope.story = null;
              $scope.errorMessage='';
          },
          function error(response){
              $scope.errorMessage = 'Error deleting story!';
              $scope.message='';
          })
          $scope.stories = null;
    }
    
    $scope.getAllStories = function () {
        StoryCRUDService.getAllStories()
          .then(function success(response){
              $scope.stories = response.data._embedded.stories;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message='';
              $scope.errorMessage = 'Error getting stories!';
          });
    }

    $scope.getImageUrl =  function () {
        var base64ImageString = arguments[0];
        var newTab = window.open();
        newTab.document.body.innerHTML = '<img src="data:image/png;base64,' + base64ImageString + '" width="100px" height="100px">';

    }

}]);

app.service('StoryCRUDService',['$http', function ($http) {
	
    this.getStory = function getStory(storyId){
        return $http({
          method: 'GET',
          url: 'stories/'+storyId
        });
	}
	
    this.addStory = function addStory(title, contents){
        return $http({
          method: 'POST',
          url: 'stories',
          data: {title:title, contents:contents}
        });
    }
	
    this.deleteStory = function deleteStory(id){
        return $http({
          method: 'DELETE',
          url: 'stories/'+id
        })
    }
	
    this.updateStory = function updateStory(id,title,contents){
        return $http({
          method: 'PATCH',
          url: 'stories/'+id,
          data: {title:title, contents:contents}
        })
    }
	
    this.getAllStories = function getAllStories(){
        return $http({
          method: 'GET',
          url: 'stories'
        });
    }


}]);

