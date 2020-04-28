import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';
import { PostPayload } from './post-payload';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AddPostService } from './add-post.service';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {

  addPostForm: FormGroup;
  
  title = new FormControl('');
  body = new FormControl('');

  private editor: { conf: { }; };

  postPayload: PostPayload;
  
  constructor(private addPostService : AddPostService,private authService : AuthService,private router:Router,private _snackBar: MatSnackBar) {
    this.addPostForm = new FormGroup({
      title: this.title,
      body: this.body
    });

    this.postPayload = {
      username:'',
      title:'',
      content:''
    }
   }

  ngOnInit(): void {
    
    if(!this.authService.isAuthenticated())
      this.router.navigateByUrl('/login');
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      panelClass:'panel-class'
    });
  }

  addPost(): void{

    this.postPayload.title = this.addPostForm.get('title').value;
    this.postPayload.content = this.addPostForm.get('body').value;
    console.log(this.postPayload);
    
    this.addPostService.addPost(this.postPayload).subscribe(data => {
      console.log(data);
      if(data.status == '201'){
        this.openSnackBar(data.message,'Close');
        this.router.navigateByUrl('/home');
        // this.dialogRef.close();
      }
      else
        this.openSnackBar('User registration failed','Close');
    }, error => {
      console.log(error);
      this.openSnackBar(error.error.message,'Close');
    });
  }

  getEditorConfiguration():Object{
    return { 
      plugins: 'link',
      height : "300",
      resize: false,
     };
  }

}
