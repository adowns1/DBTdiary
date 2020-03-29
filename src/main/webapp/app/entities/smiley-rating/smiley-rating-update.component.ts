import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISmileyRating, SmileyRating } from 'app/shared/model/smiley-rating.model';
import { SmileyRatingService } from './smiley-rating.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-smiley-rating-update',
  templateUrl: './smiley-rating-update.component.html'
})
export class SmileyRatingUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    rating: [],
    user: []
  });

  constructor(
    protected smileyRatingService: SmileyRatingService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ smileyRating }) => {
      this.updateForm(smileyRating);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(smileyRating: ISmileyRating): void {
    this.editForm.patchValue({
      id: smileyRating.id,
      rating: smileyRating.rating,
      user: smileyRating.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const smileyRating = this.createFromForm();
    if (smileyRating.id !== undefined) {
      this.subscribeToSaveResponse(this.smileyRatingService.update(smileyRating));
    } else {
      this.subscribeToSaveResponse(this.smileyRatingService.create(smileyRating));
    }
  }

  private createFromForm(): ISmileyRating {
    return {
      ...new SmileyRating(),
      id: this.editForm.get(['id'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISmileyRating>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
