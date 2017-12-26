import { OnInit } from "@angular/core/src/metadata/lifecycle_hooks";
import { Component } from "@angular/core";
import { UnavailabilityScheduleService } from "../../services/unavailability.schedule.service";
import { User } from "../../models/user";

@Component({
  moduleId: module.id,
  templateUrl: 'unavailability.schedules.query.component.html'
})

export class UnavailabilitySchedulesQueryComponent implements OnInit {
  currentUser: User;
  schedules: any[] = [];

  constructor(private scheduleService: UnavailabilityScheduleService) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit(): void {
    this.loadAllSchedules();
  }

  delete(_id: number) {
    this.scheduleService.delete(_id).subscribe(() => { this.loadAllSchedules(); });
  }

  private loadAllSchedules() {
    this.scheduleService.getByUserId(this.currentUser.id).subscribe(schedules => { this.schedules = schedules; });
    console.log(this.schedules.length);
  }
}
