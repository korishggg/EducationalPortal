import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-board-instructor',
  templateUrl: './board-instructor.component.html',
  styleUrls: ['./board-instructor.component.scss']
})
export class BoardInstructorComponent implements OnInit{

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    this.router.navigate(["instructor/groups"])
  }
}
