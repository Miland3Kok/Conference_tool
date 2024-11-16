import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TalkUpdateComponent } from './talk-update.component';

describe('TalkUpdateComponent', () => {
  let component: TalkUpdateComponent;
  let fixture: ComponentFixture<TalkUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TalkUpdateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TalkUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
