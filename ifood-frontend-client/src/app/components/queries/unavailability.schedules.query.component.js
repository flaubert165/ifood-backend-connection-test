"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var UnavailabilitySchedulesQueryComponent = (function () {
    function UnavailabilitySchedulesQueryComponent(scheduleService, mqttService) {
        this.scheduleService = scheduleService;
        this.mqttService = mqttService;
        this.schedules = [];
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }
    UnavailabilitySchedulesQueryComponent.prototype.ngOnInit = function () {
        this.loadAllSchedules();
    };
    UnavailabilitySchedulesQueryComponent.prototype.delete = function (_id) {
        var _this = this;
        this.scheduleService.delete(_id).subscribe(function () { _this.loadAllSchedules(); });
    };
    UnavailabilitySchedulesQueryComponent.prototype.loadAllSchedules = function () {
        var _this = this;
        this.scheduleService.getByUserId(this.currentUser.id).subscribe(function (schedules) { _this.schedules = schedules; });
        /**
         * Update the lastRequest for currentUser
         */
        this.mqttService.unsafePublish('restaurants/updateLasRequest/', this.currentUser.id.toString(), { qos: 1, retain: true });
    };
    UnavailabilitySchedulesQueryComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            templateUrl: 'unavailability.schedules.query.component.html'
        })
    ], UnavailabilitySchedulesQueryComponent);
    return UnavailabilitySchedulesQueryComponent;
}());
exports.UnavailabilitySchedulesQueryComponent = UnavailabilitySchedulesQueryComponent;
