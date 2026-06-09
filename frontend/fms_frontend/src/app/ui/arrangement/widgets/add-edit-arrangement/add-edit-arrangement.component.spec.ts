import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditArrangementComponent } from './add-edit-arrangement.component';

describe('AddEditArrangementComponent', () => {
  let component: AddEditArrangementComponent;
  let fixture: ComponentFixture<AddEditArrangementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddEditArrangementComponent]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditArrangementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
