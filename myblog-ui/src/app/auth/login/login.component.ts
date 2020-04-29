import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from '../auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginPayload } from './login-payload';
import { Router} from '@angular/router';
import * as jwt_decode from 'jwt-decode';
import { BlogConstants } from 'src/app/constant';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  loginPayload: LoginPayload;

  constructor(private formBuilder: FormBuilder, 
    private authService: AuthService, 
    private _snackBar: MatSnackBar,
    private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: 'rohit',
      password: 'rohit'
    });

    this.loginPayload = {
      username: '',
      password: ''
    };
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      panelClass:'panel-class'
    });
  }

  ngOnInit(): void {
    if(this.authService.isAuthenticated())
      this.router.navigateByUrl('/home');
  }

  onSubmit(): void {
    this.loginPayload.username = this.loginForm.get('username').value;
    this.loginPayload.password = this.loginForm.get('password').value;
    
    this.authService.login(this.loginPayload).subscribe(response => {
      console.log(response);
      
      response.headers.keys();

      var token = response.headers.headers.get('auth-token')[0];
      console.log('Storing data from token in local storage');
      localStorage.setItem('auth-token',token);
      localStorage.setItem('username',this.loginPayload.username+'');
      var rolesArray = jwt_decode(token).role;
      var rolesString = '';
      for (let i = 0; i < rolesArray.length; i++) {
        rolesString += rolesArray[i].authority + ",";
      }
      if(rolesString != ''){
        rolesString = rolesString.slice(0,-1);
      }
      localStorage.setItem('user-role',rolesString);
      this.authService.roles = rolesString;
      this.router.navigateByUrl('/home');
      // this.dialogRef.close();
    },error=>{
      console.log(error);
      if(error.status == 401){
        this.openSnackBar(error.error.message, 'Close');
      }else{
        this.openSnackBar(BlogConstants.SOMETHING_WENT_WRONG, 'Close');
      }
    });
  }
}

// user this for pop up
// @Component({
//   template: ''
// })
// export class LoginDialogEntryComponent {
//   constructor(public dialog: MatDialog, private router: Router,
//     private route: ActivatedRoute) {
//     this.openDialog();
//   }
//   openDialog(): void {
//     const dialogRef = this.dialog.open(LoginComponent, {
//       width: '500px',
//     });
//     dialogRef.afterClosed().subscribe(result => {
//       console.log("closed!!!!!!!!!!");
      
//       this.router.navigate(['../'], { relativeTo: this.route });
//     });
//   }
// }
