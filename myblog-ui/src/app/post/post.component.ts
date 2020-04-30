import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from './post.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BlogConstants } from '../constant';
import { PostPayload } from '../add-post/post-payload';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  paramLink:Number;
  post: PostPayload;
  constructor(private route:ActivatedRoute,private postService:PostService,private _snackBar: MatSnackBar,private router:Router) { 
    this.post = {
      content:'',
      name:'',
      title:'',
      username:''
    }
  }

  ngOnInit(): void {
    this.route.params.subscribe(params=>{
      this.paramLink = params['id'];
    });

    this.postService.getPost(this.paramLink).subscribe(response => {
      if (response.status == 200) {
        console.log(response);
        this.post = response.body;  
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
      this.router.navigateByUrl('/home');
    });

  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      panelClass: 'panel-class'
    });
  }

  

}
