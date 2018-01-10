import { Subscription } from 'rxjs';
import {RestService} from "../services/rest.service";
import {Notifier} from "../notifier";
import {Blocker} from "../blocker";
import {Subject} from "rxjs/Subject";

export class QuerySource {

  private _filterSubject: Subject<void>;

  private _postData: any;
  private _subscription: Subscription;
  private _blocker: Blocker;
  private _data: any[];
  private _isReverse: boolean;
  private _start: number;
  private _amount: number;
  private _untilEnd: boolean;
  private _itemCallBack: (item: any) => void;

  constructor(private restService: RestService, private url: string, private _notifier: Notifier) {
    this._filterSubject = new Subject<void>();
    this._subscription = null;
    this._data = [];
    this._untilEnd = false;
    this._start = 10;
    this._amount = 3;
    this._isReverse = false;
    this._postData = null;
  }

  protected get postData(): any {
    return this._postData;
  }

  protected set postData(value: any) {
    this._postData = value;
  }

  get filterSubject(): Subject<void> {
    return this._filterSubject;
  }

  get data(): any[] {
    return this._data;
  }

  public notifier(value: Notifier): this {
    this._notifier = value;
    return this;
  }

  public blocker(value: Blocker): this {
    this._blocker = value;
    return this;
  }

  public reverse(value: boolean): this {
    this._isReverse = value;
    return this;
  }

  public start(value: number): this {
    this._start = value;
    return this;
  }

  public amount(value: number): this {
    this._amount = value;
    return this;
  }

  public untilEnd(value: boolean): this {
    this._untilEnd = value;
    return this;
  }

  public itemCallBack(value: (item: any) => void): this {
    this._itemCallBack = value;
    return this;
  }

  public filterWithData(item: any): void {
    this._postData = item;
    this.doFilter();
  }

  public filter(): void {
    this._postData = {};
    this.doFilter();
  }

  private doFilter(): void {
    if (this._subscription && !this._subscription.closed) {
      this._subscription.unsubscribe();
    }
    this._subscription = null;
    this._data.length = 0;


    this.doPost(this._start);
  }

  public reset(): void {
    this.filter();
  }

  public load(amount?: number): void {
    if (this._subscription && !this._subscription.closed) {
      this._subscription.add(() => this.doPost(amount));
    }
    else {
      this.doPost(amount);
    }
  }

  private doPost(amount?: number): void {
    let index = this._data.length;
    let result = this.restService.post<any>(this.url, Object.assign({
      index: index,
      limit: index + (amount || this._amount)
    }, this._postData)).blocker(this._blocker).notifier(this._notifier, true).subscribe((value: any[]): void => {
      value.forEach((item: any): void => {
        if (this._isReverse) {
          this._data.unshift(item);
        }
        else {
          this._data.push(item);
        }

        if(this._itemCallBack) {
          this._itemCallBack(item);
        }
      });
      if(this._untilEnd) {
        if(value.length > 0) {
          result.add(() => this.doPost(amount));
        }
        else {
          this.done();
        }
      }
      else if(this._subscription === result) {
        this.done();
      }
      this._filterSubject.next();
    });
    this._subscription = result;
  }

  private done() {
    this._filterSubject.next();
  }
}
