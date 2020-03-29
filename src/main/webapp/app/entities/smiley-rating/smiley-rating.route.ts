import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISmileyRating, SmileyRating } from 'app/shared/model/smiley-rating.model';
import { SmileyRatingService } from './smiley-rating.service';
import { SmileyRatingComponent } from './smiley-rating.component';
import { SmileyRatingDetailComponent } from './smiley-rating-detail.component';
import { SmileyRatingUpdateComponent } from './smiley-rating-update.component';

@Injectable({ providedIn: 'root' })
export class SmileyRatingResolve implements Resolve<ISmileyRating> {
  constructor(private service: SmileyRatingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISmileyRating> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((smileyRating: HttpResponse<SmileyRating>) => {
          if (smileyRating.body) {
            return of(smileyRating.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SmileyRating());
  }
}

export const smileyRatingRoute: Routes = [
  {
    path: '',
    component: SmileyRatingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'SmileyRatings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SmileyRatingDetailComponent,
    resolve: {
      smileyRating: SmileyRatingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SmileyRatings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SmileyRatingUpdateComponent,
    resolve: {
      smileyRating: SmileyRatingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SmileyRatings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SmileyRatingUpdateComponent,
    resolve: {
      smileyRating: SmileyRatingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SmileyRatings'
    },
    canActivate: [UserRouteAccessService]
  }
];
