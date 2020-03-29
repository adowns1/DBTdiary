import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared/shared.module';
import { SmileyRatingComponent } from './smiley-rating.component';
import { SmileyRatingDetailComponent } from './smiley-rating-detail.component';
import { SmileyRatingUpdateComponent } from './smiley-rating-update.component';
import { SmileyRatingDeleteDialogComponent } from './smiley-rating-delete-dialog.component';
import { smileyRatingRoute } from './smiley-rating.route';

@NgModule({
  imports: [MyAppSharedModule, RouterModule.forChild(smileyRatingRoute)],
  declarations: [SmileyRatingComponent, SmileyRatingDetailComponent, SmileyRatingUpdateComponent, SmileyRatingDeleteDialogComponent],
  entryComponents: [SmileyRatingDeleteDialogComponent]
})
export class MyAppSmileyRatingModule {}
