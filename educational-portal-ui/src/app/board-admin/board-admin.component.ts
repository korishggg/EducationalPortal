import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.scss']
})
export class BoardAdminComponent implements OnInit{

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    this.router.navigate(["admin/users"])
  }

}
