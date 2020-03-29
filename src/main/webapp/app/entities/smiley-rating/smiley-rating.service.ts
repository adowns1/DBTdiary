import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISmileyRating } from 'app/shared/model/smiley-rating.model';

type EntityResponseType = HttpResponse<ISmileyRating>;
type EntityArrayResponseType = HttpResponse<ISmileyRating[]>;

@Injectable({ providedIn: 'root' })
export class SmileyRatingService {
  public resourceUrl = SERVER_API_URL + 'api/smiley-ratings';

  constructor(protected http: HttpClient) {}

  create(smileyRating: ISmileyRating): Observable<EntityResponseType> {
    return this.http.post<ISmileyRating>(this.resourceUrl, smileyRating, { observe: 'response' });
  }

  update(smileyRating: ISmileyRating): Observable<EntityResponseType> {
    return this.http.put<ISmileyRating>(this.resourceUrl, smileyRating, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISmileyRating>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISmileyRating[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
