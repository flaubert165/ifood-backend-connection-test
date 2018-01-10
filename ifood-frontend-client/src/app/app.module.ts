import { BrowserModule } from '@angular/platform-browser';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import {HomeComponent} from "./components/home.component";
import {LoginComponent} from "./components/login.component";
import {routing} from "./app.routing";
import {HttpClient, HttpClientModule} from "@angular/common/http";
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
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {TranslateLoader, TranslateModule, TranslateService} from "@ngx-translate/core";
import {SourceService} from "./services/source.service";
import {RestService} from "./services/rest.service";
import {Notifier} from "./notifier";
import {Blocker} from "./blocker";
import {InfiniteScrollModule} from "ngx-infinite-scroll";

export function translateHttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http, `${document.getElementsByTagName('base')[0].href}assets/i18n/`);
}

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
    InfiniteScrollModule,
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
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: translateHttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    NgbModule.forRoot(),
    AngularDateTimePickerModule,
    NgIdleKeepaliveModule.forRoot()
  ],
  providers: [
    Blocker,
    Notifier,
    RestService,
    UserService,
    SourceService,
    AuthGuard,
    AuthenticationService,
    AlertService,
    UnavailabilityScheduleService,
    KeepAliveService
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule {
  constructor(private translate: TranslateService) {
    translate.addLangs(["en", "pt"]);
    translate.setDefaultLang("pt");
    let browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/en|pt/) ? browserLang : "en");
  }
}

platformBrowserDynamic().bootstrapModule(AppModule);
