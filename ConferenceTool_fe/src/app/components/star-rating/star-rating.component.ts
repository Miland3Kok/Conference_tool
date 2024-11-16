import {Component, Input, Output, EventEmitter} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-star-rating',
  standalone: true,
  imports: [
    MatIcon,
    NgForOf
  ],
  templateUrl: './star-rating.component.html',
  styleUrl: './star-rating.component.css'
})
export class StarRatingComponent {
  @Input() rating: number = 0;
  @Output() ratingChange = new EventEmitter<number>();

  get stars(): boolean[] {
    return Array(5).fill(false).map((_, i) => this.rating > i);
  }

  onStarClick(index: number): void {
    this.rating = index + 1;
    this.ratingChange.emit(this.rating);
  }

}
