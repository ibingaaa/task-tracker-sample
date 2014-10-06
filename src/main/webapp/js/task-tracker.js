var tracker = angular.module('tracker', ['ngRoute', 'ngResource', 'ngAnimate']);

// FACTORIES ===========================================================================================================
// TASK TRACKER SERVICE ================================================================================================
tracker.service('trackerService', ['$rootScope', '$location', '$resource', '$route', function ($rootScope, $location, $resource, $route) {
    // LOCAL =========================================================================
    var api = $resource('api', null, {
        login: {url: 'api/j_login', method: 'POST'},
        logout: {url: 'api/logout', method: 'GET'},

        getUser: {url: 'api/user', method: 'GET'},
        createUser: {url: 'api/users', method: 'POST'},
        listDevelopers: {url: 'api/users/role/developer', method: 'GET', isArray: true},
        listDeveloperTasks: {url: 'api/user/tasks', method: 'GET', isArray: true},

        listProjects: {url: 'api/projects', method: 'GET', isArray: true},
        createProject: {url: 'api/projects', method: 'POST'},
        getProject: {url: 'api/projects/:id', method: 'GET'},
        saveProject: {url: 'api/projects/:id', method: 'POST'},
        deleteProject: {url: 'api/projects/:id', method: 'DELETE'},

        createTask: {url: 'api/projects/:id/tasks', method: 'POST'},
        getTask: {url: 'api/tasks/:id', method: 'GET'},
        saveTask: {url: 'api/tasks/:id', method: 'POST'},
        deleteTask: {url: 'api/tasks/:id', method: 'DELETE'},

        createProjectComment: {url: 'api/projects/:id/comments', method: 'POST'},
        createTaskComment: {url: 'api/tasks/:id/comments', method: 'POST'},
        saveComment: {url: 'api/comments/:id', method: 'POST'},
        deleteComment: {url: 'api/comments/:id', method: 'DELETE'}
    });
    var savedPath;
    function setActions() {
        if ($rootScope.user == undefined) return;
        var pathParams = $route.current.params;
        var originalPath = $route.current.originalPath;
        var user = $rootScope.user;
        var actions = [];

        if (user.role == 'Manager' && originalPath != "/projects-create") actions.push({name: "Create project", ref: "#projects-create"});
        if (originalPath == "/projects/:id") {
            if (user.role == 'Manager') {
                actions.push({name: "Edit project", ref: "#projects/" + pathParams.id + "/edit"});
                actions.push({name: "Delete project", expr: function() {$rootScope.$broadcast("event:deleteProject")}})
            }
            actions.push({name: "Add task", ref: "#projects/" + pathParams.id + "/create-task"})
        }
        if (originalPath == "/tasks/:id") {
            actions.push({name: "Edit task", ref: "#tasks/" + pathParams.id + "/edit"});
            actions.push({name: "Delete task", expr: function() {$rootScope.$broadcast("event:deleteTask")}});
        }
        $rootScope.actions = actions;
    }
    function menuActiveStyle() {
        var originalPath = $route.current.originalPath;
        if (originalPath.indexOf("/projects") == 0) {
            $rootScope.projectsActive = "active";
            $rootScope.tasksActive = "";
        }
        if (originalPath.indexOf("/tasks") == 0 && $rootScope.user.role == 'Developer') {
            $rootScope.projectsActive = "";
            $rootScope.tasksActive = "active";
        }
        if (originalPath.indexOf("/profile") == 0) {}
    }

    // LISTENERS =====================================================================
    $rootScope.$on("event:loginRequired", function (event, url) {
        $rootScope.user = null;
        if (url == "api/j_login") $rootScope.$broadcast('event:loginFailure');
        else {
            if (savedPath == undefined) savedPath = $location.path();
            $location.path("login");
        }
    });
    $rootScope.$on("$routeChangeSuccess", function() {
        menuActiveStyle();
        setActions();
    });

    // SERVICE INTERFACE =============================================================
    this.login = function(credentials) {
        api.login(null, credentials, function (data) {
            $rootScope.user = data;
            if (savedPath == undefined) {
                $location.path("projects");
                return;
            }
            $location.path(savedPath);
            savedPath = null;
        });
    };
    $rootScope.logout = function() {
        api.logout(null, function() {
            $rootScope.user = null;
            $location.path('login');
        })
    };

    this.createUser = function(credentials) {
        api.createUser(null, credentials, function() {
            $rootScope.$broadcast("event:userCreated");
        }, function(rejection) {
            if (rejection.status == 409) {
                $rootScope.$broadcast("event:userExist");
                return;
            }
            $rootScope.$broadcast("event:errorCreateUser");
        });
    };
    this.getUser = function () {
        if ($rootScope.user == undefined) $rootScope.user = api.getUser();
        return $rootScope.user;
    };
    this.listDevelopers = api.listDevelopers;

    this.listProjects = api.listProjects;
    this.getProject = function (id) {
        return api.getProject({id: id});
    };
    this.createProject = function(project) {
        api.createProject(null, project, function () {
            $location.path("projects");
        }, function(rejection) {
            if (rejection.status == 409) {
                $rootScope.$broadcast("event:projectExist");
                return;
            }
            $rootScope.$broadcast("event:errorCreateProject");
        });
    };
    this.saveProject = function(project) {
        api.saveProject({id: project.id}, project, function () {
            $location.path("projects/" + project.id);
        }, function(rejection) {
            if (rejection.status == 409) {
                $rootScope.$broadcast("event:projectExist");
                return;
            }
            $rootScope.$broadcast("event:errorSaveProject");
        });
    };
    this.deleteProject = function(project) {
        api.deleteProject({id: project.id}, function () {
            $location.path("projects");
        });
    };

    this.listTasks = api.listDeveloperTasks;
    this.getTask = function(id) {
        return api.getTask({id: id});
    };
    this.createTask = function(task) {
        var projectId = $route.current.params.id;
        api.createTask({id: projectId}, task, function () {
            $location.path("projects/" + projectId);
        }, function () {
            $rootScope.$broadcast("event:errorCreateTask");
        });
    };
    this.saveTask = function(task) {
        api.saveTask({id: task.id}, task, function () {
            $location.path("tasks/" + task.id);
            $rootScope.$broadcast("event:taskSaved");
        }, function () {
            $rootScope.$broadcast("event:errorSaveTask");
        });
    };
    this.changeTask = api.saveTask;
    this.deleteTask = function(task) {
        api.deleteTask({id: task.id}, function () {
            $location.path("projects/" + task.projectId);
        });
    };

    this.createProjectComment = function(projectId, comment) {
        api.createProjectComment({id: projectId}, comment, function() {
            $route.reload();
        }, function() {
            $rootScope.$broadcast("event:errorSendComment");
        });
    };
    this.createTaskComment = function(taskId, comment) {
        api.createTaskComment({id: taskId}, comment, function () {
            $route.reload();
        }, function () {
            $rootScope.$broadcast("event:errorSendComment");
        });
    };
    this.saveComment = function(comment) {
        var c = {text: comment.text};
        api.saveComment({id: comment.id}, c, function () {
            comment.edit = false;
        });
    };
    this.deleteComment = function (comment) {
        api.deleteComment({id: comment.id}, function() {
            $route.reload();
        });
    };
    this.editComment = function (comment) {
        comment.textCopy = comment.text;
        comment.edit = true;
    };
    this.cancelEditComment = function (comment) {
        comment.text = comment.textCopy;
        comment.edit = false;
    };

    this.statusStyles = function(status){
        if (status == 'DONE') return 'label-success';
        if (status == 'WORK') return 'label-warning';
        return 'label-default';
    };
    this.gotoProjects = function() {
        $location.path('projects');
    }
}]);

// LOGIN INTERCEPTOR FACTORY ===========================================================================================
tracker.factory('LoginInterceptor', ['$rootScope', '$q', function($rootScope, $q) {
    return {
        request: function(config) {
            if (config.url == "api/j_login") {
                config.headers['Content-Type'] = "application/x-www-form-urlencoded";

                var cred = config.data;
                config.data = "submit=Login&j_username=" + cred.login + "&j_password=" + cred.password;
            }
            return config;
        },
        responseError: function(rejection) {
            if (rejection.status == 401) {
                $rootScope.$broadcast("event:loginRequired", rejection.config.url);
            }
            return $q.reject(rejection);
        }
    };
}]);

// CONFIGS =============================================================================================================
// ROUTES ==============================================================================================================
tracker.config(['$routeProvider', function($routeProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: 'login.html',
            controller: 'LoginCtrl'
        })
        .when('/projects', {
            templateUrl: 'projects.html',
            controller: 'ProjectsCtrl',
            resolve: {
                trackerService: 'trackerService',
                user: function(trackerService) { return trackerService.getUser().$promise },
                projects: function(trackerService) { return trackerService.listProjects().$promise }
            }
        })
        .when('/projects/:id', {
            templateUrl: 'project.html',
            controller: 'ProjectCtrl',
            resolve: {
                trackerService: 'trackerService',
                $route: '$route',
                user: function(trackerService) { return trackerService.getUser().$promise },
                project: function(trackerService, $route) {
                    return trackerService.getProject($route.current.params.id).$promise;
                }
            }
        })
        .when('/projects-create', {
            templateUrl: 'create-edit-project.html',
            controller: 'CreateProjectCtrl',
            resolve: {
                trackerService: 'trackerService',
                user: function(trackerService) { return trackerService.getUser().$promise }
            }
        })
        .when('/projects/:id/edit', {
            templateUrl: 'create-edit-project.html',
            controller: "EditProjectCtrl",
            resolve: {
                trackerService: 'trackerService',
                $route: '$route',
                user: function(trackerService) { return trackerService.getUser().$promise; },
                project: function($route, trackerService) {
                    return trackerService.getProject($route.current.params.id).$promise;
                }
            }
        })
        .when('/tasks', {
            templateUrl: 'tasks.html',
            controller: 'UserTasksCtrl',
            resolve: {
                trackerService: 'trackerService',
                user: function(trackerService) { return trackerService.getUser().$promise },
                tasks: function(trackerService) { return trackerService.listTasks().$promise}
            }
        })
        .when('/tasks/:id', {
            templateUrl: 'task.html',
            controller: 'TaskCtrl',
            resolve: {
                trackerService: 'trackerService',
                $route: '$route',
                user: function(trackerService) { return trackerService.getUser().$promise },
                task: function(trackerService, $route) {
                    return trackerService.getTask($route.current.params.id).$promise }
            }
        })
        .when('/projects/:id/create-task', {
            templateUrl: 'create-edit-task.html',
            controller: 'CreateTaskCtrl',
            resolve: {
                trackerService: 'trackerService',
                user: function(trackerService) { return trackerService.getUser().$promise }
            }
        })
        .when('/tasks/:id/edit', {
            templateUrl: 'create-edit-task.html',
            controller: 'EditTaskCtrl',
            resolve: {
                trackerService: 'trackerService',
                $route: '$route',
                user: function(trackerService) { return trackerService.getUser().$promise },
                task: function(trackerService, $route) {
                    return trackerService.getTask($route.current.params.id).$promise
                }
            }
        })
        .when('/profile', {
            templateUrl: 'profile.html',
            controller: 'ProfileCtrl',
            resolve: {
                trackerService: 'trackerService',
                user: function(trackerService) { return trackerService.getUser().$promise }
            }
        })
        .otherwise({
            redirectTo: '/projects'
        });
}]);

// HTTP PROVIDER =======================================================================================================
tracker.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('LoginInterceptor');
}]);

// CONTROLLERS =========================================================================================================
// LOGIN CONTROLLER ====================================================================================================
tracker.controller('LoginCtrl', ['$scope', 'trackerService', function ($scope, trackerService) {
    $scope.login = function() {
        $scope.userCreated = false;
        $scope.loginFailure = false;
        trackerService.login($scope.cred);
    };

    $scope.createUser = function(){
        $scope.loginFailure = false;
        $scope.errorCreateUser = false;
        $scope.userExist = false;
        if ($scope.cred.password != $scope.retype) {
            $scope.passNotMatch = true;
            return;
        }
        $scope.passNotMatch = false;
        trackerService.createUser($scope.cred);
    };

    $scope.$on('event:loginFailure', function () {
        $scope.loginFailure = true;
    });
    $scope.$on('event:userCreated', function () {
        $scope.userCreated = true;
        $scope.register = false;
    });
    $scope.$on('event:userExist', function () {
        $scope.userExist = true;
    });
    $scope.$on('event:errorCreateUser', function() {
        $scope.errorCreateUser = true;
    })
}]);

// PROJECT LIST CONTROLLER =============================================================================================
tracker.controller('ProjectsCtrl', ['$scope', 'projects', function ($scope, projects) {
    $scope.projects = projects;
}]);

// PROJECT DETAILS CONTROLLER ==========================================================================================
tracker.controller('ProjectCtrl', ['$scope', 'project', 'trackerService', function ($scope, project, trackerService) {
    $scope.project = project;
    $scope.createComment = trackerService.createProjectComment;
    $scope.statusStyles = trackerService.statusStyles;
    $scope.saveComment = trackerService.saveComment;
    $scope.editComment = trackerService.editComment;
    $scope.cancelEditComment = trackerService.cancelEditComment;
    $scope.removeComment = trackerService.deleteComment;
    $scope.deleteProject = trackerService.deleteProject;

    $scope.$on("event:errorSendComment", function () {
        $scope.errorSendComment = true;
    });
    $scope.$on("event:deleteProject", function () {
        $scope.sureDelete = true;
    });
}]);

// CREATE PROJECT CONTROLLER ===========================================================================================
tracker.controller('CreateProjectCtrl', ['$scope', 'trackerService', function ($scope, trackerService) {
    $scope.action = 'Create';
    $scope.cancel = trackerService.gotoProjects;
    $scope.saveProject = function (project) {
        $scope.projectExist = false;
        $scope.errorCreateProject = false;
        trackerService.createProject(project);
    };

    $scope.$on('event:projectExist', function() {
        $scope.projectExist = true;
    });
    $scope.$on('event:errorCreateProject', function () {
        $scope.errorCreateProject = true;
    });
}]);

// EDIT PROJECT CONTROLLER =============================================================================================
tracker.controller('EditProjectCtrl', ['$scope', 'project', 'trackerService', function ($scope, project, trackerService) {
    $scope.action = 'Edit';
    $scope.project = project;
    $scope.cancel = trackerService.gotoProjects;
    $scope.saveProject = function (project) {
        $scope.projectExist = false;
        $scope.errorSaveProject = false;
        trackerService.saveProject(project);
    };

    $scope.$on('event:projectExist', function() {
        $scope.projectExist = true;
    });
    $scope.$on('event:errorSaveProject', function () {
        $scope.errorSaveProject = true;
    });
}]);

// USER TASKS CONTROLLER ===============================================================================================
tracker.controller('UserTasksCtrl', ['$scope', 'tasks', 'trackerService', function ($scope, tasks, trackerService) {
    $scope.tasks = tasks;
    $scope.statusStyles = trackerService.statusStyles;
}]);

// TASK CONTROLLER =====================================================================================================
tracker.controller('TaskCtrl', ['$scope', 'task', 'trackerService', function ($scope, task, trackerService) {
    $scope.task = task;
    $scope.statuses = ['FREE', 'WORK', 'DONE'];
    $scope.createComment = trackerService.createTaskComment;
    $scope.statusStyles = trackerService.statusStyles;
    $scope.loadDevelopers = function() {
        if ($scope.developers == undefined) {
            trackerService.listDevelopers(null, function (data) {
                $scope.developers = data;
            });
        }
    };
    $scope.assignDeveloper = function(developer) {
        var taskCopy = angular.copy($scope.task);
        taskCopy.developerId = developer.id;
        taskCopy.status = 'WORK';
        trackerService.changeTask({id: taskCopy.id}, taskCopy, function () {
            $scope.task = angular.copy(taskCopy);
            $scope.task.developerName = developer.name;
        });
    };
    $scope.changeStatus = function(status) {
        var taskCopy = angular.copy($scope.task);
        taskCopy.status = status;
        if (status == 'FREE') {
            taskCopy.developerId = null;
            taskCopy.developerName = null;
        }
        trackerService.changeTask({id: taskCopy.id}, taskCopy, function () {
            $scope.task = angular.copy(taskCopy);
        });
    };
    $scope.saveComment = trackerService.saveComment;
    $scope.removeComment = trackerService.deleteComment;
    $scope.deleteTask = trackerService.deleteTask;
    $scope.editComment = trackerService.editComment;
    $scope.cancelEditComment = trackerService.cancelEditComment;

    $scope.$on("event:errorSendComment", function () {
        $scope.errorSendComment = true;
    });
    $scope.$on("event:deleteTask", function () {
        $scope.sureDelete = true;
    });
}]);

// CREATE TASK CONTROLLER ==============================================================================================
tracker.controller('CreateTaskCtrl', ['$scope', '$location', 'trackerService', '$routeParams', function ($scope, $location, trackerService, $routeParams) {
    $scope.action = 'Create';
    $scope.saveTask = function(task) {
        $scope.errorSaveTask = false;
        trackerService.createTask(task);
    };
    $scope.cancel = function () {
        $location.path('projects/' + $routeParams.id);
    };

    $scope.$on('event:errorCreateTask', function () {
        $scope.errorSaveTask = true;
    });
}]);

// EDIT TASK CONTROLLER ================================================================================================
tracker.controller('EditTaskCtrl', ['$scope', '$location', 'task', 'trackerService', function ($scope, $location, task, trackerService) {
    $scope.action = 'Edit';
    $scope.task = task;
    $scope.saveTask = function (task) {
        $scope.errorSaveTask = false;
        trackerService.saveTask(task);
    };
    $scope.cancel = function () {
        $location.path('tasks/' + task.id);
    };

    $scope.$on('event:errorSaveTask', function () {
        $scope.errorSaveTask = true;
    });
}]);

// PROFILE CONTROLLER ==================================================================================================
tracker.controller('ProfileCtrl', ['$scope', 'trackerService', function($scope, trackerService) {
}]);