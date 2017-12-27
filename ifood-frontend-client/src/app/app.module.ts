import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import {HomeComponent} from "./components/home.component";
import {LoginComponent} from "./components/login.component";
import {routing} from "./app.routing";
import {HttpClientModule} from "@angular/common/http";
import {HttpModule} from "@angular/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AlertService} from "./services/alert.service";
import {AuthenticationService} from "./services/authentication.service";
import {AuthGuard} from "./services/auth.guard";
import {UserService} from "./services/user.service";
import {AlertComponent} from "./components/alert.component";
import {environment} from "../environments/environment";
import {MqttModule, MqttService} from "ngx-mqtt";
import {UnavailabilityScheduleService} from "./services/unavailability.schedule.service";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {AngularDateTimePickerModule} from "angular2-datetimepicker";
import {QueriesModule} from "./components/queries/queries.module";
import {ConfectionsModule} from "./components/confections/confections.module";
import {NgIdleKeepaliveModule} from "@ng-idle/keepalive";
import {KeepAliveService} from "./services/keep.alive.service";

export function mqttServiceFactory(): MqttService {
  return new MqttService({
    hostname: environment.mqtt.hostname,
    port: environment.mqtt.port,
    path : environment.mqtt.path,
    protocol: <'wss' | 'ws'> environment.mqtt.protocol,
    keepalive: 120
  });
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    AlertComponent,
  ],
  imports: [
    QueriesModule,
    ConfectionsModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    HttpClientModule,
    routing,
    MqttModule.forRoot({
      provide: MqttService,
      useFactory: mqttServiceFactory
    }),
    NgbModule.forRoot(),
    AngularDateTimePickerModule,
    NgIdleKeepaliveModule.forRoot()
  ],
  providers: [
    UserService,
    AuthGuard,
    AuthenticationService,
    AlertService,
    UnavailabilityScheduleService,
    KeepAliveService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
