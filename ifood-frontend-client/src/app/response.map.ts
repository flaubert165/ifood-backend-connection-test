import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import {TranslateService} from "@ngx-translate/core";
import {Notifier, NotifierState} from "./notifier";
import {Blocker} from "./blocker";

export class ResponseMap<T> {

    private static _timeout = 100000;
    private _blocker: Blocker;

    constructor(private translate: TranslateService, private observable: Observable<Response>) {
      this._blocker = null;
    }

    public blocker(blocker: Blocker): this {
      this._blocker = blocker;
      return this;
    }

    public notifier(notifier: Notifier, isHideTitleSuccess?: boolean): Observable<T> {
        if (this._blocker) {
          this._blocker.value = true;
        }

        return this.observable.timeout(ResponseMap._timeout).map((response: Response): T => {
            notifier.clear();
            notifier.state = NotifierState.Success;
            if (!isHideTitleSuccess) {
                this.translate.get('operationSuccess').subscribe((text: string) => {
                    notifier.title = text;
                });
            }

            if (this._blocker) {
              this._blocker.value = false;
            }

            return response.text() !== '' ? response.json() : null;
        }).catch((response: Response | Error): Observable<any> => {
            if (response instanceof Response) {
              let data = response.json();
              notifier.clear();
              notifier.state = NotifierState.Error;
              notifier.title = data.message;
              if (data.hasOwnProperty('errors')) {
                for (let error in data.errors) {
                  notifier.pushMessage(data.errors[error]);
                }
              }

              if (this._blocker) {
                this._blocker.value = false;
              }

              return Observable.throw(new Error(data));
            } else {
              if (this._blocker) {
                this._blocker.value = false;
              }

              return Observable.throw(response);
            }
        });
    }

    public simple(): Observable<T> {
        if (this._blocker) {
          this._blocker.value = true;
        }

        return this.observable.timeout(ResponseMap._timeout).map((response: Response): T => {
            if (this._blocker) {
              this._blocker.value = false;
            }

            return response.text() === "" ? null : response.json();
        });
    }
}
