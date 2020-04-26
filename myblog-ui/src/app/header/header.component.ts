import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService : AuthService,private router:Router){
    
  }

  ngOnInit(): void {
  }

  logout():void{
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }

  isAuthenticatedUser():Boolean{
    return this.authService.isAuthenticated();
  }
}

