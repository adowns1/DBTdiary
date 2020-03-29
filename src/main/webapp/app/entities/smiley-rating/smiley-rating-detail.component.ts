import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISmileyRating } from 'app/shared/model/smiley-rating.model';

@Component({
  selector: 'jhi-smiley-rating-detail',
  templateUrl: './smiley-rating-detail.component.html'
})
export class SmileyRatingDetailComponent implements OnInit {
  smileyRating: ISmileyRating | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ smileyRating }) => (this.smileyRating = smileyRating));
  }

  previousState(): void {
    window.history.back();
  }
}
