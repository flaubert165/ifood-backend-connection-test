
export class Blocker {
  private _value: boolean;

  constructor() {
    this._value = false;
  }

  get value(): boolean {
    return this._value;
  }

  set value(value: boolean) {
    this._value = value;
  }
}
