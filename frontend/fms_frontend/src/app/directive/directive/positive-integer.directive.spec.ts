import { PositiveIntegerDirective } from './positive-integer.directive';
import { ElementRef } from '@angular/core';

describe('PositiveIntegerDirective', () => {
  it('should create an instance', () => {
    const mockElementRef = {
      nativeElement: document.createElement('input')
    } as ElementRef;

    const directive = new PositiveIntegerDirective(mockElementRef);

    expect(directive).toBeTruthy();
  });
});
