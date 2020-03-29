import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISmileyRating } from 'app/shared/model/smiley-rating.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SmileyRatingService } from './smiley-rating.service';
import { SmileyRatingDeleteDialogComponent } from './smiley-rating-delete-dialog.component';

@Component({
  selector: 'jhi-smiley-rating',
  templateUrl: './smiley-rating.component.html'
})
export class SmileyRatingComponent implements OnInit, OnDestroy {
  smileyRatings?: ISmileyRating[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected smileyRatingService: SmileyRatingService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.smileyRatingService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISmileyRating[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInSmileyRatings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISmileyRating): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSmileyRatings(): void {
    this.eventSubscriber = this.eventManager.subscribe('smileyRatingListModification', () => this.loadPage());
  }

  delete(smileyRating: ISmileyRating): void {
    const modalRef = this.modalService.open(SmileyRatingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.smileyRating = smileyRating;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISmileyRating[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/smiley-rating'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.smileyRatings = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
