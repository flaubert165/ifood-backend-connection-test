import {AlertService} from "../../services/alert.service";
import {Component, OnInit} from "@angular/core";
import {UnavailabilityScheduleService} from "../../services/unavailability.schedule.service";
import {User} from "../../models/user";
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  templateUrl: 'register.unavailability.schedule.component.html'
})

export class RegisterUnavailabilityScheduleComponent implements OnInit {
  model: any = {};
  loading = false;
  currentUser: User;
  date1: Date = new Date();
  settings = {
    bigBanner: true,
    timePicker: true,
    format: 'short',
    defaultOpen: false
  }
  date2: Date = new Date();
  settings2 = {
    bigBanner: true,
    timePicker: true,
    format: 'short',
    defaultOpen: false
  }

  constructor(
    private scheduleService: UnavailabilityScheduleService,
    private alertService: AlertService,
    private router: Router) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  register() {
    this.loading = true;
    this.model.userId = this.currentUser.id;
    this.model.start = new Date(this.model.start);
    this.model.end = new Date(this.model.end);
    this.scheduleService.create(this.model)
      .subscribe(
        data => {
          this.alertService.success('Registration successful', true);
          this.router.navigate(['/schedule']);
          this.loading = false;
        },
        error => {
          this.alertService.error(error._body);
          this.loading = false;
        });
  }


  ngOnInit(): void {

  }

  changeStartDate(){
    this.model.startTime = new Date(this.date1);

  }

  changeEndDate(){
    this.model.endTime = new Date(this.date2);
  }
}
