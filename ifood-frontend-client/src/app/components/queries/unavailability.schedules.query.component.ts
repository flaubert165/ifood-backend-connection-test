import { OnInit } from "@angular/core/src/metadata/lifecycle_hooks";
import { Component } from "@angular/core";
import { UnavailabilityScheduleService } from "../../services/unavailability.schedule.service";
import { User } from "../../models/user";
import {MqttService} from "ngx-mqtt";

@Component({
  moduleId: module.id,
  templateUrl: 'unavailability.schedules.query.component.html'
})

export class UnavailabilitySchedulesQueryComponent implements OnInit {
  currentUser: User;
  schedules: any[] = [];

  constructor(private scheduleService: UnavailabilityScheduleService, private mqttService: MqttService) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit(): void {
    this.loadAllSchedules();
  }

  delete(_id: number) {
    this.scheduleService.delete(_id).subscribe(() => { this.loadAllSchedules(); });
    /**
     * Update the lastRequest for currentUser
     */
    this.scheduleService.updateLastRequest(this.currentUser.id);
  }

  private loadAllSchedules() {
    this.scheduleService.getByUserId(this.currentUser.id).subscribe(schedules => { this.schedules = schedules; });
    /**
     * Update the lastRequest for currentUser
     */
    this.scheduleService.updateLastRequest(this.currentUser.id);
  }
}
