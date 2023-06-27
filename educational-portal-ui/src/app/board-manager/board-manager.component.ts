import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-board-manager',
  templateUrl: './board-manager.component.html',
  styleUrls: ['./board-manager.component.scss']
})
export class BoardManagerComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    // TODO LATER IMPLEMENT AUTO REDIRECT TO USERS CONTROL COMPONENT
    // this.router.navigate(["manager/users"])
  }
  isRouteActive(routeSegment: string): boolean {
    return this.router.url.includes(`/${routeSegment}`);
  }
}
