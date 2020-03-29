import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISmileyRating } from 'app/shared/model/smiley-rating.model';
import { SmileyRatingService } from './smiley-rating.service';

@Component({
  templateUrl: './smiley-rating-delete-dialog.component.html'
})
export class SmileyRatingDeleteDialogComponent {
  smileyRating?: ISmileyRating;

  constructor(
    protected smileyRatingService: SmileyRatingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.smileyRatingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('smileyRatingListModification');
      this.activeModal.close();
    });
  }
}
