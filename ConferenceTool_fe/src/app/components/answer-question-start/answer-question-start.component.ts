import { Component } from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";

@Component({
  selector: 'app-answer-question-start',
  standalone: true,
    imports: [
        SidenavComponent
    ],
  templateUrl: './answer-question-start.component.html',
  styleUrl: './answer-question-start.component.css'
})
export class AnswerQuestionStartComponent {

}
