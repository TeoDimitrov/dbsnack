import {Component} from "@angular/core";

@Component({
  selector: 'pb',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.css']
})
export class ProgressBarComponent {

  static showProgressBar(): void {
    document.getElementById('progress-bar').style.visibility = 'visible';
  }

  static hideProgressBar(): void {
    document.getElementById('progress-bar').style.visibility = 'hidden';
  }
}
