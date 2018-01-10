import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import {RouterModule} from "@angular/router";
import {RestaurantsQueryComponent} from "./restaurants.query.component";
import {UserService} from "../../services/user.service";
import {UnavailabilitySchedulesQueryComponent} from "./unavailability.schedules.query.component";
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {TranslateModule} from "@ngx-translate/core";
import {BrowserModule} from "@angular/platform-browser";
import {RestService} from "../../services/rest.service";
import {SourceService} from "../../services/source.service";

@NgModule({
  declarations: [
    RestaurantsQueryComponent,
    UnavailabilitySchedulesQueryComponent
  ],
  imports: [
    BrowserModule,
    InfiniteScrollModule,
    CommonModule,
    TranslateModule,
    RouterModule
  ],
  exports: [
    RestaurantsQueryComponent,
    UnavailabilitySchedulesQueryComponent
  ],
  providers: [
    UserService,
    RestService,
    SourceService
  ]
})
export class QueriesModule { }
