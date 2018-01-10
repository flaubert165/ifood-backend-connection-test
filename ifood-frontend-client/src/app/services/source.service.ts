import {Injectable} from "@angular/core";
import {RestService} from "./rest.service";
import {QuerySource} from "../sources/query.source";
import {Notifier} from "../notifier";


@Injectable()
export class SourceService {
  constructor(private restService: RestService, private notifier: Notifier) {
  }

  public createQuery(url: string): QuerySource {
    return new QuerySource(this.restService, url, this.notifier);
  }
}
