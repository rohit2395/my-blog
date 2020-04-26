import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {

  constructor(private authService : AuthService,private router:Router) { }

  ngOnInit(): void {
    
    if(!this.authService.isAuthenticated())
      this.router.navigateByUrl('/login');
  }

}
