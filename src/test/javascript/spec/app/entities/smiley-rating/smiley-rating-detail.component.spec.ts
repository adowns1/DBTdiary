import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SmileyRatingDetailComponent } from 'app/entities/smiley-rating/smiley-rating-detail.component';
import { SmileyRating } from 'app/shared/model/smiley-rating.model';

describe('Component Tests', () => {
  describe('SmileyRating Management Detail Component', () => {
    let comp: SmileyRatingDetailComponent;
    let fixture: ComponentFixture<SmileyRatingDetailComponent>;
    const route = ({ data: of({ smileyRating: new SmileyRating(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [SmileyRatingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SmileyRatingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SmileyRatingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load smileyRating on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.smileyRating).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
