import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {FeedbackService} from "../../services/feedback.service";
import {StarRatingComponent} from "../star-rating/star-rating.component";
import {NgIf} from "@angular/common";
import {UserToken} from "../../security-config/userToken.service";

@Component({
  selector: 'app-feedback-talk',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormField, MatInput, MatButton, MatLabel, StarRatingComponent, NgIf, MatError],
  templateUrl: './feedback-talk.component.html',
  styleUrl: './feedback-talk.component.css'
})
export class FeedbackTalkComponent implements OnChanges{
  feedbackForm: FormGroup;
  @Input() talkIdGiven: number = 0;
  userId: string='';


  ngOnChanges(changes: SimpleChanges) {
    if (changes['talkId']) {
      this.talkIdGiven = changes['talkId'].currentValue;
    }
  }


  constructor(private feedbackService: FeedbackService, userToken : UserToken) {
    this.initUserId(userToken);
    this.feedbackForm = new FormGroup({
      rating: new FormControl('', Validators.required),
      comment: new FormControl('', Validators.required),
      talkId: new FormControl(''),
      userId: new FormControl(this.userId) // TO DO: get the user id from the session
    });
  }

    async initUserId(userToken: UserToken) {
        this.userId = await userToken.getUserId();
    }


  submitFeedback(): void {
    this.feedbackForm.get('userId')?.setValue(this.userId); // TO DO: get the user id from the session
    this.feedbackForm.get('talkId')?.setValue(this.talkIdGiven);

    if (this.feedbackForm.valid) {

      const feedbackData = this.feedbackForm.value;

      this.feedbackService.createFeedback(feedbackData)
          .then(() => {
            alert('Feedback submitted successfully');
          });
    } else {
      alert('Please provide a rating and a comment before submitting the feedback.');
    }

  }
}
