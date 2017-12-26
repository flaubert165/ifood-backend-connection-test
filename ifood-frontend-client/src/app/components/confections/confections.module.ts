import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { routing } from "../../app.routing";
import {RegisterUnavailabilityScheduleComponent} from "./register.unavailability.schedule.component";
import {UnavailabilityScheduleService} from "../../services/unavailability.schedule.service";
import {RegisterRestaurantComponent} from "./register.restaurant.component";
import {UserService} from "../../services/user.service";


@NgModule({
  declarations: [
    RegisterUnavailabilityScheduleComponent,
    RegisterRestaurantComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    routing
  ],
  exports: [
    RegisterUnavailabilityScheduleComponent,
    RegisterRestaurantComponent
  ],
  providers: [
    UnavailabilityScheduleService,
    UserService
  ]
})
export class ConfectionsModule { }
