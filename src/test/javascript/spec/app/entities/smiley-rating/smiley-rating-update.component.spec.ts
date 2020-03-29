import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SmileyRatingUpdateComponent } from 'app/entities/smiley-rating/smiley-rating-update.component';
import { SmileyRatingService } from 'app/entities/smiley-rating/smiley-rating.service';
import { SmileyRating } from 'app/shared/model/smiley-rating.model';

describe('Component Tests', () => {
  describe('SmileyRating Management Update Component', () => {
    let comp: SmileyRatingUpdateComponent;
    let fixture: ComponentFixture<SmileyRatingUpdateComponent>;
    let service: SmileyRatingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [SmileyRatingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SmileyRatingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SmileyRatingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SmileyRatingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SmileyRating(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SmileyRating();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
