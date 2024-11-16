import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TalksSpeakerComponent } from './talks-speaker.component';

describe('TalksSpeakerComponent', () => {
  let component: TalksSpeakerComponent;
  let fixture: ComponentFixture<TalksSpeakerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TalksSpeakerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TalksSpeakerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
