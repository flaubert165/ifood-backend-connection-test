"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var http_1 = require("@angular/http");
var environment_1 = require("../../environments/environment");
var UnavailabilityScheduleService = (function () {
    function UnavailabilityScheduleService(http, mqttService) {
        this.http = http;
        this.mqttService = mqttService;
    }
    UnavailabilityScheduleService.prototype.getAll = function () {
        return this.http.get(environment_1.environment.serverUrl + '/scheduling', this.jwt()).map(function (response) { return response.json(); });
    };
    UnavailabilityScheduleService.prototype.getById = function (id) {
        return this.http.get(environment_1.environment.serverUrl + '/scheduling/' + id, this.jwt()).map(function (response) { return response.json(); });
    };
    UnavailabilityScheduleService.prototype.getByUserId = function (userId) {
        return this.http.get(environment_1.environment.serverUrl + '/scheduling/' + userId.toString(), this.jwt())
            .map(function (response) { return response.json(); });
    };
    UnavailabilityScheduleService.prototype.create = function (obj) {
        return this.http.post(environment_1.environment.serverUrl + '/scheduling/register', obj, this.jwt());
    };
    UnavailabilityScheduleService.prototype.update = function (obj) {
        return this.http.put(environment_1.environment.serverUrl + '/scheduling/' + obj.id, obj, this.jwt());
    };
    UnavailabilityScheduleService.prototype.delete = function (id) {
        return this.http.delete(environment_1.environment.serverUrl + '/scheduling/' + id.toString(), this.jwt());
    };
    // private helper methods
    UnavailabilityScheduleService.prototype.jwt = function () {
        // create authorization header with jwt token
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.token) {
            var headers = new http_1.Headers({ 'Authorization': 'Bearer ' + currentUser.token });
            return new http_1.RequestOptions({ headers: headers });
        }
    };
    UnavailabilityScheduleService = __decorate([
        core_1.Injectable()
    ], UnavailabilityScheduleService);
    return UnavailabilityScheduleService;
}());
exports.UnavailabilityScheduleService = UnavailabilityScheduleService;
