import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TalkCreateComponent } from './talk-create.component';

describe('TalkCreateComponent', () => {
  let component: TalkCreateComponent;
  let fixture: ComponentFixture<TalkCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TalkCreateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TalkCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
