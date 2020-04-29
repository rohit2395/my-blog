import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { HomeService } from './home.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BlogConstants } from '../constant';
import { Observable } from 'rxjs';
import { PostPayload } from '../add-post/post-payload';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  posts: Observable<Array<PostPayload>>;

  constructor(private authService: AuthService, private homeService: HomeService, private router: Router, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {

    if (!this.authService.isAuthenticated())
      this.router.navigateByUrl('/login');

    this.getAllPosts();
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      panelClass: 'panel-class'
    });
  }

  getAllPosts(): void {
    this.homeService.getAllPosts().subscribe(response => {
      if (response.status == 200) {
        this.posts = response.body;
        console.log(this.posts);  
      } else {
        console.log(response);
        console.error('Failed to get all the posts');
      }
    }, error => {
      console.log(error);
      if(error.status == 500){
        this.openSnackBar(error.error.message, 'Close');
      }else{
        this.openSnackBar(BlogConstants.SOMETHING_WENT_WRONG, 'Close');
      }
    })
  }

}
