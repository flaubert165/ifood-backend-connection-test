import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import {RouterModule} from "@angular/router";
import {RestaurantsQueryComponent} from "./restaurants.query.component";
import {UserService} from "../../services/user.service";
import {UnavailabilitySchedulesQueryComponent} from "./unavailability.schedules.query.component";

@NgModule({
  declarations: [
    RestaurantsQueryComponent,
    UnavailabilitySchedulesQueryComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    RestaurantsQueryComponent,
    UnavailabilitySchedulesQueryComponent
  ],
  providers: [
    UserService
  ]
})
export class QueriesModule { }
