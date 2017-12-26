import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from '../app/components/home.component';
import { LoginComponent } from '../app/components/login.component';
import { AuthGuard } from '../app/services/auth.guard';
import {RestaurantsQueryComponent} from "./components/queries/restaurants.query.component";
import {RegisterUnavailabilityScheduleComponent} from "./components/confections/register.unavailability.schedule.component";
import {RegisterRestaurantComponent} from "./components/confections/register.restaurant.component";
import {UnavailabilitySchedulesQueryComponent} from "./components/queries/unavailability.schedules.query.component";

const appRoutes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterRestaurantComponent },
  { path: 'restaurants', component: RestaurantsQueryComponent },
  { path: 'schedule', component: UnavailabilitySchedulesQueryComponent },
  { path: 'schedule/register', component: RegisterUnavailabilityScheduleComponent },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
