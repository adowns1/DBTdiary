import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'smiley-rating',
        loadChildren: () => import('./smiley-rating/smiley-rating.module').then(m => m.MyAppSmileyRatingModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MyAppEntityModule {}
